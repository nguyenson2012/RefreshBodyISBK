package com.example.asus.refreshbody.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.SlidingTabLayout;
import com.example.asus.refreshbody.adapter.ViewPagerAdapter;

public class DrinkReportActivity extends AppCompatActivity {

    private ViewPager pager;
    private ViewPagerAdapter vAdapter;
    private CharSequence titles[] = {"week","month"};
    private SlidingTabLayout tabs;
    int numOftabs = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupSlidingTabs();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void setupSlidingTabs() {

        vAdapter = new ViewPagerAdapter(getSupportFragmentManager(), titles, numOftabs);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(vAdapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        tabs.setViewPager(pager);
    }


}
