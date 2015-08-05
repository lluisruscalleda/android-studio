package com.thesocialcoin.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thesocialcoin.R;
import com.thesocialcoin.controllers.HomeManager;
import com.thesocialcoin.models.pojos.TimelineItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * thesocialcoin
 * <p/>
 * Created by identitat on 04/08/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class HomeTimelineRipplesAdapter extends RecyclerView.Adapter<HomeTimelineRipplesAdapter.TimelineViewHolder>{


    /**
     * We define a parent ViewHolder for the two different Card type
     */
    public static class TimelineViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.card_view)
        CardView cardView;
        @Bind(R.id.ripple_title)
        TextView rippleTitle;
        @Bind(R.id.ripple_description)
        TextView rippleDescription;

        protected TimelineItem mItem;

        TimelineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        // Method called onBindViewHolder is called from the adapter
        protected void bindItem(TimelineItem item){
            mItem = item;
            this.rippleDescription.setText(item.getDescription());
            this.rippleTitle.setText(item.getRipple().getTitle());
        }
    }

    public static class RippleViewHolder extends TimelineViewHolder {


        RippleViewHolder(View itemView) {
            super(itemView);

        }

        // Bind holder data
        public void bindItem(TimelineItem item) {


        }
    }
    public static class CompanyRippleViewHolder extends TimelineViewHolder {


        CompanyRippleViewHolder(View itemView) {
            super(itemView);

        }

        // Bind holder data
        public void bindItem(TimelineItem item) {


        }
    }

    private List<TimelineItem> items;

    public HomeTimelineRipplesAdapter(List<TimelineItem> items) {
        this.items = items;
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public TimelineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == HomeManager.NORMAL_RIPPLE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_timeline_ripple_cardview,parent,false); //Inflating the layout

            RippleViewHolder vhItem = new RippleViewHolder(v); //Creating ViewHolder and passing the object of type view

            return vhItem; // Returning the created object

            //inflate your layout and pass it to view holder

        } else if (viewType == HomeManager.COMPANY_RIPPLE) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_timeline_ripple_company_cardview,parent,false); //Inflating the layout

            CompanyRippleViewHolder vhHeader = new CompanyRippleViewHolder(v); //Creating ViewHolder and passing the object of type view

            return vhHeader; //returning the object created


        }
        return null;

    }

    @Override
    public void onBindViewHolder(TimelineViewHolder holder, int position) {
        holder.bindItem(items.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        // return 0 or 1 depending on ripple type (normal or company ripple)
        if (getItem(position).getRipple().getCompany() != null)
            return HomeManager.COMPANY_RIPPLE;

        return HomeManager.NORMAL_RIPPLE;
    }

    public TimelineItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
