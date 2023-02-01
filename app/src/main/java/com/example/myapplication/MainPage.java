package com.example.myapplication;

import static android.widget.Toast.makeText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.Observable;

public class MainPage extends AppCompatActivity {
    public static final String DATA = "DATA";
    double [] weight_height_age_gender_A;
    double [] b_g_u_kal;
    public TextView kal_eaten_per_day, bTV, gTV, uTV, litr;
    public Button food, training;
    public String message_to_food, training_finished;
    public double gender_final;
    public double Kal_final=0;
    public ImageButton water_info, kal_info;
    int checked;
    double b=0, g=0, u=0, b_norm=0, g_norm=0, u_norm=0;
    public String kal_per_day_men="", kal_per_day_women="";
    private ProgressBar b_progress_bar, g_progress_bar, u_progress_bar, kal_progress_bar;
    private CheckBox water1, water2, water3, water4, water5, water6, water7, water8, water9, water10, water11, water12,
            water13, water14, water15, water16, water17, water18, water19, water20, water21, water22, water23, water24;
    CheckBox[] checkBoxes;
    private int Len_check_boxes;
    SharedPreferences data;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle bundle = getIntent().getExtras();
        data = getApplicationContext().getSharedPreferences(DATA, Context.MODE_PRIVATE);
        if(bundle != null) {
            weight_height_age_gender_A = bundle.getDoubleArray("2");
            b_g_u_kal=bundle.getDoubleArray("b_g_u_kal");
            training_finished=bundle.getString("training");
        }
        if (training_finished!=null){
            Toast toast = makeText(getApplicationContext(),
                    training_finished,
                    Toast.LENGTH_SHORT);
           toast.setGravity(Gravity.CENTER, 0, 0);
           toast.show();
            b=data.getLong("b", 0);
            g=data.getLong("g", 0);
            u=data.getLong("u", 0);
            Kal_final = data.getLong("kal_final", 0);
        }
        if (weight_height_age_gender_A!=null){
            double weight = weight_height_age_gender_A[0];
            double height = weight_height_age_gender_A[1];
            double age = weight_height_age_gender_A[2];
            double gender = weight_height_age_gender_A[3];
            gender_final=weight_height_age_gender_A[3];// 1-мужчина 0-женщина
            double A= weight_height_age_gender_A[4];
            if (gender==1){//если мужчина
                kal_per_day_men= Integer.toString((int)((10*weight+6.25*height-5*age+5)*A));
                SharedPreferences.Editor e = data.edit();
                e.putString("kal_per_day", kal_per_day_men);
                e.apply();
            }else{
                kal_per_day_women= Integer.toString((int)((10*weight+6.25*height-5*age-161)*A));
                SharedPreferences.Editor e = data.edit();
                e.putString("kal_per_day", kal_per_day_women);
                e.apply();
            }
            SharedPreferences.Editor e = data.edit();
            e.putInt("water", 0);
            e.apply();
        }
        if (b_g_u_kal!= null){
            b=data.getLong("b", 0);
            g=data.getLong("g", 0);
            u=data.getLong("u", 0);
            Kal_final = data.getLong("kal_final", 0);

            b+=b_g_u_kal[0];
            g+=b_g_u_kal[1];
            u+=b_g_u_kal[2];
            Kal_final+=b_g_u_kal[3];
        }
        kal_eaten_per_day=findViewById(R.id.kal_eaten_per_day);
        bTV=findViewById(R.id.b);
        gTV=findViewById(R.id.g);
        uTV=findViewById(R.id.u);
        bTV.setText(String.format("%.1f", b)+" / "+(int) Double.parseDouble(data.getString("kal_per_day", "")) * 30 / 100 / 4 +" г");
        gTV.setText(String.format("%.1f", g)+" / "+(int) Double.parseDouble(data.getString("kal_per_day", "")) * 25 / 100 / 9 +" г");
        uTV.setText(String.format("%.1f", u)+" / "+(int) Double.parseDouble(data.getString("kal_per_day", "")) * 45 / 100 / 4 +" г");
        kal_eaten_per_day.setText((int) Kal_final +" / "+data.getString("kal_per_day", ""));

        b_progress_bar=findViewById(R.id.b_progress_bar);
        g_progress_bar=findViewById(R.id.g_progress_bar);
        u_progress_bar=findViewById(R.id.u_progress_bar);
        kal_progress_bar=findViewById(R.id.kal_progress_bar);

