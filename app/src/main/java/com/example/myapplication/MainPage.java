package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainPage extends AppCompatActivity {
    double [] weight_height_age_gender_A;
    public TextView kal_per_day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            weight_height_age_gender_A = bundle.getDoubleArray("2");
        }
        double weight = weight_height_age_gender_A[0];
        double height = weight_height_age_gender_A[1];
        double age = weight_height_age_gender_A[2];
        double gender = weight_height_age_gender_A[3]; // 1-мужчина 0-женщина
        double A= weight_height_age_gender_A[4];
        kal_per_day = findViewById(R.id.kal_per_day);
        if (gender==1){//если мужчина
            kal_per_day.setText(Integer.toString((int)((10*weight+6.25*height-5*age+5)*A)));
        }else{
            kal_per_day.setText(Integer.toString((int)((10*weight+6.25*height-5*age-161)*A)));
        }
    }
}