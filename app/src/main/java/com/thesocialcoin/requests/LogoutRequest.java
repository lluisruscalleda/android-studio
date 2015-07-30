package com.thesocialcoin.requests;

import android.content.Context;

import com.android.volley.Request;
import com.thesocialcoin.R;
import com.thesocialcoin.App;
import com.thesocialcoin.models.pojos.Logout;
import com.thesocialcoin.networking.core.RequestInterface;
import com.thesocialcoin.networking.core.RequestManager;
import com.thesocialcoin.networking.ottovolley.core.OttoGsonPostRequest;

import java.util.HashMap;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 15/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class LogoutRequest extends RequestInterface {

    protected static String TAG = LogoutRequest.class.getSimpleName();
    private static String URL;
    private static Context context;

    public LogoutRequest() {

    }
    public Request create(HashMap<String,String> params) {
        return this.create();
    }

    public Request create(){

        context = App.getAppContext();
        URL = context.getResources().getString(R.string.bc_api_server_url)+"users/logout";
        // JSON Post Request receiving GSON pojo model
        OttoGsonPostRequest<Logout> request = new OttoGsonPostRequest<Logout>(RequestManager.EventBus, null, AppRequestHelper.getInstance(context).getAuthorizationToken(), URL, Logout.class, null);


        return request;
    }
}