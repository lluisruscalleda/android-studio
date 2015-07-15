package com.thesocialcoin.networking.error;

import com.thesocialcoin.networking.ottovolley.core.OttoErrorListener;
import com.squareup.otto.Bus;

/**
 * Created by identitat on 18/12/14.
 */
public abstract class OttoErrorListenerFactory {

    public  final static String LOGIN_ERROR_LISTENER = "login.error.listener";
    public  final static String REGISTER_ERROR_LISTENER = "register.error.listener";

    public static OttoErrorListener getOttoErrorListener(String criteria, Bus eventBus, int requestId) {
        if(criteria ==null) {
            return new OttoErrorListener(eventBus, requestId);
        }

        if ( criteria.equals(LOGIN_ERROR_LISTENER) ) {
            return new LoginOttoErrorListener(eventBus, requestId);
        }
        else {
            if (criteria.equals(REGISTER_ERROR_LISTENER))
                return new RegisterOttoErrorListener(eventBus, requestId);
        }

        return new OttoErrorListener(eventBus, requestId);
    }

}
