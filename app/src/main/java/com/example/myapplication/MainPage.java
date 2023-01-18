package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainPage extends AppCompatActivity {
    double [] weight_height_age_gender_A;
    double [] b_g_u_kal;
    public TextView kal_per_day, kal_eaten_per_day;
    public Button food;
    public String message_to_food;
    public double gender_final;
    private double Kal;
    public String kal_per_day_men, kal_per_day_women;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            weight_height_age_gender_A = bundle.getDoubleArray("2");
            b_g_u_kal=bundle.getDoubleArray("b_g_u_kal");
        }
        if (weight_height_age_gender_A!=null){
            double weight = weight_height_age_gender_A[0];
            double height = weight_height_age_gender_A[1];
            double age = weight_height_age_gender_A[2];
            double gender = weight_height_age_gender_A[3];
            gender_final=weight_height_age_gender_A[3];// 1-мужчина 0-женщина
            double A= weight_height_age_gender_A[4];
            kal_per_day = findViewById(R.id.kal_per_day);
            if (gender==1){//если мужчина
                kal_per_day_men= Integer.toString((int)((10*weight+6.25*height-5*age+5)*A));
                kal_per_day.setText(kal_per_day_men);
            }else{
                kal_per_day_women= Integer.toString((int)((10*weight+6.25*height-5*age-161)*A));
                kal_per_day.setText(kal_per_day_women);
            }
        }
        kal_per_day = findViewById(R.id.kal_per_day);
        if (gender_final==1){
            kal_per_day.setText(kal_per_day_men);
        }
        else {
            kal_per_day.setText(String.valueOf(1.1));
        }

        food = findViewById(R.id.food);
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message_to_food="";
                Intent intent = new Intent(MainPage.this, Food.class);
                intent.putExtra("From main menu", message_to_food);
                startActivity(intent);
            }
        });

        //вводить бжу в текст вью
        kal_eaten_per_day=findViewById(R.id.kal_eaten_per_day);;
        if (b_g_u_kal!= null){
            Kal+=b_g_u_kal[3];
            kal_eaten_per_day.setText(String.valueOf(Kal));
        }
    }
}