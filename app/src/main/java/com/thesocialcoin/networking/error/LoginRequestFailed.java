package com.thesocialcoin.networking.error;

import com.thesocialcoin.networking.ottovolley.messages.VolleyRequestFailed;

/**
 * Created by identitat on 18/12/14.
 */
public class LoginRequestFailed extends VolleyRequestFailed {

    private String msg;

    public LoginRequestFailed(int requestId, VolleyErrorWrapper error){
        super(requestId, error.getError());
        this.msg = error.getErrorMessage();
    }

    public String getErrorMessage(){
        return msg;
    }
}
