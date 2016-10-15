package com.example.asus.refreshbody.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.activity.MainActivity;
import com.example.asus.refreshbody.adapter.DrinkIntakeAdapter;
import com.example.asus.refreshbody.database.DBContext;
import com.example.asus.refreshbody.database.model.DrinkIntakeItem;
import com.example.asus.refreshbody.provider.PlanDBHelper;
import com.github.lzyzsd.circleprogress.CircleProgress;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Asus on 10/12/2016.
 */

public class FragmentDrinkWater extends Fragment implements View.OnClickListener{
    private ImageView imgDrinkState;
    private CircleProgress circleProgressDrink;
    private TextView tvDrinkState;
    private RecyclerView recyclerViewDrinkIntake;
    private TextView tvAddDrinkIntake;

    private DrinkIntakeAdapter drinkIntakeAdapter;

    private ArrayList<DrinkIntakeItem> drinkIntakeItemArrayList;

    private DBContext dbContext;

    private PlanDBHelper planDBHelper;

    private Calendar calendar;

    private int currentDay;
    private int currentMonth;
    private int currentYear;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_drink_water,container,false);
        calendar=Calendar.getInstance();
        getCurrentDay();
        setupView(layout);
        registerEvent();
        return layout;
    }

    private void getCurrentDay() {
        currentYear=calendar.get(Calendar.YEAR);
        currentMonth=calendar.get(Calendar.MONTH);
        currentDay=calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        planDBHelper=PlanDBHelper.getInstance(getActivity());
        setAdapterForRecyclerViewDrinkIntake();
    }

    private void setAdapterForRecyclerViewDrinkIntake() {
//        dbContext=DBContext.getInst();
//        drinkIntakeItemArrayList=dbContext.getDrinkIntakeListByDay(calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.YEAR));
        drinkIntakeItemArrayList=new ArrayList<DrinkIntakeItem>();
        ArrayList<DrinkIntakeItem> arrDrinkitemClone=planDBHelper.getAllDrinkIntake();
        for(int i=arrDrinkitemClone.size()-1;i>0;i--) {
            DrinkIntakeItem drinkIntakeItem = arrDrinkitemClone.get(i);
            if (drinkIntakeItem.getTimeDrink().getYearDrink() == currentYear &&
                    drinkIntakeItem.getTimeDrink().getMonthDrink() == currentMonth &&
                    drinkIntakeItem.getTimeDrink().getDayDrink() == currentDay)
                drinkIntakeItemArrayList.add(drinkIntakeItem);
        }
        drinkIntakeAdapter=new DrinkIntakeAdapter(drinkIntakeItemArrayList,getActivity());
        recyclerViewDrinkIntake.setAdapter(drinkIntakeAdapter);
        recyclerViewDrinkIntake.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void registerEvent() {
        tvAddDrinkIntake.setOnClickListener(this);
    }

    private void setupView(View layout) {
        imgDrinkState=(ImageView)layout.findViewById(R.id.img_user_state);
        circleProgressDrink=(CircleProgress)layout.findViewById(R.id.circle_progress);
        tvDrinkState=(TextView)layout.findViewById(R.id.tv_drink_target);
        recyclerViewDrinkIntake=(RecyclerView)layout.findViewById(R.id.recycler_view_drink_intake);
        tvAddDrinkIntake=(TextView)layout.findViewById(R.id.tv_add_drink_intake);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_add_drink_intake:
                Toast.makeText(getActivity(),"Add drink intake",Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).replaceFragmentCupChoose();
                break;
        }
    }

    public void addDrinkIntake(DrinkIntakeItem drinkIntakeItem) {
        drinkIntakeItemArrayList.add(drinkIntakeItem);
        drinkIntakeAdapter.notifyDataSetChanged();
    }
}
