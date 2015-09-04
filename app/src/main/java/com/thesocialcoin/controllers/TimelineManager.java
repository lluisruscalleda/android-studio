package com.thesocialcoin.controllers;

import android.content.Context;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.thesocialcoin.R;
import com.thesocialcoin.events.TimelineEvent;
import com.thesocialcoin.models.pojos.APITimelinePageResponse;
import com.thesocialcoin.models.pojos.TimelineItem;
import com.thesocialcoin.networking.core.RequestManager;
import com.thesocialcoin.networking.error.OttoErrorListenerFactory;
import com.thesocialcoin.networking.error.TimelineRequestFailed;
import com.thesocialcoin.networking.error.TimelineVolleyError;
import com.thesocialcoin.networking.ottovolley.core.OttoGsonRequest;
import com.thesocialcoin.networking.ottovolley.messages.VolleyRequestSuccess;
import com.thesocialcoin.requests.AppVersionedGetRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * thesocialcoin
 * <p/>
 * Created by identitat on 03/08/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class TimelineManager extends BaseManager {

    /**
     * Manager for Home functionalities
     *
     *
     */
    public static String tabTitles[] = new String[] { "ALL", "MY COMPANY" };

    // Home tabs for listing all ripples or only yours
    public static final int HOME_TAB_ALL = 1;
    public static final int HOME_TAB_COMPANY = 2;


    // Timeline Riple Lists : ALL , YOUR COMPANY
    private static List<TimelineItem> mAllTimelineRipples = new ArrayList<TimelineItem>();
    private static List<TimelineItem> mUserCompanyTimelineRipples = new ArrayList<TimelineItem>();



    // Different ripple types
    public static final int NORMAL_ACT = 0;
    public static final int COMPANY_ACT = 1;
    public static final int CHALLENGE_ACT = 2;

    public static final String KIND_ACT = "act";
    public static final String KIND_CHALLENGE = "challenge";

    //we use an int request state for fecthing switch all or user company acts
    private static int mFetchingAllTimeline = 0;
    private static int mFetchingUserCompanyTimeline = 0;

    private static String TAG = TimelineManager.class.getSimpleName();
    private static TimelineManager instance = null;

    private TimelineManager(Context context) {
        super();
        RequestManager.EventBus.register(this);
    }


    public static TimelineManager getInstance(Context context) {
        if (instance == null) {
            mContext = context;
            instance = new TimelineManager(mContext);
        }

        return instance;
    }

    public static boolean isFetchingAllTimeline(){
        return (mFetchingAllTimeline==0)?false:true;
    }
    public static boolean isFetchingUserCompanyTimeline(){
        return (mFetchingUserCompanyTimeline==0)?false:true;
    }

    public static List<TimelineItem> getAllTimelineActs(){
        Log.d(TAG, "getAllTimelineActs: " + mAllTimelineRipples.toString());
        return mAllTimelineRipples;
    }
    public static List<TimelineItem> getUserCompanyTimelineActs(){
        return mUserCompanyTimelineRipples;
    }

    /* Get Timeline ripples by Home page selected */
    public static List<TimelineItem> getTimelineRipplesByHomePage(int page){
        switch (page){
            case HOME_TAB_ALL:
                return getAllTimelineActs();

            case HOME_TAB_COMPANY:
                return getUserCompanyTimelineActs();

        }

        return new ArrayList<TimelineItem>();
    }

    /* Fetch all acts */
    public static void fetchActs(){
        TimelineManager.getInstance(mContext).fetchAllTimeline();
        if(UserProfileManager.getInstance(mContext).hasCompany()) {
            TimelineManager.getInstance(mContext).fetchUserCompanyTimeline();
        }
    }

    /**
     * Method to fetch all timeline act from API
     */
    public void fetchAllTimeline(){
        if(!isFetchingAllTimeline()) {
            String timelineUrl = mContext.getResources().getString(R.string.bc_api_server_url)
                    + mContext.getResources().getString(R.string.bc_api_current_version)
                    + "/" + mContext.getResources().getString(R.string.bc_api_timeline_endpoint);

            OttoGsonRequest request = new AppVersionedGetRequest<APITimelinePageResponse>().create(null, timelineUrl, APITimelinePageResponse.class, OttoErrorListenerFactory.TIMELINE_ERROR_LISTENER);
            RequestManager.addToRequestQueue(request);
            mFetchingAllTimeline = request.requestId;

            //Gson gson = new Gson();
            //APITimelinePageResponse timelinePage = gson.fromJson( LoadJSONFromAsset.load("timeline"), APITimelinePageResponse.class);
            //mAllTimelineRipples = Arrays.asList(timelinePage.getResults());
            //postEvent(produceAllTimelineDownloadedEvent());
            //mFetchingAllTimeline = 0;
        }
    }

    /**
     * Method to fetch user company timeline act from API
     */
    public void fetchUserCompanyTimeline(){
        if(!isFetchingUserCompanyTimeline()) {
            String timelineUrl = mContext.getResources().getString(R.string.bc_api_server_url)
                + mContext.getResources().getString(R.string.bc_api_current_version)
                + "/" + mContext.getResources().getString(R.string.bc_api_timeline_endpoint) +"?company_id=%d";
            if(UserProfileManager.getInstance(mContext).getUserProfile().getCompany() != null){
                String companyUrl = String.format(timelineUrl, UserProfileManager.getInstance(mContext).getUserProfile().getCompany().getId());
                OttoGsonRequest request = new AppVersionedGetRequest<APITimelinePageResponse>().create(null, companyUrl, APITimelinePageResponse.class, OttoErrorListenerFactory.TIMELINE_ERROR_LISTENER);
                RequestManager.addToRequestQueue(request);
                mFetchingUserCompanyTimeline = request.requestId;
            }
        }
    }

    @Subscribe
    public void onAPITimelineResponseReceived(VolleyRequestSuccess<APITimelinePageResponse> response)
    {
        Log.d(TAG, "Request end: " + response.requestId);
        if (response.response instanceof APITimelinePageResponse) {

            if (mFetchingAllTimeline == response.requestId){
                mAllTimelineRipples = Arrays.asList(response.response.getResults());
                postEvent(produceAllTimelineDownloadedEvent());
                mFetchingAllTimeline = 0;
            }else if(mFetchingUserCompanyTimeline == response.requestId){
                mUserCompanyTimelineRipples = Arrays.asList(response.response.getResults());
                postEvent(produceCompanyTimelineDownloadedEvent());
                mFetchingUserCompanyTimeline = 0;
            }
        }
    }

    /**
     *
     * Timeline request error response received subscription
     */
    @Subscribe
    public void onHttpErrorResponseReceived(TimelineRequestFailed requestError)
    {
        Log.d(TAG, "Request end with error: " + requestError.requestId);
        Log.d(TAG, "Volley error : " + requestError.error.toString());
        Log.d(TAG, "Volley error : " + requestError.error.getMessage());

        if (mFetchingAllTimeline == requestError.requestId){
            // post login failed event
            postEvent(produceAllTimelineDownloadErrorEvent(new TimelineVolleyError(requestError.error)));
            mFetchingAllTimeline = 0;
        }else if(mFetchingUserCompanyTimeline == requestError.requestId){
            postEvent(produceUserCompanyTimelineDownloadErrorEvent(new TimelineVolleyError(requestError.error)));
            mFetchingAllTimeline = 0;
        }
    }

    /**
     * Creates an event notifying that all timeline has downloaded
     *
     * @return
     */
    public TimelineEvent produceAllTimelineDownloadedEvent()
    {
        return new TimelineEvent(TimelineEvent.Type.SUCCESS_ALL);
    }
    /**
     * Creates an event notifying that your company timeline has downloaded
     *
     * @return
     */
    public TimelineEvent produceCompanyTimelineDownloadedEvent()
    {
        return new TimelineEvent(TimelineEvent.Type.SUCCESS_CO);
    }

    /**
     * Creates an even for sign in errors
     *
     * @return
     */
    public TimelineEvent produceAllTimelineDownloadErrorEvent(TimelineVolleyError error)
    {
        return TimelineEvent.TimelineDownloadEventWithError(TimelineEvent.Type.ERROR_ALL, error);
    }
    /**
     * Creates an even for sign in errors
     *
     * @return
     */
    public TimelineEvent produceUserCompanyTimelineDownloadErrorEvent(TimelineVolleyError error)
    {
        return TimelineEvent.TimelineDownloadEventWithError(TimelineEvent.Type.ERROR_CO, error);
    }

    /**
     * Creates an event notifying all timeline ripples list
     *
     * @return
     */
    public List<TimelineItem> produceAllTimelineRipples()
    {
        return mAllTimelineRipples;
    }
}
