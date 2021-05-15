package com.example.aiocalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.mariuszgromada.math.mxparser.Expression;

import java.util.ArrayList;

public class MassConvertorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //    Spinner Data
    String[] data = {"Kilogram", "Gram", "Milligram", "Decagram", "Tonne", "Ounce", "Pound"};
    //    Calculation Values corresponding to spinner selected item
    String[] dataSymbols = {"[kg]", "[gr]", "[mg]", "[dag]", "[t]", "[oz]", "[lb]"};

    int selected1;
    int selected2;

    Spinner spinnerDataConv1, spinnerDataConv2;
    TextView tVDataConv1, tVDataConv2, tVTitle;
    ArrayList<TextView> inputs;
    int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convertor);

        initViews();
        initData();
        initClickListeners();
    }

    private void initClickListeners() {
        tVDataConv1.setOnClickListener(v -> {
            selected = 0;
            inputs.get(0).setTextColor(getResources().getColor(R.color.red));
            inputs.get(1).setTextColor(getResources().getColor(R.color.btnTextColor));
        });

        tVDataConv2.setOnClickListener(v -> {
            selected = 1;
            inputs.get(1).setTextColor(getResources().getColor(R.color.red));
            inputs.get(0).setTextColor(getResources().getColor(R.color.btnTextColor));
        });

        findViewById(R.id.btnC).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tVDataConv1.setText("0");
                tVDataConv2.setText("0");
                return true;
            }
        });

        tVDataConv1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cm = (ClipboardManager)MassConvertorActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                String temp = tVDataConv1.getText().toString().trim();
                ClipData clipData = ClipData.newPlainText("copied to clipboard",temp);
                cm.setPrimaryClip(clipData);
                Toast.makeText(MassConvertorActivity.this, "Result Copied", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        tVDataConv2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cm = (ClipboardManager)MassConvertorActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                String temp = tVDataConv2.getText().toString().trim();
                ClipData clipData = ClipData.newPlainText("copied to clipboard",temp);
                cm.setPrimaryClip(clipData);
                Toast.makeText(MassConvertorActivity.this, "Result Copied", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    private void evaluate(){
        String data = inputs.get(selected).getText().toString().trim();
        String res = "";
        if(selected == 0){
            String exp = data + "*" + dataSymbols[selected1] + " / " + dataSymbols[selected2];
            Expression e = new Expression(exp);
            res += e.calculate();
            if(res.endsWith(".0")){
                res = res.substring(0, res.length()-2);
            }
            tVDataConv2.setText(res);
        }else {
            String exp = data + "*" + dataSymbols[selected2] + " / " + dataSymbols[selected1];
            Expression e = new Expression(exp);
            res += e.calculate();
            if(res.endsWith(".0")){
                res = res.substring(0, res.length()-2);
            }
            tVDataConv1.setText(res);
        }
    }

    public void convOnClick(View v){
        switch (v.getId()){
            case R.id.btnC:{
                String temp = inputs.get(selected).getText().toString().trim();
                if(!temp.equals("0")){
                    if(temp.length() == 1){
                        inputs.get(selected).setText("0");
                    }else{
                        inputs.get(selected).setText(temp.substring(0, temp.length()-1));
                    }
                    evaluate();
                }
                break;
            } case R.id.btnDot:{
                String temp = inputs.get(selected).getText().toString().trim();
                boolean flag = false;
                if(temp.length() >= 16){
                    Toast.makeText(MassConvertorActivity.this, "Input number is too long", Toast.LENGTH_SHORT).show();
                    break;
                }
                for(int i=0; i<temp.length(); i++){
                    if(temp.charAt(i) == '.'){
                        flag = true;
                        i = temp.length();
                    }
                }
                if(!flag){
                    inputs.get(selected).setText(temp + ".");
                }
                break;
            } case R.id.btn0:{
                String temp = inputs.get(selected).getText().toString().trim();
                if(temp.length() >= 16){
                    Toast.makeText(MassConvertorActivity.this, "Input number is too long", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(!temp.equals("0") ){
                    inputs.get(selected).setText(temp + "0");
                    evaluate();
                }
                break;
            } default:{
                String temp = inputs.get(selected).getText().toString().trim();
                if (!temp.equals("0")){
                    if(temp.length() < 16){
                        inputs.get(selected).setText(temp + ((Button)v).getText().toString().trim());
                    }else{
                        Toast.makeText(MassConvertorActivity.this, "Input number is too long", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    inputs.get(selected).setText(((Button)v).getText().toString().trim());
                }
                evaluate();
            }
        }
    }

    private void initData() {
        tVTitle.setText("Mass Convertor");
        spinnerDataConv1.setOnItemSelectedListener(this);
        spinnerDataConv2.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, data);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDataConv1.setAdapter(ad);
        spinnerDataConv2.setAdapter(ad);
        inputs = new ArrayList<>();
        inputs.add(tVDataConv1);
        inputs.add(tVDataConv2);
        selected = 0;
        selected1 = 0;
        selected2 = 0;
    }

    private void initViews() {
        spinnerDataConv1 = findViewById(R.id.spinnerConv1);
        spinnerDataConv2 = findViewById(R.id.spinnerConv2);
        tVDataConv1 = findViewById(R.id.tVConv1);
        tVDataConv2 = findViewById(R.id.tVConv2);
        tVTitle = findViewById(R.id.tVTitle);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spinnerConv1){
            selected1 = position;
            evaluate();
        }else if(spinner.getId() == R.id.spinnerConv2){
            selected2 = position;
            evaluate();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}