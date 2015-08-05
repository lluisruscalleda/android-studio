package com.thesocialcoin.networking.ottovolley.core;

import com.android.volley.VolleyError;
import com.thesocialcoin.networking.error.OttoErrorListenerFactory;
import com.thesocialcoin.networking.volleyextensions.GsonRequest;
import com.squareup.otto.Bus;

import java.util.Map;


/** GsonRequest which passes the result on to an Otto Message Bus */
public class OttoGsonRequest<T> extends GsonRequest<T> {
    /** Request ID counter for this session */
    private static int _idCounter = 1;

    /** A ID for this request, unique for the lifetime of the process (given that you do < 2BN requests) */
    public int requestId;

    public OttoGsonRequest(Bus eventBus, Map<String, String> headers, String url, Class<T> classType, String ottoErrorListener) {
        super(url,
                classType, headers,
                new OttoSuccessListener<T>(eventBus, _idCounter),
                OttoErrorListenerFactory.getOttoErrorListener(ottoErrorListener, eventBus, _idCounter));
        requestId = _idCounter;
        _idCounter++;
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
