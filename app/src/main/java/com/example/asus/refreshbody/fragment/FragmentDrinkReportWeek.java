package com.example.asus.refreshbody.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.controller.DrinkReportController;
import com.example.asus.refreshbody.database.model.DrinkIntakeItem;
import com.example.asus.refreshbody.database.model.TimeDrink;
import com.example.asus.refreshbody.provider.PlanDBHelper;
import com.example.asus.refreshbody.utils.DrawChart;
import com.example.asus.refreshbody.utils.DrawWeekChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FragmentDrinkReportWeek extends Fragment {
    private LineChart chart;
    private DrawChart drawChart, drawChartDay;
    private DrawWeekChart drawWeekChart;
    private PieChart pieChart;
    private PieChart pieChartDay;
    private PieChart pieChartWeek;
    private PlanDBHelper planDBHelper;
    private ArrayList<DrinkIntakeItem> arrDrink = new ArrayList<DrinkIntakeItem>();
    private DrinkReportController drinkReportController;

    public FragmentDrinkReportWeek() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View fragmentview = inflater.inflate(R.layout.fragment_agment_drink_report_week, container, false);

        chart = (LineChart)fragmentview.findViewById(R.id.chartWeek);
        pieChart = (PieChart) fragmentview.findViewById(R.id.chartWeekPie);

        drawWeekChart = new DrawWeekChart(chart);
        //arrDrink = PlanDBHelper.getInstance(this.getActivity()).getAllDrinkIntake();
        arrDrink.add(new DrinkIntakeItem(1,"water",100,new TimeDrink(2016,10,11,10,10)));
        arrDrink.add(new DrinkIntakeItem(1,"water",200,new TimeDrink(2016,10,12,10,10)));
        arrDrink.add(new DrinkIntakeItem(1,"water",300,new TimeDrink(2016,10,13,10,10)));
        arrDrink.add(new DrinkIntakeItem(1,"water",100,new TimeDrink(2016,10,14,10,10)));
        arrDrink.add(new DrinkIntakeItem(1,"water",200,new TimeDrink(2016,10,15,10,10)));

        arrDrink.add(new DrinkIntakeItem(1,"water",300,new TimeDrink(2016,10,16,10,10)));
        arrDrink.add(new DrinkIntakeItem(1,"water",300,new TimeDrink(2016,10,17,10,10)));

        drinkReportController = new DrinkReportController(arrDrink);
        drinkReportController.drawWeekLine(chart);

        pieChartDay = (PieChart) fragmentview.findViewById(R.id.chartPieDay) ;
        drawChartDay = new DrawChart(pieChartDay);
        drinkReportController.drawPieChart(pieChartDay,60,100,"Day");

        pieChartWeek = (PieChart) fragmentview.findViewById(R.id.chartWeekPie) ;
        drinkReportController.drawPieChart(pieChartWeek,70,100,"Week");


        return fragmentview;
    }

    public void onButtonPressed(Uri uri) {

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
