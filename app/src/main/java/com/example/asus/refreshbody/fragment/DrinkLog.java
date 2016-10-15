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
import android.widget.ListView;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.adapter.DrinkIntakeAdapter;
import com.example.asus.refreshbody.adapter.ListViewAdapter;
import com.example.asus.refreshbody.database.model.DrinkIntakeItem;
import com.example.asus.refreshbody.provider.PlanDBHelper;

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

    private DrinkIntakeAdapter drinkIntakeAdapter = null;

    private RecyclerView recyclerView = null;

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
        recyclerView = (RecyclerView) fragmentview.findViewById(R.id.recyclerview_drink_log);
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
        arrDrinkitem.add(new DrinkIntakeItem(0,"",0));
        for(int i=arrDrinkitemClone.size()-1;i>0;i--){
            DrinkIntakeItem drinkIntakeItem=arrDrinkitemClone.get(i);
            if(drinkIntakeItem.getTimeDrink().getYearDrink()==currentYear&&
                    drinkIntakeItem.getTimeDrink().getMonthDrink()==currentMonth&&
                    drinkIntakeItem.getTimeDrink().getDayDrink()==currentDay) {
                arrDrinkitem.add(drinkIntakeItem);
                if((arrDrinkitemClone.get(i-1).getTimeDrink().getDayDrink()==currentDay-1&&
                        arrDrinkitemClone.get(i-1).getTimeDrink().getMonthDrink()==currentMonth)||
                        (arrDrinkitemClone.get(i-1).getTimeDrink().getMonthDrink()==currentMonth-1))
                    //add fake drink item
                    arrDrinkitem.add(new DrinkIntakeItem(0,"",0));
            }
            if(drinkIntakeItem.getTimeDrink().getYearDrink()==currentYear&&
                    drinkIntakeItem.getTimeDrink().getMonthDrink()==currentMonth&&
                    drinkIntakeItem.getTimeDrink().getDayDrink()==currentDay-1) {
                arrDrinkitem.add(drinkIntakeItem);
                if((arrDrinkitemClone.get(i-2).getTimeDrink().getDayDrink()==currentDay-2&&
                        arrDrinkitemClone.get(i-2).getTimeDrink().getMonthDrink()==currentMonth)||
                        (arrDrinkitemClone.get(i-2).getTimeDrink().getMonthDrink()==currentMonth-1))
                    //add fake drink item
                    arrDrinkitem.add(new DrinkIntakeItem(0,"",0));
            }
            if(drinkIntakeItem.getTimeDrink().getYearDrink()==currentYear&&
                    drinkIntakeItem.getTimeDrink().getMonthDrink()==currentMonth&&
                    drinkIntakeItem.getTimeDrink().getDayDrink()==currentDay-2) {
                arrDrinkitem.add(drinkIntakeItem);
            }
        }
        drinkIntakeAdapter = new DrinkIntakeAdapter(arrDrinkitem,getActivity());

        recyclerView.setAdapter(drinkIntakeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
