package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class Training extends AppCompatActivity {
    String[] training_names;
    int[] training_videos, amount;
    boolean[] time_or_number;
    private VideoView TrainingVideo;
    int i=0;
    private Button nextTask;
    public CountDownTimer timer;
    private ImageButton start;
    private TextView Time, Number, Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            training_names=bundle.getStringArray("training_names");
            training_videos=bundle.getIntArray("training_videos");
            amount=bundle.getIntArray("amount");
            time_or_number=bundle.getBooleanArray("time_or_number");
        }

        Time=findViewById(R.id.КоличествоРазИлиВремя);
        Name=findViewById(R.id.Название);
        Number=findViewById(R.id.НомерУпражрнения);
        nextTask=findViewById(R.id.nextTask);
        start=findViewById(R.id.start);

        Name.setText(training_names[i]);
        Number.setText("№: "+ (i+1));
        TrainingVideo=findViewById(R.id.ВидеоУпражнение);
        TrainingVideo.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+training_videos[i]));
        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(View.GONE);
        TrainingVideo.setMediaController(mediaController);
        TrainingVideo.start();
        TrainingVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                TrainingVideo.start();
            }
        });
        if (time_or_number[i]){
            //разы
            start.setVisibility(View.GONE);
            Time.setText("x"+amount[i]);
        } else{
            // время
            Time.setText(amount[i]+" c");
            start.setVisibility(View.VISIBLE);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    start.setVisibility(View.GONE);
                    timer = new CountDownTimer(amount[i]*1000, 1000){
                        @Override
                        public void onTick(long l) {
                            Time.setText(""+l/1000+" c");
                        }

                        @Override
                        public void onFinish() {
                        }
                    }.start();
                }
            });
        }

        nextTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer!=null){
                    timer.cancel();
                }
                i+=1;
                if (i<training_names.length){
                    Name.setText(training_names[i]);
                    Number.setText("№: "+ (i+1));
                    TrainingVideo=findViewById(R.id.ВидеоУпражнение);
                    TrainingVideo.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+training_videos[i]));
                    TrainingVideo.start();
                    TrainingVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            TrainingVideo.start();
                        }
                    });
                    if (time_or_number[i]){
                        //разы
                        start.setVisibility(View.GONE);
                        Time.setText("x"+amount[i]);
                    } else{
                        // время
                        Time.setText(amount[i]+" c");
                        start.setVisibility(View.VISIBLE);
                        start.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                start.setVisibility(View.GONE);
                                timer = new CountDownTimer(amount[i]*1000, 1000){
                                    @Override
                                    public void onTick(long l) {
                                        Time.setText(""+l/1000+" c");
                                    }

                                    @Override
                                    public void onFinish() {
                                    }
                                }.start();
                            }
                        });

                    }
                } else{
                    Intent intent = new Intent(Training.this, MainPage.class);
                    intent.putExtra("training", "Поздравляем! Хорошая тренировка!");
                    startActivity(intent);
                }

            }
        });
    }

}