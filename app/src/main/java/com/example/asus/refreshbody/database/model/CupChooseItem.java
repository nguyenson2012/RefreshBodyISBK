package com.example.asus.refreshbody.database.model;

import io.realm.RealmObject;

/**
 * Created by Asus on 10/14/2016.
 */

public class CupChooseItem extends RealmObject {
    public static final String  CUP_CHOOSE_ID= "idCupChoose";
    private String idCupChoose;
    private int symbol;
    private String nameCup;
    private int amountCup;

    public CupChooseItem(){}

    public CupChooseItem(String idCupChoose, int symbol, String nameCup, int amountCup) {
        this.idCupChoose = idCupChoose;
        this.symbol = symbol;
        this.nameCup = nameCup;
        this.amountCup = amountCup;
    }
    public String getIdCupChoose() {
        return idCupChoose;
    }

    public void setIdCupChoose(String idCupChoose) {
        this.idCupChoose = idCupChoose;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    public String getNameCup() {
        return nameCup;
    }

    public void setNameCup(String nameCup) {
        this.nameCup = nameCup;
    }

    public int getAmountCup() {
        return amountCup;
    }

    public void setAmountCup(int amountCup) {
        this.amountCup = amountCup;
    }
}
