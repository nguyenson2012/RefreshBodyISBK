package com.example.asus.refreshbody.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.asus.refreshbody.R;
import com.example.asus.refreshbody.intef.ClickListener;
import com.example.asus.refreshbody.model.CupImage;

import java.util.ArrayList;

/**
 * Created by Asus on 10/13/2016.
 */

public class CupImageAdapter extends RecyclerView.Adapter<CupImageAdapter.MyViewHolder> {
    private ArrayList<CupImage> CupImageArrayList = new ArrayList<CupImage>();

    private LayoutInflater inflater;
    private Context context;

    private ClickListener listener;

    public CupImageAdapter(ArrayList<CupImage> CupImageArrayList, Context context) {
        this.CupImageArrayList = CupImageArrayList;
        this.context = context;
        this.inflater=LayoutInflater.from(this.context);
    }

    public void setListener(ClickListener listener){
        this.listener=listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cup_image_item,parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CupImage CupImage=CupImageArrayList.get(position);
        holder.imgCup.setImageResource(CupImage.getImgRes());
        if(CupImage.isChoose())
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.gray_light));
        else
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));

    }

    @Override
    public int getItemCount() {
        return CupImageArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCup;
        public MyViewHolder(final View itemView) {
            super(itemView);
            imgCup=(ImageView)itemView.findViewById(R.id.cup_image_item);
            imgCup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v,getAdapterPosition());
                }
            });
        }
    }
}
