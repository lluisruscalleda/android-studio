package com.thesocialcoin.controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.thesocialcoin.App;
import com.thesocialcoin.activities.LoginActivity;
import com.thesocialcoin.events.AuthenticateUserEvent;
import com.thesocialcoin.models.pojos.APILoginResponse;
import com.thesocialcoin.models.pojos.Logout;
import com.thesocialcoin.models.pojos.User;
import com.thesocialcoin.models.shared_preferences.SessionData;
import com.thesocialcoin.networking.core.RequestManager;
import com.thesocialcoin.networking.error.AuthenticateUserVolleyError;
import com.thesocialcoin.networking.error.LoginRequestFailed;
import com.thesocialcoin.networking.error.RegisterRequestFailed;
import com.thesocialcoin.networking.ottovolley.messages.VolleyRequestSuccess;
import com.thesocialcoin.requests.FacebookLoginRequest;
import com.thesocialcoin.requests.GoogleLoginRequest;
import com.thesocialcoin.requests.LinkedinLoginRequest;
import com.thesocialcoin.requests.LoginRequest;
import com.thesocialcoin.requests.RegisterRequest;
import com.thesocialcoin.utils.Codes;
import com.thesocialcoin.utils.Utils;

import java.util.HashMap;

/**
 * Created by lluisruscalleda on 20/11/14.
 */
public class AccountManager extends BaseManager {

    public static final String PARAM_AUTHTOKEN_TYPE = "auth.token";
    public static final String PARAM_AUTHTOKEN = "access_token";
    public static final String PARAM_LANGUAGE = "language";


    public enum LoginType {
        NONE, LINKEDIN, FACEBOOK, GOOGLE

    };

    private static String TAG = AccountManager.class.getSimpleName();
    private static AccountManager instance = null;


    /*
    * Interfaces
    */
    public interface OnLoginResponseListener {
        public void onLoginSucceed(APILoginResponse response);
        public void onLoginFailed(String error);
    }
    public interface OnRegisterResponseListener {
        public void onRegisterSucceed(APILoginResponse response);
        public void onRegisterFailed(String error);
    }

    private AccountManager(Context context) {
        super();
    }


    public static AccountManager getInstance(Context context) {
        if (instance == null) {
            mContext = context;
            instance = new AccountManager(mContext);
        }

        return instance;
    }

    /**
     * Authenticate the user
     *
     * @param username
     *            Username for the account
     * @param password
     *            Password for the account
     */
    public void authenticate(String username, String password)
    {
        postEvent(produceUserSignInStartEvent());

        HashMap<String,String> loginParams = new HashMap<String,String>();
        loginParams.put("user_name",username);
        loginParams.put("password", password);

        RequestManager.addToRequestQueue(new LoginRequest().create(loginParams));
    }

    /**
     * Register the user
     *
     * @param email
     *            Username for the account
     * @param password
     *            Password for the account
     */
    public void register(String email, String password)
    {
        postEvent(produceUserSignInStartEvent());

        HashMap<String,String> params = new HashMap<String,String>();
        params.put(Codes.EMAIL_VALUE,email);
        params.put(Codes.PASSWORD_VALUE, password);

        RequestManager.addToRequestQueue(new RegisterRequest().create(params));
    }


    /**
     * Authenticate the user using google plus credentials
     *
     * @param token
     *            token account
     * @param OnRegisterResponseListener
     *            listener
     *
     */
    public void authenticateWithGoogle(String token, final OnRegisterResponseListener listener)
    {
        postEvent(produceUserSignInStartEvent());

        HashMap<String,String> requestJson = new HashMap<String,String> ();
        requestJson.put(PARAM_AUTHTOKEN, token);
        requestJson.put(PARAM_LANGUAGE, Utils.getAppLanguage());

        RequestManager.addToRequestQueue(new GoogleLoginRequest().create(requestJson));
    }
    /**
     * Authenticate the user using facebook credentials
     *
     * @param token
     *            token account
     * @param OnRegisterResponseListener
     *            listener
     *
     */
    public void authenticateWithFacebook(String token, final OnRegisterResponseListener listener)
    {
        postEvent(produceUserSignInStartEvent());

        HashMap<String,String> requestJson = new HashMap<String,String> ();
        requestJson.put(PARAM_AUTHTOKEN, token);
        requestJson.put(PARAM_LANGUAGE, Utils.getAppLanguage());

        RequestManager.addToRequestQueue(new LinkedinLoginRequest().create(requestJson));
    }

