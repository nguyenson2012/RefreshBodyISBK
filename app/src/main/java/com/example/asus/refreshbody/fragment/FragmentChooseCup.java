package com.example.asus.refreshbody.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.adapter.CupChooseAdapter;
import com.example.asus.refreshbody.adapter.CupImageAdapter;
import com.example.asus.refreshbody.intef.CupChooseListener;
import com.example.asus.refreshbody.model.DrinkIntakeItem;

import java.util.ArrayList;

/**
 * Created by Asus on 10/12/2016.
 */

public class FragmentChooseCup  extends Fragment {
    private RecyclerView recyclerViewCupChoose;
    private TextView tvCustomCup;

    private ArrayList<DrinkIntakeItem> arrayListDrinkIntakeItems;

    private CupChooseAdapter cupChooseAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_choose_cup,container,false);
        setupView(layout);
        setupDefaultData();

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setAdapterForRecyclerview();
    }

    private void setAdapterForRecyclerview() {
        cupChooseAdapter=new CupChooseAdapter(arrayListDrinkIntakeItems,getActivity());
        cupChooseAdapter.setListener(new CupChooseListener() {
            @Override
            public void clickOnItem(int position) {
                Toast.makeText(getActivity(),"Click on cup choose "+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void clickEdit(int position) {
                Toast.makeText(getActivity(),"Click on cup edit "+position,Toast.LENGTH_SHORT).show();
                openDialogEditCup(arrayListDrinkIntakeItems.get(position));
            }
        });
        recyclerViewCupChoose.setAdapter(cupChooseAdapter);
        recyclerViewCupChoose.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void openDialogEditCup(DrinkIntakeItem drinkIntakeItem) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout=inflater.inflate(R.layout.dialog_custom_choose_cup,null);
        dialogBuilder.setView(layout);
        final AlertDialog alertDialog = dialogBuilder.create();

        EditText editTextNameDrink= (EditText) layout.findViewById(R.id.edittext_choose_cup_name);
        EditText editTextDrinkAmount=(EditText)layout.findViewById(R.id.edittext_choose_cup_amount);
        RecyclerView recyclerViewCupImage= (RecyclerView) layout.findViewById(R.id.recyclerview_cup_image);
        setDefaultAdapterForRecyclerviewCup(recyclerViewCupImage);

        alertDialog.show();

    }

    private void setDefaultAdapterForRecyclerviewCup(RecyclerView recyclerViewCupImage) {
        ArrayList<DrinkIntakeItem> arrayDefault=new ArrayList<DrinkIntakeItem>();
        DrinkIntakeItem water100=new DrinkIntakeItem(R.drawable.cup_one,"Water",100,0,0);
        arrayDefault.add(water100);
        DrinkIntakeItem water200=new DrinkIntakeItem(R.drawable.cup_one,"Water",200,0,0);
        arrayDefault.add(water200);
        DrinkIntakeItem water300=new DrinkIntakeItem(R.drawable.cup_one,"Water",300,0,0);
        arrayDefault.add(water300);
        CupImageAdapter cupImageAdapter=new CupImageAdapter(arrayDefault,getActivity());
        recyclerViewCupImage.setAdapter(cupImageAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCupImage.setLayoutManager(layoutManager);

    }

    private void setupDefaultData() {
        arrayListDrinkIntakeItems=new ArrayList<DrinkIntakeItem>();
        DrinkIntakeItem water100=new DrinkIntakeItem(R.drawable.cup_one,"Water",100,0,0);
        arrayListDrinkIntakeItems.add(water100);
        DrinkIntakeItem water200=new DrinkIntakeItem(R.drawable.cup_one,"Water",200,0,0);
        arrayListDrinkIntakeItems.add(water200);
        DrinkIntakeItem water300=new DrinkIntakeItem(R.drawable.cup_one,"Water",300,0,0);
        arrayListDrinkIntakeItems.add(water300);

    }

    private void setupView(View layout) {
        recyclerViewCupChoose=(RecyclerView)layout.findViewById(R.id.recyclerview_cup);
        tvCustomCup=(TextView)layout.findViewById(R.id.tv_custom_cup_choose);
    }
}
