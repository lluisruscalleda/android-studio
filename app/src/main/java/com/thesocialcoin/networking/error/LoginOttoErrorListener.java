package com.thesocialcoin.networking.error;

import com.android.volley.VolleyError;
import com.thesocialcoin.networking.ottovolley.core.OttoErrorListener;
import com.squareup.otto.Bus;

/**
 * Created by identitat on 18/12/14.
 */
public class LoginOttoErrorListener extends OttoErrorListener implements OttoErrorListenerInterface {

    public LoginOttoErrorListener(Bus eventBus, int requestId) {
        super(eventBus, requestId);
    }

    public OttoErrorListener create(Bus eventBus, int requestId){
        return new LoginOttoErrorListener(eventBus, requestId);
    }

    public void onErrorResponse(VolleyError error) {
        LoginRequestFailed loginError = new LoginRequestFailed(RequestId, new AuthenticateUserVolleyError(error));
        _eventBus.post(loginError);
    }

}
