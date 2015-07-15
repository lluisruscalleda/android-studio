package com.thesocialcoin.controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.thesocialcoin.activities.LoginActivity;
import com.thesocialcoin.events.AuthenticateUserEvent;
import com.thesocialcoin.models.pojos.Login;
import com.thesocialcoin.models.pojos.Logout;
import com.thesocialcoin.models.pojos.User;
import com.thesocialcoin.models.shared_preferences.SessionData;
import com.thesocialcoin.networking.core.RequestManager;
import com.thesocialcoin.networking.error.AuthenticateUserVolleyError;
import com.thesocialcoin.networking.error.LoginRequestFailed;
import com.thesocialcoin.networking.error.RegisterRequestFailed;
import com.thesocialcoin.networking.ottovolley.messages.VolleyRequestSuccess;
import com.thesocialcoin.requests.FacebookRequest;
import com.thesocialcoin.requests.LoginRequest;
import com.thesocialcoin.requests.RegisterRequest;
import com.thesocialcoin.utils.Codes;

import java.util.HashMap;

/**
 * Created by lluisruscalleda on 20/11/14.
 */
public class UserManager extends BaseManager {

    private static String TAG = UserManager.class.getSimpleName();
    private static UserManager instance = null;


    private UserManager(Context context) {

        RequestManager.EventBus.register(this);
    }


    public static UserManager getInstance(Context context) {
        if (instance == null) {
            mContext = context;
            instance = new UserManager(mContext);
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
     * Authenticate the user using facebook credentials
     *
     * @param email
     *            Email for the account
     * @param facebookId
     *            facebookId for the account
     * @param facebookAccessToken
     *            facebookAccessToken for the account
     */
    public void authenticateWithFacebook(String email, String facebookId, String facebookAccessToken)
    {
        postEvent(produceUserSignInStartEvent());

        HashMap<String,String> loginParams = new HashMap<String,String>();
        loginParams.put(Codes.EMAIL_VALUE, email);
        loginParams.put(Codes.FACEBOOK_ID_VALUE, facebookId);
        loginParams.put(Codes.FACEBOOK_ACCESS_TOKEN_VALUE, facebookAccessToken);
        RequestManager.addToRequestQueue(new FacebookRequest().create(loginParams));
    }

    /**
     *
     * Login response received subscription
     */
    @Subscribe
    public void onHttpErrorResponseReceived(LoginRequestFailed requestError)
    {
        Log.d(TAG, "Request end with error: " + requestError.requestId);
        Log.d(TAG, "Volley error : " + requestError.error.toString());
        Log.d(TAG, "Volley error : " + requestError.error.getMessage());

        // post succes event
        postEvent(produceUserSignInErrorEvent(new AuthenticateUserVolleyError(requestError.error)));
    }
    @Subscribe
    public void onLoginResponseReceived(VolleyRequestSuccess<Login> response)
    {
        Log.d(TAG, "Request end: " + response.requestId);
        if (response.response instanceof Login)
        {
            sessionData.setAuthenticationTokenCreationDate(response.response.getCreateDate());
            sessionData.setSessionToken(response.response.getToken());
            sessionData.setTimeToLive(response.response.getTimeToLive());
            sessionData.setUserId(response.response.getUserID());
            sessionData.setLoggedIn(true);
            sessionData.apply();

            AppManager.getInstance(mContext).postLoginActions();
        }
    }
    @Subscribe
    public void onHttpErrorResponseReceived(RegisterRequestFailed requestError)
    {
        Log.d(TAG, "Request end with error: " + requestError.requestId);
        Log.d(TAG, "Volley error : " + requestError.error.toString());
        Log.d(TAG, "Volley error : " + requestError.error.getMessage());

        // post succes event
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
    public void logoutActions()
    {
        // stop services
        AppManager.getInstance(mContext).stopAppServices();

        //RequestManager.addToRequestQueue(new LogoutRequest().create());
        SessionData sessionData = new SessionData(ApplicationController.getAppContext());
        sessionData.setLoggedIn(false);
        sessionData.setTimeToLive(null);
        sessionData.setUserId(null);
        sessionData.setSessionToken(null);
        sessionData.setAuthenticationTokenCreationDate(null);
        sessionData.apply();

        Intent mIntent = new Intent(ApplicationController.getAppContext(), LoginActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ApplicationController.getAppContext().startActivity(mIntent);
    }

    /**
     *
     * Logout response received subscription
     */
    @Subscribe
    public void onLogoutResponseReceived(VolleyRequestSuccess<Logout> response)
    {
        Log.d(TAG, "Request end: " + response.requestId);
        if (response.response instanceof Logout)
        {

            //RequestManager.EventBus.post(new FeedEvent(FeedEvent.Type.COMPLETED));
            SessionData sessionData = new SessionData(ApplicationController.getAppContext());
            sessionData.setLoggedIn(false);
            sessionData.setTimeToLive(null);
            sessionData.setUserId(null);
            sessionData.setSessionToken(null);
            sessionData.setAuthenticationTokenCreationDate(null);
            sessionData.apply();

            Intent mIntent = new Intent(ApplicationController.getAppContext(), LoginActivity.class);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            ApplicationController.getAppContext().startActivity(mIntent);
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
    public AuthenticateUserEvent produceUserSignOutSuccessEvent(Login user)
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

}
