package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainPage extends AppCompatActivity {
    public static final String DATA = "DATA";
    double [] weight_height_age_gender_A;
    double [] b_g_u_kal;
    public TextView kal_eaten_per_day, bTV, gTV, uTV;
    public Button food, training;
    public String message_to_food;
    public double gender_final;
    public double Kal_final=0;
    double b=0, g=0, u=0, b_norm=0, g_norm=0, u_norm=0;
    public String kal_per_day_men="", kal_per_day_women="";
    SharedPreferences data;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle bundle = getIntent().getExtras();
        data = getApplicationContext().getSharedPreferences(DATA, Context.MODE_PRIVATE);


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
            if (gender==1){//если мужчина
                kal_per_day_men= Integer.toString((int)((10*weight+6.25*height-5*age+5)*A));
                SharedPreferences.Editor e = data.edit();
                e.putString("kal_per_day", kal_per_day_men);
                e.apply();
            }else{
                kal_per_day_women= Integer.toString((int)((10*weight+6.25*height-5*age-161)*A));
                SharedPreferences.Editor e = data.edit();
                e.putString("kal_per_day", kal_per_day_women);
                e.apply();
            }
        }
        if (b_g_u_kal!= null){
            b=data.getLong("b", 0);
            g=data.getLong("g", 0);
            u=data.getLong("u", 0);
            Kal_final = data.getLong("kal_final", 0);

            b+=b_g_u_kal[0];
            g+=b_g_u_kal[1];
            u+=b_g_u_kal[2];
            Kal_final+=b_g_u_kal[3];
        }
        kal_eaten_per_day=findViewById(R.id.kal_eaten_per_day);
        bTV=findViewById(R.id.b);
        gTV=findViewById(R.id.g);
        uTV=findViewById(R.id.u);
        bTV.setText(String.format("%.1f", b)+" / "+(int) Double.parseDouble(data.getString("kal_per_day", "")) * 30 / 100 / 4 +" г");
        gTV.setText(String.format("%.1f", g)+" / "+(int) Double.parseDouble(data.getString("kal_per_day", "")) * 25 / 100 / 9 +" г");
        uTV.setText(String.format("%.1f", u)+" / "+(int) Double.parseDouble(data.getString("kal_per_day", "")) * 45 / 100 / 4 +" г");
        kal_eaten_per_day.setText((int) Kal_final +" / "+data.getString("kal_per_day", ""));

        food = findViewById(R.id.food);
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor e = data.edit();
                e.putLong("kal_final", (long) Kal_final);
                e.putLong("b", (long)b);
                e.putLong("g", (long)g);
                e.putLong("u", (long)u);
                e.apply();
                message_to_food="";
                Intent intent = new Intent(MainPage.this, Food.class);
                intent.putExtra("From main menu", message_to_food);
                startActivity(intent);
            }
        });


        training=findViewById(R.id.press);
        training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, Select_training.class);
                startActivity(intent);
            }
        });

    }
}
