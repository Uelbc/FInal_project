package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Registration extends AppCompatActivity {
    public EditText weight_edittext;
    public EditText height_edittext;
    public EditText age_edittext;
    public AppCompatButton next;
    public RadioButton men;
    public RadioButton women;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatButton next = findViewById(R.id.next);
        weight_edittext = findViewById(R.id.weight);
        height_edittext = findViewById(R.id.height);
        age_edittext = findViewById(R.id.age);
        men = findViewById(R.id.men);
        women = findViewById(R.id.women);
        men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                women.setChecked(false);
                men.setChecked(true);
            }
        });
        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                men.setChecked(false);
                women.setChecked(true);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNumeric(weight_edittext.getText().toString()) &
                        isNumeric(height_edittext.getText().toString()) &
                        isNumeric(age_edittext.getText().toString()) &
                        (men.isChecked()| women.isChecked())){
                    int w, h, a, g;
                    w = Integer.parseInt(weight_edittext.getText().toString());;
                    h = Integer.parseInt(height_edittext.getText().toString());
                    a = Integer.parseInt(age_edittext.getText().toString());
                    if (men.isChecked()){
                        g=1;
                    } else{
                        g=0;
                    }
                    double [] weight_height_age_gender = new double[] {w, h, a, g};
                    Intent intent = new Intent(Registration.this, SelectPhysicalActivity.class);
                    intent.putExtra("1", weight_height_age_gender);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Введите все данные",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}