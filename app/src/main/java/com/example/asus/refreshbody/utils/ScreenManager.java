package com.example.asus.refreshbody.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;

import com.example.asus.refreshbody.R;

/**
 * Created by linhdq on 9/7/16.
 */
public class ScreenManager {

    private static ScreenManager screenManager;

    public static ScreenManager getInst() {
        if (screenManager == null) {
            screenManager = new ScreenManager();
        }
        return screenManager;
    }

    public void openFragment(FragmentManager fragmentManager, int layoutContainerId,
                             Fragment fragment, boolean addToBackStack) {
        if (fragment != null && !fragment.isAdded()) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(layoutContainerId, fragment);
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(fragment.getClass().toString());
            }
            fragmentTransaction.commit();
        }
    }

    public void openFragmentWithAnimation(FragmentManager fragmentManager, int layoutContainerId,
                                          Fragment fragment, boolean addToBackStack) {
        if (fragment != null && !fragment.isAdded()) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.trans_in, R.anim.trans_out,
                    R.anim.trans_back_in, R.anim.trans_back_out);
            fragmentTransaction.replace(layoutContainerId, fragment);
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(fragment.getClass().toString());
            }
            fragmentTransaction.commit();
        }
    }

    public void setFullScreen(Window window) {
        if (Build.VERSION.SDK_INT < 19) {
            window.getDecorView().setSystemUiVisibility(View.GONE);
        } else {
//            window.getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public void showDialog(Context context, String title, String mess) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(mess)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
