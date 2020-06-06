package com.example.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class rider_waiting extends AppCompatActivity {


  //  private static int TIME_OUT = 10000;

//    RecyclerView recyclerView;
//    RecyclerView.LayoutManager  layoutManager;
    ImageView im;
    TextView la,dname,dnumber,vnumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_waiting);
        getSupportActionBar().hide();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Connected_Rides");

        final String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
      //   DatabaseReference ref = myRef.child(Uid);

//        recyclerView= (RecyclerView) findViewById(R.id.selected);
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
        im = (ImageView) findViewById(R.id.wait);
        la = (TextView) findViewById(R.id.label);
        dname = (TextView) findViewById(R.id.dname);

        dnumber = (TextView) findViewById(R.id.number2);
//        dnumber.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse(dnumber.getText().toString()));
//                startActivity(intent);
//
//            }
//        });
        vnumber= (TextView)findViewById(R.id.Vehicle_number);

   myRef.child(Uid).addValueEventListener(new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           String name = dataSnapshot.child("Driver_Name").getValue(String.class);
           final String number = dataSnapshot.child("Driver_Number").getValue(String.class);
           String v_n =dataSnapshot.child("Vehicle_Number").getValue(String.class);

//           if(name== null) {
//
//           }else {

               dname.setText( name);
              dname.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_white_24dp, 0, 0, 0);
               dnumber.setText(number);
         dnumber.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_phone_white_24dp, 0, 0, 0);
               vnumber.setText( v_n);
          // vnumber.setCompoundDrawablesWithIntrinsicBounds(R.drawable.nu, 0, 0, 0);
               la.setText("Driver Information");
               im.setVisibility(View.INVISIBLE);
           }






       @Override
       public void onCancelled(@NonNull DatabaseError databaseError) {

       }
   });
        Button b = (Button)findViewById(R.id.end);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference r = FirebaseDatabase.getInstance().getReference("Destination");
                r.child(Uid).removeValue();
                myRef.child(Uid).removeValue();
//                la.setText("Waiting for Driver To Response");
//                im.setVisibility(View.VISIBLE);
                Intent intent = new Intent(rider_waiting.this,Rider_Summary.class);
                startActivity(intent);
            }
        });
    }}











//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent i = new Intent(rider_waiting.this, Rider_Summary.class);
//                startActivity(i);
//                finish();
//            }
//        }, TIME_OUT);
//    }
