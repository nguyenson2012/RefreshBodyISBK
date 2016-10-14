package com.example.asus.refreshbody.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.database.model.DrinkIntakeItem;

import java.util.ArrayList;

/**
 * Created by nguyenvanthinh on 14/10/2016.
 */

public class ListViewAdapter extends ArrayAdapter<DrinkIntakeItem> {

    Activity context = null;
    ArrayList<DrinkIntakeItem>  myItemArrayList = null;
    int layoutId;

    /*this contructor to initialize values that import context activity
    * @parameter context: activity contain this class
    * @parameter layoutId is id layout that I customize listview
    * @parameter arr: list of item*/
    public ListViewAdapter(Activity context, int layoutId, ArrayList<DrinkIntakeItem> myItemArrayList){
        super(context,layoutId,myItemArrayList);
        this.context = context;
        this.layoutId = layoutId;
        this.myItemArrayList = myItemArrayList;
    }

    /*
    * this function use to customize layout
     * @parameter position is position of component in list
     * @parameter parent is list that is imported from container
     * @return view return convert view*/

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId,null);

        final TextView tvItem = (TextView) convertView.findViewById(R.id.tvDrinkItem);
        final TextView tvTime = (TextView) convertView.findViewById(R.id.tvTimeItem);
        DrinkIntakeItem item = myItemArrayList.get(position);
        tvItem.setText(item.getNameDrink() + "  " + item.getAmountDrink());
        tvItem.setText(item.getTimeDrink().getDayDrink() + "");

        return convertView;
    }
}
