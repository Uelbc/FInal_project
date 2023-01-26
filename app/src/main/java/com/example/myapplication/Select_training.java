package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Select_training extends AppCompatActivity {
    private Button press;
    String[] training_names;
    int[] training_videos, amount;
    boolean[] time_or_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_training);

        press=findViewById(R.id.press);
        press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                training_names= new String[]{
                        "Упражнение для пресса", "Подъем корпуса", "Боковой мостик (влево)", "Боковой мостик (вправо)", "Упражнение для пресса",
                        "Велосипед", "Планка с правого бока", "Планка с левого бока", "Уголок", "Отжимания с поворотом",
                        "Качели",  "Ягодичный мостик", "Боковые наклоны к стопам", "Альпинист",
                        "Скручивание с поворотом", "Уголок", "Планка", "Растяжка кобра",
                        "Скручивания в пояснице лежа на полу влево", "Скручивания в пояснице лежа на полу вправо"
                };
                training_videos = new int[]{
                        R.raw.uprajnenie_dlya_pressa, R.raw.podyom_korpusa, R.raw.bokovoy_mostik_vlevo, R.raw.bokovoy_mostik_vpravo, R.raw.podyom_korpusa,
                        R.raw.velosiped, R.raw.planka_s_pravogo_boka, R.raw.planka_s_levogo_boka, R.raw.ugolok, R.raw.otjimaniya_s_povorotom,
                        R.raw.kachely,  R.raw.yagodichniy_mostik, R.raw.bokovie_naklony_k_stopam, R.raw.alpinist,
                        R.raw.skruchivanie_s_povorotom, R.raw.ugolok, R.raw.planka, R.raw.rastyajka_kobra,
                        R.raw.skruchivaniya_v_poyasnize_vlevo, R.raw.skruchivaniya_v_poyasnize_vpravo
                };
                time_or_number=new boolean[]{
                        true, true, true, true, true,
                        true, false, false, true, true,
                        true, true, true, true,
                        true, true, false, false,
                        false, false
                };
                amount=new int[]{
                        28, 20, 20, 20, 30,
                        24, 20, 20, 18, 24,
                        48, 30, 34, 30,
                        24, 16, 60, 30,
                        30, 30
                };

                Intent intent = new Intent(Select_training.this, Training.class);
                intent.putExtra("training_videos", training_videos);
                intent.putExtra("training_names", training_names);
                intent.putExtra("time_or_number",time_or_number);
                intent.putExtra("amount", amount);
                startActivity(intent);
                // массив из булов в котором тру это значит разы фолс значит время
                // в зависимости от этого массива смотрим на элемент в массиве с количеством и временем и устанавливаем нужное значениме
            }
        });
    }
}