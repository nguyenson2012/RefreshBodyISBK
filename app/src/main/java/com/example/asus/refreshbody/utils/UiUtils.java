package com.example.asus.refreshbody.utils;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateFormat;

import com.example.asus.refreshbody.R;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class UiUtils {

//    private static Animation mAnimationOpen;
//
//    private static Animation mAnimationClose;
//
//    public static void hideView(Context context, View view) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (mAnimationClose == null) {
//                mAnimationClose = AnimationUtils.loadAnimation(context, R.anim.fab_close);
//            }
//            view.startAnimation(mAnimationClose);
//            view.setClickable(false);
//        } else {
//            view.setVisibility(View.GONE);
//        }
//    }
//
//    public static void showView(Context context, View view) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (mAnimationOpen == null) {
//                mAnimationOpen = AnimationUtils.loadAnimation(context, R.anim.fab_open);
//            }
//            view.startAnimation(mAnimationOpen);
//            view.setClickable(true);
//        } else {
//            view.setVisibility(View.VISIBLE);
//        }
//    }

    public static String getTimeFormat(Context context, int hour, int min) {
        String outPut = null;
        try {
            String _24HourTime = hour + ":" + min;
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm", Locale.getDefault());
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            Date _24HourDt = _24HourSDF.parse(_24HourTime);
            if (DateFormat.is24HourFormat(context)) {
                outPut = _24HourSDF.format(_24HourDt);
            } else {
                outPut = _12HourSDF.format(_24HourDt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outPut;
    }

    public static String getTimeFormat(int hour, int min) {
        String outPut = null;
        try {
            String _24HourTime = hour + ":" + min;
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date _24HourDt = _24HourSDF.parse(_24HourTime);
            outPut = _24HourSDF.format(_24HourDt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outPut;
    }

    public static boolean isConnectedViaWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
        return isConnected && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static boolean isConnectedViaMobileData(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
        return isConnected && activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    private static String mDaysList[] = null;

    public static String getDayString(Context context, int day) {
        if(mDaysList == null) {
            mDaysList = context.getResources().getStringArray(R.array.day_name);
        }
        return mDaysList[day];
    }

    public static ArrayList<Byte> getDaysArrayList(byte[] days) {
        ArrayList<Byte> daysArrayList = null;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(days);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object object = objectInputStream.readObject();

            if (object instanceof ArrayList) {
                daysArrayList = (ArrayList<Byte>) object;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return daysArrayList;
    }

    public static String getDaysString(Context context, byte[] days) {
        StringBuilder daysStringBuilder = new StringBuilder();
        ArrayList<Byte> listDays = getDaysArrayList(days);
        Resources res = context.getResources();
        if (listDays.size() == 7) {
            daysStringBuilder.append(res.getString(R.string.every_day));
        } else if (listDays.size() == 0) {
            daysStringBuilder.append(res.getString(R.string.once_time));
        } else {
            for (int i = 0; i < listDays.size() ;i++){
                daysStringBuilder.append(getDayString(context, listDays.get(i))).append(", ");
            }
            daysStringBuilder.setLength(daysStringBuilder.length() - 2);
        }

        return daysStringBuilder.toString();
    }
}
