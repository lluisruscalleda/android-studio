package com.thesocialcoin.networking.error;

import com.android.volley.VolleyError;
import com.squareup.otto.Bus;
import com.thesocialcoin.networking.ottovolley.core.OttoErrorListener;

/**
 * Created by identitat on 18/12/14.
 */
public class ActivitiesOttoErrorListener extends OttoErrorListener implements OttoErrorListenerInterface {

    public ActivitiesOttoErrorListener(Bus eventBus, int requestId) {
        super(eventBus, requestId);
    }

    public OttoErrorListener create(Bus eventBus, int requestId){
        return new ActivitiesOttoErrorListener(eventBus, requestId);
    }

    public void onErrorResponse(VolleyError error) {
        ActivitiesRequestFailed loginError = new ActivitiesRequestFailed(RequestId, new ActivitiesVolleyError(error));
        _eventBus.post(loginError);
    }

}
