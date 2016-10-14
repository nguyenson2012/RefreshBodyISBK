package com.example.asus.refreshbody.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.asus.refreshbody.utils.iLog;


public class AlarmServiceReceiver extends BroadcastReceiver {

    private static final String TAG = AlarmServiceReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        iLog.d(TAG, "onReceive() context : " + context + " intent : " + intent);
        Intent serviceIntent = new Intent(context, AlarmService.class);
        context.startService(serviceIntent);
    }
}
