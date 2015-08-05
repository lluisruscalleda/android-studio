package com.thesocialcoin.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.thesocialcoin.R;
import com.thesocialcoin.events.TimelineEvent;
import com.thesocialcoin.models.pojos.APITimelinePageResponse;
import com.thesocialcoin.models.pojos.TimelineItem;
import com.thesocialcoin.networking.core.RequestManager;
import com.thesocialcoin.networking.error.OttoErrorListenerFactory;
import com.thesocialcoin.networking.error.TimelineRequestFailed;
import com.thesocialcoin.networking.error.TimelineVolleyError;
import com.thesocialcoin.networking.ottovolley.messages.VolleyRequestSuccess;
import com.thesocialcoin.requests.AppVersionedRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * thesocialcoin
 * <p/>
 * Created by identitat on 03/08/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class HomeManager extends BaseManager {

    /**
     * Manager for Home functionalities
     *
     *
     */

    // Home tabs for listing all ripples or only yours
    public static final int HOME_TAB_ALL = 1;
    public static final int HOME_TAB_COMPANY = 2;


    // Timeline Riple Lists : ALL , YOUR COMPANY
    private static List<TimelineItem> mAllTimelineRipples = new ArrayList<TimelineItem>();
    private static List<TimelineItem> mUserCompanyTimelineRipples = new ArrayList<TimelineItem>();


    // Different ripple types
    public static final int NORMAL_RIPPLE = 0;
    public static final int COMPANY_RIPPLE = 1;

    private static boolean mFetchingTimeline = false;

    private static String TAG = HomeManager.class.getSimpleName();
    private static HomeManager instance = null;

    private HomeManager(Context context) {
        super();
        RequestManager.EventBus.register(this);
    }


    public static HomeManager getInstance(Context context) {
        if (instance == null) {
            mContext = context;
            instance = new HomeManager(mContext);
        }

        return instance;
    }

    public static boolean isFetchingTimeline(){
        return mFetchingTimeline;
    }

    public static List<TimelineItem> getAllTimelineRipples(){
        return mAllTimelineRipples;
    }
    public static List<TimelineItem> getUserCompanyTimelineRipples(){
        return mUserCompanyTimelineRipples;
    }

    /* Get Timeline ripples by Home page selected */
    public static List<TimelineItem> getTimelineRipplesByHomePage(int page){
        switch (page){
            case HOME_TAB_ALL:
                return getAllTimelineRipples();

            case HOME_TAB_COMPANY:
                return getUserCompanyTimelineRipples();

        }

        return new ArrayList<TimelineItem>();

    }

    /**
     * Method to fetch all timeline ripples from API
     */
    public void fetchAllTimeline(){
        //if(!mFetchingTimeline) {
            mFetchingTimeline = true;
            String timelineUrl = mContext.getResources().getString(R.string.bc_api_server_url)
                    + mContext.getResources().getString(R.string.bc_api_current_version)
                    + "/" + mContext.getResources().getString(R.string.bc_api_timeline_endpoint);

        RequestManager.addToRequestQueue(new AppVersionedRequest<APITimelinePageResponse>().create(null, timelineUrl, APITimelinePageResponse.class, OttoErrorListenerFactory.TIMELINE_ERROR_LISTENER));
        //}
    }

    @Subscribe
    public void onAPITimelineResponseReceived(VolleyRequestSuccess<APITimelinePageResponse> response)
    {
        Log.d(TAG, "Request end: " + response.requestId);
        if (response.response instanceof APITimelinePageResponse)
        {
            mAllTimelineRipples = Arrays.asList(response.response.getResults());
            produceAllTimelineDownloadedEvent();
            mFetchingTimeline = false;

            Toast.makeText(mContext,
                    String.valueOf(mAllTimelineRipples.size()),
                    Toast.LENGTH_LONG).show();
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

        mFetchingTimeline = false;

        // post login failed event
        postEvent(produceDownloadTimelineErrorEvent(new TimelineVolleyError(requestError.error)));
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
    public TimelineEvent produceDownloadTimelineErrorEvent(TimelineVolleyError error)
    {
        return TimelineEvent.TimelineDownloadEventWithError(TimelineEvent.Type.ERROR_ALL, error);
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
