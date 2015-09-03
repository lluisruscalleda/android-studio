package com.thesocialcoin.networking.error;

import com.android.volley.VolleyError;
import com.squareup.otto.Bus;
import com.thesocialcoin.networking.ottovolley.core.OttoErrorListener;

/**
 * Created by identitat on 18/12/14.
 */
public class UserProfileOttoErrorListener extends OttoErrorListener implements OttoErrorListenerInterface {

    public UserProfileOttoErrorListener(Bus eventBus, int requestId) {
        super(eventBus, requestId);
    }

    public OttoErrorListener create(Bus eventBus, int requestId){
        return new UserProfileOttoErrorListener(eventBus, requestId);
    }

    public void onErrorResponse(VolleyError error) {
        UserProfileRequestFailed loginError = new UserProfileRequestFailed(RequestId, new UserProfileVolleyError(error));
        _eventBus.post(loginError);
    }

}
