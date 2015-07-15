package com.thesocialcoin.networking.error;

import com.thesocialcoin.networking.ottovolley.core.OttoErrorListener;
import com.squareup.otto.Bus;
import com.thesocialcoin.networking.ottovolley.core.OttoErrorListener;

/**
 * Created by identitat on 18/12/14.
 */
public abstract interface OttoErrorListenerInterface {

    public abstract OttoErrorListener create(Bus eventBus, int requestId);
}
