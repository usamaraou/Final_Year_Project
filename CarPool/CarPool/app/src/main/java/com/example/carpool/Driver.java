package com.example.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Driver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        getSupportActionBar().hide();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
       final DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Driver_Vehicle_Details").child(uid);
        Button buttonM = (Button) findViewById(R.id.Next);

       final Switch s1 =(Switch)findViewById(R.id.switch1);




        buttonM.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                EditText t = (EditText) findViewById(R.id.number2);
                 String number = t.getText().toString();
                 if(number.isEmpty()){
                     Toast.makeText(Driver.this,"Enter Vehicle Number",Toast.LENGTH_LONG).show();
                 }else {
                ref.child("Vehicle_Number").setValue(number);
                Intent intentM = new Intent(Driver.this, DriverMapsActivity.class);
                intentM.putExtra("switch", s1.isChecked());
                startActivity(intentM);}
            }

        });
    }
}
