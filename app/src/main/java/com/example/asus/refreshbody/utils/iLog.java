package com.example.asus.refreshbody.utils;

import android.util.Log;

import com.example.asus.refreshbody.BuildConfig;

public class iLog {     //intelligent log

    private static final String PREFIX_TAG = "ISBK-";

    public interface LogTag {
        String UI = "Ui";

        String PROVIDER = "Provider";

        String SERVICE = "Service";
    }

    private static final boolean DEBUG = BuildConfig.DEBUG;

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(PREFIX_TAG + tag, msg);
        }
    }
}
