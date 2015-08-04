package com.thesocialcoin.networking.error;

import com.android.volley.VolleyError;
import com.squareup.otto.Bus;
import com.thesocialcoin.networking.ottovolley.core.OttoErrorListener;

/**
 * Created by identitat on 18/12/14.
 */
public class TimelineOttoErrorListener extends OttoErrorListener implements OttoErrorListenerInterface {

    public TimelineOttoErrorListener(Bus eventBus, int requestId) {
        super(eventBus, requestId);
    }

    public OttoErrorListener create(Bus eventBus, int requestId){
        return new TimelineOttoErrorListener(eventBus, requestId);
    }

    public void onErrorResponse(VolleyError error) {
        TimelineRequestFailed loginError = new TimelineRequestFailed(RequestId, new TimelineVolleyError(error));
        _eventBus.post(loginError);
    }

}
