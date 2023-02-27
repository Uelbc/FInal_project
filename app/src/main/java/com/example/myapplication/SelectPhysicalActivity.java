package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SelectPhysicalActivity extends AppCompatActivity {
    public FirebaseAuth mAuth;
    public static FirebaseDatabase database;
    public static DatabaseReference myRef;
    double []a;
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
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://strong-and-healthy-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("users");
        String id = mAuth.getCurrentUser().getUid();

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
                myRef.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        User user = task.getResult().getValue(User.class);
                        user.A=A;
                        myRef.child(id).setValue(user);
                        Intent intent = new Intent(SelectPhysicalActivity.this, MainPage.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}