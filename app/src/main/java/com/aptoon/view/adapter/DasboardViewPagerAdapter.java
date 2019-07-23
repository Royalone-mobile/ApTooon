package com.aptoon.view.adapter;

//import android.content.Context;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//
//import android.support.v4.app.FragmentStatePagerAdapter;
//import android.util.Log;
//import com.aptoon.view.fragment.HomeFragment;
//import com.aptoon.view.fragment.LivetvFragment;
//import com.aptoon.view.fragment.MoreFragment;
//import com.aptoon.view.fragment.SearchFragment;
//import com.aptoon.view.fragment.SoonFragment;
//import com.sdsmdg.tastytoast.TastyToast;
//
//public class DasboardViewPagerAdapter extends FragmentStatePagerAdapter {
//    private  int NUM_ITEMS = 0;
//
//    Context context;
//    public DasboardViewPagerAdapter(FragmentManager fragmentManager, int tabCount,Context context) {
//        super(fragmentManager);
//        this.context=context;
//        this.NUM_ITEMS=tabCount;
//
//    }
//
//    // Returns total number of pages
//    @Override
//    public int getCount() {
//        return NUM_ITEMS;
//    }
//
//    // Returns the fragment to display for that page
//    @Override
//    public Fragment getItem(int position) {
//        Log.e("TAG5636546", ""+position);
//        switch (position) {
//
//            case 0: // Fragment # 0 - This will show FirstFragment
//                TastyToast.makeText(context, "Home", TastyToast.LENGTH_LONG, TastyToast.WARNING);
//                return new HomeFragment();
//
//            case 1: // Fragment # 0 - This will show FirstFragment different title
//                TastyToast.makeText(context, "Search", TastyToast.LENGTH_LONG, TastyToast.WARNING);
//
//                return new SearchFragment();
//
//            case 2:
//                Log.e("TAG", "TAB3");
//                // Fragment # 1 - This will show SecondFragment
//                TastyToast.makeText(context, "Live", TastyToast.LENGTH_LONG, TastyToast.WARNING);
//
//                return new LivetvFragment();
//            case 3:
//                TastyToast.makeText(context, "Soon", TastyToast.LENGTH_LONG, TastyToast.WARNING);
//
//                return new SoonFragment();
//            case 4:
//                TastyToast.makeText(context, "More", TastyToast.LENGTH_LONG, TastyToast.WARNING);
//
//                return new MoreFragment();
//            default:
//                return null;
//        }
//    }
//
//    // Returns the page title for the top indicator
//    @Override
//    public CharSequence getPageTitle(int position) {
//        TastyToast.makeText(context, ""+position, TastyToast.LENGTH_LONG, TastyToast.WARNING);
//        Log.e("ppppp", ""+position);
//        return "Page " + position;
//
//    }
//}
//
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaison on 25/10/16.
 */

public class DasboardViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public DasboardViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public int getItemPosition(Object object){
        return super.getItemPosition(object);

    }


}

    