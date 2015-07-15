package com.thesocialcoin.networking.error;

import com.android.volley.VolleyError;

/**
 * Created by lluisruscalleda on 20/11/14.
 */
public class AuthenticateUserVolleyError extends VolleyErrorWrapper {


    public AuthenticateUserVolleyError(VolleyError error) {
        super(error);
    }
}
