package com.example.asus.refreshbody.utils;

import android.graphics.Color;

import com.example.asus.refreshbody.database.model.DrinkIntakeItem;
import com.example.asus.refreshbody.database.model.TimeDrink;
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
import java.util.Date;

/**
 * Created by nguyenvanthinh on 15/10/2016.
 */

public class DrawChart {
    private ArrayList<DrinkIntakeItem> item = new ArrayList<DrinkIntakeItem>();
    ArrayList<Entry> entries = new ArrayList<>();
    private ArrayList<PieEntry> entriesPieChart = new ArrayList<>();
    private ArrayList<String> labelX = new ArrayList<String>();
    LineDataSet dataSet;
    LineChart chart;
    int maxX =31;
    LineData lineData;
    private PieChart pieChart;
    private PieDataSet pieDataSet;
    private PieData pieData ;
    private String pieTitle;

    public DrawChart(LineChart chart){

        this.chart = chart;
    }

    public DrawChart(PieChart pieChart) {
        this.pieChart = pieChart;
    }
    public DrawChart(LineChart chart, PieChart pieChart) {
        this.pieChart = pieChart;
        this.chart = chart;
    }

    private boolean checkContainEntry(DrinkIntakeItem item){
        for (Entry entryItem : entries
                ) {
            if(item.getTimeDrink().getDayDrink() == (int) entryItem.getX()){
                return true;
            }
        }
        return false;
    }

    public void addData(ArrayList<DrinkIntakeItem> arrDrink) {
        entries = new ArrayList<>();
        Date day = new Date();

        if(arrDrink == null ) {
            return;
        }
        for (DrinkIntakeItem item : arrDrink
             ) {

            int mon = item.getTimeDrink().getMonthDrink();
            int yea = item.getTimeDrink().getYearDrink();
            int daymon = day.getMonth();
            int dayyear = day.getYear();
            if( (day.getMonth()+1) == item.getTimeDrink().getMonthDrink() && (day.getYear() + 1900) == item.getTimeDrink().getYearDrink()) {
                for (Entry entryItem : entries
                     ) {
                    if(item.getTimeDrink().getDayDrink() == (int) entryItem.getX()){
                        entryItem.setY(entryItem.getY() + item.getAmountDrink());
                        break;
                    }
                }
                if(!checkContainEntry(item)) {
                    entries.add(new Entry(item.getTimeDrink().getDayDrink(),item.getAmountDrink()));
                }
            }
        }

        if(entries != null){

            dataSet = new LineDataSet(entries, "Report");
            lineData = new LineData(dataSet);
        }
    }

    public void addDataPieChart(int amountDrank, int constant) {
        entriesPieChart.add(new PieEntry(amountDrank,0));
        entriesPieChart.add(new PieEntry((constant - amountDrank),1));
        pieDataSet = new PieDataSet(entriesPieChart, "Report");
        pieData = new PieData(pieDataSet);
        pieChart.setCenterText((100*amountDrank)/constant + "%");
    }



    public void setupPieChart() {
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setLabel("hehe ");

        pieDataSet.setDrawValues(false);
        pieChart.setDescription(pieTitle);


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

    public void drawPieChart(int amountDrank, int constant) {

        addDataPieChart(amountDrank,constant);
        setupPieChart();
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    public void setPieTitle(String pieTitle){
        this.pieTitle = pieTitle;
    }

    /*private class DrinkItem(){
        TimeDrink timeDrink;

    }*/
}
