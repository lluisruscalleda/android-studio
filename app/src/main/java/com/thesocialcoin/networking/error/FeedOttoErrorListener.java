package com.thesocialcoin.networking.error;

import com.thesocialcoin.networking.ottovolley.core.OttoErrorListener;
import com.squareup.otto.Bus;
import com.thesocialcoin.networking.ottovolley.core.OttoErrorListener;

/**
 * Created by identitat on 18/12/14.
 */
public class FeedOttoErrorListener extends OttoErrorListener implements OttoErrorListenerInterface {

    public FeedOttoErrorListener(Bus eventBus, int requestId) {
        super(eventBus, requestId);

    }

    public OttoErrorListener create(Bus eventBus, int requestId){
        return new LoginOttoErrorListener(eventBus, requestId);
    }

}
