package com.example.asus.refreshbody.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.asus.refreshbody.provider.ReminderPlan;
import com.example.asus.refreshbody.utils.iLog;

public class AlarmAlertReceiver extends BroadcastReceiver {

    private static final String TAG = AlarmAlertReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        iLog.d(TAG, "onReceive() context : " + context + " intent : " + intent);

        // Start SwitchControllerService
        Intent alarmAlertServiceIntent = new Intent(context, SwitchControllerService.class);
        alarmAlertServiceIntent.putExtra(ReminderPlan.CLASS_NAME, intent.getExtras().getSerializable(ReminderPlan.CLASS_NAME));

        iLog.d(TAG, "onReceive() " + intent.getExtras().getSerializable(ReminderPlan.CLASS_NAME));

        context.startService(alarmAlertServiceIntent);
    }
}
