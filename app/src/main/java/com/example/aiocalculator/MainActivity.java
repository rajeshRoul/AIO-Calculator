package com.example.aiocalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Initialize views
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

//    This method will initialize all views of main activity
    private void initViews(){

    }
}