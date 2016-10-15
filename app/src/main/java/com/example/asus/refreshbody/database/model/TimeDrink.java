package com.example.asus.refreshbody.database.model;

import io.realm.RealmObject;

/**
 * Created by Asus on 10/14/2016.
 */

public class TimeDrink extends RealmObject {
    private int yearDrink = 0;
    private int monthDrink = 0;
    private int dayDrink = 0;
    private int hourDrink = 0;
    private int minuteDrink = 0;

    public TimeDrink(){}

    public TimeDrink(int yearDrink, int monthDrink, int dayDrink, int hourDrink, int minuteDrink) {
        this.yearDrink = yearDrink;
        this.monthDrink = monthDrink;
        this.dayDrink = dayDrink;
        this.hourDrink = hourDrink;
        this.minuteDrink = minuteDrink;
    }

    public int getYearDrink() {
        return yearDrink;
    }

    public void setYearDrink(int yearDrink) {
        this.yearDrink = yearDrink;
    }

    public int getMonthDrink() {
        return monthDrink;
    }

    public void setMonthDrink(int monthDrink) {
        this.monthDrink = monthDrink;
    }

    public int getDayDrink() {
        return dayDrink;
    }

    public void setDayDrink(int dayDrink) {
        this.dayDrink = dayDrink;
    }

    public int getHourDrink() {
        return hourDrink;
    }

    public void setHourDrink(int hourDrink) {
        this.hourDrink = hourDrink;
    }

    public int getMinuteDrink() {
        return minuteDrink;
    }

    public void setMinuteDrink(int minuteDrink) {
        this.minuteDrink = minuteDrink;
    }
}
