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
public class FacebookLoginRequest extends RequestInterface {

    protected static String TAG = FacebookLoginRequest.class.getSimpleName();

    private static JSONObject jsonObjectParams;
    private static String URL;
    private static Context context;
    private Response.Listener mListener;
    private Response.ErrorListener mErrorListener;


    public FacebookLoginRequest() {
        _requestErrorListener = OttoErrorListenerFactory.LOGIN_ERROR_LISTENER;
    }

    public Request create(HashMap<String,String> params)
    {
        this.context = App.getAppContext();

        URL = context.getResources().getString(R.string.bc_api_server_url)+ context.getResources().getString(R.string.bc_api_facebook_auth_endpoint);

        OttoGsonPostRequest<APILoginResponse> request = new OttoGsonPostRequest<APILoginResponse>(RequestManager.EventBus, new JSONObject(params), null, URL, APILoginResponse.class, _requestErrorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(RequestManager.REQUEST_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return request;
    }
    public Request create(){
        HashMap<String,String> params = new HashMap<String,String>();
        return this.create(params);
    }

}


//CAAUCG9OASSQBAMdY1ghZCYVrYDUFwYGCeGMh0zjYNrZB9BHQ82Q4M1AVmSIAOu1yGFCqqNivAYSEzU1rCpNDkt5LShAdpMZBn0QlZB4ZBKgdMSldrvbvgHamblQFYBZAC7CiVfF7cMfqR931QqzXyfZCZAiuqKiqdwJH88HQ5QxQLaL2DpvjpHxuqmSTV2jxQKDZACm0dfpIZBQ3SIh5BvZC691
//CAAUCG9OASSQBAHtbCezVU0rvy8UfZAPvWZCV4Reev9lrXVQhF1L22RiH39ZCMGDcFGBF7kfZAMYkZCPfLvFfDONCn144hukPBOD8T8ZBrPNm8WPbuGQaO9a2ny19gKBs2ZCSS0L8x1JtSvi4bIuAThF9ONcaL8Mo7N8i53Ua0iUuykd3Vl3vAscLbhw4ItrZAsDYLYaMuBdXzoShT9snEXZA1uNeZAz4hnbIoOLs2PTqNSqQZDZD
//CAAUCG9OASSQBAJZBeUjp73AQWEYHTruYS1zT0hW3yjRKPgLtYPZBFP6ATVdnzx6GdT1a2hLusZCsCHAwbeq3Dl8Htn9ZASp1eQ5KFEk3WaVNQlZB2QH5IS3j185eAoqo8ZBHejlAZBpaySX8ZBlK22WsC0MwgU3vfa11MxFixQq7G9Ddbgaqygn0Q8t3DJjGPdFKyPixJHNEVBtqnqpnW4crYPrpRvlfrCPR6VEYwIy2BwZDZD