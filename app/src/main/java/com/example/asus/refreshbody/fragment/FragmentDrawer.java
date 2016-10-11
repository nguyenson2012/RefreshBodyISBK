package com.example.asus.refreshbody.fragment;

/**
 * Created by Ravi on 29/07/15.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.activity.MainActivity;
import com.example.asus.refreshbody.adapter.NavigationDrawerAdapter;
import com.example.asus.refreshbody.intef.ClickListener;
import com.example.asus.refreshbody.intef.FragmentDrawerListener;
import com.example.asus.refreshbody.intef.RecyclerTouchListener;
import com.example.asus.refreshbody.model.NavigationDrawerItem;

import java.util.ArrayList;

public class FragmentDrawer extends Fragment {

    private RecyclerView recyclerView;
    private View containerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;


    private NavigationDrawerAdapter adapter;

    private FragmentDrawerListener drawerListener;

    private ArrayList<NavigationDrawerItem> navigationDrawerItemArrayList;

    public FragmentDrawer() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerview_drawer);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupDefaultAdapter();
    }

    private void setupDefaultAdapter() {
        navigationDrawerItemArrayList=new ArrayList<NavigationDrawerItem>();
        NavigationDrawerItem drinkWater=new NavigationDrawerItem(R.drawable.icon_menu_drinkwater,"Drink Water");
        navigationDrawerItemArrayList.add(drinkWater);
        NavigationDrawerItem drinkLog=new NavigationDrawerItem(R.drawable.icon_menu_drinkwater,"Drink Log");
        navigationDrawerItemArrayList.add(drinkLog);
        NavigationDrawerItem drinkReport=new NavigationDrawerItem(R.drawable.icon_menu_drinkwater,"Drink Report");
        navigationDrawerItemArrayList.add(drinkReport);
        NavigationDrawerItem reminderItem=new NavigationDrawerItem(R.drawable.icon_menu_notification,"Reminder");
        navigationDrawerItemArrayList.add(reminderItem);
        adapter=new NavigationDrawerAdapter(getActivity(),navigationDrawerItemArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }


    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener=listener;
    }
}
















































































































































































































































