package com.example.asus.refreshbody.model;

/**
 * Created by Asus on 10/12/2016.
 */

public class DrinkIntakeItem {
    private int symbol;
    private String nameDrink;
    private int amountDrink;
    private int hourDrink;
    private int minuteDrink;

    public DrinkIntakeItem(){}

    public DrinkIntakeItem(int symbol, String nameDrink, int amountDrink) {
        this.symbol = symbol;
        this.nameDrink = nameDrink;
        this.amountDrink = amountDrink;
    }

    public DrinkIntakeItem(int symbol, String nameDrink, int amountDrink, int hourDrink, int minuteDrink) {
        this.symbol = symbol;
        this.nameDrink = nameDrink;
        this.amountDrink = amountDrink;
        this.hourDrink = hourDrink;
        this.minuteDrink = minuteDrink;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    public String getNameDrink() {
        return nameDrink;
    }

    public void setNameDrink(String nameDrink) {
        this.nameDrink = nameDrink;
    }

    public int getAmountDrink() {
        return amountDrink;
    }

    public void setAmountDrink(int amountDrink) {
        this.amountDrink = amountDrink;
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
