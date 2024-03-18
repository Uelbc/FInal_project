package com.example.myapplication;

import static android.widget.Toast.makeText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Scanned_food extends AppCompatActivity {
    String code;
    public static FirebaseDatabase database;
    public static DatabaseReference myRef;
    Button add;
    EditText b_barcode, g_barcode, u_barcode, kal_barcode, name_barcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_food);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            code=bundle.getString("code");
        }
        b_barcode=findViewById(R.id.b_barcode);
        g_barcode=findViewById(R.id.g_barcode);
        u_barcode=findViewById(R.id.u_barcode);
        kal_barcode=findViewById(R.id.kal_barcode);
        name_barcode=findViewById(R.id.name_barcode);
        add=findViewById(R.id.add_barcode);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance("https://strong-and-healthy-default-rtdb.europe-west1.firebasedatabase.app/");
                myRef = database.getReference("food");
                if (name_barcode.getText().toString().equals("") || b_barcode.getText().toString().equals("") ||
                        g_barcode.getText().toString().equals("") || u_barcode.getText().toString().equals("") ||
                        kal_barcode.getText().toString().equals("")){
                    Toast toast = makeText(getApplicationContext(),
                            R.string.enter_all_data,
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else {
                    Food_element food_element = new Food_element(name_barcode.getText().toString(), Integer.parseInt(kal_barcode.getText().toString()),
                            Double.parseDouble(b_barcode.getText().toString()), Double.parseDouble(g_barcode.getText().toString()),
                            Double.parseDouble(u_barcode.getText().toString()));
                    if (food_element.getKal()>1000 | food_element.getB()>100 |  food_element.getG()>100 |  food_element.getU()>100){
                            Toast toast = makeText(getApplicationContext(),
                                    R.string.incorrect_data,
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                    }
                    else{
                        myRef.child(code).setValue(food_element).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                myRef.child(code).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        Food_element food = task.getResult().getValue(Food_element.class);
                                        double[] b_g_u_kal_water = new double[]{food.getB(), food.getG(), food.getU(), food.getKal()};
                                        Intent intent = new Intent(Scanned_food.this, Food_selected.class);
                                        intent.putExtra("1", b_g_u_kal_water);
                                        intent.putExtra("name", food.getName());
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
                    }

                }
            }
        });
    }
}