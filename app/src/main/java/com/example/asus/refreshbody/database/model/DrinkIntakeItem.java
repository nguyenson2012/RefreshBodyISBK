package com.example.asus.refreshbody.database.model;

import java.sql.Time;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Asus on 10/12/2016.
 */

public class DrinkIntakeItem extends RealmObject{
    public static final String ID_DRINK = "idDrink";
    public static final String  DRINK_INTAKE_IMG_POS= "symbolPosition";
    public static final String NAME_DRINK="nameDrink";
    public static final String AMOUNT_DRINK="amountDrink";
    public static final String DRINK_ITAKE_TABLE="drink_intake_table";
    public static final String YEAR_DRINK="yearDrink";
    public static final String MONTH_DRINK="monthDrink";
    public static final String DAY_DRINK="dayDrink";
    public static final String HOUR_DRINK="hourDrink";
    public static final String MINUTE_DRINK="minuteDrink";
    public static final String IS_UPDATE="isUpdated";
    public static final String IS_DELETE="isDeleted";
    private String idDrink;
    private int symbolPosition;
    private String nameDrink;
    private int amountDrink;
    private boolean isUpdated;
    private boolean isDelete;
    private TimeDrink timeDrink;

    public DrinkIntakeItem(){}

    public DrinkIntakeItem(int symbol, String nameDrink, int amountDrink) {
        this.symbolPosition = symbol;
        this.nameDrink = nameDrink;
        this.amountDrink = amountDrink;
        isDelete=false;
        isUpdated=false;
    }

    public DrinkIntakeItem(int symbol, String nameDrink, int amountDrink, TimeDrink timeDrink) {
        this.symbolPosition = symbol;
        this.nameDrink = nameDrink;
        this.amountDrink = amountDrink;
        this.timeDrink = timeDrink;
        isDelete=false;
        isUpdated=false;
    }

    public DrinkIntakeItem(String idDrink, int symbolPosition, String nameDrink, int amountDrink, TimeDrink timeDrink) {
        this.idDrink = idDrink;
        this.symbolPosition = symbolPosition;
        this.nameDrink = nameDrink;
        this.amountDrink = amountDrink;
        this.timeDrink = timeDrink;
        isDelete=false;
        isUpdated=false;
    }

    public int getSymbolPosition() {
        return symbolPosition;
    }

    public void setSymbolPosition(int symbolPosition) {
        this.symbolPosition = symbolPosition;
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

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
