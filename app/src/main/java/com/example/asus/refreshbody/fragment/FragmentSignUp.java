package com.example.asus.refreshbody.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.RefreshBodyApplication;
import com.example.asus.refreshbody.activity.LoginOrSignUpActivity;
import com.example.asus.refreshbody.activity.MainActivity;
import com.example.asus.refreshbody.database.model.User;
import com.example.asus.refreshbody.provider.PlanDBHelper;
import com.example.asus.refreshbody.utils.Constant;
import com.example.asus.refreshbody.utils.ScreenManager;
import com.example.asus.refreshbody.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Asus on 10/14/2016.
 */

public class FragmentSignUp extends Fragment implements View.OnClickListener {
    //view
    private Button btnSignUp;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewInvalideEmailOrPassword;

    private Context context;

    private PlanDBHelper planDBHelper;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        init(view);
        addListener();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        planDBHelper=PlanDBHelper.getInstance(getActivity());
        sharedPreferences = getActivity().getSharedPreferences(Constant.MY_PREFERENCE, Context.MODE_PRIVATE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void addListener() {
        btnSignUp.setOnClickListener(this);
    }

    private void init(View view) {
        //view
        btnSignUp = (Button) view.findViewById(R.id.btn_signup);
        editTextEmail=(EditText)view.findViewById(R.id.input_email_signup);
        editTextPassword=(EditText)view.findViewById(R.id.input_password_signup);
        textViewInvalideEmailOrPassword=(TextView)view.findViewById(R.id.txt_wrong_acc_signup);
        context = view.getContext();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signup:
                //save account
                if(validateEmailAndPassword())
                createNewUser();
                break;

        }
    }

    private boolean validateEmailAndPassword() {
        boolean emailValidate=false;
        boolean passwordValidate=false;
        if(StringUtils.validateEmail(editTextEmail.getText()+""))
            emailValidate=true;
        if(StringUtils.validatePassword(editTextPassword.getText()+""))
            passwordValidate=true;
        if(!passwordValidate||!emailValidate){
            textViewInvalideEmailOrPassword.setVisibility(View.VISIBLE);
            return false;
        }else{
            textViewInvalideEmailOrPassword.setVisibility(View.GONE);
            return true;
        }

    }

    private void createNewUser() {
        //dbContext.createUser(new User(editTextEmail.getText()+"",editTextPassword.getText()+""));
        planDBHelper.insertUser(new User(editTextEmail.getText()+"",editTextPassword.getText()+""));
        String email=editTextEmail.getText()+"";
        String password=editTextPassword.getText()+"";
        if (!email.isEmpty() && !password.isEmpty()) {
            registerUser(email, password);
        } else {
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.enter_detail), Toast.LENGTH_LONG)
                    .show();
        }
    }
    private void registerUser(final String email,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constant.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("register response", "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String userId = jObj.getString(Constant.ID_USER);
                        String email =jObj.getString(Constant.EMAIL);
                        String password = jObj.getString(Constant.PASSWORD);

                        // Inserting row in users table
                        planDBHelper.insertUser(new User(email,password));
                        saveIdUser(userId);
                    openMainActivity();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("register error", "Registration Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

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


    private void openMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}

