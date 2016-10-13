package com.example.asus.refreshbody.activity;

import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.fragment.FragmentChooseCup;
import com.example.asus.refreshbody.fragment.FragmentDrawer;
import com.example.asus.refreshbody.fragment.FragmentDrinkWater;
import com.example.asus.refreshbody.intef.FragmentDrawerListener;
import com.example.asus.refreshbody.utils.ScreenManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FragmentDrawerListener{

    private Toolbar mToolbar;


    private ScreenManager screenManager;

    private FragmentDrinkWater fragmentDrinkWater;
    private FragmentDrawer drawerFragment;
    private FragmentChooseCup fragmentChooseCup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpView();
        intiliazeFragment();
        addFragmentDrinkWater();
    }

    private void addFragmentDrinkWater() {
        screenManager.openFragment(getSupportFragmentManager(),R.id.frame_container,fragmentDrinkWater,false);
    }

    private void intiliazeFragment() {
        fragmentDrinkWater=new FragmentDrinkWater();
        fragmentChooseCup=new FragmentChooseCup();
    }

    private void setUpView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Menu");

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        screenManager=ScreenManager.getInst();
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        switch (position){
            case 0://Drink water
                Toast.makeText(this,"Drink Water",Toast.LENGTH_SHORT).show();
                screenManager.openFragment(getSupportFragmentManager(),R.id.frame_container,fragmentDrinkWater,false);
                break;
            case 1://Drink log
                Toast.makeText(this,"Drink Log",Toast.LENGTH_SHORT).show();
                break;
            case 2://Drink record
                Toast.makeText(this,"Drink Record",Toast.LENGTH_SHORT).show();
                break;
            case 3://Reminder
                Toast.makeText(this,"Reminder",Toast.LENGTH_SHORT).show();
                break;

        }
    }

    public void replaceFragmentCupChoose() {
        screenManager.openFragment(getSupportFragmentManager(),R.id.frame_container,fragmentChooseCup,true);
    }
}
