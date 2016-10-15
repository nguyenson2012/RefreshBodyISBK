package com.example.asus.refreshbody.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.database.model.DrinkIntakeItem;
import com.example.asus.refreshbody.utils.DrawChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;

public class FragmentDrinkReportMonth extends Fragment {
    private LineChart chart;
    private DrawChart drawChart,drawChartDay;
    private PieChart pieChart;
    private PieChart pieChartDay;
    public FragmentDrinkReportMonth() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View fragmentview = inflater.inflate(R.layout.fragment_fragment_drink_report_month, container, false);

      //  LineChart chart = new LineChart(this.getContext());
        chart = (LineChart)fragmentview.findViewById(R.id.chartMonth);
        pieChart = (PieChart) fragmentview.findViewById(R.id.chartMonthPie);

        drawChart = new DrawChart(chart,pieChart);
        drawChart.drawLineChart();
        drawChart.drawPieChart();

        pieChartDay = (PieChart) fragmentview.findViewById(R.id.chartPieDay) ;
        drawChartDay = new DrawChart(pieChartDay);
        drawChartDay.drawPieChart();

        return fragmentview;
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
