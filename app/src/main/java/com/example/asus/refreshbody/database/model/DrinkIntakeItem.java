package com.example.asus.refreshbody.database.model;

import java.sql.Time;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Asus on 10/12/2016.
 */

public class DrinkIntakeItem extends RealmObject{
    public static final String USER_ID = "idDrink";
    private String idDrink;
    private int symbol;
    private String nameDrink;
    private int amountDrink;
    private TimeDrink timeDrink;

    public DrinkIntakeItem(){}

    public DrinkIntakeItem(int symbol, String nameDrink, int amountDrink) {
        this.symbol = symbol;
        this.nameDrink = nameDrink;
        this.amountDrink = amountDrink;
    }

    public DrinkIntakeItem(int symbol, String nameDrink, int amountDrink, TimeDrink timeDrink) {
        this.symbol = symbol;
        this.nameDrink = nameDrink;
        this.amountDrink = amountDrink;
        this.timeDrink = timeDrink;
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

    public TimeDrink getTimeDrink() {
        return timeDrink;
    }

    public void setTimeDrink(TimeDrink timeDrink) {
        this.timeDrink = timeDrink;
    }

    public String getIdDrink() {
        return idDrink;
    }

    public void setIdDrink(String idDrink) {
        this.idDrink = idDrink;
    }
}
