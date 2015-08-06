package com.thesocialcoin;

import android.test.InstrumentationTestCase;
import android.util.Log;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.thesocialcoin.models.pojos.APITimelinePageResponse;
import com.thesocialcoin.networking.core.RequestManager;
import com.thesocialcoin.networking.error.OttoErrorListenerFactory;
import com.thesocialcoin.networking.ottovolley.messages.VolleyRequestSuccess;
import com.thesocialcoin.requests.AppVersionedGetRequest;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;

/**
 * thesocialcoin
 * <p/>
 * Created by identitat on 04/08/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class HomeManagerTest extends InstrumentationTestCase {

    private final static String TAG = "HomeManagerTest";

    public HomeManagerTest(){

    }

    protected static void postEvent(Object event){
        RequestManager.EventBus.post(event);
    }

    public void testGetTimeline_then200IsReceived()
            throws ClientProtocolException, IOException {

        RequestManager.ensureInitialized(getInstrumentation().getContext());
        RequestManager.EventBus.register(getInstrumentation().getContext());

        //do login to get session


        String endpoint = "http://151.80.235.34:8080/v0.1/timeline";

        postEvent(new AppVersionedGetRequest<APITimelinePageResponse>().create(null, endpoint, APITimelinePageResponse.class, OttoErrorListenerFactory.ACTIVITIES_ERROR_LISTENER));

    }

    @Subscribe
    public void onAPITimelineResponseReceived(VolleyRequestSuccess<APITimelinePageResponse> response)
    {
        Log.d(TAG, "Request end: " + response.requestId);
        if (response.response instanceof APITimelinePageResponse)
        {
            Toast.makeText(getInstrumentation().getContext(),
                    response.response.getResults().toString(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
