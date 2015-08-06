package com.thesocialcoin.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.thesocialcoin.App;
import com.thesocialcoin.R;
import com.thesocialcoin.controllers.HomeManager;
import com.thesocialcoin.models.pojos.Ripple;
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
    public static class TimelineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.card_view)
        CardView cardView;
        @Bind(R.id.title)
        TextView actTitle;
        @Bind(R.id.description)
        TextView actDescription;

        protected TimelineItem mItem;

        TimelineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        // Method called onBindViewHolder is called from the adapter
        protected void bindItem(TimelineItem item){
            mItem = item;
        }

        @Override
        public void onClick(View v) {
            if (mItem != null) {
                Toast.makeText(App.getAppContext(),
                        mItem.getDescription(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public static class ActViewHolder extends TimelineViewHolder {


        ActViewHolder(View itemView) {
            super(itemView);

        }

        // Bind holder data
        public void bindItem(TimelineItem item) {
            super.bindItem(item);
            this.actDescription.setText((item.getDescription()!=null)?item.getDescription():"mOck Description");
            if(item.getRipple()==null){
                this.actTitle.setText("mOck Title");
            }else{
                this.actTitle.setText(item.getRipple().getTitle());
            }
        }
    }
    public static class CompanyActViewHolder extends TimelineViewHolder {


        CompanyActViewHolder(View itemView) {
            super(itemView);

        }

        // Bind holder data
        public void bindItem(TimelineItem item) {
            super.bindItem(item);
            this.actDescription.setText((item.getDescription()!=null)?item.getDescription():"mOck Description");
            if(item.getRipple()==null){
                this.actTitle.setText("mOck Title");
            }else{
                this.actTitle.setText(item.getRipple().getTitle());
            }
        }
    }

    public static class ChallengeViewHolder extends TimelineViewHolder {


        ChallengeViewHolder(View itemView) {
            super(itemView);

        }

        // Bind holder data
        public void bindItem(TimelineItem item) {
            super.bindItem(item);
            this.actDescription.setText(item.getDescription());
            this.actTitle.setText(item.getTitle());
        }
    }

    private List<TimelineItem> mData;

    public HomeTimelineRipplesAdapter(List<TimelineItem> items) {
        this.mData = items;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public TimelineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case HomeManager.NORMAL_ACT:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_timeline_act_cardview,parent,false); //Inflating the layout

                ActViewHolder vh1 = new ActViewHolder(view1); //Creating ViewHolder and passing the object of type view

                return vh1; // Returning the created object


            case HomeManager.COMPANY_ACT:

                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_timeline_act_company_cardview,parent,false); //Inflating the layout

                CompanyActViewHolder vh2 = new CompanyActViewHolder(view2); //Creating ViewHolder and passing the object of type view

                return vh2; //returning the object created

            case HomeManager.CHALLENGE_ACT:
                View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_timeline_challenge_cardview,parent,false); //Inflating the layout

                ChallengeViewHolder vh3 = new ChallengeViewHolder(view3); //Creating ViewHolder and passing the object of type view

                return vh3; // Returning the created object

            default:return null;

        }

    }

    @Override
    public void onBindViewHolder(TimelineViewHolder holder, int position) {
        holder.bindItem(mData.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        // return 0 or 1 depending on ripple type (normal or company ripple)
        TimelineItem item = getItem(position);
        if(item != null){

            if(item.getKind().equals(HomeManager.KIND_ACT)){
                Ripple ripple = getItem(position).getRipple();
                if (ripple.getCompany() != null) {
                    return HomeManager.COMPANY_ACT;
                } else{
                    return HomeManager.NORMAL_ACT;
                }
            } else if(item.getKind().equals(HomeManager.KIND_CHALLENGE)){
                return HomeManager.CHALLENGE_ACT;
            }
        }

        return 0;
    }

    public TimelineItem getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateList(List<TimelineItem> data) {
        mData = data;
        notifyDataSetChanged();
    }


}
