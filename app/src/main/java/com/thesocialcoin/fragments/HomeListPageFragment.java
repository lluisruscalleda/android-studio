package com.thesocialcoin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.bundler.CastedArrayListArgsBundler;
import com.thesocialcoin.R;
import com.thesocialcoin.adapters.HomeTimelineRipplesAdapter;
import com.thesocialcoin.controllers.HomeManager;
import com.thesocialcoin.models.pojos.TimelineItem;

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

    private int mPage;

    @Bind(R.id.total_items)
    TextView total;
    @Bind(R.id.rv_timeline)
    RecyclerView timelineRecyclerView;

    @Arg ( bundler = CastedArrayListArgsBundler.class )
    List<TimelineItem> mTimelineRipples;   // Foo implements Parcelable

    public static HomeListPageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        HomeListPageFragment fragment = new HomeListPageFragment();
        fragment.setArguments(args);
        return fragment;
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

        mTimelineRipples = HomeManager.getInstance(getActivity()).getCrimes();
        timelineRecyclerView.setAdapter(new HomeTimelineRipplesAdapter(mTimelineRipples));


        return view;
    }


    /**
     *  Fill timeline adapter
     */
    private void fillTimeline(){
        //Feth timeline
        HomeManager.getInstance(getActivity()).fetchAllTimeline();
    }
}
