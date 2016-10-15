package com.example.asus.refreshbody.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.fragment.FragmentLogin;
import com.example.asus.refreshbody.fragment.FragmentSignUp;
import com.example.asus.refreshbody.provider.PlanDBHelper;
import com.example.asus.refreshbody.utils.ScreenManager;

/**
 * Created by Asus on 10/14/2016.
 */

public class LoginOrSignUpActivity extends AppCompatActivity {
    private FragmentLogin fragmentLogin;
    private FragmentSignUp fragmentSignUp;

    private ScreenManager screenManager;

    private PlanDBHelper planDBHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_signup_activity);
        screenManager=ScreenManager.getInst();
        planDBHelper=PlanDBHelper.getInstance(this);
        planDBHelper.deleleAllDrinkIntake();
        intiliazeFragment();
        addFragmentLogin();
    }

    private void addFragmentLogin() {
        screenManager.openFragment(getSupportFragmentManager(),R.id.container_login_or_signup,fragmentLogin,false);
    }

    private void intiliazeFragment() {
        fragmentLogin=new FragmentLogin();
        fragmentSignUp=new FragmentSignUp();
    }
}
