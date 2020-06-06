package com.example.carpool.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool.Interface.ItemClickListener;
import com.example.carpool.R;

public class SelectedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView Rider_Name,Rider_Number,Rider_Destination,Rider_Pickup;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public SelectedViewHolder(@NonNull View itemView) {
        super(itemView);

        Rider_Name = (TextView) itemView.findViewById(R.id.Name);


        Rider_Number= (TextView)itemView.findViewById(R.id.Number);
        Rider_Pickup= (TextView)itemView.findViewById(R.id.Pickup);
        Rider_Destination =(TextView)itemView.findViewById(R.id.Destination);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      //  itemClickListener.onclick(v,getAdapterPosition(),false);
      // itemClickListener.onclick(v,);



    }
}
