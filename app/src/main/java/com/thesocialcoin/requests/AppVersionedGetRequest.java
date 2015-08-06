package com.thesocialcoin.requests;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.thesocialcoin.App;
import com.thesocialcoin.networking.core.RequestInterface;
import com.thesocialcoin.networking.core.RequestManager;
import com.thesocialcoin.networking.ottovolley.core.OttoGsonRequest;

import java.util.HashMap;

/**
 * thesocialcoin
 * <p/>
 * Created by identitat on 04/08/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class AppVersionedGetRequest<T> extends RequestInterface {

    protected static String TAG = AppVersionedGetRequest.class.getSimpleName();
    private static Context context;

    public AppVersionedGetRequest() {
    }

    public OttoGsonRequest create(HashMap<String,String> params, String endpoint, Class<T> classType, String requestErrorListener)
    {
        this.context = App.getAppContext();


        OttoGsonRequest<T> getRequest = new OttoGsonRequest<T>(RequestManager.EventBus, AppRequestHelper.getInstance(context).getAuthorizationToken(), endpoint, classType, requestErrorListener);
        getRequest.setRetryPolicy(new DefaultRetryPolicy(RequestManager.REQUEST_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return getRequest;

    }

    public Request create(){
        HashMap<String,String> params = new HashMap<String,String>();
        return this.create(params);
    }

    @Override
    public Request create(HashMap<String, String> params) {
        return null;
    }

}
