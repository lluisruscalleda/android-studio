package com.thesocialcoin.networking.volleyextensions;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.thesocialcoin.controllers.ApplicationController;

/**
 * Created by lluisruscalleda on 15/10/14.
 */
public class VolleySingleton {

    private static VolleySingleton instance = null;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    LruBitmapCache mLruBitmapCache;

    private VolleySingleton(Context context) {
        requestQueue = Volley.newRequestQueue(ApplicationController.getAppContext());

        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruBitmapCache cache = new LruBitmapCache();

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }


    public static VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }


    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ApplicationController.getAppContext());
        }

        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (imageLoader == null) {
            getLruBitmapCache();
            imageLoader = new ImageLoader(this.requestQueue, mLruBitmapCache);
        }

        return this.imageLoader;
    }

    public LruBitmapCache getLruBitmapCache() {
        if (mLruBitmapCache == null)
            mLruBitmapCache = new LruBitmapCache();
        return this.mLruBitmapCache;
    }

}