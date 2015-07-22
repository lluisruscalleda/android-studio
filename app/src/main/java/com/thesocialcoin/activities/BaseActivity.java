package com.thesocialcoin.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.otto.Subscribe;
import com.thesocialcoin.controllers.AppManager;
import com.thesocialcoin.events.AuthenticateUserEvent;
import com.thesocialcoin.models.shared_preferences.SessionData;
import com.thesocialcoin.networking.core.RequestManager;
import com.thesocialcoin.utils.FontUtils;

import icepick.Icepick;
import icepick.Icicle;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 15/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */

/**
 * Base Activity that performs various functions that all Activities in this app
 * should do. Such as:
 *
 * Registering for the event bus. Setting the current site's theme. Finishing
 * the Activity if the user logs out but the Activity requires authentication.
 */

public abstract class BaseActivity extends Activity {

    private static String TAG = BaseActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    protected SessionData sessionData;

    @Icicle int mActivityid;


    protected LinearLayout no_connection_view;
    protected FrameLayout act_content;

    protected String type_filter = "1";
    protected static final int FILTER_VIEW = 1;


    ImageLoader imageLoader;

    // font styles
    public Typeface boldType;
    public Typeface lightType;


    /**
     * Post the event to the service bus
     *
     * @param event The event to dispatch on the service bus
     */
    protected void postEvent(Object event) {
        RequestManager.EventBus.post(event);
    }


    @Override
    public void setContentView(final int layoutResID) {

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");

        if (!AppManager.getInstance(this).isLoggedIn()) {
            goToLogin();
        }

        registerServiceBus(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");

        registerServiceBus(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");

        sessionData = new SessionData(this);

        //Styles of the app
        boldType = FontUtils.getAppsBoldFont(this);
        lightType = FontUtils.getAppsBoldFont(this);

        Icepick.restoreInstanceState(this, savedInstanceState);


        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Icepick.saveInstanceState(this, outState);
    }


    private static int sActivityidCounter = 0;

    private int generateActivityid() {
        return sActivityidCounter++;
    }


    protected void showNoNetworkConnection() {


    }

    public void showToast(String message) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_LONG).show();
    }

    public void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
        finish();
    }



    /**
     * **************************************************************************************
     */

    private void registerServiceBus(boolean register) {
        if (register) {
            RequestManager.EventBus.register(this);
            RequestManager.ResponseBuffer.stopAndProcess();
        } else {
            RequestManager.ResponseBuffer.startSaving();
            RequestManager.EventBus.unregister(this);
        }
    }

    /**
     * **************************************************************************************
     */

    protected void showPDialog(String message) {
        if (pDialog == null) {
            pDialog = new ProgressDialog(this);
            pDialog.setCanceledOnTouchOutside(false);
            // Showing progress dialog before making http request
            pDialog.setMessage(message);
            pDialog.show();
        }
    }

    protected void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
    /******************************************************************************************/

//    /**
//     * Update the UI drawer menu
//     */
//    protected void updateUiDrawerMenuForItemsReceived()
//    {
//        navDrawerItems.clear();
//        navDrawerItems.addAll(mDrawerModelView.getDrawerItems());
//        mDrawerAdapter.notifyDataSetChanged();
//    }


    /**
     * **********************************
     * <p/>
     * Event handling
     */
    @Subscribe
    public void onAuthenticationEvent(AuthenticateUserEvent event) {
        if (event.getType().equals(AuthenticateUserEvent.Type.ERROR)) {
            goToLogin();
        }
    }

}
