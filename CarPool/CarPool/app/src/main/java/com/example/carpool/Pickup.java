package com.example.carpool;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class Pickup extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap gMap;
    Location mlocation;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager  layoutManager;
   public TextView ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup);
        getSupportActionBar().hide();



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
       // mapView = mapFragment.getView()

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

      //  String id = FirebaseAuth.getInstance().getCurrentUser().getUid();


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("MYRides");
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference myRef1 = myRef.child(uid);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {



                        String n = snapshot1.child("Rider_Pickup").getValue(String.class);

                         //n = "Select Pickup point";





                        if (n == null) {
                            Toast.makeText(Pickup.this, "No Pickup found", Toast.LENGTH_LONG).show();

                        }
                         else {
                            if (n.equals("G9 Markaz")) {
                                LatLng g9 = new LatLng(33.690036, 73.030187);
                                gMap.addMarker(new MarkerOptions().position(g9).title("G9 Markaz"));
                                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(g9, 10));


                            } else
                            {
                        if (n.equals("Air University")) {
                            LatLng Air = new LatLng(33.713818, 73.026399);
                            gMap.addMarker(new MarkerOptions().position(Air).title("Sami"));
                            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Air, 10));


                        } else {if(n.equals("F8 Markaz")){
                            LatLng f8 = new LatLng(33.712382, 73.036899);
                            //    sydney.showInfoWindow();
                            gMap.addMarker(new MarkerOptions().position(f8).title("F8 Markaz"));
                            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(f8, 10));
                        }else {
                            if(n.equals("Sadar")){
                                LatLng sadar = new LatLng(33.5914237, 73.0535122);
                                gMap.addMarker(new MarkerOptions().position(sadar).title("Sadar"));
                                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sadar, 10));
                            }
                        }


                        }
                    }

                }

                    }}}
//                   // for (snapshot.)
//                   // List <String> post = snapshot.getValue();
//
//
//
//                }
//
//
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        recyclerView= (RecyclerView) findViewById(R.id.sss);
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//
//        final FirebaseRecyclerAdapter<SelectedRides, SelectedViewHolder> adapter1 = new FirebaseRecyclerAdapter<SelectedRides, SelectedViewHolder>(SelectedRides.class,R.layout.user_data,SelectedViewHolder.class,myRef1) {
//            @Override
//            protected void populateViewHolder(SelectedViewHolder selectedViewHolder, SelectedRides selectedRides, int i) {
//
//
//
//
//                selectedViewHolder.Rider_Name.setText(selectedRides.getRider_Name());
//                selectedViewHolder.Rider_Number.setText(selectedRides.getRider_Number());
//                selectedViewHolder.Rider_Pickup.setText("Pickup Point :" + selectedRides.getRider_Pickup());
//                selectedViewHolder.Rider_Destination.setText("Destination :" + selectedRides.getRider_Destination());
//
//                final DatabaseReference selected = FirebaseDatabase.getInstance().getReference("Rides");
//                selected.child(uid).child("Rider_Name").setValue(selectedRides.getRider_Pickup());
//               // final TextView s = (TextView)findViewById(R.id.textView15);
//                String ss= selectedRides.getRider_Pickup();
//
//              //  s.setText(ss);
////                arrayOfSongs= new ArrayList<>();
////                arrayOfSongs.add(ss);
////                final DatabaseReference myRef = database.getReference("ss");
////                myRef.child(uid).push().setValue(arrayOfSongs);
////
//
//
//            }
//        };
//        Intent intent= getIntent();
//
//
//        recyclerView.setAdapter(adapter1);
//        if (ss==null){
//                            Toast.makeText(Pickup.this,"No Pickup found",Toast.LENGTH_LONG).show();
//
//                        }else
//
//
//                        if(ss.equals("G9 Markaz")) {
//                            LatLng g9 = new LatLng(33.690036, 73.030187);
//                            gMap.addMarker(new MarkerOptions().position(g9).title("G9 Markaz"));
//                            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(g9, 15));
//
//                            LatLng Air = new LatLng(33.713818, 73.026399);
//                            gMap.addMarker(new MarkerOptions().position(Air).title("Air University"));
//                            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Air, 15));
//                        }else
//                        if(ss.equals("G9 Markaz") || ss.equals("Select Pickup Point")  ) {
//                            LatLng g9 = new LatLng(33.690036, 73.030187);
//                            gMap.addMarker(new MarkerOptions().position(g9).title("G9 Markaz"));
//                            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(g9, 15));
//
//
//                        }
//                        else
//                        if(ss.equals("Air University") ) {
//                            LatLng Air = new LatLng(33.713818, 73.026399);
//                            gMap.addMarker(new MarkerOptions().position(Air).title("Air University"));
//                            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Air, 15));
//
//
//                        }
//                        else {
//
//
//                        }
//                    }

        //String  n= null ;



//        if(ss.equals(null)){
//              Toast.makeText(Pickup.this,"Saaaaaaa",Toast.LENGTH_LONG).show();
//        }
//       else







//        gMap.clear();
////        if(text.equals("F8 Markaz")) {
//            LatLng sydney = new LatLng(33.712382, 73.036899);
//        //    sydney.showInfoWindow();
//            gMap.addMarker(new MarkerOptions().position(sydney).title("F8 Markaz"));
//            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10));
//
//            LatLng sydney2 = new LatLng(33.5914237, 73.0535122);
//            gMap.addMarker(new MarkerOptions().position(sydney2).title("Sadar"));
//            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney2, 10));
//

//        LatLng sydney3 = new LatLng(33.712382, 73.036899);
//        gMap.addMarker(new MarkerOptions().position(sydney3).title("G8 Markaz"));
//        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney3, 10));


        Button b = (Button)findViewById(R.id.back);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Pickup.this,Driver_Selected_Rides.class);
                startActivity(intent);

            }
        });
}}
