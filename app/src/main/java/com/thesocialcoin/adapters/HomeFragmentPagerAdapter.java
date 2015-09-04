package com.thesocialcoin.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.thesocialcoin.App;
import com.thesocialcoin.controllers.TimelineManager;
import com.thesocialcoin.fragments.HomeListPageFragment;
import com.thesocialcoin.fragments.HomeListPageFragmentBuilder;
import com.thesocialcoin.models.pojos.TimelineItem;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * thesocialcoin
 * <p/>
 * Created by identitat on 03/08/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class HomeFragmentPagerAdapter extends FragmentStatePagerAdapter {

    // This holds all the currently displayable views, in order from left to right.
    private ArrayList<String> pagesTitle = new ArrayList<String>();
    private Map<Integer, String> mFragmentTags;
    private FragmentManager mFragmentManager;

    public HomeFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
        mFragmentTags = new HashMap<Integer, String>();
    }

    //-----------------------------------------------------------------------------
    // Used by ViewPager.  "Object" represents the page; tell the ViewPager where the
    // page should be displayed, from left-to-right.  If the page no longer exists,
    // return POSITION_NONE.
    @Override
    public int getItemPosition (Object object)
    {
        int index = pagesTitle.indexOf (object);
        if (index == -1)
            return POSITION_NONE;
        else
            return index;
    }

    @Override
    public int getCount() {
        return pagesTitle.size();
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
        return pagesTitle.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object obj = super.instantiateItem(container, position);

        //Object obj = pages.get(position);
        if (obj instanceof Fragment) {
            // record the fragment tag here.
            HomeListPageFragment f = (HomeListPageFragment) obj;
            String tag = pagesTitle.get(position);
            mFragmentTags.put(position+1, tag);
        }
        return obj;
    }

    //-----------------------------------------------------------------------------
    // Used by ViewPager.  Called when ViewPager no longer needs a page to display; it
    // is our job to remove the page from the container, which is normally the
    // ViewPager itself.  Since all our pages are persistent, we do nothing to the
    // contents of our "views" ArrayList.
    @Override
    public void destroyItem (ViewGroup container, int position, Object object)
    {
        container.removeView (getItem (position).getView());
    }

    //-----------------------------------------------------------------------------
    // Add "view" to right end of "views".
    // Returns the position of the new view.
    // The app should call this to add pages; not used by ViewPager.
    public int addView (String title)
    {
        return addView (title, pagesTitle.size());
    }

    //-----------------------------------------------------------------------------
    // Add "view" at "position" to "views".
    // Returns position of new view.
    // The app should call this to add pages; not used by ViewPager.
    public int addView (String title, int position)
    {
        pagesTitle.add (position, title);
        return position;
    }

    //-----------------------------------------------------------------------------
    // Removes "view" from "views".
    // Retuns position of removed view.
    // The app should call this to remove pages; not used by ViewPager.
//    public int removeView (ViewPager pager, int position)
//    {
//        return removeView (pager, pages.indexOf (v));
//    }

    //-----------------------------------------------------------------------------
    // Removes the "view" at "position" from "views".
    // Retuns position of removed view.
    // The app should call this to remove pages; not used by ViewPager.
    public int removeView (ViewPager pager, int position)
    {
        // ViewPager doesn't have a delete method; the closest is to set the adapter
        // again.  When doing so, it deletes all its views.  Then we can delete the view
        // from from the adapter and finally set the adapter to the pager again.  Note
        // that we set the adapter to null before removing the view from "views" - that's
        // because while ViewPager deletes all its views, it will call destroyItem which
        // will in turn cause a null pointer ref.
        pager.setAdapter (null);
        pagesTitle.remove (position);
        pager.setAdapter (this);

        return position;
    }

    public HomeListPageFragment getFragment(int position) {
        String tag = mFragmentTags.get(position+1);
        if (tag == null)
            return null;
        return (HomeListPageFragment)mFragmentManager.findFragmentByTag(tag);
    }


}