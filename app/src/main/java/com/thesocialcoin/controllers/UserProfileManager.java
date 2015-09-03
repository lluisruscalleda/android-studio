package com.thesocialcoin.controllers;

import android.content.Context;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.thesocialcoin.R;
import com.thesocialcoin.events.UserProfileEvent;
import com.thesocialcoin.models.pojos.APITimelinePageResponse;
import com.thesocialcoin.models.pojos.TimelineItem;
import com.thesocialcoin.models.pojos.User;
import com.thesocialcoin.networking.core.RequestManager;
import com.thesocialcoin.networking.error.OttoErrorListenerFactory;
import com.thesocialcoin.networking.ottovolley.core.OttoGsonRequest;
import com.thesocialcoin.networking.ottovolley.messages.VolleyRequestSuccess;
import com.thesocialcoin.requests.AppVersionedGetRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lluisruscalleda on 03/09/15.
 */
public class UserProfileManager extends BaseManager {

    /**
     * Manager for User Profile functionalities
     *
     *
     */

    private static String TAG = UserProfileManager.class.getSimpleName();
    private static UserProfileManager instance = null;

    private UserProfileManager(Context context) {
        super();
        RequestManager.EventBus.register(this);
    }


    public static UserProfileManager getInstance(Context context) {
        if (instance == null) {
            mContext = context;
            instance = new UserProfileManager(mContext);
        }

        return instance;
    }

    private static User mUserProfile = new User();
    private static boolean mFetchingUserProfile = false;
    private static boolean mHasCompany = false;

    public static User getUserProfile(){
        return mUserProfile;
    }
    public static boolean isFetchingUserProfile(){
        return mFetchingUserProfile;
    }
    public static boolean hasCompany(){
        return mHasCompany;
    }
    /**
     * Method to fetch User Profile data from API
     */
    public void fetchUserProfile(){
        if(!isFetchingUserProfile()) {
            String url = mContext.getResources().getString(R.string.bc_api_server_url)
                    + mContext.getResources().getString(R.string.bc_api_current_version)
                    + "/" + mContext.getResources().getString(R.string.bc_api_user_info_endpoint);

            OttoGsonRequest request = new AppVersionedGetRequest<User>().create(null, url, User.class, OttoErrorListenerFactory.USERPROFILE_ERROR_LISTENER);
            RequestManager.addToRequestQueue(request);
            mFetchingUserProfile = true;
        }
    }

    @Subscribe
    public void onAPIUserProfileResponseReceived(VolleyRequestSuccess<User> response)
    {
        Log.d(TAG, "Request end: " + response.requestId);
        if (response.response instanceof User) {

            mUserProfile = response.response;
            mFetchingUserProfile = false;
            if(mUserProfile.getCompany() != null){
                mHasCompany = true;
            }
            postEvent(new UserProfileEvent(UserProfileEvent.Type.SUCCESS));
        }
    }
}
