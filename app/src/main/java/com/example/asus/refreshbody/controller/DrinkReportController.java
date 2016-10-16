package com.example.asus.refreshbody.controller;

import com.example.asus.refreshbody.database.model.DrinkIntakeItem;
import com.example.asus.refreshbody.provider.PlanDBHelper;
import com.example.asus.refreshbody.utils.DrawChart;
import com.example.asus.refreshbody.utils.DrawWeekChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nguyenvanthinh on 16/10/2016.
 */

public class DrinkReportController {
    private DrawChart drawChart, drawChartDay;
    private DrawWeekChart drawWeekChart;
    private ArrayList<DrinkIntakeItem> arrDrink;

    public DrinkReportController(ArrayList<DrinkIntakeItem> arrDrink) {
        this.arrDrink = arrDrink;
    }


    public ArrayList<DrinkIntakeItem> getDataForWeek() {

        ArrayList<DrinkIntakeItem> currentArrDrink = new ArrayList<DrinkIntakeItem>();

        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        //c.set(item.getTimeDrink().getYearDrink() ,item.getTimeDrink().getMonthDrink()  - 1, item.getTimeDrink().getDayDrink());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        for (DrinkIntakeItem item : arrDrink
             ) {
            int mon = item.getTimeDrink().getMonthDrink();
            Date previousDay = new Date(item.getTimeDrink().getYearDrink() - 1900,item.getTimeDrink().getMonthDrink() - 1,item.getTimeDrink().getDayDrink());
            Calendar c1 = Calendar.getInstance();
            c1.setTime(previousDay);
            int thisday = c.get(Calendar.DAY_OF_WEEK);
            int beforeday = c1.get(Calendar.DAY_OF_WEEK);
            int k = date.getDate();
            int l = previousDay.getDate();
            if(beforeday <= thisday) {
                currentArrDrink.add(item);
            }
        }
        return currentArrDrink;

    }

    public void drawWeekLine(LineChart chart) {

        drawWeekChart = new DrawWeekChart(chart);
        drawWeekChart.drawLineChart(getDataForWeek());
    }

    public void drawLine(LineChart chart){
        drawChart = new DrawChart(chart);
        drawChart.addData(arrDrink);
        drawChart.drawLineChart();
    }

    public void drawPieChart(PieChart piechart, int amountDrank, int constant, String title ) {
        drawChart = new DrawChart(piechart);
        drawChart.setPieTitle(title);
        drawChart.drawPieChart(amountDrank,constant);
    }

}
