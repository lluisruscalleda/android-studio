package com.thesocialcoin.controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.otto.Subscribe;
import com.thesocialcoin.App;
import com.thesocialcoin.R;
import com.thesocialcoin.activities.LoginActivity;
import com.thesocialcoin.events.AuthenticateUserEvent;
import com.thesocialcoin.helpers.JsonTrimMessage;
import com.thesocialcoin.models.pojos.APILoginResponse;
import com.thesocialcoin.models.pojos.Logout;
import com.thesocialcoin.models.pojos.User;
import com.thesocialcoin.models.pojos.iPojo;
import com.thesocialcoin.models.shared_preferences.SessionData;
import com.thesocialcoin.networking.core.RequestManager;
import com.thesocialcoin.networking.error.AuthenticateUserVolleyError;
import com.thesocialcoin.networking.error.LoginRequestFailed;
import com.thesocialcoin.networking.error.RegisterRequestFailed;
import com.thesocialcoin.networking.helpers.VolleyErrorHelper;
import com.thesocialcoin.networking.ottovolley.messages.VolleyRequestSuccess;
import com.thesocialcoin.requests.FacebookRequest;
import com.thesocialcoin.requests.GplusRequest;
import com.thesocialcoin.requests.LoginRequest;
import com.thesocialcoin.requests.RegisterRequest;
import com.thesocialcoin.utils.Codes;
import com.thesocialcoin.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by lluisruscalleda on 20/11/14.
 */
public class UserManager extends BaseManager {

    private static String TAG = UserManager.class.getSimpleName();
    private static UserManager instance = null;


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

    private UserManager(Context context) {
        super();
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
     * Authenticate the user using google plus credentials
     *
     * @param facebookToken
     *            token account
     * @param OnRegisterResponseListener
     *            listener
     *
     */
    public void authenticateWithGoogle(String facebookToken, final OnRegisterResponseListener listener)
    {
        postEvent(produceUserSignInStartEvent());

        HashMap<String,String> requestJson = new HashMap<String,String> ();
        requestJson.put(Codes.reg_user_facebook_token, facebookToken);
        requestJson.put(Codes.reg_user_language, Utils.getAppLanguage());

        RequestManager.addToRequestQueue(new GplusRequest().create(requestJson));
    }
    /**
     * Authenticate the user using facebook credentials
     *
     * @param facebookToken
     *            token account
     * @param OnRegisterResponseListener
     *            listener
     *
     */
    public void authenticateWithFacebook(String facebookToken, final OnRegisterResponseListener listener)
    {
//        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
//
//        JSONObject requestJson = new JSONObject();
//        try {
//            requestJson.put(Codes.reg_user_facebook_token, facebookToken);
//            requestJson.put(Codes.reg_user_language, Utils.getAppLanguage());
//        } catch (JSONException e1) {
//            Log.e("JSONObject", "JSONException " + e1);
//            e1.printStackTrace();
//        }
//
//        JsonObjectRequest jsObjRequest = new JsonObjectRequest
//                (Request.Method.POST, mContext.getResources().getString(R.string.bc_api_server_url)+ "api-facebook-auth", requestJson.toString(), new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        listener.onRegisterSucceed((APILoginResponse) new iPojo().create(response.toString(), APILoginResponse.class));
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        NetworkResponse response = error.networkResponse;
//                        if(response != null && response.data != null){
//                            String errorMsg = null;
//                            switch(response.statusCode){
//                                case 400:
//                                    errorMsg = null;
//                                    errorMsg = new String(error.getMessage());
//                                    if(errorMsg != null) {
//                                        errorMsg = JsonTrimMessage.trimMessage(errorMsg, "Error");
//                                        listener.onRegisterFailed("WS_ERROR_CODE_" + errorMsg);
//                                    }else{
//                                        listener.onRegisterFailed("generic_server_down");
//                                    }
//                                    break;
//                                default:
//                                    errorMsg = VolleyErrorHelper.getMessage(error, mContext);
//                                    listener.onRegisterFailed(errorMsg);
//                                    break;
//                            }
//                        }else{
//                            String errorMsg = null;
//                            errorMsg = new String(error.getMessage());
//                            if(errorMsg != null) {
//                                if(JsonTrimMessage.trimMessage(errorMsg, "Error") == null){
//                                    listener.onRegisterFailed("WS_ERROR_CODE_" + errorMsg);
//                                }else {
//                                    listener.onRegisterFailed("WS_ERROR_CODE_" + JsonTrimMessage.trimMessage(errorMsg, "Error"));
//                                }
//
//                            }else{
//                                listener.onRegisterFailed("generic_server_down");
//                            }
//                        }
//
//                    }
//                }){
//            @Override
//            protected VolleyError parseNetworkError(VolleyError volleyError){
//                if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
//                    VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
//                    volleyError = error;
//                }
//
//                return volleyError;
//            }
//        };
//
//        RequestManager.addToRequestQueue(jsObjRequest);

        postEvent(produceUserSignInStartEvent());

        HashMap<String,String> requestJson = new HashMap<String,String> ();
        requestJson.put(Codes.reg_user_facebook_token, facebookToken);
        requestJson.put(Codes.reg_user_language, Utils.getAppLanguage());

        RequestManager.addToRequestQueue(new FacebookRequest().create(requestJson));
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
            sessionData.setSessionToken(response.response.getToken());
            sessionData.setUserData(response.response.getUser().serialize());
            sessionData.apply();

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
    public void doLogout()
    {
        // stop services
        AppManager.getInstance(mContext).stopAppServices();

        //RequestManager.addToRequestQueue(new LogoutRequest().create());
        SessionData sessionData = new SessionData(App.getAppContext());
        sessionData.clearEditor();

        Intent mIntent = new Intent(App.getAppContext(), LoginActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        App.getAppContext().startActivity(mIntent);
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

}
