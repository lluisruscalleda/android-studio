package com.thesocialcoin.models.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 15/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class Register {
    @Expose
    @SerializedName("id")
    private String token;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("ttl")
    private String timeToLive;
    @Expose
    @SerializedName("projectId")
    private String projectId;



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(String timeToLive) {
        this.timeToLive = timeToLive;
    }

}
