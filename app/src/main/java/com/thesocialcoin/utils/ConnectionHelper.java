package com.thesocialcoin.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.thesocialcoin.App;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 14/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class ConnectionHelper {

    public static Boolean hasConnection(){

        ConnectivityManager connectivity = (ConnectivityManager) App.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }

        if(wifiConnection()){
            return true;
        }else{
            if(mobileConnection()){
                return true;
            }else{
                return false;
            }
        }
    }

    public static Boolean wifiConnection(){
        ConnectivityManager connectivity = (ConnectivityManager) App.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Boolean mobileConnection(){
        ConnectivityManager connectivity = (ConnectivityManager) App.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }
}

