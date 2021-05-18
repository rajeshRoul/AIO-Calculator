package com.example.aiocalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aiocalculator.currency.CurrencyBuild;
import com.example.aiocalculator.currency.CurrencyInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyConvertorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerDataConv1, spinnerDataConv2;
    TextView tVDataInput, tVDataResult, tVTitle;

    String[] data = {"United States Dollar", "Euro", "Indian Rupee", "New Zealand Dollar", "UAE Dirham", "Afghan Afghani",
     "Armenian Dram", "Australian Dollar", "Barbados Dollar", "Bangladeshi Taka", "Brazilian Real", "Canadian Dollar",
    "Chinese Renminbi", "Czech Koruna", "Dominican Peso", "Egyptian Pound", "Hong Kong Dollar", "Indonesian Rupiah",
    "Japanese Yen", "South Korean Won", "Sri Lanka Rupee", "Mexican Peso", "Nigerian Naira", "Philippine Peso",
            "Pakistani Rupee", "Russian Ruble", "Saudi Riyal", "Singapore Dollar", "South African Rand", "Tanzanian Shilling"};

    String[] values = {"USD", "EUR", "INR", "NZD", "AED", "AFN", "AMD", "AUD", "BBD", "BDT", "BRL", "CAD", "CNY", "CZK",
    "DOP", "EGP", "HKD", "IDR", "JPY", "KRW", "LKR", "MXN", "NGN", "PHP", "PKR", "RUB", "SAR", "SGD", "ZAR", "TZS"};

    JsonObject rates = null;

    int conversionTo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convertor);

        initViews();
        initData();
        initClickListeners();
    }

    private void evaluate(){
        if(rates == null){
            tVDataResult.setText("Error Occurred");
            return;
        }
        double currency = Double.parseDouble(tVDataInput.getText().toString().trim());
        double multiplier = Double.parseDouble(rates.get(values[conversionTo]).toString());
        double result = currency * multiplier;
        tVDataResult.setText(String.valueOf(result));
    }

    public boolean isInternetConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        else
            return false;
    }

    private void fetchLatestData(int fromIndex){
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();

        if(isInternetConnected()){
            CurrencyInterface retrofitInterface = CurrencyBuild.getRetrofitInstance().create(CurrencyInterface.class);
            Call<JsonObject> call = retrofitInterface.getExchangeCurrency(values[fromIndex]);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject res = response.body();
                    rates = res.getAsJsonObject("conversion_rates");
                    evaluate();
                    progress.dismiss();
                }
                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progress.dismiss();
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.convertorContainer), "Cannot fetch Latest Data", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    fetchLatestData(fromIndex);
                                }
                            });
                    snackbar.show();
                }
            });
        }else{
            progress.dismiss();

            Snackbar snackbar = Snackbar.make(findViewById(R.id.convertorContainer), "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fetchLatestData(fromIndex);
                        }
                    });
            snackbar.show();
        }

    }

    private void initClickListeners() {

        tVDataInput.setOnClickListener(v -> {
            Toast.makeText(CurrencyConvertorActivity.this, "Long Press to Copy", Toast.LENGTH_SHORT).show();
        });

        tVDataResult.setOnClickListener(v -> {
            Toast.makeText(CurrencyConvertorActivity.this, "Long Press to Copy", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnC).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tVDataInput.setText("0");
                tVDataResult.setText("0");
                return true;
            }
        });

        tVDataInput.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cm = (ClipboardManager)CurrencyConvertorActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                String temp = tVDataInput.getText().toString().trim();
                ClipData clipData = ClipData.newPlainText("copied to clipboard",temp);
                cm.setPrimaryClip(clipData);
                Toast.makeText(CurrencyConvertorActivity.this, "Input Copied", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        tVDataResult.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cm = (ClipboardManager)CurrencyConvertorActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                String temp = tVDataResult.getText().toString().trim();
                ClipData clipData = ClipData.newPlainText("copied to clipboard",temp);
                cm.setPrimaryClip(clipData);
                Toast.makeText(CurrencyConvertorActivity.this, "Result Copied", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void initViews() {
        spinnerDataConv1 = findViewById(R.id.spinnerConv1);
        spinnerDataConv2 = findViewById(R.id.spinnerConv2);
        tVDataInput = findViewById(R.id.tVConv1);
        tVDataResult = findViewById(R.id.tVConv2);
        tVTitle = findViewById(R.id.tVTitle);
    }

    private void initData() {
        tVTitle.setText("Currency Convertor");
        spinnerDataConv1.setOnItemSelectedListener(this);
        spinnerDataConv2.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, data);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDataConv1.setAdapter(ad);
        spinnerDataConv2.setAdapter(ad);
        tVDataInput.setText("0");
        tVDataResult.setText("0");
    }

    public void convOnClick(View v){
        switch (v.getId()){
            case R.id.btnC:{
                String temp = tVDataInput.getText().toString().trim();
                if(!temp.equals("0")){
                    if(temp.length() == 1){
                        tVDataInput.setText("0");
                    }else{
                        tVDataInput.setText(temp.substring(0, temp.length()-1));
                    }
                    evaluate();
                }
                break;
            } case R.id.btnDot:{
                String temp = tVDataInput.getText().toString().trim();
                boolean flag = false;
                if(temp.length() >= 16){
                    Toast.makeText(CurrencyConvertorActivity.this, "Input number is too long", Toast.LENGTH_SHORT).show();
                    break;
                }
                for(int i=0; i<temp.length(); i++){
                    if(temp.charAt(i) == '.'){
                        flag = true;
                        i = temp.length();
                    }
                }
                if(!flag){
                    tVDataInput.setText(temp + ".");
                }
                break;
            } case R.id.btn0:{
                String temp = tVDataInput.getText().toString().trim();
                if(temp.length() >= 16){
                    Toast.makeText(CurrencyConvertorActivity.this, "Input number is too long", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(!temp.equals("0") ){
                    tVDataInput.setText(temp + "0");
                    evaluate();
                }
                break;
            } default:{
                String temp = tVDataInput.getText().toString().trim();
                if (!temp.equals("0")){
                    if(temp.length() < 16){
                        tVDataInput.setText(temp + ((Button)v).getText().toString().trim());
                    }else{
                        Toast.makeText(CurrencyConvertorActivity.this, "Input number is too long", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    tVDataInput.setText(((Button)v).getText().toString().trim());
                }
                evaluate();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spinnerConv1){
            rates = null;
            fetchLatestData(position);
            evaluate();
        }else if(spinner.getId() == R.id.spinnerConv2){
            conversionTo = position;
            evaluate();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}