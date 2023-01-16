package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class SelectPhysicalActivity extends AppCompatActivity {
    double []a;
    double [] weight_height_age_gender_A;
    double A;
    Button next;
    public RadioButton level1, level2, level3, level4, level5, level6, level7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            a = bundle.getDoubleArray("1");
        }
        level1 = findViewById(R.id.level1);
        level2 = findViewById(R.id.level2);
        level3 = findViewById(R.id.level3);
        level4 = findViewById(R.id.level4);
        level5 = findViewById(R.id.level5);
        level6 = findViewById(R.id.level6);
        level7 = findViewById(R.id.level7);

        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (level1.isChecked()){
                    A=1.2;
                }
                if (level2.isChecked()){
                    A=1.45;
                }
                if (level3.isChecked()){
                    A=1.6;
                }
                if (level4.isChecked()){
                    A=1.75;
                }
                if (level5.isChecked()){
                    A=1.9;
                }
                if (level6.isChecked()){
                    A= 2.05;
                }
                if (level7.isChecked()){
                    A=2.2;
                }
                double [] b = new double[] {a[0], a[1], a[2], a[3], A};
                Intent intent = new Intent(SelectPhysicalActivity.this, MainPage.class);
                intent.putExtra("2", b);
                startActivity(intent);
            }
        });
    }
}