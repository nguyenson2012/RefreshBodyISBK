package com.example.asus.refreshbody.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.activity.LoginOrSignUpActivity;
import com.example.asus.refreshbody.activity.MainActivity;
import com.example.asus.refreshbody.database.DBContext;
import com.example.asus.refreshbody.database.model.User;
import com.example.asus.refreshbody.utils.Constant;
import com.example.asus.refreshbody.utils.ScreenManager;
import com.example.asus.refreshbody.utils.StringUtils;

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

    private DBContext dbContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        init(view);
        addListener();

        return view;
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
        editTextPassword=(EditText)view.findViewById(R.id.input_layout_password_signup);
        textViewInvalideEmailOrPassword=(TextView)view.findViewById(R.id.txt_wrong_acc_signup);
        context = view.getContext();
        dbContext=DBContext.getInst();
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
        if(passwordValidate||emailValidate){
            textViewInvalideEmailOrPassword.setVisibility(View.VISIBLE);
            return false;
        }else{
            textViewInvalideEmailOrPassword.setVisibility(View.GONE);
            return true;
        }

    }

    private void createNewUser() {
        dbContext.createUser(new User(editTextEmail.getText()+"",editTextPassword.getText()+""));
        openMainActivity();
    }

    private void openMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}

