package com.thesocialcoin.networking.error;

import com.android.volley.VolleyError;

/**
 * Created by lluisruscalleda on 20/11/14.
 */
public class UserProfileVolleyError extends VolleyErrorWrapper {


    public UserProfileVolleyError(VolleyError error) {
        super(error);
    }
}
