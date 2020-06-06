package com.example.carpool;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Rider_Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider__summary);
        getSupportActionBar().hide();
    }
}
