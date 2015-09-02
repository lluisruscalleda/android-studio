package com.thesocialcoin.adapters;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.thesocialcoin.App;
import com.thesocialcoin.controllers.TimelineManager;
import com.thesocialcoin.fragments.HomeListPageFragment;
import com.thesocialcoin.fragments.HomeListPageFragmentBuilder;
import com.thesocialcoin.models.pojos.TimelineItem;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * thesocialcoin
 * <p/>
 * Created by identitat on 03/08/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class HomeFragmentPagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "ALL", "MY COMPANY" };

    private Map<Integer, String> mFragmentTags;
    private FragmentManager mFragmentManager;

    public HomeFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
        mFragmentTags = new HashMap<Integer, String>();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public HomeListPageFragment getItem(int position) {
        int mPage = position+1;
        // passem la llista a arrayList per problemes amb el parceler i la llibreria fragmentargs
        ArrayList<TimelineItem> list = new ArrayList<TimelineItem>();
        list.addAll(TimelineManager.getInstance(App.getAppContext()).getTimelineRipplesByHomePage(position + 1));

        if(list != null || list.size() > 0) {
            return new HomeListPageFragmentBuilder(mPage, Parcels.wrap(list)).build();
        }
        else{
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object obj = super.instantiateItem(container, position);
        if (obj instanceof Fragment) {
            // record the fragment tag here.
            HomeListPageFragment f = (HomeListPageFragment) obj;
            String tag = f.getTag();
            mFragmentTags.put(position+1, tag);
        }
        return obj;
    }

    public HomeListPageFragment getFragment(int position) {
        String tag = mFragmentTags.get(position+1);
        if (tag == null)
            return null;
        return (HomeListPageFragment)mFragmentManager.findFragmentByTag(tag);
    }


}