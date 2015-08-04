package com.thesocialcoin.events;

import com.thesocialcoin.networking.error.TimelineVolleyError;

/**
 * thesocialcoin
 * <p/>
 * Created by identitat on 04/08/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class TimelineEvent extends AbstractEvent {

    public enum Type {
        START_ALL,
        SUCCESS_ALL,
        ERROR_ALL,
        START_CO,
        SUCCESS_CO,
        ERROR_CO,
        START_YOURS,
        SUCCESS_YOURS,
        ERROR_YOURS
    }

    private TimelineVolleyError error;

    public TimelineEvent(Type type) {
        super(type);
    }

    public static TimelineEvent TimelineDownloadEventWithError(Type type, TimelineVolleyError error){
        TimelineEvent event = new TimelineEvent(type);
        event.setError(error);

        return event;
    }

    public TimelineVolleyError getError(){return error;}
    public void setError(TimelineVolleyError error){this.error = error;}
}
