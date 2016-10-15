package com.example.asus.refreshbody.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.activity.MainActivity;
import com.example.asus.refreshbody.database.DBContext;
import com.example.asus.refreshbody.utils.Constant;
import com.example.asus.refreshbody.utils.ScreenManager;

/**
 * Created by Asus on 10/14/2016.
 */

public class FragmentLogin extends Fragment implements View.OnClickListener {
    //view
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button btnLogin;
    private TextView btnCreateAccount;
    private TextView txtWrongAcc;
    private CoordinatorLayout coordinatorLayout;
    private Snackbar snackbar;

    //
    private Resources resources;
    private SharedPreferences sharedPreferences;

    //
    private Context context;
    private ScreenManager screenManager;

    //
    private FragmentSignUp fragmentSignUp;

    //animation
    private Animation animationIn;
    private Animation animationOut;

    //database
    private DBContext dbContext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        init(view);
        addListener();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //
        checkUserLoggedIn();
    }

    @Override
    public void onStop() {
        super.onStop();
        //
        snackbar.dismiss();
    }

    private void addListener() {
        btnLogin.setOnClickListener(this);
        btnCreateAccount.setOnClickListener(this);
    }

    private void fillLogin(){
        editTextEmail.setText("admin@gmail.com");
        editTextPassword.setText("123456");
    }

    private void init(View view) {
        //view
        editTextEmail = (EditText) view.findViewById(R.id.input_email_login);
        editTextPassword = (EditText) view.findViewById(R.id.input_password_login);
        btnLogin = (Button) view.findViewById(R.id.btn_login);
        btnCreateAccount = (TextView) view.findViewById(R.id.tv_create_account_new_here);
        txtWrongAcc = (TextView) view.findViewById(R.id.txt_wrong_acc_login);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout_login);

        //
        context = view.getContext();
        screenManager = ScreenManager.getInst();

        //
        sharedPreferences = getActivity().getSharedPreferences(Constant.MY_PREFERENCE, Context.MODE_PRIVATE);
        resources = getResources();
        //
        snackbar =
                Snackbar.make(coordinatorLayout,
                        "Logging in...",
                        Snackbar.LENGTH_INDEFINITE);
        Snackbar.SnackbarLayout snack_view = (Snackbar.SnackbarLayout) snackbar.getView();
        snack_view.setBackgroundColor(resources.getColor(R.color.colorPrimary));
        snack_view.addView(new ProgressBar(context));

        //
        fragmentSignUp = new FragmentSignUp();



        //animation
        animationIn = AnimationUtils.loadAnimation(context, R.anim.zoom_in);
        animationOut = AnimationUtils.loadAnimation(context, R.anim.zoom_out);

        //database
        dbContext = DBContext.getInst();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                checkUserAuthentication();
                break;
            case R.id.tv_create_account_new_here:
                btnCreateAccount.startAnimation(animationIn);
                animationOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        screenManager.openFragmentWithAnimation(getActivity().getSupportFragmentManager(),
                                R.id.container_login_or_signup, fragmentSignUp, true);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                btnCreateAccount.startAnimation(animationOut);
                break;
        }
    }

    private void checkUserLoggedIn() {
        boolean isUserLoggedIn = sharedPreferences.getBoolean(Constant.CHECK_USER_LOGGED_IN, false);
        if (isUserLoggedIn) {
            doActivityAfterLogin();
        } else {
            fillLogin();
        }
    }

    private void doActivityAfterLogin() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void checkUserAuthentication() {
        //
        txtWrongAcc.setVisibility(View.GONE);
        //
        String userEmail = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        if (dbContext.getUserByUsernameAndPassword(userEmail, password) != null) {
            //
            snackbar.show();
            //
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constant.LOGGED_IN_USER_EMAIL, userEmail);
            editor.putBoolean(Constant.CHECK_USER_LOGGED_IN, true);
            editor.commit();

            //
            doActivityAfterLogin();
        } else {
            txtWrongAcc.setVisibility(View.VISIBLE);
        }
    }
}

