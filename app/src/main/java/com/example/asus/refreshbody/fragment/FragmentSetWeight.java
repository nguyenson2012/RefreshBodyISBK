package com.example.asus.refreshbody.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.activity.MainActivity;
import com.example.asus.refreshbody.utils.Constant;

/**
 * Created by Asus on 10/15/2016.
 */

public class FragmentSetWeight extends Fragment implements View.OnClickListener{
    private EditText editTextWeight;
    private TextView tvDrinkTarget;
    private TextView tvOk;
    private TextView tvCancel;

    private SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_ask_height_user,container,false);
        setupView(layout);
        registerEvent();
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity)getActivity()).closeDrawer();
        sharedPreferences=getActivity().getSharedPreferences(Constant.MY_PREFERENCE, Context.MODE_PRIVATE);
    }

    private void registerEvent() {
        editTextWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()==0)
                    tvDrinkTarget.setText("0");
                else {
                    int currentWeight = Integer.parseInt(s.toString());
                    int targetAmount = currentWeight * Constant.water_rate;
                    tvDrinkTarget.setText(targetAmount + "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvCancel.setOnClickListener(this);
        tvOk.setOnClickListener(this);
    }

    private void setupView(View layout) {
        editTextWeight=(EditText)layout.findViewById(R.id.edittext_weight);
        tvDrinkTarget=(TextView)layout.findViewById(R.id.tv_target_amount);
        tvOk=(TextView)layout.findViewById(R.id.tv_ok_welcome);
        tvCancel=(TextView)layout.findViewById(R.id.tv_cancel_welcome);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel_welcome:
                ((MainActivity)getActivity()).addFragmentDrinkWater();
                break;
            case R.id.tv_ok_welcome:
                int weight = Integer.parseInt(editTextWeight.getText() + "");
                saveDrinkTarget(weight*Constant.water_rate, weight);
                ((MainActivity)getActivity()).addFragmentDrinkWater();
                break;
        }
    }

    private void saveDrinkTarget(int drinkTarget, int weight) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(Constant.DRINK_TARGET,drinkTarget);
        editor.putInt(SettingsFragment.WEIGHT, weight);
        editor.commit();
    }
}
