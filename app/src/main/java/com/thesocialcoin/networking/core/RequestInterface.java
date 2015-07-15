package com.thesocialcoin.networking.core;

import android.os.Bundle;

import com.android.volley.Request;

import java.util.HashMap;


/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 14/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public abstract class RequestInterface {

    private Bundle mArguments;
    protected String _requestErrorListener= null;

    public abstract Request create();
    public abstract Request create(HashMap<String,String> params);

    public void setArguments(Bundle args) {
        mArguments = args;
    }
    public Bundle getArguments() {
        return mArguments;
    }

}