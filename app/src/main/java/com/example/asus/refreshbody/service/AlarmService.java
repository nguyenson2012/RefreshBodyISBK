package com.example.asus.refreshbody.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;

import com.example.asus.refreshbody.controller.AlarmAlertReceiver;
import com.example.asus.refreshbody.provider.PlanContract;
import com.example.asus.refreshbody.provider.ReminderPlan;
import com.example.asus.refreshbody.utils.UiUtils;
import com.example.asus.refreshbody.utils.iLog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

/**
 * This service is used to set and cancel alarm<br/>
 * It gets the earliest alarm in provider then set it up
 */
public class AlarmService extends Service {

    private static final String TAG = AlarmService.class.getSimpleName();

    private Alarm getNextConnectionAlarm() {
        iLog.d(TAG, "getNextConnectionAlarm() begin");
        Alarm result = null;

        Set<Alarm> alarmsQueue = new TreeSet<>(new Comparator<Alarm>() {
            @Override
            public int compare(Alarm lhs, Alarm rhs) {
                long diff = lhs.calendar.getTimeInMillis() - rhs.calendar.getTimeInMillis();

                if (diff > 0) {
                    return 1;
                } else if (diff < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        String selections = PlanContract.PlanEntry.COLUMN_ENABLE + " = ?";

        String[] selectionArgs = new String[]{String.valueOf(1)};

        Cursor cursor = getContentResolver().query(
                PlanContract.PlanEntry.CONTENT_URI,
                null, selections, selectionArgs, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Alarm alarm = new Alarm();
                alarm.setValue(cursor);
                alarm.reminderPlan = (new ReminderPlan.Builder()).setPlanFromCursor(cursor).build();
                alarmsQueue.add(alarm);
            }
            cursor.close();

            if (alarmsQueue.iterator().hasNext()) {
                result = alarmsQueue.iterator().next();
            }
        }
        iLog.d(TAG, "getNextConnectionAlarm() finish result : " + result);
        return result;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        iLog.d(TAG, "onStartCommand() intent : " + intent + " flags : " + flags + " startId : " + startId);
        Alarm alarm = getNextConnectionAlarm();

        iLog.d(TAG, "onStartCommand() " + (alarm == null ? "null" : alarm.reminderPlan));
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent alarmConnectionIntent = new Intent(getApplicationContext(), AlarmAlertReceiver.class);
        alarmConnectionIntent.putExtra(ReminderPlan.CLASS_NAME, alarm == null ? null : alarm.reminderPlan);

        PendingIntent connectionPendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(),
                13,
                alarmConnectionIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        if (alarm != null) {
            Calendar calendar = alarm.calendar;

            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    0,
                    connectionPendingIntent);
            iLog.d(TAG, "SetAlarm " + getTimeUntilNextAlarmMessage(calendar.getTimeInMillis()));
        } else {
            iLog.d(TAG, "CancelAlarm");
            alarmManager.cancel(connectionPendingIntent);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private class Alarm {
        long id;
        Calendar calendar;
        ReminderPlan reminderPlan;

        void setValue(Cursor data) {
            id = data.getLong(data.getColumnIndex(PlanContract.PlanEntry._ID));
            int hour = data.getInt(data.getColumnIndex(PlanContract.PlanEntry.COLUMN_TIME_HOUR));
            int minute = data.getInt(data.getColumnIndex(PlanContract.PlanEntry.COLUMN_TIME_MINUTE));
            int mode = data.getInt(data.getColumnIndex(PlanContract.PlanEntry.COLUMN_MODE));
            int workingTime = data.getInt(data.getColumnIndex(PlanContract.PlanEntry.COLUMN_WORKINGTIME));
            if (mode == 1) hour +=  workingTime + 1;
            byte[] daysByte = data.getBlob(data.getColumnIndex(PlanContract.PlanEntry.COLUMN_DAYS));
            ArrayList<Byte> daysArrayList = UiUtils.getDaysArrayList(daysByte);

            calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);

            if (calendar.before(Calendar.getInstance())) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            if (daysArrayList.size() != 0) {
                while (!daysArrayList.contains((byte) (calendar.get(Calendar.DAY_OF_WEEK) - 1))) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }
            }
        }
    }

    /**
     * Use for log only
     *
     * @param triggerTime time in ms
     * @return convert result
     */
    private String getTimeUntilNextAlarmMessage(long triggerTime) {
        long timeDifference = triggerTime - System.currentTimeMillis();
        long days = timeDifference / (1000 * 60 * 60 * 24);
        long hours = timeDifference / (1000 * 60 * 60) - (days * 24);
        long minutes = timeDifference / (1000 * 60) - (days * 24 * 60) - (hours * 60);
        long seconds = timeDifference / (1000) - (days * 24 * 60 * 60) - (hours * 60 * 60) - (minutes * 60);
        String alert = "Alarm will be triggered in ";
        if (days > 0) {
            alert += String.format(Locale.UK,
                    "%d days, %d hours, %d minutes and %d seconds", days,
                    hours, minutes, seconds);
        } else {
            if (hours > 0) {
                alert += String.format(Locale.UK, "%d hours, %d minutes and %d seconds",
                        hours, minutes, seconds);
            } else {
                if (minutes > 0) {
                    alert += String.format(Locale.UK, "%d minutes, %d seconds", minutes,
                            seconds);
                } else {
                    alert += String.format(Locale.UK, "%d seconds", seconds);
                }
            }
        }
        return alert;
    }
}
