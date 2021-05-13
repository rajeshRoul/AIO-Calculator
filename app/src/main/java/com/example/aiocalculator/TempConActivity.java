package com.example.aiocalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class TempConActivity extends AppCompatActivity {

    TextView tVCelsius, tVKelvin, tVFahrenheit;
//    View Selected Status
//    0 for Celsius, 1 for Fahrenheit, 2 for Kelvin
    int selected;

    ArrayList<TextView> arrayListInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_con);

        initViews();
        initData();
        initClickListeners();
    }

    private void initClickListeners() {

        tVCelsius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = 0;
                arrayListInput.get(0).setTextColor(getResources().getColor(R.color.red));
                arrayListInput.get(1).setTextColor(getResources().getColor(R.color.btnTextColor));
                arrayListInput.get(2).setTextColor(getResources().getColor(R.color.btnTextColor));
            }
        });

        tVFahrenheit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = 1;
                arrayListInput.get(1).setTextColor(getResources().getColor(R.color.red));
                arrayListInput.get(0).setTextColor(getResources().getColor(R.color.btnTextColor));
                arrayListInput.get(2).setTextColor(getResources().getColor(R.color.btnTextColor));
            }
        });

        tVKelvin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = 2;
                arrayListInput.get(2).setTextColor(getResources().getColor(R.color.red));
                arrayListInput.get(1).setTextColor(getResources().getColor(R.color.btnTextColor));
                arrayListInput.get(0).setTextColor(getResources().getColor(R.color.btnTextColor));
            }
        });

    }

    private void initData() {
        selected = 0;
        arrayListInput = new ArrayList<>();
        arrayListInput.add(tVCelsius);
        arrayListInput.add(tVFahrenheit);
        arrayListInput.add(tVKelvin);
        arrayListInput.get(selected).setText("0");
        convert();
    }

    public void convOnClick(View v){
        switch (v.getId()){
            case R.id.btnAC:{
                arrayListInput.get(selected).setText("0");
                convert();
                break;
            }case R.id.btnC:{
                String temp = arrayListInput.get(selected).getText().toString().trim();
                if(temp.length() <= 1){
                    arrayListInput.get(selected).setText("0");
                }else {
                    arrayListInput.get(selected).setText(temp.substring(0, temp.length()-1));
                }
                convert();
                break;
            }case R.id.btnCopy:{
                ClipboardManager cm = (ClipboardManager)TempConActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                String temp = tVCelsius.getText().toString() + " °C, " + tVFahrenheit.getText().toString() + " °F, " + tVKelvin.getText().toString() + " K ";
                ClipData clipData = ClipData.newPlainText("copied to clipboard",temp);
                cm.setPrimaryClip(clipData);
                Toast.makeText(TempConActivity.this, "Result Copied", Toast.LENGTH_SHORT).show();
                break;
            }case R.id.btnDot:{
                String temp = arrayListInput.get(selected).getText().toString().trim();
                boolean flag = false;
                for(int i=0; i<temp.length(); i++){
                    if(temp.charAt(i) == '.'){
                        flag = true;
                    }
                }
                if(!flag){
                    arrayListInput.get(selected).setText(temp + ".");
                }
                break;
            }case R.id.btnNeg:{
                String temp = arrayListInput.get(selected).getText().toString().trim();
                if(!temp.equals("0")){
                    if(temp.charAt(0) == '-'){
                        arrayListInput.get(selected).setText(temp.substring(1));
                    }else {
                        arrayListInput.get(selected).setText("-" + temp);
                    }
                    convert();
                }
                break;
            } default:{
                String temp = arrayListInput.get(selected).getText().toString().trim();
                if (!temp.equals("0")){
                    arrayListInput.get(selected).setText(temp + ((Button)v).getText().toString().trim());
                }else {
                    arrayListInput.get(selected).setText(((Button)v).getText().toString().trim());
                }
                convert();
            }
        }
    }

    private void convert(){
        switch(selected){
            case 0:{
                double celsius = parseInput();
                double fahrenheit =((celsius*9)/5)+32;
                double kelvin = celsius + 273.15;
                Toast.makeText(TempConActivity.this, "f k" + fahrenheit + " " + kelvin, Toast.LENGTH_LONG);
                tVFahrenheit.setText(fahrenheit + "");
                tVKelvin.setText(kelvin + "");
                break;
            } case 1:{
                double fahrenheit = parseInput();
                double celsius = (fahrenheit-32)*(0.5556);
                double kelvin = 273.5 + ((fahrenheit - 32.0) * (5.0/9.0));
                Toast.makeText(TempConActivity.this, "c k" + celsius + " " + kelvin, Toast.LENGTH_LONG);
                tVCelsius.setText(celsius + "");
                tVKelvin.setText(kelvin + "");
                break;
            } case 2:{
                double kelvin = parseInput();
                double celsius = kelvin - 273.15;
                double fahrenheit = (9/5.0*(kelvin - 273.15) + 32);
                Toast.makeText(TempConActivity.this, "f c" + fahrenheit + " " + celsius, Toast.LENGTH_LONG);

                tVCelsius.setText(celsius + "");
                tVFahrenheit.setText(fahrenheit + "");
                break;
            }
        }
    }

    private double parseInput() {
        String s = arrayListInput.get(selected).getText().toString().trim();
        double res = 1.0;
        if(s.charAt(0) == '-'){
            res *= -1;
            s = s.substring(1);
        }
        res = res * Double.parseDouble(s);
        return res;
    }

    private void initViews() {
        tVCelsius = findViewById(R.id.tVCelsius);
        tVFahrenheit = findViewById(R.id.tVFahrenheit);
        tVKelvin = findViewById(R.id.tVKelvin);
    }



}

