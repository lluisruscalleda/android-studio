package com.thesocialcoin.controllers;

import android.content.Context;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.thesocialcoin.events.AuthenticateUserEvent;
import com.thesocialcoin.models.shared_preferences.SessionData;
import com.thesocialcoin.networking.ottovolley.messages.VolleyRequestFailed;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 15/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class AppManager extends BaseManager {

    private static String TAG = AppManager.class.getSimpleName();
    private static AppManager instance = null;


    private static String clientId;

    private AppManager(Context context) {
        super();
        sessionData = new SessionData(mContext);
    }

    public static AppManager getInstance(Context context) {
        Log.d(TAG, "getInstance()");
        if (instance == null) {
            mContext = context;
            instance = new AppManager(mContext);
        }
        return instance;
    }

    public static void startAppServices() {
        Log.d(TAG, "starting app services............");
        if (AppManager.getInstance(mContext).isLoggedIn()) {
            Log.d(TAG, "Logged services.....");

        } else {
            Log.d(TAG, "No Logged services.....");
        }
    }

    public static void stopAppServices() {
        Log.d(TAG, "stopAppServices()............");

    }

    public static void initManagers() {
        Log.d(TAG, "initManagers()");


    }

    public void postLoginActions() {

        this.initManagers();

        //We retrieve the timeline data
        HomeManager.getInstance(mContext).fetchAllTimeline();
        HomeManager.getInstance(mContext).fetchUserCompanyTimeline();
    }

    public boolean isClientRegistered() {
        return clientId() != null;
    }

    private static String clientId() {
        if (clientId == null) {
            clientId = sessionData.getClientId();
        }

        return clientId;
    }

    public boolean isLoggedIn() {
        return sessionData.isLoggedIn();
    }


    /**********************************************************************************************
     * WS 403 and 401 Error handling
     */

    @Subscribe
    public void onErrorResponseReceived(VolleyRequestFailed requestFailed) {
        Log.d(TAG, "VolleyRequestFailed");
        if (requestFailed.error.networkResponse != null) {
            if (requestFailed.error.networkResponse.statusCode == 403 || requestFailed.error.networkResponse.statusCode == 401) {
                this.notifyUserIsNotLoggedIn();
            }
        }
    }

    public void notifyUserIsNotLoggedIn() {
        postEvent(new AuthenticateUserEvent(AuthenticateUserEvent.Type.ERROR));
    }
}

