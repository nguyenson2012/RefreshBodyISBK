package com.example.asus.refreshbody.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.asus.refreshbody.database.model.CupChooseItem;
import com.example.asus.refreshbody.database.model.DrinkIntakeItem;
import com.example.asus.refreshbody.database.model.TimeDrink;
import com.example.asus.refreshbody.database.model.User;

import java.util.ArrayList;

/**
 * Created by solei on 13/10/2016.
 */

public class PlanDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PlanList.db";

    private static final String INTEGER_TYPE = " INTEGER";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private final String SQL_CREATE_PLAN_TABLE =
            "CREATE TABLE " + PlanContract.PlanEntry.TABLE_NAME + " (" +
                    PlanContract.PlanEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    PlanContract.PlanEntry.COLUMN_TIME_HOUR + INTEGER_TYPE + COMMA_SEP +
                    PlanContract.PlanEntry.COLUMN_TIME_MINUTE + INTEGER_TYPE + COMMA_SEP +
                    PlanContract.PlanEntry.COLUMN_DAYS + " BLOB NOT NULL" + COMMA_SEP +
                    PlanContract.PlanEntry.COLUMN_MODE + TEXT_TYPE + COMMA_SEP +
                    PlanContract.PlanEntry.COLUMN_LABEL + TEXT_TYPE + COMMA_SEP +
                    PlanContract.PlanEntry.COLUMN_NOTE + TEXT_TYPE + COMMA_SEP +
                    PlanContract.PlanEntry.COLUMN_WORKINGTIME + INTEGER_TYPE + COMMA_SEP +
                    PlanContract.PlanEntry.COLUMN_SOUND + INTEGER_TYPE + COMMA_SEP +
                    PlanContract.PlanEntry.COLUMN_VIBRATION + INTEGER_TYPE + COMMA_SEP +
                    PlanContract.PlanEntry.COLUMN_ENABLE + INTEGER_TYPE +
                    ")";

    private final String SQL_CREATE_CUP_CHOOSE=
            "CREATE TABLE " + CupChooseItem.CUP_CHOOSE_TABLE+ " (" +
                    CupChooseItem.CUP_CHOOSE_ID + " STRING PRIMARY KEY" + COMMA_SEP +
                    CupChooseItem.CUP_CHOOSE_IMG_POS + INTEGER_TYPE + COMMA_SEP +
                    CupChooseItem.NAME_CUP + TEXT_TYPE + COMMA_SEP +
                    CupChooseItem.AMOUNT_CUP + INTEGER_TYPE+
                    ")";
    private final String SQL_CREATE_DRINK_INTAKE=
            "CREATE TABLE " + DrinkIntakeItem.DRINK_ITAKE_TABLE+ " (" +
                    DrinkIntakeItem.ID_DRINK + " STRING PRIMARY KEY" + COMMA_SEP +
                    DrinkIntakeItem.DRINK_INTAKE_IMG_POS + INTEGER_TYPE + COMMA_SEP +
                    DrinkIntakeItem.NAME_DRINK + TEXT_TYPE + COMMA_SEP +
                    DrinkIntakeItem.AMOUNT_DRINK + INTEGER_TYPE+COMMA_SEP+
                    DrinkIntakeItem.YEAR_DRINK + INTEGER_TYPE+COMMA_SEP+
                    DrinkIntakeItem.MONTH_DRINK + INTEGER_TYPE+COMMA_SEP+
                    DrinkIntakeItem.DAY_DRINK + INTEGER_TYPE+COMMA_SEP+
                    DrinkIntakeItem.HOUR_DRINK + INTEGER_TYPE+COMMA_SEP+
                    DrinkIntakeItem.MINUTE_DRINK + INTEGER_TYPE+COMMA_SEP+
                    DrinkIntakeItem.IS_UPDATE + INTEGER_TYPE+COMMA_SEP+
                    DrinkIntakeItem.IS_DELETE + INTEGER_TYPE+
                    ")";
    private final String SQL_CREATE_USER_TABLE=
            "CREATE TABLE " + User.USER_TABLE+ " (" +
                    User.EMAIL + " STRING PRIMARY KEY" + COMMA_SEP +
                    User.PASSWORD + TEXT_TYPE+
                    ")";
    private static final String SQL_DELETE_PLAN_TABLE =
            "DROP TABLE IF EXISTS " + PlanContract.PlanEntry.TABLE_NAME;
    private static final String SQL_DELETE_CUP_CHOOSE_TABLE =
            "DROP TABLE IF EXISTS " + CupChooseItem.CUP_CHOOSE_TABLE;
    private static final String SQL_DELETE_DRINK_INTAKE_TABLE =
            "DROP TABLE IF EXISTS " + DrinkIntakeItem.DRINK_ITAKE_TABLE;
    private static final String SQL_DELETE_USER_TABLE =
            "DROP TABLE IF EXISTS " + User.USER_TABLE;

    public static PlanDBHelper instance;

    public PlanDBHelper(Context context) {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public PlanDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static PlanDBHelper getInstance(Context context){
        if(instance==null)
            instance=new PlanDBHelper(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PLAN_TABLE);
        db.execSQL(SQL_CREATE_CUP_CHOOSE);
        db.execSQL(SQL_CREATE_DRINK_INTAKE);
        db.execSQL(SQL_CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PLAN_TABLE);
        onCreate(db);
    }
    public boolean insertCupChoose(CupChooseItem cupChooseItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CupChooseItem.CUP_CHOOSE_ID,cupChooseItem.getIdCupChoose());
        contentValues.put(CupChooseItem.CUP_CHOOSE_IMG_POS,cupChooseItem.getSymbolPosition());
        contentValues.put(CupChooseItem.NAME_CUP,cupChooseItem.getNameCup());
        contentValues.put(CupChooseItem.AMOUNT_CUP,cupChooseItem.getAmountCup());
        long column=db.insert(CupChooseItem.CUP_CHOOSE_TABLE,null,contentValues);
        if(column>0)
            return true;
        else
            return false;
    }
    public boolean updateCupChoose(CupChooseItem cupChooseItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CupChooseItem.CUP_CHOOSE_ID,cupChooseItem.getIdCupChoose());
        contentValues.put(CupChooseItem.CUP_CHOOSE_IMG_POS,cupChooseItem.getSymbolPosition());
        contentValues.put(CupChooseItem.NAME_CUP,cupChooseItem.getNameCup());
        contentValues.put(CupChooseItem.AMOUNT_CUP,cupChooseItem.getAmountCup());
        int column=db.update(CupChooseItem.CUP_CHOOSE_TABLE,contentValues,CupChooseItem.CUP_CHOOSE_ID+" = ?",new String[]{cupChooseItem.getIdCupChoose()});
    if(column>0)
        return true;
    else
        return false;
    }

    public int deleleCupChoose(String cupChooseId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CupChooseItem.CUP_CHOOSE_TABLE,
                CupChooseItem.CUP_CHOOSE_ID+" = ? ",
                new String[] { cupChooseId });
    }
    public ArrayList<CupChooseItem> getAllCupChoose(){
        ArrayList<CupChooseItem> cupChooseItemArrayList = new ArrayList<CupChooseItem>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CupChooseItem.CUP_CHOOSE_TABLE, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            cupChooseItemArrayList.add(new CupChooseItem(res.getString(res.getColumnIndex(CupChooseItem.CUP_CHOOSE_ID)),
                    res.getInt(res.getColumnIndex(CupChooseItem.CUP_CHOOSE_IMG_POS)),
                    res.getString(res.getColumnIndex(CupChooseItem.NAME_CUP)),
                    res.getInt(res.getColumnIndex(CupChooseItem.AMOUNT_CUP))));
            res.moveToNext();
        }
        return cupChooseItemArrayList;
    }

    public boolean insertDrinkIntake(DrinkIntakeItem drinkIntakeItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DrinkIntakeItem.ID_DRINK,drinkIntakeItem.getIdDrink());
        contentValues.put(DrinkIntakeItem.DRINK_INTAKE_IMG_POS,drinkIntakeItem.getSymbolPosition());
        contentValues.put(DrinkIntakeItem.NAME_DRINK,drinkIntakeItem.getNameDrink());
        contentValues.put(DrinkIntakeItem.AMOUNT_DRINK,drinkIntakeItem.getAmountDrink());
        contentValues.put(DrinkIntakeItem.YEAR_DRINK,drinkIntakeItem.getTimeDrink().getYearDrink());
        contentValues.put(DrinkIntakeItem.MONTH_DRINK,drinkIntakeItem.getTimeDrink().getMonthDrink());
        contentValues.put(DrinkIntakeItem.DAY_DRINK,drinkIntakeItem.getTimeDrink().getDayDrink());
        contentValues.put(DrinkIntakeItem.HOUR_DRINK,drinkIntakeItem.getTimeDrink().getHourDrink());
        contentValues.put(DrinkIntakeItem.MINUTE_DRINK,drinkIntakeItem.getTimeDrink().getMinuteDrink());
        contentValues.put(DrinkIntakeItem.IS_UPDATE,drinkIntakeItem.isUpdated());
        contentValues.put(DrinkIntakeItem.IS_DELETE,drinkIntakeItem.isDelete());
        long column=db.insert(DrinkIntakeItem.DRINK_ITAKE_TABLE,null,contentValues);
        if(column>0)
            return true;
        else
            return false;
    }
    public boolean updateDrinkIntake(DrinkIntakeItem drinkIntakeItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DrinkIntakeItem.ID_DRINK,drinkIntakeItem.getIdDrink());
        contentValues.put(DrinkIntakeItem.DRINK_INTAKE_IMG_POS,drinkIntakeItem.getSymbolPosition());
        contentValues.put(DrinkIntakeItem.NAME_DRINK,drinkIntakeItem.getNameDrink());
        contentValues.put(DrinkIntakeItem.AMOUNT_DRINK,drinkIntakeItem.getAmountDrink());
        contentValues.put(DrinkIntakeItem.YEAR_DRINK,drinkIntakeItem.getTimeDrink().getYearDrink());
        contentValues.put(DrinkIntakeItem.MONTH_DRINK,drinkIntakeItem.getTimeDrink().getMonthDrink());
        contentValues.put(DrinkIntakeItem.DAY_DRINK,drinkIntakeItem.getTimeDrink().getDayDrink());
        contentValues.put(DrinkIntakeItem.HOUR_DRINK,drinkIntakeItem.getTimeDrink().getHourDrink());
        contentValues.put(DrinkIntakeItem.MINUTE_DRINK,drinkIntakeItem.getTimeDrink().getMinuteDrink());
        contentValues.put(DrinkIntakeItem.IS_UPDATE,drinkIntakeItem.isUpdated());
        contentValues.put(DrinkIntakeItem.IS_DELETE,drinkIntakeItem.isDelete());
        int column=db.update(DrinkIntakeItem.DRINK_ITAKE_TABLE,contentValues,DrinkIntakeItem.ID_DRINK+" = ?",new String[]{drinkIntakeItem.getIdDrink()});
        if(column>0)
            return true;
        else
            return false;
    }

    public int deleleDrinkIntake(String drinkId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DrinkIntakeItem.DRINK_ITAKE_TABLE,
                DrinkIntakeItem.ID_DRINK+" = ? ",
                new String[] { drinkId });
    }
    public ArrayList<DrinkIntakeItem> getAllDrinkIntake(){
        ArrayList<DrinkIntakeItem> cupChooseItemArrayList = new ArrayList<DrinkIntakeItem>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+DrinkIntakeItem.DRINK_ITAKE_TABLE, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            DrinkIntakeItem drinkIntakeItem=new DrinkIntakeItem(res.getString(res.getColumnIndex(DrinkIntakeItem.ID_DRINK)),
                    res.getInt(res.getColumnIndex(DrinkIntakeItem.DRINK_INTAKE_IMG_POS)),
                    res.getString(res.getColumnIndex(DrinkIntakeItem.NAME_DRINK)),
                    res.getInt(res.getColumnIndex(DrinkIntakeItem.AMOUNT_DRINK)),
                    new TimeDrink(res.getInt(res.getColumnIndex(DrinkIntakeItem.YEAR_DRINK)),
                            res.getInt(res.getColumnIndex(DrinkIntakeItem.MONTH_DRINK)),
                            res.getInt(res.getColumnIndex(DrinkIntakeItem.DAY_DRINK)),
                            res.getInt(res.getColumnIndex(DrinkIntakeItem.HOUR_DRINK)),
                            res.getInt(res.getColumnIndex(DrinkIntakeItem.MINUTE_DRINK))));
            if(res.getInt(res.getColumnIndex(DrinkIntakeItem.IS_UPDATE))==1)
                drinkIntakeItem.setUpdated(true);
            else
                drinkIntakeItem.setUpdated(false);
            if(res.getInt(res.getColumnIndex(DrinkIntakeItem.IS_DELETE))==1)
                drinkIntakeItem.setDelete(true);
            else
                drinkIntakeItem.setDelete(false);
            cupChooseItemArrayList.add(drinkIntakeItem);
            res.moveToNext();
        }
        return cupChooseItemArrayList;
    }

    public boolean insertUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.EMAIL,user.getEmail());
        contentValues.put(User.PASSWORD,user.getPassword());
        long column=db.insert(User.USER_TABLE,null,contentValues);
        if(column>0)
            return true;
        else
            return false;
    }
    public boolean updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.EMAIL,user.getEmail());
        contentValues.put(User.PASSWORD,user.getPassword());
        int column=db.update(User.USER_TABLE,contentValues,User.EMAIL+" = ?",new String[]{user.getEmail()});
        if(column>0)
            return true;
        else
            return false;
    }

}
