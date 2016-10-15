package com.example.asus.refreshbody.utils;

import com.example.asus.refreshbody.database.model.DrinkIntakeItem;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;

/**
 * Created by nguyenvanthinh on 15/10/2016.
 */

public class DrawWeekChart extends DrawChart {
    ArrayList<DrinkIntakeItem> arrDrink = new ArrayList<DrinkIntakeItem>();
    ArrayList<String> labels = new ArrayList<String>();

    public DrawWeekChart(LineChart chart) {
        super(chart);
    }

    public DrawWeekChart(PieChart pieChart) {
        super(pieChart);
    }

    public DrawWeekChart(LineChart chart, PieChart pieChart) {
        super(chart, pieChart);
    }

    public void init() {
        labels.add("Mon");
        labels.add("Tues");
        labels.add("Wed");
        labels.add("Thu");
        labels.add("Fri");
        labels.add("Sat");
        labels.add("Sun");
        this.maxX = 7;
        //lineData = new LineData(labels, dataSet);
    }

}
