package com.example.asus.refreshbody.activity;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.adapter.ViewPagerAdapter;
import com.example.asus.refreshbody.database.DBContext;
import com.example.asus.refreshbody.database.DefaultData;
import com.example.asus.refreshbody.database.model.CupChooseItem;
import com.example.asus.refreshbody.fragment.DrinkLog;
import com.example.asus.refreshbody.fragment.FragmentChooseCup;
import com.example.asus.refreshbody.fragment.FragmentDrawer;
import com.example.asus.refreshbody.fragment.FragmentDrinkReportMonth;
import com.example.asus.refreshbody.fragment.FragmentDrinkReportWeek;
import com.example.asus.refreshbody.fragment.FragmentDrinkWater;
import com.example.asus.refreshbody.fragment.FragmentReminder;
import com.example.asus.refreshbody.fragment.FragmentReminderPlanDetail;
import com.example.asus.refreshbody.intef.FragmentDrawerListener;
import com.example.asus.refreshbody.provider.PlanContract;
import com.example.asus.refreshbody.service.AlarmServiceReceiver;
import com.example.asus.refreshbody.utils.ScreenManager;
import com.example.asus.refreshbody.utils.iLog;
import com.example.asus.refreshbody.SlidingTabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FragmentDrawerListener, FragmentReminder.OnListItemSelectedListener{
    private String TAG = MainActivity.this.getClass().getSimpleName();

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;


    private ScreenManager screenManager;

    private FragmentDrinkWater fragmentDrinkWater;
    private FragmentDrawer drawerFragment;
    private FragmentReminder fragmentReminder;
    private FragmentChooseCup fragmentChooseCup;
    private DrinkLog fragmentDrinkLog;
    private FragmentDrinkReportWeek fragmentDrinkReportWeek;
    private FragmentDrinkReportMonth fragmentDrinkReportMonth;



    private DBContext dbContext;

    @Override
    protected void onDestroy() {
        iLog.d(iLog.LogTag.UI, TAG + " onDestroy()");
        getContentResolver().unregisterContentObserver(mContentObserver);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getContentResolver().registerContentObserver(PlanContract.PlanEntry.CONTENT_URI, true, mContentObserver);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        setUpView();
        intiliazeFragment();
        addFragmentDrinkWater();
        setDefaultCupChoose();
    }

    private void setDefaultCupChoose() {
        dbContext=DBContext.getInst();
        if(dbContext.getAllCupChooseItem().size()==0)
            DefaultData.getInst(dbContext).setDefaultCupChooseItem();
    }

    private void addFragmentDrinkWater() {
        screenManager.openFragment(getSupportFragmentManager(),R.id.frame_container,fragmentDrinkWater,false);
    }

    private void intiliazeFragment() {
        fragmentDrinkWater=new FragmentDrinkWater();
        fragmentChooseCup=new FragmentChooseCup();
        fragmentDrinkLog = new DrinkLog();
        fragmentReminder = new FragmentReminder();
    }

    private void setUpView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        drawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawer.addDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Menu");

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer,mDrawer, mToolbar);
        drawerFragment.setDrawerListener(this);

        screenManager=ScreenManager.getInst();
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        //detachViewpager();

        switch (position){
            case 0://Drink water
                Toast.makeText(this,"Drink Water",Toast.LENGTH_SHORT).show();
                screenManager.openFragment(getSupportFragmentManager(),R.id.frame_container,fragmentDrinkWater,false);
                break;
            case 1://Drink log
                Toast.makeText(this,"Drink Log",Toast.LENGTH_SHORT).show();
                screenManager.openFragment(getSupportFragmentManager(),R.id.frame_container,fragmentDrinkLog,false);
                break;
            case 2://Drink recordx
                Toast.makeText(this,"Drink Record",Toast.LENGTH_SHORT).show();
                onChooseReport();
                break;
            case 3://Reminder
                Toast.makeText(this,"Reminder",Toast.LENGTH_SHORT).show();
                screenManager.openFragment(getSupportFragmentManager(), R.id.frame_container, fragmentReminder, false);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onListItemSelected(Uri uri, long id) {
        FragmentReminderPlanDetail fragment = FragmentReminderPlanDetail.newInstance(id);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null).commit();
    }

    private ContentObserver mContentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            iLog.d(iLog.LogTag.UI, TAG + " mContentObserver onChange() selfChange : " + selfChange);

            // Re-trigger AlarmServiceReceiver to start AlarmService to update next alarm
            Intent intent = new Intent(getApplicationContext(), AlarmServiceReceiver.class);
            sendBroadcast(intent);
        }
    };

    public void replaceFragmentCupChoose() {
        screenManager.openFragment(getSupportFragmentManager(),R.id.frame_container,fragmentChooseCup,true);
    }

    public void onChooseReport(){
        Intent intent = new Intent(this,DrinkReportActivity.class);
        startActivity(intent);
    }
    public void addDrinkIntake(CupChooseItem cupChooseItem) {
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

}
