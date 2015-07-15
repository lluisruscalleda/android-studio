package com.thesocialcoin.events;

/**
 *
 * The class is marked as abstract to avoid direct creation of objects.
 * We want to use the class only as a base for our other events.
 * The _type property is defined as an Enum so that each subclass is able to describe its own types and all of them can still be used the same way across all our events.
 *
 *
 * Created by lluisruscalleda on 19/10/14.
 */
public abstract class AbstractEvent
{
    private Enum _type;

    protected AbstractEvent(Enum type)
    {
        this._type = type;
    }

    public Enum getType()
    {
        return this._type;
    }
}