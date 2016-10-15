package com.example.asus.refreshbody.database;

import com.example.asus.refreshbody.database.model.CupChooseItem;
import com.example.asus.refreshbody.database.model.DrinkIntakeItem;
import com.example.asus.refreshbody.database.model.User;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Asus on 10/14/2016.
 */

public class DBContext {
    private Realm realm;

    private DBContext() {
        realm = Realm.getDefaultInstance();
    }

    private static DBContext inst;

    public static DBContext getInst() {
        if (inst == null) {
            inst = new DBContext();
        }
        return inst;
    }
    public void createUser(User user){
        realm.beginTransaction();
        realm.copyToRealm(user);
        realm.commitTransaction();
    }

    public User getUserByUsernameAndPassword(String userEmail, String password) {
        User user = null;
        try {
            user = realm.where(User.class).equalTo(User.EMAIL, userEmail).equalTo(User.PASSWORD, password).findFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public void createDrinkIntakeItem(DrinkIntakeItem drinkIntakeItem){
        realm.beginTransaction();
        realm.copyToRealm(drinkIntakeItem);
        realm.commitTransaction();
    }
    public ArrayList<DrinkIntakeItem> getDrinkIntakeListByDay(int day, int month, int year){
        List<DrinkIntakeItem> allDrinkIntake=realm.where(DrinkIntakeItem.class).findAll();
        ArrayList<DrinkIntakeItem> listDrinkIntakeChoose=new ArrayList<DrinkIntakeItem>();
        for(DrinkIntakeItem drinkIntakeItem:allDrinkIntake){
            if(drinkIntakeItem.getTimeDrink().getDayDrink()==day&&drinkIntakeItem.getTimeDrink().getMonthDrink()==month
                    &&drinkIntakeItem.getTimeDrink().getYearDrink()==year)
                listDrinkIntakeChoose.add(drinkIntakeItem);
        }
        return listDrinkIntakeChoose;
    }

    public void deleteDrinkIntake(final String drinkId){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<DrinkIntakeItem> rows = realm.where(DrinkIntakeItem.class).equalTo(DrinkIntakeItem.USER_ID,drinkId).findAll();
                rows.clear();
            }
        });
    }
    public void updateDrinkIntake(DrinkIntakeItem drinkIntakeItem,int symbolPosition,String nameDrink,int drinkAmount){
        realm.beginTransaction();
        drinkIntakeItem.setSymbolPosition(symbolPosition);
        drinkIntakeItem.setNameDrink(nameDrink);
        drinkIntakeItem.setAmountDrink(drinkAmount);
        realm.commitTransaction();
    }

    public void createCupChooseItem(CupChooseItem cupChooseItem){
        realm.beginTransaction();
        realm.copyToRealm(cupChooseItem);
        realm.commitTransaction();
    }

    public List<CupChooseItem> getAllCupChooseItem(){
        return realm.where(CupChooseItem.class).findAll();
    }

    public void updateCupChooseItem(CupChooseItem cupChooseItem,int imgPos,String nameCup,int amountDrink){
        realm.beginTransaction();
        cupChooseItem.setSymbolPosition(imgPos);
        cupChooseItem.setNameCup(nameCup);
        cupChooseItem.setAmountCup(amountDrink);
        realm.commitTransaction();
    }


}
