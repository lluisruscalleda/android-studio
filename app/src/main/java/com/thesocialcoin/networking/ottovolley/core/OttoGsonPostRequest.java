package com.thesocialcoin.networking.ottovolley.core;

import com.android.volley.VolleyError;
import com.squareup.otto.Bus;
import com.thesocialcoin.networking.error.OttoErrorListenerFactory;
import com.thesocialcoin.networking.volleyextensions.GsonPostRequest;

import org.json.JSONObject;

import java.util.Map;


/** GsonRequest which passes the result on to an Otto Message Bus */
public class OttoGsonPostRequest<T> extends GsonPostRequest<T> {
    /** Request ID counter for this session */
    private static int _idCounter = 1;

    /** A ID for this request, unique for the lifetime of the process (given that you do < 2BN requests) */
    public int requestId;

    private OttoSuccessListener successListener;
    private OttoErrorListener errorListener;

    public OttoGsonPostRequest(Bus eventBus, JSONObject jsonRequest, Map<String, String> headers, String url, Class<T> classType, String ottoErrorListener) {
        super(url, jsonRequest,
                classType, headers,
                new OttoSuccessListener<T>(eventBus, _idCounter),
                OttoErrorListenerFactory.getOttoErrorListener(ottoErrorListener, eventBus, _idCounter));
        requestId = _idCounter;
        _idCounter++;
    }

    public void setErrorListener(){

    }

    public void setSuccesListener(){

    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError){
        if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
            VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
            volleyError = error;
        }

        return volleyError;
    }
}
