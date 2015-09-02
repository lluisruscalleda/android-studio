package com.thesocialcoin.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.squareup.otto.Subscribe;
import com.thesocialcoin.R;
import com.thesocialcoin.adapters.HomeTimelineAdapter;
import com.thesocialcoin.controllers.TimelineManager;
import com.thesocialcoin.events.TimelineEvent;
import com.thesocialcoin.models.pojos.TimelineItem;
import com.thesocialcoin.networking.core.RequestManager;
import com.thesocialcoin.utils.RecyclerViewPositionHelper;
import com.thesocialcoin.views.RecycleEmptyErrorView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.parceler.Parcels;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * thesocialcoin
 * <p/>
 * Created by identitat on 03/08/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class HomeListPageFragment extends Fragment {

    private static String TAG = HomeListPageFragment.class.getSimpleName();

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_ITEM_LIST = "ARG_ITEM_LIST";

    private Handler handler = new Handler();
    RecyclerViewPositionHelper mRecyclerViewHelper;


    @Bind(R.id.swipe_container)
    SwipeRefreshLayout mSwipeContainer;
    @Bind(R.id.empty_view)
    RelativeLayout mEmptyView;
    @Bind(R.id.error_view)
    RelativeLayout mErrorView;
    @Bind(R.id.rv_timeline)
    RecycleEmptyErrorView timelineRecyclerView;

    /* Bundle Args injected reading */
    @Arg
    int mPage;
    @Arg
    Parcelable mTimelineRipples;

    private HomeTimelineAdapter mHomeTimelineAdapter;
    private List<TimelineItem> items = new ArrayList<TimelineItem>();


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        RequestManager.EventBus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
        RequestManager.EventBus.unregister(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        FragmentArgs.inject(this);

    }
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        //mostrem el loading de refresh mentres esta esperant resposta del server
//        mSwipeContainer.post(new Runnable() {
//            @Override
//            public void run() {
//                mSwipeContainer.setRefreshing(true);
//            }
//        });
//    }


    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        ButterKnife.bind(this, view);

        items = Parcels.unwrap(mTimelineRipples);

        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        timelineRecyclerView.setLayoutManager(llm);
        mSwipeContainer.setEnabled(false);

        setupAdapter();

        mSwipeContainer.setColorSchemeResources(R.color.light_lake, R.color.soft_lake, R.color.hard_lake);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeContainer.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        mSwipeContainer.setRefreshing(false);
                    }
                }, 2500);
            }
        });

        //we assign an OnScrollListener to our ListView after the initialization of our swipe to refresh layout
        timelineRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mSwipeContainer.setEnabled(llm.findFirstCompletelyVisibleItemPosition() == 0);
            }
        });


        return view;
    }

    private void setupAdapter() {
        // adding items to the recycler view
        mHomeTimelineAdapter = new HomeTimelineAdapter(items);
        timelineRecyclerView.setAdapter(mHomeTimelineAdapter);
        timelineRecyclerView.setEmptyView(mEmptyView);
        timelineRecyclerView.setErrorView(mErrorView);
    }

    private final Runnable refreshing = new Runnable(){
        public void run(){
            try {
                // TODO : isRefreshing should be attached to your data request status
                if(mSwipeContainer.isRefreshing()){
                    // re run the verification after 1 second
                    handler.postDelayed(this, 1000);
                }else{
                    // stop the animation after the data is fully loaded
                    mSwipeContainer.setRefreshing(false);
                    // TODO : update your list with the new data
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * Communication events with parent activity
     *
     */
    @Subscribe
    public void onTimelineEventReceived(TimelineEvent event) {
        if (event.getType().equals(TimelineEvent.Type.DO_ERROR_ALL)) {
            if(mPage == TimelineManager.HOME_TAB_ALL) {
                timelineRecyclerView.showErrorView();
            }

        }
        if (event.getType().equals(TimelineEvent.Type.DO_ERROR_YOURS)) {
            if(mPage == TimelineManager.HOME_TAB_COMPANY) {
                timelineRecyclerView.showErrorView();
            }
        }
        if(event.getType().equals(TimelineEvent.Type.DO_UPDATE_ALL)){
            if(mPage == TimelineManager.HOME_TAB_ALL) {
                timelineRecyclerView.hideErrorView();
                items = event.getData();
                mHomeTimelineAdapter = new HomeTimelineAdapter(items);
                timelineRecyclerView.setAdapter(mHomeTimelineAdapter);
            }
        }
        if(event.getType().equals(TimelineEvent.Type.DO_UPDATE_YOURS)){
            if(mPage == TimelineManager.HOME_TAB_COMPANY) {
                timelineRecyclerView.hideErrorView();
                items = event.getData();
                mHomeTimelineAdapter = new HomeTimelineAdapter(items);
                timelineRecyclerView.setAdapter(mHomeTimelineAdapter);
            }
        }
    }

}
