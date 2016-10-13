package com.example.asus.refreshbody.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.model.DrinkIntakeItem;

import java.util.ArrayList;

/**
 * Created by Asus on 10/13/2016.
 */

public class CupImageAdapter extends RecyclerView.Adapter<CupImageAdapter.MyViewHolder> {
    ArrayList<DrinkIntakeItem> cupChooseItemArrayList = new ArrayList<DrinkIntakeItem>();
    private LayoutInflater inflater;
    private Context context;

    public CupImageAdapter(ArrayList<DrinkIntakeItem> cupChooseItemArrayList, Context context) {
        this.cupChooseItemArrayList = cupChooseItemArrayList;
        this.context = context;
        this.inflater=LayoutInflater.from(this.context);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cup_image_item,parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DrinkIntakeItem cupChooseItem=cupChooseItemArrayList.get(position);
        holder.imgCup.setImageResource(cupChooseItem.getSymbol());

    }

    @Override
    public int getItemCount() {
        return cupChooseItemArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCup;
        public MyViewHolder(View itemView) {
            super(itemView);
            imgCup=(ImageView)itemView.findViewById(R.id.cup_image_item);
        }
    }
}
