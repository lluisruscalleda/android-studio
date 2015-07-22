package com.thesocialcoin.requests;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.thesocialcoin.App;
import com.thesocialcoin.R;
import com.thesocialcoin.models.pojos.APILoginResponse;
import com.thesocialcoin.networking.core.RequestInterface;
import com.thesocialcoin.networking.core.RequestManager;
import com.thesocialcoin.networking.error.OttoErrorListenerFactory;
import com.thesocialcoin.networking.ottovolley.core.OttoGsonPostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 15/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class LoginRequest extends RequestInterface {

    protected static String TAG = LoginRequest.class.getSimpleName();

    private static JSONObject jsonObjectParams;
    private static String URL;
    private static Context context;
    private Response.Listener mListener;
    private Response.ErrorListener mErrorListener;

    private HashMap<String,String> _params;

    public LoginRequest() {
        _requestErrorListener = OttoErrorListenerFactory.LOGIN_ERROR_LISTENER;
    }


    public Request create(HashMap<String,String> params)
    {
        this.context = App.getAppContext();
        this._params = params;

        URL = context.getResources().getString(R.string.bc_api_server_url)+"users/login";


        try {
            jsonObjectParams = new JSONObject();
            jsonObjectParams.put("email", params.get("user_name"));
            jsonObjectParams.put("password", params.get("password"));
            jsonObjectParams.put("ttl", 1209600000);

            Log.d(TAG, jsonObjectParams.toString());
        } catch (JSONException e){
            Log.e(TAG, e.toString());
        }

        // JSON Post Request receiving GSON pojo model
        //LoginRequest ha de ser el POJO creado
        OttoGsonPostRequest<APILoginResponse> request = new OttoGsonPostRequest<APILoginResponse>(RequestManager.EventBus, jsonObjectParams, AppRequestHelper.getInstance(context).getAuthorizationToken(), URL, APILoginResponse.class, _requestErrorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(RequestManager.REQUEST_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        return request;
    }
    public Request create(){
        HashMap<String,String> params = new HashMap<String,String>();
        return this.create(params);
    }

}
