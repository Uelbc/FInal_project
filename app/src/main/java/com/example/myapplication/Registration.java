package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class Registration extends AppCompatActivity {
    public EditText weight_edittext;
    public EditText height_edittext;
    public EditText age_edittext;
    public AppCompatButton next;
    public RadioButton men;
    public RadioButton women;
    public FirebaseAuth mAuth;
    public static FirebaseDatabase database;
    public static DatabaseReference myRef;
    Boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            flag=bundle.getBoolean("flag");
        }
        AppCompatButton next = findViewById(R.id.next);
        weight_edittext = findViewById(R.id.weight);
        height_edittext = findViewById(R.id.height);
        age_edittext = findViewById(R.id.age);
        men = findViewById(R.id.men);
        women = findViewById(R.id.women);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://strong-and-healthy-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("users");
        String id = mAuth.getCurrentUser().getUid();


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
                    myRef.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            User user = task.getResult().getValue(User.class);
                            user.age=a;
                            user.weight=w;
                            user.height=h;
                            user.gender=g;
                            myRef.child(id).setValue(user);
                            if (flag){
                                Intent i = new Intent(Registration.this, MainPage.class);
                                startActivity(i);
                            }
                            else {
                                Intent intent = new Intent(Registration.this, SelectPhysicalActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
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