package com.example.asus.refreshbody.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.database.model.DrinkIntakeItem;
import com.example.asus.refreshbody.utils.DrawChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;

public class FragmentDrinkReportWeek extends Fragment {
    private LineChart chart;
    private DrawChart drawChart, drawChartDay;
    private PieChart pieChart;
    private PieChart pieChartDay;
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

        drawChart = new DrawChart(chart,pieChart);
        drawChart.drawLineChart();
        drawChart.drawPieChart();

        pieChartDay = (PieChart) fragmentview.findViewById(R.id.chartPieDay) ;
        drawChartDay = new DrawChart(pieChartDay);
        drawChartDay.drawPieChart();


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
