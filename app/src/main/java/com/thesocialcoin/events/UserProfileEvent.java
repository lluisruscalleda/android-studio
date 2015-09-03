package com.thesocialcoin.events;

import com.thesocialcoin.models.pojos.TimelineItem;
import com.thesocialcoin.models.pojos.User;
import com.thesocialcoin.networking.error.TimelineVolleyError;
import com.thesocialcoin.networking.error.UserProfileVolleyError;

import java.util.List;

/**
 * thesocialcoin
 * <p/>
 * Created by identitat on 04/08/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class UserProfileEvent extends AbstractEvent {

    public enum Type {
        START,
        SUCCESS,
        ERROR
    }

    private UserProfileVolleyError error;
    private User data;

    public UserProfileEvent(Type type) {
        super(type);
    }

    /**
     * Event constructor with our type Volley Error associated
     *
     */
    public static UserProfileEvent UserProfileEventWithError(Type type, UserProfileVolleyError error){
        UserProfileEvent event = new UserProfileEvent(type);
        event.setError(error);

        return event;
    }

    /**
     * Event constructor with data associated
     *
     */
    public static UserProfileEvent UserProfileEventWithData(Type type, User data){
        UserProfileEvent event = new UserProfileEvent(type);
        event.setData(data);

        return event;
    }

    public UserProfileVolleyError getError(){return error;}
    public void setError(UserProfileVolleyError error){this.error = error;}

    public User getData(){return data;}
    public void setData(User data){this.data = data;}
}
