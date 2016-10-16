package com.example.asus.refreshbody.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.asus.refreshbody.provider.PlanDBHelper;
import com.example.asus.refreshbody.utils.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Asus on 10/16/2016.
 */

public class FragmentBackUp extends Fragment {
    private TextView tvBackup,tvRestore;

    private PlanDBHelper planDBHelper;
    private String userId;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_backup,container,false);
        setupView(layout);
        registerEvent();
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        planDBHelper=PlanDBHelper.getInstance(getActivity());
        sharedPreferences=getActivity().getSharedPreferences(Constant.MY_PREFERENCE, Context.MODE_PRIVATE);
    }

    private void registerEvent() {
        tvRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tvBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<DrinkIntakeItem> drinkIntakeItems=planDBHelper.getAllDrinkIntake();
                for(DrinkIntakeItem drinkIntakeItem:drinkIntakeItems){
                    if(!drinkIntakeItem.isUpdated())
                        insertDrinkIntakeToServer(drinkIntakeItem);
                }
                Toast.makeText(getActivity(),"Back Up Success",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void insertDrinkIntakeToServer(final DrinkIntakeItem drinkIntakeItem) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constant.URL_UPLOAD_HISTORIES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("respond:", response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("register error", "Registration Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                getUserId();
                params.put(Constant.ID_DRINK_INTAKE,drinkIntakeItem.getIdDrink());
                params.put(Constant.ID_USER,userId);
                params.put(Constant.SYMBOL_POSITION,drinkIntakeItem.getSymbolPosition()+"");
                params.put(Constant.AMOUNT_DRINK,drinkIntakeItem.getAmountDrink()+"");
                params.put(Constant.NAME_DRINK,drinkIntakeItem.getNameDrink());
                params.put(Constant.TIME_DRINK,drinkIntakeItem.getDateString());
                return params;
            }

        };

        // Adding request to request queue
        RefreshBodyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void getUserId() {
        userId=sharedPreferences.getString(Constant.ID_USER,"");
    }

    private void setupView(View layout) {
        tvBackup=(TextView)layout.findViewById(R.id.tv_backup_record);
        tvRestore=(TextView)layout.findViewById(R.id.tv_restore_record);
    }


}
