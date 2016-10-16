package com.example.asus.refreshbody.provider;

import com.example.asus.refreshbody.database.model.CupChooseItem;

/**
 * Created by Asus on 10/15/2016.
 */

public class DefaultDataSqlite {
    private PlanDBHelper planDBHelper;

    private static DefaultDataSqlite inst;

    public static DefaultDataSqlite getInst(PlanDBHelper planDBHelper) {
        if (inst == null) {
            inst = new DefaultDataSqlite(planDBHelper);
        }
        return inst;
    }
    private DefaultDataSqlite(){}

    private DefaultDataSqlite(PlanDBHelper planDBHelper) {
        this.planDBHelper = planDBHelper;
    }

    public void setDefaultData(){
        //insert cup choose
        planDBHelper.insertCupChoose(new CupChooseItem("1",1,"water",100));
        planDBHelper.insertCupChoose(new CupChooseItem("2",2,"water",200));
        planDBHelper.insertCupChoose(new CupChooseItem("3",3,"water",300));
        planDBHelper.insertCupChoose(new CupChooseItem("4",4,"water",400));
        planDBHelper.insertCupChoose(new CupChooseItem("5",5,"water",500));

    }
}
