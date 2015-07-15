package com.thesocialcoin.networking.ottovolley.core;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.thesocialcoin.networking.ottovolley.messages.VolleyRequestFailed;
import com.squareup.otto.Bus;


public class OttoErrorListener implements Response.ErrorListener {
    public int RequestId;
    protected Bus _eventBus;

    public OttoErrorListener(Bus eventBus, int requestId) {
        RequestId = requestId;
        _eventBus = eventBus;
    }

    public void onErrorResponse(VolleyError error) {
        VolleyRequestFailed message = new VolleyRequestFailed(RequestId, error);
        _eventBus.post(message);
    }
}