package com.thesocialcoin.networking.services;

/**
 * Created by lluisruscalleda on 21/10/14.
 */
import android.app.Service;
import android.os.Binder;

import java.lang.ref.WeakReference;


public class LocalBinder<S extends Service> extends Binder
{
    private WeakReference<S> mService;

    public LocalBinder(S service)
    {
        mService = new WeakReference<S>(service);
    }
    public S getService()
    {
        return mService.get();
    }
    public void close()
    {
        mService = null;
    }
}