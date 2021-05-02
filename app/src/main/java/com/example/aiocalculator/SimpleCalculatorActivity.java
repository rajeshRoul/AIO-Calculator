package com.example.aiocalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.mariuszgromada.math.mxparser.*;

public class SimpleCalculatorActivity extends AppCompatActivity {

    TextView tVCalcInput, tVCalcRes;
    GridLayout btnsGrid, btnsGridAdvanced;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_calculator);

        initViews();
        initData();
        initClickListeners();
    }

    private void initClickListeners() {

        tVCalcRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager)SimpleCalculatorActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("copied to clipboard",tVCalcRes.getText().toString().substring(1));
                cm.setPrimaryClip(clipData);
                Toast.makeText(SimpleCalculatorActivity.this, "Result Copied", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void evaluateExpression(){
        String exp = tVCalcInput.getText().toString().trim();
        Expression e = new Expression(exp);
        String res = "=" + e.calculate();
        if(res.charAt(res.length()-1) == '0' && res.charAt(res.length()-2) == '.'){
            tVCalcRes.setText(res.substring(0, res.length()-2));
        }else{
            tVCalcRes.setText(res);
        }

    }

    private void initData() {
        tVCalcInput.setText("0");
        tVCalcRes.setText("=0");
        btnsGrid.setVisibility(View.VISIBLE);
        btnsGridAdvanced.setVisibility(View.GONE);
    }

    private void initViews() {
        tVCalcInput= findViewById(R.id.tVCalcInput);
        tVCalcRes = findViewById(R.id.tVCalcRes);
        btnsGrid = (GridLayout)findViewById(R.id.btnsGrid);
        btnsGridAdvanced = (GridLayout)findViewById(R.id.btnsGridAdvanced);
    }

    public void calcOnClick(View v){
        switch (v.getId()){
            case R.id.btnAC:{
                tVCalcInput.setText("0");
                tVCalcRes.setText("=0");
                break;
            }
            case R.id.btnC:{
                if(tVCalcInput.getText().toString().trim() != "0"){
                    String temp = tVCalcInput.getText().toString().trim();
                    if(temp.length() == 1){
                        tVCalcInput.setText("0");
                    }else{
                        tVCalcInput.setText(temp.substring(0, temp.length()-1));
                    }
                }
                evaluateExpression();
                break;
            }
            case R.id.btnEquals:{
                tVCalcInput.setText(tVCalcRes.getText().toString().substring(1));
                break;
            }
            case R.id.btnswitchAdvanced:{
                btnsGrid.setVisibility(View.GONE);
                btnsGridAdvanced.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.btnswitchBasic: {
                btnsGrid.setVisibility(View.VISIBLE);
                btnsGridAdvanced.setVisibility(View.GONE);
                break;
            }
            default:{
                String temp = ((Button)v).getText().toString();
                String existing = tVCalcInput.getText().toString().trim();
                if(existing == "0"){
                    if(!(temp.equals("+") || temp.equals("%") || temp.equals("x") || temp.equals("/") || temp.equals("00"))){
                        tVCalcInput.setText(temp);
                    }
                }else{
                    tVCalcInput.setText((existing + temp));
                }
                evaluateExpression();
            }
        }
    }


}