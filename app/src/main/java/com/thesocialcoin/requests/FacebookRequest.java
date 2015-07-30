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
        this.context = App.getAppContext();
        this._params = params;

        URL = context.getResources().getString(R.string.bc_api_server_url)+ context.getResources().getString(R.string.bc_api_facebook_auth_endpoint);

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


//CAAUCG9OASSQBAMdY1ghZCYVrYDUFwYGCeGMh0zjYNrZB9BHQ82Q4M1AVmSIAOu1yGFCqqNivAYSEzU1rCpNDkt5LShAdpMZBn0QlZB4ZBKgdMSldrvbvgHamblQFYBZAC7CiVfF7cMfqR931QqzXyfZCZAiuqKiqdwJH88HQ5QxQLaL2DpvjpHxuqmSTV2jxQKDZACm0dfpIZBQ3SIh5BvZC691
//CAAUCG9OASSQBAHtbCezVU0rvy8UfZAPvWZCV4Reev9lrXVQhF1L22RiH39ZCMGDcFGBF7kfZAMYkZCPfLvFfDONCn144hukPBOD8T8ZBrPNm8WPbuGQaO9a2ny19gKBs2ZCSS0L8x1JtSvi4bIuAThF9ONcaL8Mo7N8i53Ua0iUuykd3Vl3vAscLbhw4ItrZAsDYLYaMuBdXzoShT9snEXZA1uNeZAz4hnbIoOLs2PTqNSqQZDZD
//CAAUCG9OASSQBAJZBeUjp73AQWEYHTruYS1zT0hW3yjRKPgLtYPZBFP6ATVdnzx6GdT1a2hLusZCsCHAwbeq3Dl8Htn9ZASp1eQ5KFEk3WaVNQlZB2QH5IS3j185eAoqo8ZBHejlAZBpaySX8ZBlK22WsC0MwgU3vfa11MxFixQq7G9Ddbgaqygn0Q8t3DJjGPdFKyPixJHNEVBtqnqpnW4crYPrpRvlfrCPR6VEYwIy2BwZDZD