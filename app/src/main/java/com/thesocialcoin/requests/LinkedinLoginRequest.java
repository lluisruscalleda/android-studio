package com.thesocialcoin.requests;

import android.content.Context;

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


    public LinkedinLoginRequest() {
        _requestErrorListener = OttoErrorListenerFactory.LOGIN_ERROR_LISTENER;
    }

    public Request create(HashMap<String,String> params)
    {
        this.context = App.getAppContext();

        URL = context.getResources().getString(R.string.bc_api_server_url)+ context.getResources().getString(R.string.bc_api_linkedin_auth_endpoint);


        OttoGsonPostRequest<APILoginResponse> request = new OttoGsonPostRequest<APILoginResponse>(RequestManager.EventBus, new JSONObject(params), null, URL, APILoginResponse.class, _requestErrorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(RequestManager.REQUEST_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return request;
    }
    public Request create(){
        HashMap<String,String> params = new HashMap<String,String>();
        return this.create(params);
    }

}