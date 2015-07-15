package com.thesocialcoin.requests;

import android.content.Context;

import com.android.volley.Request;
import com.mobdala.acapulcoopen.R;
import com.mobdala.application.models.pojo.Logout;
import com.mobdala.application.models.pojo.MDBeacon;
import com.mobdala.controllers.MyApplication;
import com.mobdala.networking.core.RequestInterface;
import com.mobdala.networking.core.RequestManager;
import com.mobdala.networking.ottovolley.core.OttoGsonPostRequest;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dcacenabes on 28/10/14.
 */
public class LogoutRequest extends RequestInterface {

    protected static String TAG = LogoutRequest.class.getSimpleName();
    private static String URL;
    private static Context context;

    public LogoutRequest() {

    }
    public Request create(List<MDBeacon> params)
    {
        return this.create();
    }
    public Request create(HashMap<String,String> params) {
        return this.create();
    }

    public Request create(){

        context = MyApplication.getAppContext();
        URL = context.getResources().getString(R.string.bc_api_server_url)+"users/logout";
        // JSON Post Request receiving GSON pojo model
        OttoGsonPostRequest<Logout> request = new OttoGsonPostRequest<Logout>(RequestManager.EventBus, null, AppRequestHelper.getInstance(context).getAuthorizationToken(), URL, Logout.class, null);


        return request;
    }
}