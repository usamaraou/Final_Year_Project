package com.example.carpool;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Driver_Rides_Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__rides__summary);
        getSupportActionBar().hide();
    }
}
