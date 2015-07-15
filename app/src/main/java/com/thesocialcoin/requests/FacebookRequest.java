package com.thesocialcoin.requests;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.thesocialcoin.R;
import com.thesocialcoin.controllers.ApplicationController;
import com.thesocialcoin.models.pojos.Login;
import com.thesocialcoin.models.shared_preferences.SessionData;
import com.thesocialcoin.networking.core.RequestInterface;
import com.thesocialcoin.networking.core.RequestManager;
import com.thesocialcoin.networking.error.OttoErrorListenerFactory;
import com.thesocialcoin.networking.ottovolley.core.OttoGsonPostRequest;
import com.thesocialcoin.utils.Codes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dcacenabes on 27/10/14.
 */
public class FacebookRequest extends RequestInterface {

    protected static String TAG = FacebookRequest.class.getSimpleName();

    private static JSONObject jsonObjectParams;
    private static String URL;
    private static Context context;
    private Response.Listener mListener;
    private Response.ErrorListener mErrorListener;

    private HashMap<String,String> _params;

    public FacebookRequest() {
        _requestErrorListener = OttoErrorListenerFactory.LOGIN_ERROR_LISTENER;
    }

    public Request create(HashMap<String,String> params)
    {
        this.context = ApplicationController.getAppContext();
        this._params = params;

        URL = context.getResources().getString(R.string.bc_api_server_url)+ "users/facebookAccess";

        String myVersion = android.os.Build.VERSION.RELEASE; // e.g. myVersion := "1.6"
        int sdkVersion = android.os.Build.VERSION.SDK_INT;

        SessionData sessionData = new SessionData(context);
        JSONObject jsonParams = new JSONObject();

        try {

            jsonParams.put("email", params.get(Codes.EMAIL_VALUE));
            jsonParams.put("facebookId", "_" + params.get(Codes.FACEBOOK_ID_VALUE));
            jsonParams.put("projectId", params.get(Codes.PROJECT_IDENTIFIER_VALUE));
            jsonParams.put("token", params.get(Codes.FACEBOOK_ACCESS_TOKEN_VALUE));

            jsonObjectParams = new JSONObject();
            jsonObjectParams.put("data", jsonParams);

            String json = jsonObjectParams.toString();
            Log.d(TAG, json);
        } catch (JSONException e){
            Log.e(TAG, e.toString());
        }

        // JSON Post Request receiving GSON pojo model
        //LoginRequest ha de ser el POJO creado
        Map<String, String> headers = AppRequestHelper.getInstance(context).getAuthorizationToken();
        String json = jsonParams.toString();
        headers.put("data", json);
        OttoGsonPostRequest<Login> request = new OttoGsonPostRequest<Login>(RequestManager.EventBus, jsonObjectParams, headers, URL, Login.class, _requestErrorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(RequestManager.REQUEST_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return request;
    }
    public Request create(){
        HashMap<String,String> params = new HashMap<String,String>();
        return this.create(params);
    }

}
