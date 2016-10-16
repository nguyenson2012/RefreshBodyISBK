package com.example.asus.refreshbody.utils;

import android.graphics.Color;

import com.example.asus.refreshbody.database.model.DrinkIntakeItem;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Calendar;

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

    private boolean checkContainEntry(int day){
        for (Entry entryItem : entries
                ) {
            if((5*(day-1)) == (int) entryItem.getX()){
                return true;
            }
        }
        return false;
    }

    public void addDataWeek(ArrayList<DrinkIntakeItem> arrDrink) {
        entries = new ArrayList<>();
        for (DrinkIntakeItem item : arrDrink
             ) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR,item.getTimeDrink().getYearDrink());
            c.set(Calendar.MONTH,item.getTimeDrink().getMonthDrink()-1);
            c.set(Calendar.DAY_OF_MONTH,item.getTimeDrink().getDayDrink());
            //c.set(item.getTimeDrink().getYearDrink() ,item.getTimeDrink().getMonthDrink()  - 1, item.getTimeDrink().getDayDrink());
            int day = c.get(Calendar.DAY_OF_WEEK);
            for (Entry itemEntry: entries
                 ) {
                if(itemEntry.getX() == 5*(day-1)) {
                    itemEntry.setY(itemEntry.getY() + item.getAmountDrink());
                    break;
                }
            }
            if (!checkContainEntry(day)) {
                entries.add(new Entry(5*(day-1),item.getAmountDrink()));
            }

        }

        if(entries != null){

            dataSet = new LineDataSet(entries, "Report");
            lineData = new LineData(dataSet);
        }


    }
    public void setupLineChart() {
        super.setupLineChart();
        getXAxist();
    }

    public XAxis getXAxist(){

        super.getXAxist();
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());

        return xAxis;
    }

    public void drawLineChart(ArrayList<DrinkIntakeItem> arrDrink) {

        addDataWeek(arrDrink);
        setupLineChart();
        super.drawLineChart();
    }

}

class MyCustomXAxisValueFormatter implements AxisValueFormatter {



    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        switch((int)((value+5)/5)) {
            case 1:
                return "Sun";
            case 2:
                return "Mon";
            case 3:
                return "Tues";
            case 4:
                return "Wed";
            case 5:
                return "Thur";
            case 6:
                return "Fri";
            case 7:
                return "Sat";
            default: return "haha";
        }
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}