    /**
     * Authenticate the user using facebook credentials
     *
     * @param token
     *            token account
     * @param OnRegisterResponseListener
     *            listener
     *
     */
    public void authenticateWithLinkedin(String token, final OnRegisterResponseListener listener)
    {
        postEvent(produceUserSignInStartEvent());

        HashMap<String,String> requestJson = new HashMap<String,String> ();
        requestJson.put(PARAM_AUTHTOKEN, token);
        requestJson.put(PARAM_LANGUAGE, Utils.getAppLanguage());

        RequestManager.addToRequestQueue(new FacebookLoginRequest().create(requestJson));
    }


    /**
     *
     * Login request error response received subscription
     */
    @Subscribe
    public void onHttpErrorResponseReceived(LoginRequestFailed requestError)
    {
        Log.d(TAG, "Request end with error: " + requestError.requestId);
        Log.d(TAG, "Volley error : " + requestError.error.toString());
        Log.d(TAG, "Volley error : " + requestError.error.getMessage());

        // post login failed event
        postEvent(produceUserSignInErrorEvent(new AuthenticateUserVolleyError(requestError.error)));
    }

    /**
     *
     * Login request success response received subscription
     */
    @Subscribe
    public void onLoginResponseReceived(VolleyRequestSuccess<APILoginResponse> response)
    {
        Log.d(TAG, "Request end: " + response.requestId);
        if (response.response instanceof APILoginResponse)
        {
            // Save user data to session
            saveUserSession(response.response.getToken(), response.response.getUser());

            // post login success event
            postEvent(produceUserSignInSuccessEvent());

            // post login success actions
            AppManager.getInstance(mContext).postLoginActions();
        }
    }

    @Subscribe
    public void onHttpErrorResponseReceived(RegisterRequestFailed requestError)
    {
        Log.d(TAG, "Request end with error: " + requestError.requestId);
        Log.d(TAG, "Volley error : " + requestError.error.toString());
        Log.d(TAG, "Volley error : " + requestError.error.getMessage());

        // post register failed event
        postEvent(produceUserSignInErrorEvent(new AuthenticateUserVolleyError(requestError.error)));
    }

    /**
     *
     * User data response received
     */
    @Subscribe
    public void onUserDataResponseReceived(VolleyRequestSuccess<User> response)
    {
        if (response.response instanceof User)
        {
            sessionData.setUserEmail(response.response.getEmail());
            sessionData.setUserUsername(response.response.getUsername());

            sessionData.apply();

            // post succes event
            postEvent(produceUserSignInSuccessEvent());
        }
    }

    /**
     * Do logout method will perform logout actions on the app
     * */
    public void doLogout()
    {
        // Stop App services
        AppManager.getInstance(mContext).stopAppServices();


        // Clear all session data
        removeUserSession();
        sessionData.clearEditor();
    }

    /**
     *
     * Logout API response received subscription (if endpoint exists)
     */
    @Subscribe
    public void onLogoutResponseReceived(VolleyRequestSuccess<Logout> response)
    {
        Log.d(TAG, "Request end: " + response.requestId);
        if (response.response instanceof Logout)
        {

            //RequestManager.EventBus.post(new FeedEvent(FeedEvent.Type.COMPLETED));
            SessionData sessionData = new SessionData(App.getAppContext());
            sessionData.setLoggedIn(false);
            sessionData.setTimeToLive(null);
            sessionData.setUserId(null);
            sessionData.setSessionToken(null);
            sessionData.setAuthenticationTokenCreationDate(null);
            sessionData.apply();

            Intent mIntent = new Intent(App.getAppContext(), LoginActivity.class);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            App.getAppContext().startActivity(mIntent);
        }
    }

