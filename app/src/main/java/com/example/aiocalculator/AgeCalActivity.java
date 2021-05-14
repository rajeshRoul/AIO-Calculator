package com.example.aiocalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class AgeCalActivity extends AppCompatActivity {
    TextView tVSelectDate, tVAgeRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_cal);

        initViews();
        initClickListeners();
    }

    private void initClickListeners() {
        tVSelectDate.setOnClickListener(v -> {
            int mYear, mMonth, mDay;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
            }else{
                Date date = new Date();
                mDay = date.getDate();
                mYear = date.getYear();
                mMonth = date.getMonth();
            }
            DatePickerDialog datePickerDialog = new DatePickerDialog(AgeCalActivity.this,
                    (view, birthYear, birthMonth, birthDate) -> {
                    setAge(mDay, mMonth, mYear, birthDate, birthMonth, birthYear);
                    tVSelectDate.setText(birthDate + "-" + birthMonth + "-" + birthYear);
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
    }

    private void setAge(int current_date, int current_month, int current_year, int birth_date, int birth_month, int birth_year)
    {
        int[] month = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        // if birth date is greater then current
        // birth_month, then donot count this month
        // and add 30 to the date so as to subtract
        // the date and get the remaining days
        if (birth_date > current_date) {
            current_month = current_month - 1;
            current_date = current_date + month[birth_month - 1];
        }

        // if birth month exceeds current month,
        // then do not count this year and add
        // 12 to the month so that we can subtract
        // and find out the difference
        if (birth_month > current_month) {
            current_year = current_year - 1;
            current_month = current_month + 12;
        }

        // calculate date, month, year
        int calculated_year = current_year - birth_year;
//        Invalid data
        if(calculated_year < 0){
            tVAgeRes.setText("Please Enter a Valid Date of Birth. \nDate of Birth must be earlier than today's date");
            return;
        }
        int calculated_date = current_date - birth_date;
        int calculated_month = current_month - birth_month;

        // setResult
        tVAgeRes.setText("Years: " + calculated_year +
                "\n Months: " + calculated_month + "\n Days: " +
                calculated_date);
    }

    private void initViews() {
        tVSelectDate = findViewById(R.id.tVSelectDate);
        tVAgeRes = findViewById(R.id.tVAgeRes);
    }
}