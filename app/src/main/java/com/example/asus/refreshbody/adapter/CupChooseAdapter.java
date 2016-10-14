package com.example.asus.refreshbody.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.database.model.CupChooseItem;
import com.example.asus.refreshbody.intef.CupChooseListener;
import com.example.asus.refreshbody.database.model.DrinkIntakeItem;

import java.util.ArrayList;

/**
 * Created by Asus on 10/12/2016.
 */

public class CupChooseAdapter extends RecyclerView.Adapter<CupChooseAdapter.MyViewHolder> {
    ArrayList<CupChooseItem> cupChooseItemArrayList = new ArrayList<CupChooseItem>();
    private LayoutInflater inflater;
    private Context context;

    private CupChooseListener listener;

    public CupChooseAdapter(ArrayList<CupChooseItem> cupChooseItemArrayList, Context context) {
        this.cupChooseItemArrayList = cupChooseItemArrayList;
        this.context = context;
        this.inflater=LayoutInflater.from(this.context);
    }

    public void setListener(CupChooseListener listener){
        this.listener=listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cup_choose_item,parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CupChooseItem cupChooseItem=cupChooseItemArrayList.get(position);
        holder.imgDrink.setImageResource(cupChooseItem.getSymbol());
        holder.tvNameDrink.setText(cupChooseItem.getNameCup());
        holder.tvDrinkAmount.setText(cupChooseItem.getAmountCup()+" ml");
    }

    @Override
    public int getItemCount() {
        return cupChooseItemArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameDrink;
        TextView tvDrinkAmount;
        ImageView imgDrink;
        ImageView imgEditDrink;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvNameDrink=(TextView)itemView.findViewById(R.id.tv_name_cup_choose_item);
            tvDrinkAmount=(TextView)itemView.findViewById(R.id.tv_amount_cup_choose_item);
            imgDrink=(ImageView)itemView.findViewById(R.id.img_cup_choose_item);
            imgEditDrink=(ImageView)itemView.findViewById(R.id.img_edit_cup_choose_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clickOnItem(getAdapterPosition());
                }
            });
            imgEditDrink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clickEdit(getAdapterPosition());
                }
            });
        }
    }
}
