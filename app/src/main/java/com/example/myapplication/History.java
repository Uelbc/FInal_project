package com.example.myapplication;

import static android.widget.Toast.makeText;
import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarDay;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class History extends AppCompatActivity {
    CalendarView calendarView;
    public FirebaseAuth mAuth;
    public static FirebaseDatabase database;
    public static DatabaseReference myRef;
    List<String> history;
    ImageButton history_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        history_info=findViewById(R.id.history_info);
        history_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(History.this, R.style.MyAlertTheme)
                        .setTitle("Календарь активности")
                        .setMessage(R.string.why_history)
                        .show();
            }
        });
        calendarView=findViewById(R.id.history);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://strong-and-healthy-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("users");
        if (mAuth.getCurrentUser().getUid()!=null){
            String id = mAuth.getCurrentUser().getUid();
            myRef.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    User user = task.getResult().getValue(User.class);
                    history=user.getHistory();
                    List<EventDay> events = new ArrayList<>();
                    if (history!=null){
                        for (int i=1; i<history.size(); i+=1){
                            String s = history.get(i);
                            String[] Date_check = s.split(" ");
                            String Date=Date_check[0];
                            String check=Date_check[1];
                            String[] d_m_y = Date.split("-");
                            String d = d_m_y[0];
                            String m = d_m_y[1];
                            String y = d_m_y[2];
                            Calendar event= Calendar.getInstance();
                            event.set(Integer.parseInt(y), Integer.parseInt(m)-1, Integer.parseInt(d));
                            if (check.equals("1")){
                                events.add(new EventDay(event, R.color.green));
                            }
                            else{
                                events.add(new EventDay(event, R.color.red));
                            }
                        }
                    }
                    calendarView.setEvents(events);
                }
            });
        }
    }
}