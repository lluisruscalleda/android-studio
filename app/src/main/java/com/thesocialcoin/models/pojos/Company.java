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

import java.util.ArrayList;
import java.util.List;
@Parcel
public class Company {

    @Expose
    private int id;
    @Expose
    private String url;
    @Expose
    private String name;
    @Expose
    private String description;
    @Expose
    private String slogan;
    @Expose
    private String color;
    @Expose
    private String logo;
    @SerializedName("num_people")
    @Expose
    private int numPeople;
    @Expose
    private List<Value> values = new ArrayList<Value>();
    @SerializedName("twitter_url")
    @Expose
    private String twitterUrl;
    @SerializedName("facebook_url")
    @Expose
    private String facebookUrl;
    @SerializedName("linkedin_url")
    @Expose
    private String linkedinUrl;
    @Expose
    private int score;

    public Company() {}

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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The slogan
     */
    public String getSlogan() {
        return slogan;
    }

    /**
     *
     * @param slogan
     * The slogan
     */
    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    /**
     *
     * @return
     * The color
     */
    public String getColor() {
        return color;
    }

    /**
     *
     * @param color
     * The color
     */
    public void setColor(String color) {
        this.color = color;
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
     * The numPeople
     */
    public int getNumPeople() {
        return numPeople;
    }

    /**
     *
     * @param numPeople
     * The num_people
     */
    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    /**
     *
     * @return
     * The values
     */
    public List<Value> getValues() {
        return values;
    }

    /**
     *
     * @param values
     * The values
     */
    public void setValues(List<Value> values) {
        this.values = values;
    }

    /**
     *
     * @return
     * The twitterUrl
     */
    public String getTwitterUrl() {
        return twitterUrl;
    }

    /**
     *
     * @param twitterUrl
     * The twitter_url
     */
    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    /**
     *
     * @return
     * The facebookUrl
     */
    public String getFacebookUrl() {
        return facebookUrl;
    }

    /**
     *
     * @param facebookUrl
     * The facebook_url
     */
    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    /**
     *
     * @return
     * The linkedinUrl
     */
    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    /**
     *
     * @param linkedinUrl
     * The linkedin_url
     */
    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
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

}
