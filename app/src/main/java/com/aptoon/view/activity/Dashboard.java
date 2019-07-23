package com.aptoon.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.aptoon.utils.ExceptionHandler;
import com.aptoon.utils.LocaleHelper;
import com.aptoon.view.adapter.DasboardViewPagerAdapter;
import com.aptoon.R;
import com.aptoon.view.adapter.NonSwipeableViewPager;
import com.aptoon.view.fragment.HomeFragment;
import com.aptoon.view.fragment.LivetvFragment;
import com.aptoon.view.fragment.MoreFragment;
import com.aptoon.view.fragment.SearchFragment;
import com.aptoon.view.fragment.SoonFragment;

public class Dashboard extends AppCompatActivity {
    private TabLayout tabLayout;
    //This is our viewPager
    private NonSwipeableViewPager viewPager;
    DasboardViewPagerAdapter adapterViewPager;
    //Fragments

    HomeFragment homeFragment;
    SearchFragment searchFragment;
    LivetvFragment livetvFragment;
    SoonFragment soonFragment;
    MoreFragment moreFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);
        initView();

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    private void initView() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(5);
        setupViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.Home)).setIcon(R.drawable.home_));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.search)).setIcon(R.drawable.search));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.livetv)).setIcon(R.drawable.live_tv));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.soon)).setIcon(R.drawable.soon));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.more)).setIcon(R.drawable.more));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), false);
                switch (tab.getPosition()) {
                    case 0:
                        Log.e("TAG", "TAB1");
                        tabLayout.getTabAt(0).setIcon(R.drawable.home_);
                        break;
                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.path);
                        break;
                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.drawable.live_tv_);
                        break;
                    case 3:
                        tabLayout.getTabAt(3).setIcon(R.drawable.soon_);
                        break;
                    case 4:
                        tabLayout.getTabAt(4).setIcon(R.drawable.more_);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabLayout.getTabAt(0).setIcon(R.drawable.home);
                tabLayout.getTabAt(1).setIcon(R.drawable.search);
                tabLayout.getTabAt(2).setIcon(R.drawable.live_tv);
                tabLayout.getTabAt(3).setIcon(R.drawable.soon);
                tabLayout.getTabAt(4).setIcon(R.drawable.more);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        adapterViewPager = new DasboardViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        livetvFragment = new LivetvFragment();
        soonFragment = new SoonFragment();
        moreFragment = new MoreFragment();
        adapterViewPager.addFragment(homeFragment, "HOME");
        adapterViewPager.addFragment(searchFragment, "SEARCH");
        adapterViewPager.addFragment(livetvFragment, "LIVETV");
        adapterViewPager.addFragment(soonFragment, "SOON");
        adapterViewPager.addFragment(moreFragment, "MORE");
        viewPager.setAdapter(adapterViewPager);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }


}
