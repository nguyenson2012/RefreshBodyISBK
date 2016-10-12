package com.example.asus.refreshbody.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.refreshbody.R;
import com.github.lzyzsd.circleprogress.CircleProgress;

/**
 * Created by Asus on 10/12/2016.
 */

public class FragmentDrinkWater extends Fragment implements View.OnClickListener{
    private ImageView imgDrinkState;
    private CircleProgress circleProgressDrink;
    private TextView tvDrinkState;
    private RecyclerView recyclerViewDrinkIntake;
    private TextView tvAddDrinkIntake;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_drink_water,container,false);
        setupView(layout);
        registerEvent();
        return layout;
    }

    private void registerEvent() {
        tvAddDrinkIntake.setOnClickListener(this);
    }

    private void setupView(View layout) {
        imgDrinkState=(ImageView)layout.findViewById(R.id.img_user_state);
        circleProgressDrink=(CircleProgress)layout.findViewById(R.id.circle_progress);
        tvDrinkState=(TextView)layout.findViewById(R.id.tv_drink_target);
        recyclerViewDrinkIntake=(RecyclerView)layout.findViewById(R.id.recyclerview_drawer);
        tvAddDrinkIntake=(TextView)layout.findViewById(R.id.tv_add_drink_intake);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_add_drink_intake:
                Toast.makeText(getActivity(),"Add drink intake",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
