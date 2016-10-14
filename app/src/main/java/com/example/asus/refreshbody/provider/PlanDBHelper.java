package com.example.asus.refreshbody.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by solei on 13/10/2016.
 */

public class PlanDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PlanList.db";

    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private final String SQL_CREATE_PLAN_TABLE =
            "CREATE TABLE " + PlanContract.PlanEntry.TABLE_NAME + " (" +
                    PlanContract.PlanEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    PlanContract.PlanEntry.COLUMN_TIME_HOUR + INTEGER_TYPE + COMMA_SEP +
                    PlanContract.PlanEntry.COLUMN_TIME_MINUTE + INTEGER_TYPE + COMMA_SEP +
                    PlanContract.PlanEntry.COLUMN_DAYS + " BLOB NOT NULL" + COMMA_SEP +
                    PlanContract.PlanEntry.COLUMN_SOUND + INTEGER_TYPE + COMMA_SEP +
                    PlanContract.PlanEntry.COLUMN_VIBRATION + INTEGER_TYPE + COMMA_SEP +
                    PlanContract.PlanEntry.COLUMN_ENABLE + INTEGER_TYPE +
                    ")";

    private static final String SQL_DELETE_PLAN_TABLE =
            "DROP TABLE IF EXISTS " + PlanContract.PlanEntry.TABLE_NAME;


    public PlanDBHelper(Context context) {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public PlanDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PLAN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PLAN_TABLE);
        onCreate(db);
    }
}
