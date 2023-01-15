package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    int [] a;
    public TextView kal_per_day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            a = bundle.getIntArray("1");
        }
        int weight = a[0];
        int height = a[1];
        int age = a[2];
        int gender = a[3]; // 1-мужчина 0-женщина
        kal_per_day = findViewById(R.id.kal_per_day);
        if (gender==1){//если мужчина
            kal_per_day.setText(Integer.toString((int)(10*weight+6.25*height-5*age+5)));
        }else{
            kal_per_day.setText(Integer.toString((int)(10*weight+6.25*height-5*age-161)));
        }
    }
}