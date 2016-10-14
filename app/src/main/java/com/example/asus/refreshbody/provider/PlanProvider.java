package com.example.asus.refreshbody.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.asus.refreshbody.utils.iLog;

public class PlanProvider extends ContentProvider {

    private static final String TAG = PlanProvider.class.getSimpleName();

    private static final int REMINDER = 100;
    private static final int REMINDER_ID = 101;

    private static UriMatcher mUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        String authority = PlanContract.CONTENT_AUTHORITY;
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(authority, PlanContract.PATH_REMINDER_PLAN, REMINDER);
        matcher.addURI(authority, PlanContract.PATH_REMINDER_PLAN + "/#", REMINDER_ID);

        return matcher;
    }

    private PlanDBHelper mDbHelper;

    @Override
    public boolean onCreate() {
        iLog.d(iLog.LogTag.PROVIDER, TAG + " onCreate()");

        mDbHelper = new PlanDBHelper(getContext());
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        iLog.d(iLog.LogTag.PROVIDER, TAG + " getType() uri : " + uri);
        switch (mUriMatcher.match(uri)) {
            case REMINDER:
                return PlanContract.PlanEntry.CONTENT_TYPE;
            case REMINDER_ID:
                return PlanContract.PlanEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        iLog.d(iLog.LogTag.PROVIDER, TAG + " query() uri : " + uri);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;

        switch (mUriMatcher.match(uri)) {
            case REMINDER:
                cursor = db.query(
                        PlanContract.PlanEntry.TABLE_NAME,
                        projection, selection, selectionArgs,
                        null, null, sortOrder
                );
                break;
            case REMINDER_ID:
                long _id = ContentUris.parseId(uri);
                cursor = db.query(
                        PlanContract.PlanEntry.TABLE_NAME,
                        projection,
                        PlanContract.PlanEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null, null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
//        iLog.d(iLog.LogTag.PROVIDER, TAG + " insert() uri : " + uri);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long _id;
        Uri returnUri;

        switch (mUriMatcher.match(uri)) {
            case REMINDER:
                _id = db.insert(PlanContract.PlanEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = PlanContract.PlanEntry.buildTableUri(_id);
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
//        iLog.d(iLog.LogTag.PROVIDER, TAG + " delete() uri : " + uri);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rows;

        switch (mUriMatcher.match(uri)) {
            case REMINDER:
                rows = db.delete(PlanContract.PlanEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (selection == null || rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//        iLog.d(iLog.LogTag.PROVIDER, TAG + " update() uri : " + uri);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rows;

        switch (mUriMatcher.match(uri)) {
            case REMINDER:
                rows = db.update(PlanContract.PlanEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows;
    }
}
