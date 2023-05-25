package com.example.myapplication;


import static android.widget.Toast.makeText;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {
    private EditText edit_txt_Username, edit_txt_Email, edit_txt_Pass, edit_txt_CoPass;
    private Button button_register;
    private ImageButton language_button;
    private TextView text_view_login;
    public FirebaseAuth mAuth;
    String username, email, password, co_password, language;
    public static FirebaseDatabase database;
    Bundle bundle=null;
    public static DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        bundle=getIntent().getExtras();
        if (bundle.getString("language")!=null){
            language=bundle.getString("language");
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("language", language);
            editor.apply();
        }
        language = sharedPref.getString("language", "ru");
        setApplicationLocale(language);
        setContentView(R.layout.activity_signup);


        edit_txt_Username = findViewById(R.id.edit_txt_Username);
        edit_txt_Email = findViewById(R.id.edit_txt_Email);
        edit_txt_Pass = findViewById(R.id.edit_txt_Pass);
        edit_txt_CoPass = findViewById(R.id.edit_txt_CoPass);
        text_view_login = findViewById(R.id.text_view_login);
        button_register = findViewById(R.id.button_register);
        language_button=findViewById(R.id.language_register);

        language_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SignUpActivity.this, R.style.MyAlertTheme)
                        .setTitle(R.string.change_language)
                        .setPositiveButton(
                                R.string.Russian,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if (language.equals("ru")){
                                            dialog.dismiss();
                                            Toast toast = makeText(getApplicationContext(),
                                                    "Этот язык уже установлен",
                                                    Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();
                                        }
                                        else{
                                            SharedPreferences sharedPref = SignUpActivity.this.getPreferences(Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPref.edit();
                                            editor.putString("language", "ru");
                                            editor.apply();
                                            new AlertDialog.Builder(SignUpActivity.this, R.style.MyAlertTheme)
                                                    .setTitle(R.string.you_should_restart)
                                                    .setPositiveButton(R.string.restart,
                                                            new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    //finish();
                                                                    finishAffinity();
                                                                }
                                                            })
                                                    .show();
                                        }
                                    }
                                })

                        .setNegativeButton(
                                R.string.english,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if (language.equals("en")){
                                            dialog.dismiss();
                                            Toast toast = makeText(getApplicationContext(),
                                                    "This language is already selected",
                                                    Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();
                                        }
                                        else{
                                            SharedPreferences sharedPref = SignUpActivity.this.getPreferences(Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPref.edit();
                                            editor.putString("language", "en");
                                            editor.apply();
                                            new AlertDialog.Builder(SignUpActivity.this, R.style.MyAlertTheme)
                                                    .setTitle(R.string.you_should_restart)
                                                    .setPositiveButton(R.string.restart,
                                                            new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    //finish();
                                                                    finishAffinity();
                                                                }
                                                            })
                                                    .show();
                                        }
                                    }
                                })
                        .show();
            }
        });


        database = FirebaseDatabase.getInstance("https://strong-and-healthy-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("users");

        text_view_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                intent.putExtra("language", sharedPref.getString("language", "ru"));
                startActivity(intent);
            }
        });
        email = edit_txt_Email.getText().toString().trim();
        password = edit_txt_Pass.getText().toString().trim();
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Intent i = new Intent(SignUpActivity.this, MainPage.class);
            startActivity(i);
        }

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
                                        String id = mAuth.getCurrentUser().getUid();
                                        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                        String date = df.format(Calendar.getInstance().getTime());
                                        List<String> history = new ArrayList<String>();
                                        history.add(date+" 0");
                                        User user = new User(0,0,0,0,0, 0,0,0,0,0,date,history);
                                        myRef.child(id).setValue(user);

                                        Toast.makeText(getApplicationContext(),
                                                        R.string.successful_register,
                                                        Toast.LENGTH_LONG)
                                                .show();
                                        Intent intent
                                                = new Intent(SignUpActivity.this,
                                                Registration.class);
                                        intent.putExtra("language", sharedPref.getString("language", "ru"));
                                        startActivity(intent);
                                    }
                                    else {

                                        // Registration failed
                                        Toast.makeText(
                                                        getApplicationContext(),
                                                        getString(R.string.ne_udalos)
                                                                + getString(R.string.please_try_again),
                                                        Toast.LENGTH_LONG)
                                                .show();

                                    }
                                }
                            });

                } else {
                    Toast.makeText(SignUpActivity.this, R.string.passwords_dont_match, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private boolean validateUsername() {
        username = edit_txt_Username.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(SignUpActivity.this, R.string.enter_username, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateEmail() {
        email = edit_txt_Email.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(SignUpActivity.this, R.string.enter_email, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(SignUpActivity.this, R.string.enter_email_correct, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePassword() {
        password = edit_txt_Pass.getText().toString().trim();
        co_password = edit_txt_CoPass.getText().toString().toLowerCase();

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(SignUpActivity.this, R.string.enter_password, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(co_password)) {
            Toast.makeText(SignUpActivity.this, R.string.repeat_password, Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(SignUpActivity.this, R.string.too_short_password, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public void setApplicationLocale(String locale) {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(locale.toLowerCase()));
        } else {
            config.locale = new Locale(locale.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
    }

}