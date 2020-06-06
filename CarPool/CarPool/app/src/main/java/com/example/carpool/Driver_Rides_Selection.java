package com.example.carpool;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool.Interface.ItemClickListener;
import com.example.carpool.ViewHolder.UserViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Driver_Rides_Selection extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager  layoutManager;
     DatabaseReference myRef;
     Button button;



//    ListView lt;
//    ArrayList<String> arrayList = new ArrayList<>();
//    ArrayList<String> w = new ArrayList<>();
//    ArrayAdapter<String> arrayAdapter;
   // List<Map<String, String>> data = new ArrayList<Map<String, String>>();

   // FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__rides__selection);
        getSupportActionBar().hide();


        //     Button button = (Button) findViewById(R.id.SelectRider);
//        button.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                Intent intent = new Intent(Driver_Rides_Selection.this, Rider_Summary.class);
//                startActivity(intent);
//                // Toast.makeText(MainActivity.this,"Welcome", Toast.LENGTH_LONG).show();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("RidesAvailable");

        recyclerView = (RecyclerView) findViewById(R.id.lt);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loaduser();


    }

    private void loaduser() {

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                  final    String value = String.valueOf(dataSnapshot1.child("Name").getValue());
                     dataSnapshot.getValue().toString();



                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        final FirebaseRecyclerAdapter<User,UserViewHolder> adapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(User.class,R.layout.user_data,UserViewHolder.class, myRef) {
            @Override
            protected void populateViewHolder(UserViewHolder userViewHolder, final User user, int i) {
                userViewHolder.Name.setText(user.getName());
                userViewHolder.Number.setText(user.getNumber());
                userViewHolder.Pickup.setText("Pickup Point :" + user.getPickUp_Point());
                userViewHolder.Destination.setText("Destination :" + user.getDestination());


                final User clickItem = user;
                userViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onclick(View view, int Position, boolean isLongClick) {
                        final AlertDialog.Builder alert = new AlertDialog.Builder(Driver_Rides_Selection.this);
                alert.setCancelable(false);
                alert.setTitle(Html.fromHtml("<font color='#FF7F27'>Do u want to select this rider ?</font>"));
                alert.setIcon(R.drawable.ic_person_black_24dp);


                alert.setPositiveButton(Html.fromHtml("<font color='#000000'>Yess</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final DatabaseReference selected = FirebaseDatabase.getInstance().getReference("Connected_Rides");
                        selected.child(user.getKey()).child("Rider_Name").setValue(user.getName());
                        selected.child(user.getKey()).child("Rider_Number").setValue(user.getNumber());
                        selected.child(user.getKey()).child("Rider_Destination").setValue(user.getDestination());
                        selected.child(user.getKey()).child("Rider_Pickup").setValue(user.getPickUp_Point());
                        selected.child(user.getKey()).child("Key").setValue(user.getKey());
                        myRef.child(user.getKey()).removeValue();
                        final DatabaseReference selected1 = FirebaseDatabase.getInstance().getReference("MYRides");
                       final String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        DatabaseReference s= selected1.child(Uid);
                    //    DatabaseReference s2 =s.child(Uid);

                        s.child(user.getKey()).child("Key").setValue(user.getKey());
                        s.child(user.getKey()).child("Rider_Name").setValue(user.getName());
                        s.child(user.getKey()).child("Rider_Number").setValue(user.getNumber());
                        s.child(user.getKey()).child("Rider_Destination").setValue(user.getDestination());
                        s.child(user.getKey()).child("Rider_Pickup").setValue(user.getPickUp_Point());

                      //  myRef.child(user.getKey()).removeValue();

                       // final String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        DatabaseReference User = FirebaseDatabase.getInstance().getReference("User");
                        final DatabaseReference zon = User.child(Uid);


                        zon.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                              String Name = dataSnapshot.child("name").getValue().toString();
                                String Number = dataSnapshot.child("number").getValue().toString();
                                selected.child(user.getKey()).child("Driver_Name").setValue(Name);
                                selected.child(user.getKey()).child("Driver_Number").setValue(Number);
                            //    final String sami = dataSnapshot.child("Key").getValue().toString();

                              //  myRef.child(sami).removeValue();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        DatabaseReference User1 = FirebaseDatabase.getInstance().getReference("Driver_Vehicle_Details");
                        final DatabaseReference zon1 = User1.child(Uid);
                        zon1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String N = dataSnapshot.child("Vehicle_Number").getValue().toString();
                                selected.child(user.getKey()).child("Vehicle_Number").setValue(N);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        Toast.makeText(Driver_Rides_Selection.this, clickItem.getName()  ,Toast.LENGTH_LONG).show();
                     //   finish();
                    }
                });
                alert.setNegativeButton(Html.fromHtml("<font color='#000000'>No</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                alert.show();




                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);

        button=(Button)findViewById(R.id.SelectRider);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(Driver_Rides_Selection.this,Driver_Selected_Rides.class);
                startActivity(intent);
            }
        });
}



}

