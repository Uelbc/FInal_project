package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Food_selected extends AppCompatActivity {
    double[] b_g_u_kal_water;
    double b, g, u, kal, water;
    public TextView bel, gir, ugl, kalor, name, water_food;
    public EditText massa;
    public Button add;
    public VideoView videoView;

    public User user;
    public FirebaseAuth mAuth;
    public static FirebaseDatabase database;
    public static DatabaseReference myRef;


    String Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_selected);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            b_g_u_kal_water = bundle.getDoubleArray("1");
            Name = bundle.getString("name");
        }
        b=b_g_u_kal_water[0];
        g=b_g_u_kal_water[1];
        u=b_g_u_kal_water[2];
        kal=b_g_u_kal_water[3];
        water=b_g_u_kal_water[4];
        bel=findViewById(R.id.b);
        String text_b=String.valueOf(b)+ " г";
        bel.setText(text_b);
        gir=findViewById(R.id.g);
        String text_g=String.valueOf(g)+ " г";
        gir.setText(text_g);
        ugl=findViewById(R.id.u);
        String text_u=String.valueOf(u)+ " г";
        ugl.setText(text_u);
        kalor=findViewById(R.id.kal);
        String text_k=String.valueOf(kal)+ " ккал";
        kalor.setText(text_k);
        water_food=findViewById(R.id.water_food);
        String text_water=String.valueOf(water)+" мл";
        water_food.setText(text_water);

        name=findViewById(R.id.name);
        name.setText(Name);
        massa = findViewById(R.id.massa);
        // добавить проверку вводимого текста

        massa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isNumeric(massa.getText().toString())){
                    bel.setText(String.format("%.1f", b/100*Double.parseDouble(massa.getText().toString())));
                    gir.setText(String.format("%.1f", g/100*Double.parseDouble(massa.getText().toString())));
                    ugl.setText(String.format("%.1f", u/100*Double.parseDouble(massa.getText().toString())));
                    kalor.setText(String.format("%.1f", kal/100*Double.parseDouble(massa.getText().toString())));
                    water_food.setText(String.format("%.1f", water/100*Double.parseDouble(massa.getText().toString())));
                } else{
                    bel.setText("0");
                    gir.setText("0");
                    ugl.setText("0");
                    kalor.setText("0");
                    water_food.setText("0");

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNumeric(massa.getText().toString())){
                    double m = Double.parseDouble(massa.getText().toString());
                    mAuth = FirebaseAuth.getInstance();
                    database = FirebaseDatabase.getInstance("https://strong-and-healthy-default-rtdb.europe-west1.firebasedatabase.app/");
                    myRef = database.getReference("users");
                    String id = mAuth.getCurrentUser().getUid();
                    myRef.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            User user = task.getResult().getValue(User.class);
                            user.setB(user.getB()+b/100*m);
                            user.setU(user.getU()+u/100*m);
                            user.setG(user.getG()+u/100*m);
                            user.setKal(user.getKal()+kal/100*m);
                            myRef.child(id).setValue(user);
                            Intent intent = new Intent(Food_selected.this, MainPage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                        }
                    });

                } else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Введите массу продукта",
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