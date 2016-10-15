package com.example.asus.refreshbody.controller;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.activity.MainActivity;
import com.example.asus.refreshbody.provider.PlanContract;
import com.example.asus.refreshbody.provider.ReminderPlan;
import com.example.asus.refreshbody.service.AlarmServiceReceiver;
import com.example.asus.refreshbody.utils.iLog;


public class SwitchControllerService extends Service {

    private static final String TAG = SwitchControllerService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        iLog.d(TAG, "onStartCommand() intent : " + intent + " flags : " + flags + " startId : " + startId);
        if (intent != null) {
            handleCommandIntent(intent);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void handleCommandIntent(Intent intent) {
        iLog.d(TAG, "handleCommandIntent()");
        ReminderPlan reminderPlan = (ReminderPlan) intent.getExtras().getSerializable(ReminderPlan.CLASS_NAME);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.drawable.icon_menu_notification);

        if (reminderPlan.getMode() == 0) {
            notificationBuilder.setContentTitle("Drink Water Reminder");
            notificationBuilder.setContentText("Have you drink enough water today?");
        } else if (reminderPlan.getMode() == 1) {
            notificationBuilder.setContentTitle("Time to rest");
            notificationBuilder.setContentText("You worked during " + reminderPlan.getWorkingTime() + 1 + "h. Take a rest"  );

        } else if (reminderPlan.getMode() == 2) {
            notificationBuilder.setContentTitle(reminderPlan.getLabel());
            notificationBuilder.setContentText(reminderPlan.getNote());
        }

        if (reminderPlan.getSound()) notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        if (reminderPlan.getVibration()) notificationBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
//        notificationBuilder.setDefaults(Notification.FLAG_SHOW_LIGHTS);
//        notificationBuilder.setLights(Color.RED, 3000, 3000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_MAX);
        }

        Intent notiIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 4, notiIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setAutoCancel(true);

        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify((int) reminderPlan.getId(), notificationBuilder.build());

        updateNewAlarm(reminderPlan);

    }

    private void updateNewAlarm(ReminderPlan plan) {
        // if this plan repeat = false, disable it
        if (!plan.getRepeat()) {
            // get value then edit
            ContentValues contentValues = new ContentValues();
            contentValues.put(PlanContract.PlanEntry._ID, plan.getId());
            contentValues.put(PlanContract.PlanEntry.COLUMN_ENABLE, false);

            Uri uri = PlanContract.PlanEntry.CONTENT_URI;

            // update provider
            getContentResolver().update(
                    uri,
                    contentValues,
                    PlanContract.PlanEntry._ID + " = ?",
                    new String[]{String.valueOf(plan.getId())}
            );

            if (plan.getMode() == 1)
                getContentResolver().delete(uri, PlanContract.PlanEntry._ID + " = ?", new String[]{String.valueOf(plan.getId())});
        }

        // Re-trigger AlarmServiceReceiver to update new alarm
        Intent alarmServiceIntent = new Intent(getApplicationContext(), AlarmServiceReceiver.class);
        sendBroadcast(alarmServiceIntent);
    }

}
