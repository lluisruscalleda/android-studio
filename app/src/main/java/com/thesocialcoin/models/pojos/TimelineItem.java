package com.thesocialcoin.models.pojos;

/**
 * thesocialcoin
 * <p/>
 * Created by identitat on 04/08/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimelineItem {

    @Expose
    private String url;
    @Expose
    private int id;
    @Expose
    private User user;
    @SerializedName("num_comments")
    @Expose
    private int numComments;
    @Expose
    private String photo;
    @Expose
    private int value;
    @Expose
    private Ripple ripple;
    @Expose
    private String description;
    @Expose
    private String latitude;
    @Expose
    private String longitude;
    @Expose
    private Place place;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @Expose
    private boolean anonymous;
    @Expose
    private String kind;

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
     * The numComments
     */
    public int getNumComments() {
        return numComments;
    }

    /**
     *
     * @param numComments
     * The num_comments
     */
    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    /**
     *
     * @return
     * The photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     *
     * @param photo
     * The photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     *
     * @return
     * The value
     */
    public int getValue() {
        return value;
    }

    /**
     *
     * @param value
     * The value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     *
     * @return
     * The ripple
     */
    public Ripple getRipple() {
        return ripple;
    }

    /**
     *
     * @param ripple
     * The ripple
     */
    public void setRipple(Ripple ripple) {
        this.ripple = ripple;
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
     * The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     * The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     * The place
     */
    public Place getPlace() {
        return place;
    }

    /**
     *
     * @param place
     * The place
     */
    public void setPlace(Place place) {
        this.place = place;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The anonymous
     */
    public boolean isAnonymous() {
        return anonymous;
    }

    /**
     *
     * @param anonymous
     * The anonymous
     */
    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    /**
     *
     * @return
     * The kind
     */
    public String getKind() {
        return kind;
    }

    /**
     *
     * @param kind
     * The kind
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

}