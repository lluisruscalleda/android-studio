package com.thesocialcoin.models.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dcacenabes on 27/10/14.
 */
public class Login {
    @Expose
    @SerializedName("id")
    private String token;
    @Expose
    @SerializedName("userId")
    private String userID;
    @Expose
    @SerializedName("ttl")
    private String timeToLive;
    @Expose
    @SerializedName("created")
    private String creationDate;

    public Date getCreateDate() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(this.creationDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(String timeToLive) {
        this.timeToLive = timeToLive;
    }

}
