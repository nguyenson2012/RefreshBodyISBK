package com.example.asus.refreshbody.adapter;

/**
 * Created by nguyenvanthinh on 15/10/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.asus.refreshbody.fragment.FragmentDrinkReportMonth;
import com.example.asus.refreshbody.fragment.FragmentDrinkReportWeek;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private CharSequence titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    private int numbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created

    public ViewPagerAdapter(FragmentManager fm, CharSequence titles[], int numbOfTabs) {
        super(fm);
        this.titles = titles;
        this.numbOfTabs = numbOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            FragmentDrinkReportWeek fragment = new FragmentDrinkReportWeek();
            return fragment;
        } else {
            FragmentDrinkReportMonth fragment = new FragmentDrinkReportMonth();
            return fragment;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return numbOfTabs;
    }
}
