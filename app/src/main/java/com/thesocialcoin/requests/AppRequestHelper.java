package com.thesocialcoin.requests;

import android.content.Context;
import android.util.Log;

import com.thesocialcoin.models.shared_preferences.SessionData;

import java.util.HashMap;
import java.util.Map;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 15/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class AppRequestHelper {

    private static String TAG = AppRequestHelper.class.getSimpleName();
    private static AppRequestHelper instance = null;
    protected static SessionData sessionData;
    protected static Context mContext;

    private AppRequestHelper(Context context) {

    }
    public static AppRequestHelper getInstance(Context context)
    {
        Log.d(TAG, "getInstance()");
        if (instance == null)
        {
            mContext = context;
            instance = new AppRequestHelper(context);
        }

        return instance;
    }

    /*
    * getting Authorization token
    */
    public static Map<String, String> getAuthorizationToken() {
        Map<String, String> params = new HashMap<String, String>();
        String authToken = new SessionData(mContext).getSessionToken();
        if (authToken != null) {
            params.put("Authorization", authToken);
        }

        return params;
    }
}
