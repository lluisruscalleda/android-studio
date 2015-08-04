package com.thesocialcoin.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.thesocialcoin.R;
import com.thesocialcoin.events.TimelineEvent;
import com.thesocialcoin.models.pojos.APITimelinePageResponse;
import com.thesocialcoin.networking.error.OttoErrorListenerFactory;
import com.thesocialcoin.networking.ottovolley.messages.VolleyRequestSuccess;
import com.thesocialcoin.requests.AppVersionedRequest;

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

    public static final int HOME_TAB_ALL = 1;
    public static final int HOME_TAB_COMPANY = 2;

    private static String TAG = HomeManager.class.getSimpleName();
    private static HomeManager instance = null;

    private HomeManager(Context context) {
        super();
    }


    public static HomeManager getInstance(Context context) {
        if (instance == null) {
            mContext = context;
            instance = new HomeManager(mContext);
        }

        return instance;
    }

    public void fetchTimeline(){
        String timelineUrl = mContext.getResources().getString(R.string.bc_api_server_url)
                + "/" + mContext.getResources().getString(R.string.bc_api_current_version)
                + "/" + mContext.getResources().getString(R.string.bc_api_timeline_endpoint);

        postEvent(new AppVersionedRequest<APITimelinePageResponse>().create(null, timelineUrl, APITimelinePageResponse.class, OttoErrorListenerFactory.ACTIVITIES_ERROR_LISTENER));
    }

    @Subscribe
    public void onAPITimelineResponseReceived(VolleyRequestSuccess<APITimelinePageResponse> response)
    {
        Log.d(TAG, "Request end: " + response.requestId);
        if (response.response instanceof APITimelinePageResponse)
        {
            Toast.makeText(mContext,
                    response.response.getResults().toString(),
                    Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Creates an event notifying that timeline has downloaded
     *
     * @return
     */
    public TimelineEvent produceTimelineDownloadedEvent()
    {
        return new TimelineEvent(TimelineEvent.Type.SUCCESS);
    }

}
