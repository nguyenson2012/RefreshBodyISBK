package com.example.asus.refreshbody.database.model;

import io.realm.RealmObject;

/**
 * Created by Asus on 10/14/2016.
 */

public class CupChooseItem extends RealmObject {
    public static final String  CUP_CHOOSE_ID= "idCupChoose";
    public static final String  CUP_CHOOSE_IMG_POS= "symbolPosition";
    public static final String NAME_CUP="nameCup";
    public static final String AMOUNT_CUP="amountCup";
    public static final String CUP_CHOOSE_TABLE="cup_choose_table";
    private String idCupChoose;
    private int symbolPosition;
    private String nameCup;
    private int amountCup;

    public CupChooseItem(){}

    public CupChooseItem(String idCupChoose, int symbol, String nameCup, int amountCup) {
        this.idCupChoose = idCupChoose;
        this.symbolPosition = symbol;
        this.nameCup = nameCup;
        this.amountCup = amountCup;
    }
    public String getIdCupChoose() {
        return idCupChoose;
    }

    public void setIdCupChoose(String idCupChoose) {
        this.idCupChoose = idCupChoose;
    }

    public int getSymbolPosition() {
        return symbolPosition;
    }

    public void setSymbolPosition(int symbolPosition) {
        this.symbolPosition = symbolPosition;
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
