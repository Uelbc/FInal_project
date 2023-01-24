package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class Food_selected extends AppCompatActivity {
    double[] b_g_u_kal;
    double b, g, u, kal;
    public TextView bel, gir, ugl, kalor, name;
    public EditText massa;
    public Button add;
    public VideoView videoView;
    String Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_selected);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            b_g_u_kal = bundle.getDoubleArray("1");
            Name = bundle.getString("name");
        }
        b=b_g_u_kal[0];
        g=b_g_u_kal[1];
        u=b_g_u_kal[2];
        kal=b_g_u_kal[3];
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

        name=findViewById(R.id.name);
        name.setText(Name);
        massa = findViewById(R.id.massa);
        // добавить проверку вводимого текста

        add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double m = Double.parseDouble(massa.getText().toString());
                double [] b_g_u_kal = new double[] {b/100*m, g/100*m, u/100*m, kal/100*m};
                Intent intent = new Intent(Food_selected.this, MainPage.class);
                intent.putExtra("b_g_u_kal", b_g_u_kal);
                startActivity(intent);
            }
        });
        videoView=findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.alp));
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);

        mediaController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        videoView.start();
    }
}