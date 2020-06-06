package com.example.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getSupportActionBar().hide();




        // Switch Button Rider or Driver
        Button buttonS = (Button) findViewById(R.id.Switch);

        buttonS.setOnClickListener(new View.OnClickListener(){
            Switch userTypeSwitch = (Switch) findViewById(R.id.UserTypeSwitch);
            public void onClick(View v){
                if (userTypeSwitch.isChecked()) {

                    Intent intentR = new Intent(SignIn.this, Rider_Select_Ride_Type.class);
                    startActivity(intentR);
                    Toast.makeText(SignIn.this,"Rider", Toast.LENGTH_LONG).show();

                }
                else
                {
                    Intent intentD = new Intent(SignIn.this, Driver.class);
                    startActivity(intentD);
                    Toast.makeText(SignIn.this,"Driver", Toast.LENGTH_LONG).show();

                }
            }

        });





    }
}