        b_progress_bar.setMax((int) Double.parseDouble(data.getString("kal_per_day", "")) * 30 / 100 / 4);
        g_progress_bar.setMax((int) Double.parseDouble(data.getString("kal_per_day", "")) * 25 / 100 / 9);
        u_progress_bar.setMax((int) Double.parseDouble(data.getString("kal_per_day", "")) * 45 / 100 / 4);
        kal_progress_bar.setMax(Integer.parseInt(data.getString("kal_per_day", "")));

        b_progress_bar.setProgress((int)b);
        g_progress_bar.setProgress((int)g);
        u_progress_bar.setProgress((int)u);
        kal_progress_bar.setProgress((int)Kal_final);
        if (Kal_final>Integer.parseInt(data.getString("kal_per_day", ""))){
            kal_progress_bar.getProgressDrawable().setColorFilter(
                    Color.RED, PorterDuff.Mode.SRC_IN);
            kal_eaten_per_day.setText("Свыше нормы на "+(int)(Kal_final-Integer.parseInt(data.getString("kal_per_day", ""))));
        }

        water1=findViewById(R.id.water1); water2=findViewById(R.id.water2); water3=findViewById(R.id.water3); water4=findViewById(R.id.water4);
        water5=findViewById(R.id.water5); water6=findViewById(R.id.water6); water7=findViewById(R.id.water7); water8=findViewById(R.id.water8);
        water9=findViewById(R.id.water9); water10=findViewById(R.id.water10); water11=findViewById(R.id.water11); water12=findViewById(R.id.water12);
        water13=findViewById(R.id.water13); water14=findViewById(R.id.water14); water15=findViewById(R.id.water15); water16=findViewById(R.id.water16);
        water17=findViewById(R.id.water17); water18=findViewById(R.id.water18); water19=findViewById(R.id.water19); water20=findViewById(R.id.water20);
        water21=findViewById(R.id.water21); water22=findViewById(R.id.water22); water23=findViewById(R.id.water23); water24=findViewById(R.id.water24);
        checkBoxes= new CheckBox[]{water1, water2, water3, water4, water5, water6, water7, water8, water9, water10, water11, water12,
                water13, water14, water15, water16, water17, water18, water19, water20, water21, water22, water23, water24};
        Len_check_boxes=data.getInt("water", 0);
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
                litr.setText(Double.toString(4.00)+" л");}
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
                litr.setText(Double.toString(4.250)+" л");}
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
                litr.setText(Double.toString(4.50)+" л");}
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
                litr.setText(Double.toString(4.750)+" л");}
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
                litr.setText(Double.toString(5.00)+" л");}
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
                litr.setText(Double.toString(5.250)+" л");}
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
                litr.setText(Double.toString(5.50)+" л");}
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
                litr.setText(Double.toString(5.750)+" л");}
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
                litr.setText(Double.toString(6.00)+" л");}
            else{checked=23;
                obsInt.set(checked);}
        }
        });
        water_info=findViewById(R.id.water_info);
        water_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainPage.this)
                        .setTitle("Зачем нужно следить за колличеством выпитой воды?")
                        .setMessage("Are you sure you want to delete this entry?")
                        .show();
            }
        });

        kal_info =findViewById(R.id.kal_info);
        kal_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainPage.this)
                        .setTitle("Зачем нужно следить за колличеством съеденных калорий?")
                        .setMessage("Are you sure you want to delete this entry?")
                        .show();
            }
        });



        food = findViewById(R.id.food);
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor e = data.edit();
                e.putLong("kal_final", (long) Kal_final);
                e.putLong("b", (long)b);
                e.putLong("g", (long)g);
                e.putLong("u", (long)u);
                e.putInt("water", checked);
                e.apply();
                message_to_food="";
                Intent intent = new Intent(MainPage.this, Food.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("From main menu", message_to_food);
                startActivity(intent);
            }
        });


        training=findViewById(R.id.press);
        training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor e = data.edit();
                e.putLong("kal_final", (long) Kal_final);
                e.putLong("b", (long)b);
                e.putLong("g", (long)g);
                e.putLong("u", (long)u);
                e.putInt("water", checked);
                e.apply();
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
}
