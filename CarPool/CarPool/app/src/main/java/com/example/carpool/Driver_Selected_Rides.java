package com.example.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.carpool.ViewHolder.SelectedViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Driver_Selected_Rides extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager  layoutManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__selected__rides);
        getSupportActionBar().hide();

        Button button = (Button) findViewById(R.id.end);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("MYRides");
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference myRef1 = myRef.child(uid);


        recyclerView= (RecyclerView) findViewById(R.id.selected);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final FirebaseRecyclerAdapter<SelectedRides, SelectedViewHolder> adapter1 = new FirebaseRecyclerAdapter<SelectedRides, SelectedViewHolder>(SelectedRides.class,R.layout.user_data,SelectedViewHolder.class,myRef1) {
            @Override
            protected void populateViewHolder(SelectedViewHolder selectedViewHolder, SelectedRides selectedRides, int i) {

                final SelectedRides clickItem = selectedRides;
                selectedViewHolder.Rider_Name.setText(selectedRides.getRider_Name());
                selectedViewHolder.Rider_Number.setText(selectedRides.getRider_Number());
                selectedViewHolder.Rider_Pickup.setText("Pickup Point :" + selectedRides.getRider_Pickup());
                selectedViewHolder.Rider_Destination.setText("Destination :" + selectedRides.getRider_Destination());

            }


        };

        recyclerView.setAdapter(adapter1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final DatabaseReference re= FirebaseDatabase.getInstance().getReference("Driver_Vehicle_Details");
                myRef.child(uid).removeValue();

                re.child(uid).removeValue();

                Intent i = new Intent(Driver_Selected_Rides.this,Driver_Rides_Summary.class);
                startActivity(i);
            }
        });

        Button b = (Button) findViewById(R.id.Pickup);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Driver_Selected_Rides.this,Pickup.class);

                startActivity(i);
            }
        });

    }


}


