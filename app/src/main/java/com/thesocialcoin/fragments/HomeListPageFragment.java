package com.thesocialcoin.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.squareup.otto.Subscribe;
import com.thesocialcoin.R;
import com.thesocialcoin.adapters.HomeTimelineRipplesAdapter;
import com.thesocialcoin.controllers.HomeManager;
import com.thesocialcoin.events.TimelineEvent;
import com.thesocialcoin.models.pojos.TimelineItem;
import com.thesocialcoin.networking.core.RequestManager;
import com.thesocialcoin.views.RecycleEmptyErrorView;

import org.parceler.Parcels;

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

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_ITEM_LIST = "ARG_ITEM_LIST";


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

    private HomeTimelineRipplesAdapter mHomeTimelineRipplesAdapter;
    private List<TimelineItem> items;


    @Override
    public void onResume() {
        super.onResume();
        RequestManager.EventBus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.EventBus.unregister(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);

    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        timelineRecyclerView.setLayoutManager(llm);

        // adding items to the recycler view
        items = Parcels.unwrap(mTimelineRipples);
        mHomeTimelineRipplesAdapter = new HomeTimelineRipplesAdapter(items);
        timelineRecyclerView.setAdapter(mHomeTimelineRipplesAdapter);
        timelineRecyclerView.setEmptyView(mEmptyView);
        timelineRecyclerView.setErrorView(mErrorView);

        return view;
    }


    /**
     * Communication events with parent activity
     *
     */
    @Subscribe
    public void onTimelineEventReceived(TimelineEvent event) {
        if (event.getType().equals(TimelineEvent.Type.DO_ERROR_ALL)) {
            if(mPage == HomeManager.HOME_TAB_ALL) {
                timelineRecyclerView.showErrorView();
            }

        }
        if (event.getType().equals(TimelineEvent.Type.DO_ERROR_YOURS)) {
            if(mPage == HomeManager.HOME_TAB_COMPANY) {
                timelineRecyclerView.showErrorView();
            }
        }
        if(event.getType().equals(TimelineEvent.Type.DO_UPDATE_ALL)){
            if(mPage == HomeManager.HOME_TAB_ALL) {
                timelineRecyclerView.hideErrorView();
                items = event.getData();
                mHomeTimelineRipplesAdapter = new HomeTimelineRipplesAdapter(items);
                timelineRecyclerView.setAdapter(mHomeTimelineRipplesAdapter);
            }
        }
        if(event.getType().equals(TimelineEvent.Type.DO_UPDATE_YOURS)){
            if(mPage == HomeManager.HOME_TAB_COMPANY) {
                timelineRecyclerView.hideErrorView();
                items = event.getData();
                mHomeTimelineRipplesAdapter = new HomeTimelineRipplesAdapter(items);
                timelineRecyclerView.setAdapter(mHomeTimelineRipplesAdapter);
            }
        }
    }

}
