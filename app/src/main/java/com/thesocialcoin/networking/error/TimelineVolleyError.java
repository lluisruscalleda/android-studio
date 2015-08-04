package com.thesocialcoin.networking.error;

import com.android.volley.VolleyError;

/**
 * Created by lluisruscalleda on 20/11/14.
 */
public class TimelineVolleyError extends VolleyErrorWrapper {


    public TimelineVolleyError(VolleyError error) {
        super(error);
    }
}
