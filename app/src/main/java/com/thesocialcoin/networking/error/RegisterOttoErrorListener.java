package com.thesocialcoin.networking.error;

import com.android.volley.VolleyError;
import com.thesocialcoin.networking.ottovolley.core.OttoErrorListener;
import com.squareup.otto.Bus;

/**
 * Created by identitat on 18/12/14.
 */
public class RegisterOttoErrorListener extends OttoErrorListener implements OttoErrorListenerInterface {

    public RegisterOttoErrorListener(Bus eventBus, int requestId) {
        super(eventBus, requestId);
    }

    public OttoErrorListener create(Bus eventBus, int requestId){
        return new RegisterOttoErrorListener(eventBus, requestId);
    }

    public void onErrorResponse(VolleyError error) {
        RegisterRequestFailed message = new RegisterRequestFailed(RequestId, error);
        _eventBus.post(message);
    }

}
