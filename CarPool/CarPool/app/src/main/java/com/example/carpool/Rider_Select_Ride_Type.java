package com.example.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Rider_Select_Ride_Type extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider__select__ride__type);
        getSupportActionBar().hide();

       final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Destination");
        final FirebaseUser UId = FirebaseAuth.getInstance().getCurrentUser();
      // final String user = UId.getUid();

        final Button button = (Button) findViewById(R.id.SelectType);

        Spinner spinner = (Spinner)findViewById(R.id.spinner1);
      final   Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);
        String text = spinner.getSelectedItem().toString();
        String text2 = spinner2.getSelectedItem().toString();
//        if(text.equals("Select Destination")){
//            button.setVisibility(View.VISIBLE);
//        }else {
//            button.
//            button.setVisibility(View.VISIBLE);
//        }


       // Toast.makeText(Rider_Select_Ride_Type.this,text,Toast.LENGTH_LONG).show();

       final Switch s = (Switch) findViewById(R.id.UserTypeSwitch);


//         String sa = "Select Destination";
//        if(text == sa){
//            button.setClickable(false);
//        }else {
//           button.setEnabled(true);
//
//        }

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){


                Spinner spinner = (Spinner)findViewById(R.id.spinner1);
                String text = spinner.getSelectedItem().toString();
                String text2 = spinner2.getSelectedItem().toString();

//                if(text.equals("Select Destination")){
//                 button.setVisibility(View.INVISIBLE);
//                   // Toast.makeText(Rider_Select_Ride_Type.this,"SAmi",Toast.LENGTH_LONG).show();
//                }else{
//                    button.setVisibility(View.VISIBLE);
              //  Toast.makeText(Rider_Select_Ride_Type.this,text,Toast.LENGTH_LONG).show();

                    ref.child(UId.getUid()).child("Destination").setValue(text);
                ref.child(UId.getUid()).child("PickUp_Point").setValue(text2);

                if(s.isChecked()){
                    ref.child(UId.getUid()).child("Vehicle_Type").setValue("Car");

                }else{
                    ref.child(UId.getUid()).child("Vehicle_Type").setValue("Bike");
                }


                //Toast.makeText(Rider_Select_Ride_Type.this,text,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Rider_Select_Ride_Type.this, Rider_Maps.class);
                startActivity(intent);

                // Toast.makeText(MainActivity.this,"Welcome", Toast.LENGTH_LONG).show();
            }//}

        });
    }
}
