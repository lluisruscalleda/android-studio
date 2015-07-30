package com.thesocialcoin.controllers;

import android.content.Context;
import android.widget.Toast;

import com.thesocialcoin.models.shared_preferences.SessionData;
import com.thesocialcoin.networking.core.RequestManager;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 15/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class BaseManager {

    protected static SessionData sessionData;
    protected static Context mContext;

    public BaseManager(Context context) {
        RequestManager.EventBus.register(this);
    }

    public BaseManager()
    {
        RequestManager.EventBus.register(this);
    }

    protected static void postEvent(Object event){
        RequestManager.EventBus.post(event);
    }

    protected static void showToast(String message)
    {
        Toast.makeText(mContext,
                message,
                Toast.LENGTH_LONG).show();
    }
}
