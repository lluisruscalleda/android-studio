package com.thesocialcoin.events;

/**
 * Created by identitat on 21/10/14.
 */
public class SharedPreferencesEvent extends AbstractEvent
{
    public enum Type
    {
        COMPLETE,
        START
    }

    public SharedPreferencesEvent(Type type) {
        super(type);
    }


}
