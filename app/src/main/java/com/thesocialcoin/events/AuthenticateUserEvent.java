package com.thesocialcoin.events;

import com.thesocialcoin.models.pojos.Login;
import com.thesocialcoin.networking.error.AuthenticateUserVolleyError;


/**
 * Created by lluisruscalleda on 20/11/14.
 */
public class AuthenticateUserEvent extends AbstractEvent {

    public enum Type {
        START,
        SUCCESS,
        ERROR,
        LOGOUT_START,
        LOGOUT_SUCCESS,
        LOGOUT_ERROR
    }

    private Login userLoginData;
    private AuthenticateUserVolleyError error;

    public AuthenticateUserEvent(Type type) {
        super(type);
    }
    public static AuthenticateUserEvent AuthenticateUserEventWithUserData(Type type, Login userLoginData) {
        AuthenticateUserEvent event = new AuthenticateUserEvent(type);
        event.setLoginData(userLoginData);

        return event;
    }
    public static AuthenticateUserEvent AuthenticateUserEventWithError(Type type, AuthenticateUserVolleyError error){
        AuthenticateUserEvent event = new AuthenticateUserEvent(type);
        event.setError(error);

        return event;
    }

    public Login getLoginData(){return userLoginData;}
    public void setLoginData(Login userLoginData){this.userLoginData = userLoginData;}

    public AuthenticateUserVolleyError getError(){return error;}
    public void setError(AuthenticateUserVolleyError error){this.error = error;}
}
