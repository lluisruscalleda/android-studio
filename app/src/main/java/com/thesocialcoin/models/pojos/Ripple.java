package com.thesocialcoin.models.pojos;

/**
 * thesocialcoin
 * <p/>
 * Created by identitat on 04/08/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Ripple {

    @Expose
    String url;
    @Expose
    int id;
    @Expose
    String title;
    @Expose
    String code;
    @SerializedName("user_id")
    @Expose
    int userId;
    @Expose
    User user;
    @Expose
    Company company;
    @SerializedName("activated_at")
    @Expose
    String activatedAt;
    @Expose
    Object challenge;
    @Expose
    String logo;
    @Expose
    int score;
    @SerializedName("num_acts")
    @Expose
    int numActs;
    @SerializedName("num_users")
    @Expose
    int numUsers;
    @SerializedName("num_cities")
    @Expose
    int numCities;

    public Ripple(){ /*Required empty bean constructor for parceler lib*/ }


    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The code
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @return
     * The userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     * The company
     */
    public Company getCompany() {
        return company;
    }

    /**
     *
     * @param company
     * The company
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     *
     * @return
     * The activatedAt
     */
    public String getActivatedAt() {
        return activatedAt;
    }

    /**
     *
     * @param activatedAt
     * The activated_at
     */
    public void setActivatedAt(String activatedAt) {
        this.activatedAt = activatedAt;
    }

    /**
     *
     * @return
     * The challenge
     */
    public Object getChallenge() {
        return challenge;
    }

    /**
     *
     * @param challenge
     * The challenge
     */
    public void setChallenge(Object challenge) {
        this.challenge = challenge;
    }

    /**
     *
     * @return
     * The logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     *
     * @param logo
     * The logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     *
     * @return
     * The score
     */
    public int getScore() {
        return score;
    }

    /**
     *
     * @param score
     * The score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     *
     * @return
     * The numActs
     */
    public int getNumActs() {
        return numActs;
    }

    /**
     *
     * @param numActs
     * The num_acts
     */
    public void setNumActs(int numActs) {
        this.numActs = numActs;
    }

    /**
     *
     * @return
     * The numUsers
     */
    public int getNumUsers() {
        return numUsers;
    }

    /**
     *
     * @param numUsers
     * The num_users
     */
    public void setNumUsers(int numUsers) {
        this.numUsers = numUsers;
    }

    /**
     *
     * @return
     * The numCities
     */
    public int getNumCities() {
        return numCities;
    }

    /**
     *
     * @param numCities
     * The num_cities
     */
    public void setNumCities(int numCities) {
        this.numCities = numCities;
    }

}
