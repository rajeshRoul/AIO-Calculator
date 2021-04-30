package com.example.aiocalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rVCalcList;
    ArrayList<Calculator> calcArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Initialize views
        initViews();
        initData();

        CalculatorAdapter calcAdapter;
        calcAdapter = new CalculatorAdapter(this);
        rVCalcList.setAdapter(calcAdapter);
        rVCalcList.setLayoutManager(new GridLayoutManager(this, 2));
        calcAdapter.setCalcArrayList(calcArrayList);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

//    This method will initialize all views of main activity
    private void initViews(){
        rVCalcList = (RecyclerView) findViewById(R.id.rVCalcList);
    }

//    Initialize Data
    private void initData(){
        calcArrayList = new ArrayList<>();
        calcArrayList.add(new Calculator(R.mipmap.simple, "Simple Calculator"));
        calcArrayList.add(new Calculator(R.mipmap.age, "Age Calculator"));
        calcArrayList.add(new Calculator(R.mipmap.number_system, "Numeral Calculator"));
        calcArrayList.add(new Calculator(R.mipmap.simple, "Simple Calculator"));
        calcArrayList.add(new Calculator(R.mipmap.simple, "Simple Calculator"));
        calcArrayList.add(new Calculator(R.mipmap.simple, "Simple Calculator"));
        calcArrayList.add(new Calculator(R.mipmap.simple, "Simple Calculator"));
        calcArrayList.add(new Calculator(R.mipmap.simple, "Simple Calculator"));
    }
}