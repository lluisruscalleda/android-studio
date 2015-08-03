package com.thesocialcoin.controllers;

import android.content.Context;

/**
 * thesocialcoin
 * <p/>
 * Created by identitat on 03/08/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class HomeManager extends BaseManager {

    /**
     * Manager for Home functionalities
     *
     *
     */

    public enum HomeTabs {
        ALL, COMPANY

    };

    private static String TAG = HomeManager.class.getSimpleName();
    private static HomeManager instance = null;

    private HomeManager(Context context) {
        super();
    }


    public static HomeManager getInstance(Context context) {
        if (instance == null) {
            mContext = context;
            instance = new HomeManager(mContext);
        }

        return instance;
    }

}
