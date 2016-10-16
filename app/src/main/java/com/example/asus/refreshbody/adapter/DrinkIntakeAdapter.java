package com.example.asus.refreshbody.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.database.model.DrinkIntakeItem;
import com.example.asus.refreshbody.intef.ClickListener;

import java.util.ArrayList;

/**
 * Created by Asus on 10/12/2016.
 */

public class DrinkIntakeAdapter extends RecyclerView.Adapter<DrinkIntakeAdapter.MyViewHolder> {
    ArrayList<DrinkIntakeItem> drinkIntakeItemArrayList = new ArrayList<DrinkIntakeItem>();
    private LayoutInflater inflater;
    private Context context;

    private ClickListener listener;

    private boolean isLogDrink;

    public DrinkIntakeAdapter(ArrayList<DrinkIntakeItem> data,Context context,boolean isLogDrink) {
        this.drinkIntakeItemArrayList = data;
        this.context = context;
        inflater=LayoutInflater.from(this.context);
        this.isLogDrink=isLogDrink;
    }

    @Override
    public DrinkIntakeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.drink_intake_item,parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DrinkIntakeItem drinkIntakeItem=drinkIntakeItemArrayList.get(position);
        if(isLogDrink)
            holder.imgDeleteDrinkIntake.setVisibility(View.INVISIBLE);
        if(drinkIntakeItem.getAmountDrink()!=0) {
            switch (drinkIntakeItem.getSymbolPosition()){
                case 1:
                    holder.imgDrink.setImageResource(R.drawable.cup1);
                    break;
                case 2:
                    holder.imgDrink.setImageResource(R.drawable.cup2);
                    break;
                case 3:
                    holder.imgDrink.setImageResource(R.drawable.cup3);
                    break;
                case 4:
                    holder.imgDrink.setImageResource(R.drawable.cup4);
                    break;
                case 5:
                    holder.imgDrink.setImageResource(R.drawable.cup5);
                    break;
            }
            holder.tvNameDrink.setText(drinkIntakeItem.getNameDrink());
            holder.tvDrinkAmount.setText(drinkIntakeItem.getAmountDrink() + " ml");
            holder.tvTimeDrinkWater.setText(convertToTwoNumber(drinkIntakeItem.getTimeDrink().getHourDrink()) + ":" +
                    convertToTwoNumber(drinkIntakeItem.getTimeDrink().getMinuteDrink()));
            holder.tvTotalDrink.setVisibility(View.GONE);
            holder.tvDay.setVisibility(View.GONE);
        }else {
            holder.imgDrink.setVisibility(View.GONE);
            holder.tvNameDrink.setVisibility(View.GONE);
            holder.tvDrinkAmount.setVisibility(View.GONE);
            holder.tvTimeDrinkWater.setVisibility(View.GONE);
            holder.imgDeleteDrinkIntake.setVisibility(View.GONE);
            holder.tvDay.setVisibility(View.VISIBLE);
            holder.tvDay.setText(drinkIntakeItem.getTimeDrink().getDayDrink()+"-"+drinkIntakeItem.getTimeDrink().getMonthDrink()+"-"+
            drinkIntakeItem.getTimeDrink().getYearDrink());
        }


    }
    private String convertToTwoNumber(int hour){
        if(hour>=10)
            return hour+"";
        else
            return "0"+hour;
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
        TextView tvDay;
        TextView tvTotalDrink;
        ImageView imgDeleteDrinkIntake;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvNameDrink=(TextView)itemView.findViewById(R.id.tv_name_drink_item);
            imgDrink=(ImageView)itemView.findViewById(R.id.img_drink_intake_item);
            tvTimeDrinkWater=(TextView) itemView.findViewById(R.id.tv_time_drink_water);
            tvDrinkAmount=(TextView)itemView.findViewById(R.id.tv_drink_amount_item);
            tvDay=(TextView)itemView.findViewById(R.id.tv_day_drink_intake_item);
            tvTotalDrink=(TextView)itemView.findViewById(R.id.tv_total_drink);
            imgDeleteDrinkIntake=(ImageView)itemView.findViewById(R.id.img_delete);
            imgDeleteDrinkIntake.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v,getAdapterPosition());
                }
            });
        }
    }
}
