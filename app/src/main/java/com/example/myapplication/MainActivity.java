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

public class MainActivity extends AppCompatActivity {
    public EditText weight_edittext;
    public EditText height_edittext;
    public EditText age_edittext;
    public AppCompatButton register;
    public RadioButton men;
    public RadioButton women;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatButton register = findViewById(R.id.next);
        weight_edittext = findViewById(R.id.editTextTextPersonName);
        height_edittext = findViewById(R.id.editTextTextPersonName2);
        age_edittext = findViewById(R.id.editTextTextPersonName3);
        men = findViewById(R.id.radioButton);
        women = findViewById(R.id.radioButton2);
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

        register.setOnClickListener(new View.OnClickListener() {
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
                    Intent intent = new Intent(MainActivity.this,MainActivity3.class);
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