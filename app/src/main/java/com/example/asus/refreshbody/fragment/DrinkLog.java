package com.example.asus.refreshbody.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.adapter.ListViewAdapter;
import com.example.asus.refreshbody.database.model.DrinkIntakeItem;

import java.util.ArrayList;

/**
 * created by Thinh
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class DrinkLog extends Fragment {

    private ArrayList<DrinkIntakeItem> arrDrinkitem;
    private ListViewAdapter mylvAdapter = null;
    private ListView lvItemDrink = null;

    public DrinkLog() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentview = inflater.inflate(R.layout.fragment_drink_log, container, false);

        lvItemDrink = (ListView) fragmentview.findViewById(R.id.lvItemDrink);
        arrDrinkitem = new ArrayList<DrinkIntakeItem>();
        arrDrinkitem.add(new DrinkIntakeItem(1,"water",700));
        arrDrinkitem.add(new DrinkIntakeItem(2,"water",600));
        mylvAdapter = new ListViewAdapter(this.getActivity(),R.layout.item_cup_l,arrDrinkitem);

        lvItemDrink.setAdapter(mylvAdapter);

        // Inflate the layout for this fragment
        return fragmentview;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
