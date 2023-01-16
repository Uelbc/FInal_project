package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class MainActivity3 extends AppCompatActivity {
    double []a;
    double []b;
    double A;
    Button next;
    public RadioButton a1, a2, a3, a4, a5, a6, a7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            a = bundle.getDoubleArray("1");
        }
        a1 = findViewById(R.id.radioButton3);
        a2 = findViewById(R.id.radioButton4);
        a3 = findViewById(R.id.radioButton5);
        a4 = findViewById(R.id.radioButton6);
        a5 = findViewById(R.id.radioButton7);
        a6 = findViewById(R.id.radioButton8);
        a7 = findViewById(R.id.radioButton9);

        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (a1.isChecked()){
                    A=1.2;
                }
                if (a2.isChecked()){
                    A=1.45;
                }
                if (a3.isChecked()){
                    A=1.65;
                }
                if (a4.isChecked()){
                    A=1.85;
                }
                if (a5.isChecked()){
                    A=2.05;
                }
                if (a6.isChecked()){
                    A= 2.25;
                }
                if (a7.isChecked()){
                    A=2.45;
                }
                double [] b = new double[] {a[0], a[1], a[2], a[3], A};
                Intent intent = new Intent(MainActivity3.this,MainActivity2.class);
                intent.putExtra("2", b);
                startActivity(intent);
            }
        });
    }
}