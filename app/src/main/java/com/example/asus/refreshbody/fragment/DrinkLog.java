package com.example.asus.refreshbody.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.adapter.DrinkIntakeAdapter;
import com.example.asus.refreshbody.adapter.ListViewAdapter;
import com.example.asus.refreshbody.database.model.DrinkIntakeItem;
import com.example.asus.refreshbody.intef.ClickListener;
import com.example.asus.refreshbody.provider.PlanDBHelper;
import com.example.asus.refreshbody.utils.view.MyLinearLayoutManager;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * created by Thinh
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class DrinkLog extends Fragment {

    private ArrayList<DrinkIntakeItem> arrDrinkitem;

    private DrinkIntakeAdapter drinkIntakeAdapterOne;

    private RecyclerView recyclerViewOne = null;
    private PlanDBHelper planDBHelper;

    private Calendar calendar;

    private int currentDay;
    private int currentMonth;
    private int currentYear;

    public DrinkLog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentview = inflater.inflate(R.layout.fragment_drink_log, container, false);
        recyclerViewOne = (RecyclerView) fragmentview.findViewById(R.id.recyclerview_drink_log_one);
        getCurrentDay();
        return fragmentview;
    }

    private void getCurrentDay() {
        calendar=Calendar.getInstance();
        currentYear=calendar.get(Calendar.YEAR);
        currentMonth=calendar.get(Calendar.MONTH);
        currentDay=calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        planDBHelper=PlanDBHelper.getInstance(getActivity());
        setAdapterForRecyclerView();
    }

    private void setAdapterForRecyclerView() {
        arrDrinkitem = new ArrayList<DrinkIntakeItem>();
        ArrayList<DrinkIntakeItem> arrDrinkitemClone=planDBHelper.getAllDrinkIntake();
        if(arrDrinkitemClone.size()!=0) {
            int day = arrDrinkitemClone.get(0).getTimeDrink().getDayDrink();
            arrDrinkitem.add(new DrinkIntakeItem(1, "", 0, arrDrinkitemClone.get(0).getTimeDrink()));
            for (DrinkIntakeItem drinkIntakeItem : arrDrinkitemClone) {
                if (drinkIntakeItem.getTimeDrink().getDayDrink() != day) {
                    arrDrinkitem.add(new DrinkIntakeItem(1, "", 0, drinkIntakeItem.getTimeDrink()));
                    day = drinkIntakeItem.getTimeDrink().getDayDrink();
                }
                arrDrinkitem.add(drinkIntakeItem);

            }
            drinkIntakeAdapterOne = new DrinkIntakeAdapter(arrDrinkitem, getActivity(), true);
            recyclerViewOne.setAdapter(drinkIntakeAdapterOne);
            recyclerViewOne.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
