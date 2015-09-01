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
public class TimelineItem {

    @Expose
    String url;
    @Expose
    int id;
    @Expose
    User user;
    @SerializedName("num_comments")
    @Expose
    int numComments;
    @Expose
    String photo;
    @Expose
    Value value;
    @Expose
    Ripple ripple;
    @Expose
    String description;
    @Expose
    String latitude;
    @Expose
    String longitude;
    @Expose
    Place place;
    @SerializedName("created_at")
    @Expose
    String createdAt;
    @Expose
    boolean anonymous;
    @Expose
    String kind;
    @Expose
    private String title;
    @Expose
    private String image;
    @Expose
    private Company company;
    @Expose
    private int completed;
    @SerializedName("num_people")
    @Expose
    private int numPeople;
    @SerializedName("num_acts")
    @Expose
    private int numActs;
    @SerializedName("num_cities")
    @Expose
    private int numCities;
    @SerializedName("objective_value")
    @Expose
    private int objectiveValue;
    @SerializedName("objective_completed")
    @Expose
    private int objectiveCompleted;
    @Expose
    private boolean liked;
    @SerializedName("num_likes")
    @Expose
    private int numLikes;

    public TimelineItem(){ /*Required empty bean constructor for parceler lib*/ }

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
    public Value getValue() {
        return value;
    }

    /**
     *
     * @param value
     * The value
     */
    public void setValue(Value value) {
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
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
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
     * The completed
     */
    public int getCompleted() {
        return completed;
    }

    /**
     *
     * @param completed
     * The completed
     */
    public void setCompleted(int completed) {
        this.completed = completed;
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

    /**
     *
     * @return
     * The objectiveValue
     */
    public int getObjectiveValue() {
        return objectiveValue;
    }

    /**
     *
     * @param objectiveValue
     * The objective_value
     */
    public void setObjectiveValue(int objectiveValue) {
        this.objectiveValue = objectiveValue;
    }

    /**
     *
     * @return
     * The objectiveCompleted
     */
    public int getObjectiveCompleted() {
        return objectiveCompleted;
    }

    /**
     *
     * @param objectiveCompleted
     * The objective_completed
     */
    public void setObjectiveCompleted(int objectiveCompleted) {
        this.objectiveCompleted = objectiveCompleted;
    }

    /**
     *
     * @return
     * The liked
     */
    public boolean getLiked() {
        return liked;
    }

    /**
     *
     * @param liked
     * The liked
     */
    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    /**
     *
     * @return
     * The numLikes
     */
    public int getNumLikes() {
        return numLikes;
    }

    /**
     *
     * @param numLikes
     * The num_likes
     */
    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }
}