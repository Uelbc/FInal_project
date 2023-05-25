package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    private EditText txtemail, txtpassoword;
    private Button login_btn;
    private TextView text_view_signup;
    FirebaseAuth mAuth;
    String loginemail, loginpassword;
    Bundle bundle;
    String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtemail = findViewById(R.id.edit_txt_login_email);
        txtpassoword = findViewById(R.id.edit_txt_login_pass);
        text_view_signup = findViewById(R.id.text_view_signup);
        login_btn = findViewById(R.id.button_login);

        mAuth = FirebaseAuth.getInstance();
        bundle=getIntent().getExtras();
        if (bundle!=null){
            language=bundle.getString("language");
        }


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateEmail() | !validatePassword()) {
                    return;
                }


                mAuth.signInWithEmailAndPassword(loginemail, loginpassword).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {


                                    Toast.makeText(Login.this, R.string.enter_complete, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, MainPage.class);
                                    intent.putExtra("language", language);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    
                                    Toast.makeText(Login.this, R.string.not_successful, Toast.LENGTH_SHORT).show();
                                }
                            }

                        });
            }
        });



        text_view_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateEmail() {
        loginemail = txtemail.getText().toString().trim();
        if (TextUtils.isEmpty(loginemail)) {
            Toast.makeText(Login.this, R.string.enter_email, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(loginemail).matches()) {
            Toast.makeText(Login.this, R.string.enter_email_correct, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePassword() {
        loginpassword = txtpassoword.getText().toString().trim();

        if (TextUtils.isEmpty(loginpassword)) {
            Toast.makeText(Login.this, R.string.enter_password, Toast.LENGTH_SHORT).show();
            return false;

        } else {
            return true;
        }
    }
}