package com.example.myapplication;

import static android.widget.Toast.makeText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainPage extends AppCompatActivity {
    public static final String DATA = "DATA";
    public TextView kal_eaten_per_day, bTV, gTV, uTV, litr, water_goal;
    public Button food, training;
    public String training_finished;
    Boolean flag_logout=true;
    public ImageButton water_info, kal_info;
    int checked;
    public double Kal_final, weight;
    double b, g, u;
    public int kal_per_day;
    private ProgressBar b_progress_bar, g_progress_bar, u_progress_bar, kal_progress_bar;
    private CheckBox water1, water2, water3, water4, water5, water6, water7, water8, water9, water10, water11, water12,
            water13, water14, water15, water16, water17, water18, water19, water20, water21, water22, water23, water24;
    CheckBox[] checkBoxes;
    private int Len_check_boxes;
    SharedPreferences data;
    public User user;
    ImageButton menu;
    List<String> history;
    String language;
    Bundle bundle;

    public FirebaseAuth mAuth;
    public static FirebaseDatabase database;
    public static DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        bundle=getIntent().getExtras();
        if (bundle!=null){
            language=bundle.getString("language");
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("language", language);
            editor.apply();
        }
        language = sharedPref.getString("language", "ru");
        setApplicationLocale(language);
        setContentView(R.layout.activity_main2);
        menu=findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {

                PopupMenu popup = new PopupMenu(MainPage.this, view);
                popup.inflate(R.menu.actions);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.logout:
                                String id = mAuth.getCurrentUser().getUid();

                                myRef.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        flag_logout=false;
                                        User user = task.getResult().getValue(User.class);
                                        user.setChecked_water(checked);
                                        myRef.child(id).setValue(user);
                                        mAuth.signOut();
                                        Log.w("RRR", "11");
                                        Intent intent=new Intent(MainPage.this, SignUpActivity.class);
                                        intent.putExtra("language", language);
                                        startActivity(intent);
                                    }
                                });
                                return true;
                            case R.id.change_parametrs:
                                Intent intent=new Intent(MainPage.this, Registration.class);
                                intent.putExtra("flag", true);
                                startActivity(intent);
                                return true;
                            case R.id.calendar:
                                Intent intent1 = new Intent(MainPage.this, History.class);
                                startActivity(intent1);
                                return true;
                            case R.id.change_level:
                                Intent i1=new Intent(MainPage.this, SelectPhysicalActivity.class);
                                startActivity(i1);
                                return true;
                            case R.id.language:
                                new AlertDialog.Builder(MainPage.this, R.style.MyAlertTheme)
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
                                                            SharedPreferences sharedPref = MainPage.this.getPreferences(Context.MODE_PRIVATE);
                                                            SharedPreferences.Editor editor = sharedPref.edit();
                                                            editor.putString("language", "ru");
                                                            editor.apply();
                                                            new AlertDialog.Builder(MainPage.this, R.style.MyAlertTheme)
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
                                                    SharedPreferences sharedPref = MainPage.this.getPreferences(Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPref.edit();
                                                    editor.putString("language", "en");
                                                    editor.apply();
                                                    new AlertDialog.Builder(MainPage.this, R.style.MyAlertTheme)
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
                        return false;
                    }
                });
                MenuPopupHelper menuHelper = new MenuPopupHelper(MainPage.this, (MenuBuilder) popup.getMenu(), view);
                menuHelper.setForceShowIcon(true);
                menuHelper.show();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://strong-and-healthy-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("users");
        if (mAuth.getCurrentUser().getUid()!=null){
            String id = mAuth.getCurrentUser().getUid();
            myRef.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @SuppressLint({"DefaultLocale", "SetTextI18n"})
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    user = task.getResult().getValue(User.class);
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String date = df.format(Calendar.getInstance().getTime());



                    kal_eaten_per_day=findViewById(R.id.kal_eaten_per_day);
                    if (user.getGender()==1){
                        kal_per_day=(int)((10*user.getWeight()+6.25*user.getHeight()-5*user.getAge()+5)*user.getA());
                    }
                    if (user.getGender()==0){
                        kal_per_day=(int)((10*user.getWeight()+6.25*user.getHeight()-5*user.getAge()-161)*user.getA());
                    }
                    Kal_final= user.getKal();
                    b=user.getB();
                    g=user.getG();
                    u=user.getU();
                    Len_check_boxes=user.getChecked_water();
                    weight=user.getWeight();

                    if (!user.getDate().equals(date)){
                        user.setB(0);
                        user.setG(0);
                        user.setU(0);
                        user.setKal(0);
                        user.setChecked_water(0);
                        history=user.getHistory();
                        if (Kal_final>0.9*kal_per_day && Kal_final<1.1*kal_per_day){
                            history.add(user.getDate()+" 1");
                        }
                        else{
                            history.add(user.getDate()+ " 0");
                        }
                        user.setHistory(history);
                        user.setDate(date);
                        myRef.child(id).setValue(user);
                    }
                    Kal_final= user.getKal();
                    b=user.getB();
                    g=user.getG();
                    u=user.getU();
                    Len_check_boxes=user.getChecked_water();
                    weight=user.getWeight();
                    kal_eaten_per_day.setText(String.format("%.0f", Kal_final)+" / "+kal_per_day);

                    bTV=findViewById(R.id.b);
                    gTV=findViewById(R.id.g);
                    uTV=findViewById(R.id.u);

                    bTV.setText(String.format("%.1f", b)+" / "+kal_per_day * 30 / 400 + getString(R.string.g));
                    gTV.setText(String.format("%.1f", g)+" / "+kal_per_day * 25 / 900 + getString(R.string.g));
                    uTV.setText(String.format("%.1f", u)+" / "+kal_per_day * 45 / 400 + getString(R.string.g));

                    b_progress_bar=findViewById(R.id.b_progress_bar);
                    g_progress_bar=findViewById(R.id.g_progress_bar);
                    u_progress_bar=findViewById(R.id.u_progress_bar);
                    kal_progress_bar=findViewById(R.id.kal_progress_bar);

                    b_progress_bar.setMax(kal_per_day * 30 / 100 / 4);
                    g_progress_bar.setMax(kal_per_day * 25 / 100 / 9);
                    u_progress_bar.setMax(kal_per_day * 45 / 100 / 4);
                    kal_progress_bar.setMax(kal_per_day);

                    b_progress_bar.setProgress((int)b);
                    g_progress_bar.setProgress((int)g);
                    u_progress_bar.setProgress((int)u);
                    kal_progress_bar.setProgress((int) Kal_final);
                    if (Kal_final>0.9*kal_per_day && Kal_final<1.1*kal_per_day){
                        kal_progress_bar.getProgressDrawable().setColorFilter(
                                Color.GREEN, PorterDuff.Mode.SRC_IN);
                    }
                    else{
                        kal_progress_bar.getProgressDrawable().setColorFilter(
                                Color.RED, PorterDuff.Mode.SRC_IN);
                    }
                    if (Kal_final>kal_per_day){
                        kal_eaten_per_day.setText(getString(R.string.more_on)+" "+(Kal_final-kal_per_day));
                    }
                    water_goal=findViewById(R.id.water_goal);
                    water_goal.setText(getString(R.string.goal)+ Double.toString(weight*40/1000)+getString(R.string.l));


                    water1=findViewById(R.id.water1); water2=findViewById(R.id.water2); water3=findViewById(R.id.water3); water4=findViewById(R.id.water4);
                    water5=findViewById(R.id.water5); water6=findViewById(R.id.water6); water7=findViewById(R.id.water7); water8=findViewById(R.id.water8);
                    water9=findViewById(R.id.water9); water10=findViewById(R.id.water10); water11=findViewById(R.id.water11); water12=findViewById(R.id.water12);
                    water13=findViewById(R.id.water13); water14=findViewById(R.id.water14); water15=findViewById(R.id.water15); water16=findViewById(R.id.water16);
                    water17=findViewById(R.id.water17); water18=findViewById(R.id.water18); water19=findViewById(R.id.water19); water20=findViewById(R.id.water20);
                    water21=findViewById(R.id.water21); water22=findViewById(R.id.water22); water23=findViewById(R.id.water23); water24=findViewById(R.id.water24);
                    checkBoxes= new CheckBox[]{water1, water2, water3, water4, water5, water6, water7, water8, water9, water10, water11, water12,
                            water13, water14, water15, water16, water17, water18, water19, water20, water21, water22, water23, water24};
                    litr = findViewById(R.id.litr);
                    for (int i=0; i<Len_check_boxes; i++){
                        checkBoxes[i].setChecked(true);
                        checkBoxes[i].setVisibility(View.VISIBLE);
                        checkBoxes[i].setButtonDrawable(getResources().getDrawable(R.drawable.water));
                        checkBoxes[i+1].setVisibility(View.VISIBLE);
                    }
                    litr.setText(Double.toString(Len_check_boxes*0.250));

                    checked=Len_check_boxes;
                    ObservableInteger obsInt = new ObservableInteger();
                    obsInt.set(checked);
                    obsInt.setOnIntegerChangeListener(new OnIntegerChangeListener()
                    {
                        @Override
                        public void onIntegerChanged(int newValue)
                        {
                            for (int i=0; i<checkBoxes.length; i++){
                                if (i<checked){
                                    checkBoxes[i].setVisibility(View.VISIBLE);
                                    checkBoxes[i].setButtonDrawable(R.drawable.water);
                                }
                                else{
                                    if(i==checked){
                                        checkBoxes[i].setButtonDrawable(R.drawable.add);
                                        checkBoxes[i].setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        checkBoxes[i].setVisibility(View.GONE);
                                    }
                                }
                            }
                            litr.setText(Double.toString(checked*0.25));
                        }
                    });
                    water1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                        if (buttonView.isChecked()) {
                            water1.setButtonDrawable(R.drawable.water);
                            water2.setButtonDrawable(R.drawable.add);
                            water2.setChecked(false);
                            water2.setVisibility(View.VISIBLE);
                            checked+=1;
                            litr.setText(Double.toString(0.250));}
                        else{checked=0;
                            obsInt.set(checked);}
                    }
                    });
                    water2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {if (buttonView.isChecked()) {
                        water2.setButtonDrawable(R.drawable.water);
                        water3.setButtonDrawable(R.drawable.add);
                        water3.setChecked(false);
                        water3.setVisibility(View.VISIBLE);
                        checked+=1;
                        litr.setText(Double.toString(0.50));}
                    else{checked=1;
                        obsInt.set(checked);}
                    }
                    });
                    water3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {if (buttonView.isChecked()) {
                        water3.setButtonDrawable(R.drawable.water);
                        water4.setButtonDrawable(R.drawable.add);
                        water4.setChecked(false);
                        water4.setVisibility(View.VISIBLE);
                        checked+=1;
                        litr.setText(Double.toString(0.750));}
                    else{checked=2;
                        obsInt.set(checked);}
                    }
                    });
                    water4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {if (buttonView.isChecked()) {
                        water4.setButtonDrawable(R.drawable.water);
                        water5.setButtonDrawable(R.drawable.add);
                        water5.setChecked(false);
                        checked+=1;
                        water5.setVisibility(View.VISIBLE);
                        litr.setText(Double.toString(1.0));}
                    else{checked=3;
                        obsInt.set(checked);}
                    }
                    });
                    water5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {if (buttonView.isChecked()) {
                        water5.setButtonDrawable(R.drawable.water);
                        water6.setButtonDrawable(R.drawable.add);
                        water6.setChecked(false);
                        checked+=1;
                        water6.setVisibility(View.VISIBLE);
                        litr.setText(Double.toString(1.250));}
                    else{checked=4;
                        obsInt.set(checked);}
                    }
                    });
                    water6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {if (buttonView.isChecked()) {
                        water6.setButtonDrawable(R.drawable.water);
                        water7.setButtonDrawable(R.drawable.add);
                        water7.setChecked(false);
                        checked+=1;
                        water7.setVisibility(View.VISIBLE);
                        litr.setText(Double.toString(1.50));}
                    else{checked=5;
                        obsInt.set(checked);}
                    }
                    });
                    water7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {if (buttonView.isChecked()) {
                        water7.setButtonDrawable(R.drawable.water);
                        water8.setButtonDrawable(R.drawable.add);
                        water8.setChecked(false);
                        checked+=1;
                        water8.setVisibility(View.VISIBLE);
                        litr.setText(Double.toString(1.750));}
                    else{checked=6;
                        obsInt.set(checked);}
                    }
                    });
                    water8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {if (buttonView.isChecked()) {
                        water8.setButtonDrawable(R.drawable.water);
                        water9.setButtonDrawable(R.drawable.add);
                        water9.setChecked(false);
                        checked+=1;
                        water9.setVisibility(View.VISIBLE);
                        litr.setText(Double.toString(2.0));}
                    else{checked=7;
                        obsInt.set(checked);}
                    }
                    });
                    water9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {if (buttonView.isChecked()) {
                        water9.setButtonDrawable(R.drawable.water);
                        water10.setButtonDrawable(R.drawable.add);
                        water10.setChecked(false);
                        checked+=1;
                        water10.setVisibility(View.VISIBLE);
                        litr.setText(Double.toString(2.250));}
                    else{checked=8;
                        obsInt.set(checked);}
                    }
                    });
                    water10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {if (buttonView.isChecked()) {
                        water10.setButtonDrawable(R.drawable.water);
                        water11.setButtonDrawable(R.drawable.add);
                        water11.setChecked(false);
                        checked+=1;
                        water11.setVisibility(View.VISIBLE);
                        litr.setText(Double.toString(2.50));}
                    else{checked=9;
                        obsInt.set(checked);}
                    }
                    });
                    water11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {if (buttonView.isChecked()) {
                        water11.setButtonDrawable(R.drawable.water);
                        water12.setButtonDrawable(R.drawable.add);
                        water12.setChecked(false);
                        checked+=1;
                        water12.setVisibility(View.VISIBLE);
                        litr.setText(Double.toString(2.750));}
                    else{checked=10;
                        obsInt.set(checked);}
                    }
                    });
                    water12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {if (buttonView.isChecked()) {
                        water12.setButtonDrawable(R.drawable.water);
                        water13.setButtonDrawable(R.drawable.add);
                        water13.setChecked(false);
                        checked+=1;
                        water13.setVisibility(View.VISIBLE);
                        litr.setText(Double.toString(3.0));}
                    else{checked=11;
                        obsInt.set(checked);}
                    }
                    });
                    water13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {if (buttonView.isChecked()) {
                        water13.setButtonDrawable(R.drawable.water);
                        water14.setButtonDrawable(R.drawable.add);
                        water14.setChecked(false);
                        checked+=1;
                        water14.setVisibility(View.VISIBLE);
                        litr.setText(Double.toString(3.250));}
                    else{
                        checked=12;
                        obsInt.set(checked);}
                    }
                    });
                    water14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {if (buttonView.isChecked()) {
                        water14.setButtonDrawable(R.drawable.water);
                        water15.setButtonDrawable(R.drawable.add);
                        water15.setChecked(false);
                        checked+=1;
                        water15.setVisibility(View.VISIBLE);
                        litr.setText(Double.toString(3.50));}
                    else{checked=13;
                        obsInt.set(checked);}
                    }
                    });
                    water15.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {if (buttonView.isChecked()) {
                        water15.setButtonDrawable(R.drawable.water);
                        water16.setButtonDrawable(R.drawable.add);
                        water16.setChecked(false);
                        checked+=1;
                        water16.setVisibility(View.VISIBLE);
                        litr.setText(Double.toString(3.750));}
                    else{checked=14;
                        obsInt.set(checked);}
                    }
                    });
                    water16.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {
                        if (buttonView.isChecked()) {
                            water16.setButtonDrawable(R.drawable.water);
                            water17.setButtonDrawable(R.drawable.add);
                            water17.setChecked(false);
                            water17.setVisibility(View.VISIBLE);
                            checked+=1;
                            litr.setText(Double.toString(4.00));}
                        else{checked=15;
                            obsInt.set(checked);}
                    }});
                    water17.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {
                        if (buttonView.isChecked()) {
                            water17.setButtonDrawable(R.drawable.water);
                            water18.setButtonDrawable(R.drawable.add);
                            water18.setChecked(false);
                            water18.setVisibility(View.VISIBLE);
                            checked+=1;
                            litr.setText(Double.toString(4.250));}
                        else{checked=16;
                            obsInt.set(checked);}
                    }
                    });
                    water18.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {
                        if (buttonView.isChecked()) {
                            water18.setButtonDrawable(R.drawable.water);
                            water19.setButtonDrawable(R.drawable.add);
                            water19.setChecked(false);
                            water19.setVisibility(View.VISIBLE);
                            checked+=1;
                            litr.setText(Double.toString(4.50));}
                        else{checked=17;
                            obsInt.set(checked);}
                    }
                    });
                    water19.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {
                        if (buttonView.isChecked()) {
                            water19.setButtonDrawable(R.drawable.water);
                            water20.setButtonDrawable(R.drawable.add);
                            water20.setChecked(false);
                            water20.setVisibility(View.VISIBLE);
                            checked+=1;
                            litr.setText(Double.toString(4.750));}
                        else{checked=18;
                            obsInt.set(checked);}
                    }
                    });
                    water20.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {
                        if (buttonView.isChecked()) {
                            water20.setButtonDrawable(R.drawable.water);
                            water21.setButtonDrawable(R.drawable.add);
                            water21.setChecked(false);
                            water21.setVisibility(View.VISIBLE);
                            checked+=1;
                            litr.setText(Double.toString(5.00));}
                        else{checked=19;
                            obsInt.set(checked);}
                    }
                    });
                    water21.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {
                        if (buttonView.isChecked()) {
                            water21.setButtonDrawable(R.drawable.water);
                            water22.setButtonDrawable(R.drawable.add);
                            water22.setChecked(false);
                            water22.setVisibility(View.VISIBLE);
                            checked+=1;
                            litr.setText(Double.toString(5.250));}
                        else{checked=20;
                            obsInt.set(checked);}
                    }
                    });
                    water22.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {
                        if (buttonView.isChecked()) {
                            water22.setButtonDrawable(R.drawable.water);
                            water23.setButtonDrawable(R.drawable.add);
                            water23.setChecked(false);
                            water23.setVisibility(View.VISIBLE);
                            checked+=1;
                            litr.setText(Double.toString(5.50));}
                        else{checked=21;
                            obsInt.set(checked);}
                    }
                    });
                    water23.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {
                        if (buttonView.isChecked()) {
                            water23.setButtonDrawable(R.drawable.water);
                            water24.setButtonDrawable(R.drawable.add);
                            water24.setChecked(false);
                            water24.setVisibility(View.VISIBLE);
                            checked+=1;
                            litr.setText(Double.toString(5.750));}
                        else{checked=22;
                            obsInt.set(checked);}
                    }
                    });
                    water24.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {
                        if (buttonView.isChecked()) {
                            water24.setButtonDrawable(R.drawable.water);
                            checked+=1;
                            litr.setText(Double.toString(6.00));}
                        else{checked=23;
                            obsInt.set(checked);}
                    }
                    });
                }
            });
        }






        Bundle bundle = getIntent().getExtras();
        data = getApplicationContext().getSharedPreferences(DATA, Context.MODE_PRIVATE);
        if(bundle != null) {
            training_finished=bundle.getString("training");
        }
        if (training_finished!=null){
            Toast toast = makeText(getApplicationContext(),
                    training_finished,
                    Toast.LENGTH_SHORT);
           toast.setGravity(Gravity.CENTER, 0, 0);
           toast.show();
        }



        water_info=findViewById(R.id.water_info);
        water_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainPage.this, R.style.MyAlertTheme)
                        .setTitle(R.string.zechem_sledit)
                        .setMessage(R.string.why_water_norm)
                        .show();
            }
        });

        kal_info =findViewById(R.id.kal_info);
        kal_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainPage.this, R.style.MyAlertTheme)
                        .setTitle(R.string.zachem_sobludat)
                        .setMessage(R.string.why_kal_norm)
                        .show();
            }
        });


        food = findViewById(R.id.food);
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, Food_table.class);
                intent.putExtra("language", language);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });


        training=findViewById(R.id.press);
        training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, Select_training.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

    }
    public interface OnIntegerChangeListener
    {
        public void onIntegerChanged(int newValue);
    }

    public class ObservableInteger
    {
        private OnIntegerChangeListener listener;

        private int value;

        public void setOnIntegerChangeListener(OnIntegerChangeListener listener)
        {
            this.listener = listener;
        }

        public int get()
        {
            return value;
        }

        public void set(int value)
        {
            this.value = value;

            if(listener != null)
            {
                listener.onIntegerChanged(value);
            }
        }
    }

    @Override
    protected void onStop() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://strong-and-healthy-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("users");
        if (flag_logout){
            if (mAuth.getCurrentUser().getUid()!=null){
                String id = mAuth.getCurrentUser().getUid();
                myRef.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        User user = task.getResult().getValue(User.class);
                        user.setChecked_water(checked);
                        myRef.child(id).setValue(user);
                    }
                });
            }
        }
        super.onStop();
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
