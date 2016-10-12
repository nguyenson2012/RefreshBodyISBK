package com.example.asus.refreshbody.model;

/**
 * Created by Asus on 10/12/2016.
 */

public class DrinkIntakeItem {
    private int symbol;
    private String nameDrink;
    private int amountDrink;

    public DrinkIntakeItem(){}

    public DrinkIntakeItem(int symbol, String nameDrink, int amountDrink) {
        this.symbol = symbol;
        this.nameDrink = nameDrink;
        this.amountDrink = amountDrink;
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
}
