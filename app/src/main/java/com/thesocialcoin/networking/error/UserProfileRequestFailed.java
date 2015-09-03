package com.thesocialcoin.networking.error;

import com.thesocialcoin.networking.ottovolley.messages.VolleyRequestFailed;

/**
 * Created by identitat on 18/12/14.
 */
public class UserProfileRequestFailed extends VolleyRequestFailed {

    private String msg;

    public UserProfileRequestFailed(int requestId, VolleyErrorWrapper error){
        super(requestId, error.getError());
        this.msg = error.getErrorMessage();
    }

    public String getErrorMessage(){
        return msg;
    }
}
