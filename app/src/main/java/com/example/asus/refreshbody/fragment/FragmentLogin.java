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
import android.util.Log;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.RefreshBodyApplication;
import com.example.asus.refreshbody.activity.MainActivity;
import com.example.asus.refreshbody.database.model.DrinkIntakeItem;
import com.example.asus.refreshbody.database.model.TimeDrink;
import com.example.asus.refreshbody.database.model.User;
import com.example.asus.refreshbody.provider.PlanDBHelper;
import com.example.asus.refreshbody.utils.Constant;
import com.example.asus.refreshbody.utils.ScreenManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    private PlanDBHelper planDBHelper;

    //animation
    private Animation animationIn;
    private Animation animationOut;


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
        //checkUserLoggedIn();
    }

    @Override
    public void onStop() {
        super.onStop();
        //
        snackbar.dismiss();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        planDBHelper=PlanDBHelper.getInstance(getActivity());
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
        // Check for empty data in the form
        if (!userEmail.isEmpty() && !password.isEmpty()) {
            // login user
            checkLogin(userEmail, password);
        } else {
            // Prompt user to enter credentials
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.check_credential), Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void checkLogin(final String userEmail, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.GET,
                Constant.URL_LOGIN+"/"+userEmail+"/"+password, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("login response", "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String idUser=jObj.getString(Constant.ID_USER);
                    JSONArray histories=jObj.getJSONArray(Constant.HISTORIES);
                    for(int i=0;i<histories.length();i++){
                        JSONObject jsonObject=histories.getJSONObject(i);
                        DrinkIntakeItem drinkIntakeItem=new DrinkIntakeItem();
                        drinkIntakeItem.setIdDrink(jsonObject.getString(Constant.ID_DRINK_INTAKE));
                        drinkIntakeItem.setSymbolPosition(Integer.parseInt(jsonObject.getString(Constant.SYMBOL_POSITION)));
                        drinkIntakeItem.setAmountDrink(Integer.parseInt(jsonObject.getString(Constant.AMOUNT_DRINK)));
                        drinkIntakeItem.setNameDrink(jsonObject.getString(Constant.NAME_DRINK));
                        drinkIntakeItem.setDateString(jsonObject.getString(Constant.TIME_DRINK));
                        planDBHelper.insertDrinkIntake(drinkIntakeItem);
                    }
                    saveIdUser(idUser);

                    // Inserting row in users table
                    planDBHelper.insertUser(new User(userEmail, password));
                    // Launch main activity
                    doActivityAfterLogin();

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error login", "Login Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constant.EMAIL, userEmail);
                params.put(Constant.PASSWORD, password);

                return params;
            }

        };

        // Adding request to request queue
        RefreshBodyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void saveIdUser(String idUser) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(Constant.ID_USER,idUser);
        editor.commit();
    }

    private void setLoginSession() {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(Constant.IS_LOGGED_IN,true);
        editor.commit();
    }

    private boolean checkUserExist(String userEmail, String password) {
        ArrayList<User> userArrayList=planDBHelper.getAllUser();
        boolean isUserExist=false;
        for(User user:userArrayList){
            if(user.getEmail().equals(userEmail)&&user.getPassword().equals(password))
                isUserExist=true;
        }
        return isUserExist;
    }
}

