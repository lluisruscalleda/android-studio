package com.thesocialcoin.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.thesocialcoin.R;
import com.thesocialcoin.adapters.HomeFragmentPagerAdapter;
import com.thesocialcoin.controllers.AccountManager;
import com.thesocialcoin.controllers.TimelineManager;
import com.thesocialcoin.events.TimelineEvent;
import com.thesocialcoin.networking.core.RequestManager;
import com.thesocialcoin.utils.FontUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    private static String TAG = HomeActivity.class.getSimpleName();

    // UI references.
    @Bind(R.id.home_progress)
    View mProgressView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    private HomeFragmentPagerAdapter mHomePagerAdapter;

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        RequestManager.EventBus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.EventBus.unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");

        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        // If the Android version is lower than Jellybean, use this call to hide
        // the status bar.
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        if(!AccountManager.getInstance(this).isLoggedIn()){
            gotoLogin();
        }


        setupNavigationView();
        setupToolbar();
        setupTabPager();


        //We retrieve the timeline data
        if(TimelineManager.getInstance(this).getAllTimelineActs() == null || TimelineManager.getInstance(this).getAllTimelineActs().size() == 0) {
            Log.d(TAG, "TimelineManager.getInstance(this).getAllTimelineActs(): "+TimelineManager.getInstance(this).getAllTimelineActs());
            showProgress(true);
            TimelineManager.getInstance(this).fetchAllTimeline();
            TimelineManager.getInstance(this).fetchUserCompanyTimeline();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupNavigationView(){

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawers();
    }

    @OnClick(R.id.nav_burguer)
    public void setupToolbar(){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void setupTabPager(){
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mHomePagerAdapter = new HomeFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mHomePagerAdapter);
        mViewPager.addOnPageChangeListener(onPageChangeListener);

        if(tabLayout == null)
            return;

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Give the TabLayout the ViewPager
        LinearLayout view = (LinearLayout) tabLayout.getChildAt(0);
        for (int i=0; i < view.getChildCount(); i++){
            TextView textView = (TextView) view.getChildAt(i);
            textView.setTypeface(FontUtils.getAppsBoldFont(this));
        }

        // Attach the view pager to the tab strip
        tabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * Activity subscription to timeline data changes for notify it
     */
    @Subscribe
    public void onTimelineEventChange(TimelineEvent event) {
        showProgress(false);
        if (event.getType().equals(TimelineEvent.Type.SUCCESS_ALL)) {
            // notify all timeline was updated
            postEvent(TimelineEvent.TimelineDownloadEventWithData(TimelineEvent.Type.DO_UPDATE_ALL, TimelineManager.getInstance(this).getAllTimelineActs()));
        }
        if (event.getType().equals(TimelineEvent.Type.SUCCESS_CO)) {
            // notify user company timeline was updated
            postEvent(TimelineEvent.TimelineDownloadEventWithData(TimelineEvent.Type.DO_UPDATE_YOURS, TimelineManager.getInstance(this).getUserCompanyTimelineActs()));
        }
        if(event.getType().equals(TimelineEvent.Type.ERROR_ALL)){
            // notify all timeline fragment error
            postEvent(new TimelineEvent(TimelineEvent.Type.DO_ERROR_ALL));
        }
        if(event.getType().equals(TimelineEvent.Type.ERROR_YOURS)){
            // notify all timeline fragment error
            postEvent(new TimelineEvent(TimelineEvent.Type.DO_ERROR_YOURS));
        }
    }




    private void gotoLogin(){
        // user is not logged in redirect him to Login Activity
        Intent i = new Intent(this, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Staring Login Activity
        this.startActivity(i);
        this.finish();
    }


    /**
     * View Pager Scroll Listener
     *
     * */
    OnPageChangeListener onPageChangeListener =
        new OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(HomeActivity.this,
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        };


    protected static void postEvent(Object event){
        RequestManager.EventBus.post(event);
    }

    /**
     * Shows the progress UI and hides the content.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mViewPager.setVisibility(show ? View.GONE : View.VISIBLE);
            mViewPager.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mViewPager.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mViewPager.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}

