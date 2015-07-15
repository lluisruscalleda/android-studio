package com.thesocialcoin.networking.error;

import com.android.volley.VolleyError;
import com.thesocialcoin.networking.ottovolley.messages.VolleyRequestFailed;

/**
 * Created by identitat on 18/12/14.
 */
public class RegisterRequestFailed extends VolleyRequestFailed {

    public RegisterRequestFailed(int requestId, VolleyError error){
        super(requestId, error);
    }
}
