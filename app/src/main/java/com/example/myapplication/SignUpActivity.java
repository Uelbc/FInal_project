package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    private EditText edit_txt_Username, edit_txt_Email, edit_txt_Pass, edit_txt_CoPass;
    private Button button_register;
    private TextView text_view_login;
    private FirebaseAuth mAuth;
    String username, email, password, co_password;
    public FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        edit_txt_Username = findViewById(R.id.edit_txt_Username);
        edit_txt_Email = findViewById(R.id.edit_txt_Email);
        edit_txt_Pass = findViewById(R.id.edit_txt_Pass);
        edit_txt_CoPass = findViewById(R.id.edit_txt_CoPass);
        text_view_login = findViewById(R.id.text_view_login);
        button_register = findViewById(R.id.button_register);


        db=FirebaseFirestore.getInstance();

        text_view_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
        email = edit_txt_Email.getText().toString().trim();
        password = edit_txt_Pass.getText().toString().trim();
        mAuth=FirebaseAuth.getInstance();

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateUsername() | !validateEmail() | !validatePassword() ) {
                    return;
                }

                if (password.equals(co_password)) {

                    mAuth
                            .createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    if (task.isSuccessful()) {
                                        Person person = new Person(email, password, 0, 0, 0, 0, 0);
                                        db.collection("users")
                                                        .add(person);
                                        Toast.makeText(getApplicationContext(),
                                                        "Успешная регистрация!",
                                                        Toast.LENGTH_LONG)
                                                .show();



                                        Intent intent
                                                = new Intent(SignUpActivity.this,
                                                Registration.class);
                                        startActivity(intent);
                                    }
                                    else {

                                        // Registration failed
                                        Toast.makeText(
                                                        getApplicationContext(),
                                                        "Ну удалось зарегистрироваться!!"
                                                                + " Пожалуйста попробуйте ещё раз",
                                                        Toast.LENGTH_LONG)
                                                .show();

                                    }
                                }
                            });

                } else {
                    Toast.makeText(SignUpActivity.this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private boolean validateUsername() {
        username = edit_txt_Username.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(SignUpActivity.this, "Введите имя пользователя", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateEmail() {
        email = edit_txt_Email.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(SignUpActivity.this, "Введите электронную почту", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(SignUpActivity.this, "Введите электронную почту корректно", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePassword() {
        password = edit_txt_Pass.getText().toString().trim();
        co_password = edit_txt_CoPass.getText().toString().toLowerCase();

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(SignUpActivity.this, "Введите пароль", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(co_password)) {
            Toast.makeText(SignUpActivity.this, "Повторите пароль", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(SignUpActivity.this, "Пароль слишком короткий", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

}