package com.example.asus.refreshbody.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.model.DrinkIntakeItem;

import java.util.ArrayList;

/**
 * Created by Asus on 10/12/2016.
 */

public class DrinkIntakeAdapter extends RecyclerView.Adapter<DrinkIntakeAdapter.MyViewHolder> {
    ArrayList<DrinkIntakeItem> drinkIntakeItemArrayList = new ArrayList<DrinkIntakeItem>();
    private LayoutInflater inflater;
    private Context context;

    public DrinkIntakeAdapter(ArrayList<DrinkIntakeItem> data,Context context) {
        this.drinkIntakeItemArrayList = data;
        this.context = context;
        inflater=LayoutInflater.from(this.context);
    }

    @Override
    public DrinkIntakeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.drink_intake_item,parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DrinkIntakeItem drinkIntakeItem=drinkIntakeItemArrayList.get(position);
        holder.imgDrink.setImageResource(drinkIntakeItem.getSymbol());
        holder.tvNameDrink.setText(drinkIntakeItem.getNameDrink());
        holder.tvDrinkAmount.setText(drinkIntakeItem.getAmountDrink()+" ml");
        holder.tvTimeDrinkWater.setText(drinkIntakeItem.getHourDrink()+":"+drinkIntakeItem.getMinuteDrink());


    }


    @Override
    public int getItemCount() {
        return drinkIntakeItemArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameDrink;
        TextView tvDrinkAmount;
        ImageView imgDrink;
        TextView tvTimeDrinkWater;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvNameDrink=(TextView)itemView.findViewById(R.id.tv_name_drink_item);
            imgDrink=(ImageView)itemView.findViewById(R.id.img_drink_intake_item);
            tvTimeDrinkWater=(TextView) itemView.findViewById(R.id.tv_time_drink_water);
            tvDrinkAmount=(TextView)itemView.findViewById(R.id.tv_drink_amount_item);
        }
    }
}
