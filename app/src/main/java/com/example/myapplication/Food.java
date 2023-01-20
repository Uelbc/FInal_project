package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Food extends AppCompatActivity {
    public Button borsch, omelette, white_bread, pasta, syrniki, chicken_cutlet, egg, grechka, vegetable_salade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        borsch=findViewById(R.id.Тренировки_кнопка);
        omelette=findViewById(R.id.ОмлетКнопка);
        white_bread=findViewById(R.id.БелыйХлебКнопка);
        pasta=findViewById(R.id.МакароныКнопка);
        syrniki=findViewById(R.id.СырникиКнопка);
        chicken_cutlet=findViewById(R.id.КуринаяКотлетаКнопка);
        egg=findViewById(R.id.ОтварноеЯйцоКнопка);
        grechka=findViewById(R.id.ГречкаКнопка);
        vegetable_salade=findViewById(R.id.ОвощнойСалатКнопка);

        borsch.setOnClickListener(new View.OnClickListener() {
            double b, g, u, kal;
            @Override
            public void onClick(View view) {
                b=4.0;
                g=3.0;
                u=10.0;
                kal=60.0;
                double [] b_g_u_kal = new double[] {b, g, u, kal};
                Intent intent = new Intent(Food.this, Food_selected.class);
                intent.putExtra("1", b_g_u_kal);
                intent.putExtra("name", "Борщ");
                startActivity(intent);
            }
        });
        omelette.setOnClickListener(new View.OnClickListener() {
            double b, g, u, kal;
            @Override
            public void onClick(View view) {
                b=10.6;
                g=11.7;
                u=0.6;
                kal=154.0;
                double [] b_g_u_kal = new double[] {b, g, u, kal};
                Intent intent = new Intent(Food.this, Food_selected.class);
                intent.putExtra("1", b_g_u_kal);
                intent.putExtra("name", "Омлет");
                startActivity(intent);
            }
        });
        white_bread.setOnClickListener(new View.OnClickListener() {
            double b, g, u, kal;
            @Override
            public void onClick(View view) {
                u=49.1;
                b=9.2;
                g=3.2;
                kal=265.0;
                double [] b_g_u_kal = new double[] {b, g, u, kal};
                Intent intent = new Intent(Food.this, Food_selected.class);
                intent.putExtra("1", b_g_u_kal);
                intent.putExtra("name", "Белый хлеб");
                startActivity(intent);
            }
        });
        pasta.setOnClickListener(new View.OnClickListener() {
            double b, g, u, kal;
            @Override
            public void onClick(View view) {
                u=30.9;
                b=5.8;
                g=0.9;
                kal=158.0;
                double [] b_g_u_kal = new double[] {b, g, u, kal};
                Intent intent = new Intent(Food.this, Food_selected.class);
                intent.putExtra("1", b_g_u_kal);
                intent.putExtra("name", "Отварные макароны");
                startActivity(intent);
            }
        });
        syrniki.setOnClickListener(new View.OnClickListener() {
            double b, g, u, kal;
            @Override
            public void onClick(View view) {
                u=26.0;
                b=14.0;
                g=5.0;
                kal=204.0;
                double [] b_g_u_kal = new double[] {b, g, u, kal};
                Intent intent = new Intent(Food.this, Food_selected.class);
                intent.putExtra("1", b_g_u_kal);
                intent.putExtra("name", "Сырники творожные");
                startActivity(intent);
            }
        });
        chicken_cutlet.setOnClickListener(new View.OnClickListener() {
            double b, g, u, kal;
            @Override
            public void onClick(View view) {
                u=12.8;
                b=14.9;
                g=19.6;
                kal=287.0;
                double [] b_g_u_kal = new double[] {b, g, u, kal};
                Intent intent = new Intent(Food.this, Food_selected.class);
                intent.putExtra("1", b_g_u_kal);
                intent.putExtra("name", "Куриная котлета");
                startActivity(intent);
            }
        });
        egg.setOnClickListener(new View.OnClickListener() {
            double b, g, u, kal;
            @Override
            public void onClick(View view) {
                u=1.1;
                b=12.6;
                g=10.6;
                kal=155.0;
                double [] b_g_u_kal = new double[] {b, g, u, kal};
                Intent intent = new Intent(Food.this, Food_selected.class);
                intent.putExtra("1", b_g_u_kal);
                intent.putExtra("name", "Яйцо отварное, куриное");
                startActivity(intent);
            }
        });
        grechka.setOnClickListener(new View.OnClickListener() {
            double b, g, u, kal;
            @Override
            public void onClick(View view) {
                u=19.9;
                b=3.4;
                g=0.6;
                kal=92.0;
                double [] b_g_u_kal = new double[] {b, g, u, kal};
                Intent intent = new Intent(Food.this, Food_selected.class);
                intent.putExtra("1", b_g_u_kal);
                intent.putExtra("name", "Гречка отварная");
                startActivity(intent);
            }
        });
        vegetable_salade.setOnClickListener(new View.OnClickListener() {
            double b, g, u, kal;
            @Override
            public void onClick(View view) {
                u=3.2;
                b=1.3;
                g=0.1;
                kal=16.0;
                double [] b_g_u_kal = new double[] {b, g, u, kal};
                Intent intent = new Intent(Food.this, Food_selected.class);
                intent.putExtra("1", b_g_u_kal);
                intent.putExtra("name", "Овощной салат");
                startActivity(intent);
            }
        });

    }
}