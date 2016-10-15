package com.example.asus.refreshbody.utils;

import android.graphics.Color;

import com.example.asus.refreshbody.database.model.DrinkIntakeItem;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by nguyenvanthinh on 15/10/2016.
 */

public class DrawChart {
    private ArrayList<DrinkIntakeItem> item = new ArrayList<DrinkIntakeItem>();
    private ArrayList<Entry> entries = new ArrayList<>();
    private ArrayList<PieEntry> entriesPieChart = new ArrayList<>();
    private ArrayList<String> labelX = new ArrayList<String>();
    LineDataSet dataSet;
    LineChart chart;
    int maxX =31;
    LineData lineData;
    private PieChart pieChart;
    private PieDataSet pieDataSet;
    private PieData pieData ;

    public DrawChart(LineChart chart){

        this.chart = chart;
        addData(null);
        setupLineChart();
    }

    public DrawChart(PieChart pieChart) {
        this.pieChart = pieChart;
        addDataPieChart(null);
        setupPieChart();
    }
    public DrawChart(LineChart chart, PieChart pieChart) {
        this.pieChart = pieChart;
        this.chart = chart;
        addData(null);
        addDataPieChart(null);
        setupLineChart();
        setupPieChart();
    }

    public void addData(ArrayList<DrinkIntakeItem> item) {
        entries.add(new Entry(5,6));
        entries.add(new Entry(6,7));
        entries.add(new Entry(7,8));
        if(entries != null){
            dataSet = new LineDataSet(entries, "Report");
            lineData = new LineData(dataSet);
        }
    }

    public void addDataPieChart(ArrayList<DrinkIntakeItem> item) {
        entriesPieChart.add(new PieEntry(6,0));
        entriesPieChart.add(new PieEntry(4,1));
        pieDataSet = new PieDataSet(entriesPieChart, "Report");
        pieData = new PieData(pieDataSet);
    }



    public void setupPieChart() {
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setLabel("hehe ");

        pieDataSet.setDrawValues(false);
        pieChart.setCenterText("60%");
        pieChart.setDescription("Week");


    }

    public void setupLineChart() {

        dataSet.setColor(Color.BLACK);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(3f);
        dataSet.setDrawFilled(true);
        chart.setDescription("Report");
        chart.setNoDataTextDescription("REPORT");
        getXAxist();
        getYAxist();
        chart.getAxisLeft().setEnabled(true);
        chart.getAxisRight().setEnabled(false);



    }


    public XAxis getXAxist(){

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);

        xAxis.setTextColor(Color.RED);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaxValue(maxX);
        xAxis.setAxisMinValue(0);


        return xAxis;
    }
    public YAxis getYAxist(){

        YAxis yAxis = chart.getAxisLeft();
        //yAxis.setDrawLabels(false); // no axis labels
        //yAxis.setDrawAxisLine(false); // no axis line
        yAxis.setDrawGridLines(true); // no grid lines
        yAxis.setDrawZeroLine(true); // draw a zero line
        //mChart.getAxisRight().setEnabled(false); // no right axis


        return yAxis;
    }
    public LineDataSet getDataset() {
        return dataSet;
    }

    public LineData getLineData(){
        return lineData;
    }
    public void drawLineChart() {

       if(dataSet != null) {
           setupLineChart();
           chart.setData(lineData);
           chart.invalidate(); // refresh
       }
    }

    public void drawPieChart() {
        if(pieDataSet != null) {
            setupPieChart();
            pieChart.setData(pieData);
            pieChart.invalidate();
        }
    }
}
