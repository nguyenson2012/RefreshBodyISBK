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
import com.example.asus.refreshbody.controller.DrinkReportController;
import com.example.asus.refreshbody.database.model.DrinkIntakeItem;
import com.example.asus.refreshbody.database.model.TimeDrink;
import com.example.asus.refreshbody.provider.PlanDBHelper;
import com.example.asus.refreshbody.utils.DrawChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.Date;

public class FragmentDrinkReportMonth extends Fragment {
    private LineChart chart;
    private DrawChart drawChart,drawChartDay;
    private PieChart pieChart;
    private PieChart pieChartDay;
    private ArrayList<DrinkIntakeItem> arrDrink = new ArrayList<DrinkIntakeItem>();
    private DrinkReportController drinkReportController;

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
        pieChartDay = (PieChart) fragmentview.findViewById(R.id.chartPieDay) ;

        arrDrink.add(new DrinkIntakeItem(1,"water",100,new TimeDrink(2016,10,11,10,10)));
        arrDrink.add(new DrinkIntakeItem(1,"water",200,new TimeDrink(2016,10,12,10,10)));
        arrDrink.add(new DrinkIntakeItem(1,"water",300,new TimeDrink(2016,10,13,10,10)));
        arrDrink.add(new DrinkIntakeItem(1,"water",100,new TimeDrink(2016,10,14,10,10)));
        arrDrink.add(new DrinkIntakeItem(1,"water",200,new TimeDrink(2016,10,15,10,10)));

        arrDrink.add(new DrinkIntakeItem(1,"water",300,new TimeDrink(2016,10,16,10,10)));
        arrDrink.add(new DrinkIntakeItem(1,"water",300,new TimeDrink(2016,10,17,10,10)));
        //arrDrink = PlanDBHelper.getInstance(this.getActivity()).getAllDrinkIntake();

        drinkReportController = new DrinkReportController(arrDrink);
        drinkReportController.drawLine(chart);
        drinkReportController.drawPieChart(pieChartDay,totalDrinkDay(arrDrink),2000,"Day");
        drinkReportController.drawPieChart(pieChart,totalDrinkMonth(arrDrink),60000,"Month");

        return fragmentview;
    }

    public int totalDrinkDay(ArrayList<DrinkIntakeItem> arrDrink) {
        Date day = new Date();
        int total = 0;
        for (DrinkIntakeItem item: arrDrink
                ) {
            if(day.getDate() == item.getTimeDrink().getDayDrink() && (day.getMonth() + 1) == item.getTimeDrink().getMonthDrink()) {
                total = total + item.getAmountDrink();
            }

        }
        return total;
    }

    public int totalDrinkMonth(ArrayList<DrinkIntakeItem> arrDrink) {
        Date day = new Date();
        int total = 0;
        for (DrinkIntakeItem item: arrDrink
                ) {
            if( (day.getMonth() +1) == item.getTimeDrink().getMonthDrink()) {
                total = total + item.getAmountDrink();
            }
        }
        return total;
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
