package com.thesocialcoin.controllers;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader;
import com.codeslap.persistence.DatabaseSpec;
import com.codeslap.persistence.PersistenceConfig;
import com.crashlytics.android.Crashlytics;
import com.thesocialcoin.networking.core.RequestManager;
import com.thesocialcoin.utils.ConnectionHelper;
import com.thesocialcoin.utils.LruBitmapCache;
import io.fabric.sdk.android.Fabric;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 14/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class ApplicationController extends Application {

    public static final String TAG = ApplicationController.class.getSimpleName();
    private static Context mContext;
    private ImageLoader mImageLoader;
    private static String password = "";

    /**
     * A singleton instance of the application class for easy access in other places
     */
    private static ApplicationController sInstance;

    public static Context getAppContext(){ return mContext; }

    public static boolean isConnectedToInternet(){ return ConnectionHelper.hasConnection(); }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        ApplicationController.password = password;
    }

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized ApplicationController getInstance() {
        return sInstance;
    }



    public void onCreate(){
        Log.d(TAG, "onCreate()");
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        DatabaseSpec database = PersistenceConfig.registerSpec(/**db version**/1);
        //database.match(Foo.class, Bar.class);

        mContext = getApplicationContext();

        // Init networking instances: Volley queue, Otto response event bus.
        RequestManager.ensureInitialized(this);

        // Parse object initialization for push notifications
        //Parse.initialize(this, getResources().getString(R.string.bc_parse_application_id), getResources().getString(R.string.bc_parse_application_key));

        /*ParsePush.subscribeInBackground("TENISMEXICANO", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });*/


        if (AppManager.getInstance(this).isLoggedIn())
        {
            if(isConnectedToInternet()){
                Log.d(TAG, "User has internet connection");
                verifyBluetooth();
                AppManager.getInstance(this).initManagers();

            }else{
                Log.d(TAG, "User does not have internet connection");
                //TODO: show alert dialog with no connection
            }
        }

        sInstance = this;
    }

    /**
     * Called by the system when the device configuration changes while your component is running.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }
    /**
     * Called by the system when the device configuration changes while your component is running.
     */
    @Override
    public void onLowMemory() {
        Log.d(TAG, "onLowMemory");
        super.onLowMemory();
    }
    /**
     * Called by the system when the device configuration changes while your component is running.
     */
    @Override
    public void onTerminate() {
        Log.d(TAG, "onTerminate");
        super.onTerminate();
    }

    private void showLogError(String message) {
        Log.d(TAG, message);
    }


    public ImageLoader getImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(RequestManager.VolleyRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public static void verifyBluetooth() {
        Log.d(TAG, "Verifying bluetooth...");
        /*try {
            if (!BeaconManager.getInstanceForApplication(this).checkAvailability()) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Bluetooth not enabled");
                builder.setMessage("Please enable bluetooth in settings and restart this application.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                        System.exit(0);
                    }
                });
                builder.show();
            }
        }
        catch (RuntimeException e) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bluetooth LE not available");
            builder.setMessage("Sorry, this device does not support Bluetooth LE.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    finish();
                    System.exit(0);
                }

            });
            builder.show();

        }*/

    }
}

