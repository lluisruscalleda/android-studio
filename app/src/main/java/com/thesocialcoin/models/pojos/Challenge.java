package com.thesocialcoin.models.pojos;

/**
 * thesocialcoin
 * <p/>
 * Created by identitat on 06/08/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Challenge {

    @Expose
    private Integer id;
    @Expose
    private String url;
    @Expose
    private String title;
    @Expose
    private String description;
    @Expose
    private String image;
    @Expose
    private Company company;
    @Expose
    private int completed;
    @SerializedName("num_people")
    @Expose
    private Integer numPeople;
    @SerializedName("num_acts")
    @Expose
    private Integer numActs;
    @SerializedName("num_cities")
    @Expose
    private Integer numCities;
    @SerializedName("objective_value")
    @Expose
    private int objectiveValue;
    @SerializedName("objective_completed")
    @Expose
    private int objectiveCompleted;
    @Expose
    private Boolean liked;
    @SerializedName("num_likes")
    @Expose
    private Integer numLikes;

    public Challenge() {}

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
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
    public Integer getNumPeople() {
        return numPeople;
    }

    /**
     *
     * @param numPeople
     * The num_people
     */
    public void setNumPeople(Integer numPeople) {
        this.numPeople = numPeople;
    }

    /**
     *
     * @return
     * The numActs
     */
    public Integer getNumActs() {
        return numActs;
    }

    /**
     *
     * @param numActs
     * The num_acts
     */
    public void setNumActs(Integer numActs) {
        this.numActs = numActs;
    }

    /**
     *
     * @return
     * The numCities
     */
    public Integer getNumCities() {
        return numCities;
    }

    /**
     *
     * @param numCities
     * The num_cities
     */
    public void setNumCities(Integer numCities) {
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
    public Boolean getLiked() {
        return liked;
    }

    /**
     *
     * @param liked
     * The liked
     */
    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    /**
     *
     * @return
     * The numLikes
     */
    public Integer getNumLikes() {
        return numLikes;
    }

    /**
     *
     * @param numLikes
     * The num_likes
     */
    public void setNumLikes(Integer numLikes) {
        this.numLikes = numLikes;
    }

}