    /**
     * Creates an event notifying that authentication has begun
     *
     * @return
     */
    public AuthenticateUserEvent produceUserSignInStartEvent()
    {
        return new AuthenticateUserEvent(AuthenticateUserEvent.Type.START);
    }

    /**
     * Creates an event containing the signed in user
     *
     *            User currently signed in
     * @return
     */
    public AuthenticateUserEvent produceUserSignInSuccessEvent()
    {
        return new AuthenticateUserEvent(AuthenticateUserEvent.Type.SUCCESS);
    }

    /**
     * Creates an even for sign in errors
     *
     * @return
     */
    public AuthenticateUserEvent produceUserSignInErrorEvent(AuthenticateUserVolleyError error)
    {
        return AuthenticateUserEvent.AuthenticateUserEventWithError(AuthenticateUserEvent.Type.ERROR, error);
    }

    /**
     * Creates an event notifying that authentication has begun
     *
     * @return
     */
    public AuthenticateUserEvent produceUserSignOutStartEvent()
    {
        return new AuthenticateUserEvent(AuthenticateUserEvent.Type.LOGOUT_START);
    }

    /**
     * Creates an event containing the signed in user
     *
     * @param user
     *            User currently signed in
     * @return
     */
    public AuthenticateUserEvent produceUserSignOutSuccessEvent(APILoginResponse user)
    {
        return AuthenticateUserEvent.AuthenticateUserEventWithUserData(AuthenticateUserEvent.Type.LOGOUT_SUCCESS, user);
    }

    /**
     * Creates an even for sign in errors
     *
     * @param error
     *            AuthenticateUserError
     *
     * @return
     */
    public AuthenticateUserEvent produceUserSignOutErrorEvent(AuthenticateUserVolleyError error)
    {
        return AuthenticateUserEvent.AuthenticateUserEventWithError(AuthenticateUserEvent.Type.LOGOUT_ERROR, error);
    }



    /**
     * Some Session helper functions
     */

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public static void checkLogin(){
        // Check login status
        if(!isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(mContext, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            mContext.startActivity(i);
        }
    }

    /**
     * Save user session method will save api session token and user data
     * */
    public static void saveUserSession(String apiToken, User userData) {
        sessionData.setSessionToken(apiToken);
        sessionData.setUserData(userData.serialize());
        sessionData.apply();
    }

    /**
     * Remove user session method will remove api session token and user data from de shared preferences
     * */
    public static void removeUserSession(){
        sessionData.setSessionToken(null);
        sessionData.setUserData(null);
        sessionData.apply();
    }

    /**
     * Get user session method will retrieve the api token saved in shared preferences
     * */
    public static String getUserSession(){
        return sessionData.getSessionToken();
    }

    public static void setGoogleAccountName(String accountName){
        sessionData.setGoogleAccountName(accountName);
        sessionData.apply();
    }
    public static String getGoogleAccountName(){
        return sessionData.getGoogleAccountName();
    }
    public static void setGoogleSessionToken(String token){
        sessionData.setGoogleSessionToken(token);
        sessionData.apply();
    }
    public static String getGoogleSessionToken(){
        return sessionData.getGoogleSessionToken();
    }

    public static void setLinkedinAccesToken(String newToken){
        sessionData.setLinkedinAccessToken(newToken);
    }
    public static String getLinkedinAccessToken(){
        return sessionData.getLinkedinAccesToken();
    }
    public static void setLinkedinSessionToken(String token){
        sessionData.setGoogleSessionToken(token);
    }
    public static String getLinkedinSessionToken(){
        return sessionData.getGoogleSessionToken();
    }


    /**
     * Is logged in method will return if there's an active user in session
     * */
    private static boolean isLoggedIn(){
        return (getUserSession() != null)?true:false;
    }
}
