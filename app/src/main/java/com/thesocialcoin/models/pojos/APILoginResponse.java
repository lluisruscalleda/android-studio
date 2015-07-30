package com.thesocialcoin.models.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 15/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class APILoginResponse {
    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("user")
    private User user;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static APILoginResponse create(String serializedData){

        iPojo<APILoginResponse> jsonObject = new iPojo<APILoginResponse>();
        return (APILoginResponse) jsonObject.create(serializedData, APILoginResponse.class);
    }

}
