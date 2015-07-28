package com.thesocialcoin.networking.error;

import com.android.volley.VolleyError;
import com.thesocialcoin.App;
import com.thesocialcoin.networking.helpers.VolleyErrorHelper;

/**
 * Created by lluisruscalleda on 19/10/14.
 */
public abstract class VolleyErrorWrapper {


    private VolleyError error;


    public VolleyErrorWrapper(VolleyError error){
        this.error = error;
    }

    /**
     * @return
     * 		Code for the error.
     */
    public int getErrorCode(){
        if(error!=null && error.networkResponse!=null){
            return error.networkResponse.statusCode;
        }
        return -1;
    }
    /**
     * @return
     * 		Message for the error.
     */
    public String getErrorMessage(){
        return (error.getMessage() != null)?error.getMessage(): VolleyErrorHelper.getErrorType(error, App.getAppContext());
    }

    public VolleyError getError(){
        return error;
    }


}
