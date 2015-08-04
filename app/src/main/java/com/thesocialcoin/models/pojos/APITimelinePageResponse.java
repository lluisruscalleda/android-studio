package com.thesocialcoin.models.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * thesocialcoin
 * <p/>
 * Created by identitat on 04/08/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class APITimelinePageResponse {

    @Expose
    @SerializedName("next")
    private String next;
    @Expose
    @SerializedName("previous")
    private String previous;
    @Expose
    @SerializedName("results")
    private TimelineItem[] results;

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public TimelineItem[] getResults() {
        return results;
    }

    public void setResults(TimelineItem[] previous) {
        this.results = results;
    }

    public static APITimelinePageResponse create(String serializedData){

        iPojo<APITimelinePageResponse> jsonObject = new iPojo<APITimelinePageResponse>();
        return (APITimelinePageResponse) jsonObject.create(serializedData, APITimelinePageResponse.class);
    }

}
