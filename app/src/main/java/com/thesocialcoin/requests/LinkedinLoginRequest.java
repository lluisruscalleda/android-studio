package com.thesocialcoin.requests;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.thesocialcoin.App;
import com.thesocialcoin.R;
import com.thesocialcoin.models.pojos.APILoginResponse;
import com.thesocialcoin.models.shared_preferences.SessionData;
import com.thesocialcoin.networking.core.RequestInterface;
import com.thesocialcoin.networking.core.RequestManager;
import com.thesocialcoin.networking.error.OttoErrorListenerFactory;
import com.thesocialcoin.networking.ottovolley.core.OttoGsonPostRequest;
import com.thesocialcoin.utils.Codes;
import com.thesocialcoin.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 15/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class LinkedinLoginRequest extends RequestInterface {

    protected static String TAG = LinkedinLoginRequest.class.getSimpleName();

    private static JSONObject jsonObjectParams;
    private static String URL;
    private static Context context;
    private Response.Listener mListener;
    private Response.ErrorListener mErrorListener;

    private HashMap<String,String> _params;

    public LinkedinLoginRequest() {
        _requestErrorListener = OttoErrorListenerFactory.LOGIN_ERROR_LISTENER;
    }

    public Request create(HashMap<String,String> params)
    {
        this.context = App.getAppContext();
        this._params = params;

        URL = context.getResources().getString(R.string.bc_api_server_url)+ context.getResources().getString(R.string.bc_api_linkedin_auth_endpoint);

        String myVersion = android.os.Build.VERSION.RELEASE; // e.g. myVersion := "1.6"
        int sdkVersion = android.os.Build.VERSION.SDK_INT;

        SessionData sessionData = new SessionData(context);
        JSONObject requestJson = new JSONObject();

        try {
            requestJson.put(Codes.reg_user_facebook_token, params.get(Codes.reg_user_facebook_token));
            requestJson.put(Codes.reg_user_language, Utils.getAppLanguage());

            Log.d(TAG, requestJson.toString());
        } catch (JSONException e){
            Log.e(TAG, e.toString());
        }

        OttoGsonPostRequest<APILoginResponse> request = new OttoGsonPostRequest<APILoginResponse>(RequestManager.EventBus, requestJson, null, URL, APILoginResponse.class, _requestErrorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(RequestManager.REQUEST_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return request;
    }
    public Request create(){
        HashMap<String,String> params = new HashMap<String,String>();
        return this.create(params);
    }

}