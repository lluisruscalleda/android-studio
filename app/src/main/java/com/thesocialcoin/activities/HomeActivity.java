package com.thesocialcoin.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.thesocialcoin.R;
import com.thesocialcoin.adapters.HomeFragmentPagerAdapter;
import com.thesocialcoin.controllers.AccountManager;
import com.thesocialcoin.controllers.HomeManager;
import com.thesocialcoin.events.TimelineEvent;
import com.thesocialcoin.networking.core.RequestManager;
import com.thesocialcoin.utils.FontUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {


    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    private HomeFragmentPagerAdapter mHomePagerAdapter;

    @Override
    public void onResume() {
        super.onResume();
        RequestManager.EventBus.register(this);

        //We retrieve the timeline data
        HomeManager.getInstance(this).fetchAllTimeline();
    }

    @Override
    public void onPause() {
        super.onPause();
        RequestManager.EventBus.unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        viewPager.setAdapter(mHomePagerAdapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);

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
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Activity subscription to timeline data changes for notify it
     */
    @Subscribe
    public void onTimelineChange(TimelineEvent event) {
        if (event.getType().equals(TimelineEvent.Type.SUCCESS_ALL)) {
            // notify all timeline was updated
            mHomePagerAdapter.getFragment(viewPager.getCurrentItem()).updateTimeline();
        }
        if (event.getType().equals(TimelineEvent.Type.SUCCESS_CO)) {
            // notify user company timeline was updated
            mHomePagerAdapter.getFragment(viewPager.getCurrentItem()).updateTimeline();
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

}

