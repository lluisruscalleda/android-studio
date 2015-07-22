package com.thesocialcoin.networking.core;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.thesocialcoin.App;
import com.thesocialcoin.networking.ottovolley.core.OttoGsonResponseBuffer;
import com.thesocialcoin.networking.volleyextensions.VolleySingleton;


public class RequestManager {
    public static AndroidBus EventBus;
    public static RequestQueue VolleyRequestQueue;
    public static OttoGsonResponseBuffer ResponseBuffer;
    private static boolean _isInitialized;
    private static Context mContext;

    public static final int REQUEST_TIMEOUT_MS = 10000;
    
    public static void ensureInitialized(Context context) {
        if (!_isInitialized) {
            _isInitialized = true;
            mContext = context;
            VolleyRequestQueue = VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue();
            EventBus = new AndroidBus();
            ResponseBuffer = new OttoGsonResponseBuffer(EventBus);
        }
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     *
     * @param req
     * @param tag
     */
    public static <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? App.TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        VolleyRequestQueue.add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     * @param tag
     */
    public static <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(App.TAG);

        VolleyRequestQueue.add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public static void cancelPendingRequests(Object tag) {
        if (VolleyRequestQueue != null) {
            VolleyRequestQueue.cancelAll(tag);
        }
    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public static RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (VolleyRequestQueue == null) {
            VolleyRequestQueue = VolleySingleton.getInstance(mContext.getApplicationContext()).getRequestQueue();
        }

        return VolleyRequestQueue;
    }

    /* To register/unregister to the Bus */
    public static void registerServiceBus(Context context, boolean register) {
        if (register) {
            RequestManager.EventBus.register(context);
            RequestManager.ResponseBuffer.stopAndProcess();
        }
        else {
            RequestManager.ResponseBuffer.startSaving();
            RequestManager.EventBus.unregister(context);
        }
    }



}
