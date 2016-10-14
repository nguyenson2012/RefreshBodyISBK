package com.example.asus.refreshbody.database;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.database.model.CupChooseItem;

/**
 * Created by Asus on 10/14/2016.
 */

public class DefaultData {
    private DBContext dbContext;

    private static DefaultData inst;

    public static DefaultData getInst(DBContext dbContext) {
        if (inst == null) {
            inst = new DefaultData(dbContext);
        }
        return inst;
    }
    private DefaultData(){}

    private DefaultData(DBContext dbContext) {
        this.dbContext = dbContext;
    }

    public void setDefaultCupChooseItem(){
        dbContext.createCupChooseItem(new CupChooseItem("1", R.drawable.cup_one,"Water",100));
        dbContext.createCupChooseItem(new CupChooseItem("2", R.drawable.cup_one,"Water",200));
        dbContext.createCupChooseItem(new CupChooseItem("3", R.drawable.cup_one,"Water",300));
        dbContext.createCupChooseItem(new CupChooseItem("4", R.drawable.cup_one,"Water",400));
        dbContext.createCupChooseItem(new CupChooseItem("5", R.drawable.cup_one,"Water",500));
        dbContext.createCupChooseItem(new CupChooseItem("6", R.drawable.cup_one,"Water",600));
    }
}
