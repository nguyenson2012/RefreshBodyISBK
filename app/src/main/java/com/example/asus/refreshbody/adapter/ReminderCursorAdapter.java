package com.example.asus.refreshbody.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.provider.PlanContract;
import com.example.asus.refreshbody.utils.UiUtils;
import com.example.asus.refreshbody.utils.iLog;

/**
 * Created by solei on 14/10/2016.
 */

public class ReminderCursorAdapter extends CursorAdapter {
    private final String TAG = ReminderCursorAdapter.this.getClass().getSimpleName();

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private int mColumnIdIndex;
    private int mColumnHourIndex;
    private int mColumnMinuteIndex;
    private int mColumnEnableIndex;
    private int mColumnDaysIndex;
//    private int mColumnSound;
//    private int mColumnVibration;

    public ReminderCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        iLog.d(TAG, "new Adapter is being created");

        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.list_reminder_item, parent, false);

        ViewHolder holder = new ViewHolder();
        holder.mRoot = view.findViewById(R.id.list_item_container);
        holder.mTextTime = (TextView) view.findViewById(R.id.textViewTime);
        holder.mTextDays = (TextView) view.findViewById(R.id.text_days);
        holder.mSwitchOnOff = (Switch) view.findViewById(R.id.switchOnOff);

        view.setTag(holder);

        initializeColumnIndex(cursor);

        return view;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        final long id = cursor.getLong(mColumnIdIndex);
        int timeHour = cursor.getInt(mColumnHourIndex);
        int timeMinute = cursor.getInt(mColumnMinuteIndex);
        final boolean enable = cursor.getInt(mColumnEnableIndex) != 0;
        byte[] days = cursor.getBlob(mColumnDaysIndex);

        holder.mTextTime.setText(UiUtils.getTimeFormat(mContext, timeHour, timeMinute));
        holder.mSwitchOnOff.setChecked(enable);
        holder.mTextDays.setText(UiUtils.getDaysString(mContext, days));
        holder.mSwitchOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get value then edit
                ContentValues contentValues = new ContentValues();
                contentValues.put(PlanContract.PlanEntry._ID, id);
                contentValues.put(PlanContract.PlanEntry.COLUMN_ENABLE, !enable);

                // update provider
                mContext.getContentResolver().update(
                        getContentUri(),
                        contentValues,
                        PlanContract.PlanEntry._ID + " = ?",
                        new String[]{String.valueOf(id)}
                );
            }
        });
    }

    private Uri getContentUri() {
        return PlanContract.PlanEntry.CONTENT_URI;
    }

    private void initializeColumnIndex(Cursor cursor) {
        mColumnIdIndex = cursor.getColumnIndex(PlanContract.PlanEntry._ID);
        mColumnHourIndex = cursor.getColumnIndex(PlanContract.PlanEntry.COLUMN_TIME_HOUR);
        mColumnMinuteIndex = cursor.getColumnIndex(PlanContract.PlanEntry.COLUMN_TIME_MINUTE);
        mColumnEnableIndex = cursor.getColumnIndex(PlanContract.PlanEntry.COLUMN_ENABLE);
        mColumnDaysIndex = cursor.getColumnIndex(PlanContract.PlanEntry.COLUMN_DAYS);

    }

    private class ViewHolder {
        protected View mRoot;
        protected TextView mTextTime;
        protected Switch mSwitchOnOff;
        protected TextView mTextDays;
    }

}
