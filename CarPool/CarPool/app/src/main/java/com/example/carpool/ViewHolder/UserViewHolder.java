package com.example.carpool.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool.Interface.ItemClickListener;
import com.example.carpool.R;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
     public TextView Name,Number,Destination,Pickup;


     private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        Name = (TextView) itemView.findViewById(R.id.Name);


        Number= (TextView)itemView.findViewById(R.id.Number);
        Pickup= (TextView)itemView.findViewById(R.id.Pickup);
        Destination =(TextView)itemView.findViewById(R.id.Destination);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onclick(v,getAdapterPosition(),false);

    }
}
