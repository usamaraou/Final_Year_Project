package com.example.carpool.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool.Interface.ItemClickListener;
import com.example.carpool.R;

public class DriverViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView Driver_Name,Driver_Number;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public DriverViewHolder(@NonNull View itemView) {
        super(itemView);


        Driver_Name = (TextView) itemView.findViewById(R.id.Name);


        Driver_Number= (TextView)itemView.findViewById(R.id.Number);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onclick(v,getAdapterPosition(),false);

    }
}