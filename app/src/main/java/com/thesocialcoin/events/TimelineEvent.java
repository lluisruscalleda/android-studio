package com.thesocialcoin.events;

import com.thesocialcoin.models.pojos.TimelineItem;
import com.thesocialcoin.networking.error.TimelineVolleyError;

import java.util.List;

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
        ERROR_YOURS,
        DO_UPDATE_ALL,  /* communicate to home page fragment ALL that has to update */
        DO_UPDATE_YOURS, /* communicate to home page fragment YOURS that has to update  */
        DO_UPDATE_CO, /* communicate to home page fragment CO that has to update */
        DO_ERROR_ALL,
        DO_ERROR_YOURS,
        DO_ERROR_CO

    }

    private TimelineVolleyError error;
    private List<TimelineItem> data;

    public TimelineEvent(Type type) {
        super(type);
    }

    /**
     * Event constructor with our type Volley Error associated
     *
     */
    public static TimelineEvent TimelineDownloadEventWithError(Type type, TimelineVolleyError error){
        TimelineEvent event = new TimelineEvent(type);
        event.setError(error);

        return event;
    }

    /**
     * Event constructor with data associated
     *
     */
    public static TimelineEvent TimelineDownloadEventWithData(Type type, List<TimelineItem> data){
        TimelineEvent event = new TimelineEvent(type);
        event.setData(data);

        return event;
    }

    public TimelineVolleyError getError(){return error;}
    public void setError(TimelineVolleyError error){this.error = error;}

    public List<TimelineItem> getData(){return data;}
    public void setData(List<TimelineItem> data){this.data = data;}
}
