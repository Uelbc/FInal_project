package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Select_training extends AppCompatActivity {
    private Button press, ruki, spina;
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
                        "Скручивания влево", "Скручивания вправо"
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

        ruki=findViewById(R.id.ruki);
        ruki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                training_names= new String[]{
                        "Махи руками по\nчасовой стрелке", "Махи руками против\nчасовой стрелки", "Подъем ноги бицесом\nлевой руки", "Прыжки без скакалки", "Подъем ноги бицесом\nправой руки",
                        "Скручивания лежа\nдля левой руки", "Берпи", "Скручивания лежа\nдля правой руки", "Отжимание сидя на полу", "Боковые удары по очереди",
                        "Военные отжимания",  "Сведение локтей", "Боковые удары по очереди", "Отжимание сидя на полу", "Берпи",
                        "Скручивание лежа\nдля левой руки", "Скручивания лежа\nдля правой руки", "Военные отжимания", "Сведение локтей", "Скручивания в проеме\nдля левой руки",
                        "Скручивания в проеме\nдля правой руки","Движения вверх-вниз\nсведенными локтями","Растяжка левого трицепса","Растяжка правого трицепса","Отжимания с поворотом",
                        "Растяжка левого бицепса","Растяжка правого бицепса"
                };
                training_videos = new int[]{
                        R.raw.mahi_rukami_po_chasovoy, R.raw.mahi_rukami_protiv_chasovoy, R.raw.podem_nogi_bicepsom_levoy_ruki, R.raw.prijki_bez_skakalki, R.raw.podem_nogi_bicepsom_pravoy_ruki,
                        R.raw.skruchivaniya_leja_dlya_levoy_ruki, R.raw.berpi, R.raw.skruchivaniya_leja_dlya_pravoy_ruki, R.raw.otjimanie_sidya_na_polu, R.raw.bokovie_udari_po_ocheredi,
                        R.raw.voennie_otjimaniya,  R.raw.svedenie_loktey, R.raw.bokovie_udari_po_ocheredi, R.raw.otjimanie_sidya_na_polu, R.raw.berpi,
                        R.raw.skruchivaniya_leja_dlya_levoy_ruki, R.raw.skruchivaniya_leja_dlya_pravoy_ruki, R.raw.voennie_otjimaniya, R.raw.svedenie_loktey, R.raw.skruchivaniya_v_proeme_levaya_ruka,
                        R.raw.skruchivaniya_v_proeme_pravaya_ruka, R.raw.dvijenie_vverh_vniz_svedennimi_loktyami, R.raw.rastyajka_levogo_trizepsa, R.raw.rastyajka_pravogo_trizepsa, R.raw.otjimaniya_s_povorotom,
                        R.raw.rastyajka_levogo_bizepsa, R.raw.rastyajka_pravogo_bizepsa
                };
                time_or_number=new boolean[]{
                        false, false, true, false, true,
                        true, true, true, true, false,
                        true, true, false, true, true,
                        true, true, true, true, true,
                        true, false, false, false, true,
                        false,false
                };
                amount=new int[]{
                        30, 30, 16, 30, 16,
                        14, 16, 14, 18, 30,
                        14, 16, 30, 16, 16,
                        12, 12, 12, 16,  8,
                         8, 18, 30, 30, 12,
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
        spina=findViewById(R.id.spina);
        spina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                training_names= new String[]{
                        "Прыжки", "Отжимание назад", "Гиперэкстензии", "V-отжимания на полу", "Гусеница",
                        "Растяжка на полу влево", "Растяжка на полу вправо", "Гиперэкстензии", "V-отжимания на полу", "Отжимание назад",
                        "гусеница",  "Поза кошки - поза коровы", "Отжимания на спине", "Y-подъемы", "Отжимания на спине",
                        "Обратный снежный ангел", "Поза ребенка"
                };
                training_videos = new int[]{
                        R.raw.prijki, R.raw.otjimaniya_nazad, R.raw.gyperekstenzii, R.raw.v_otjimaniya_na_polu, R.raw.gusenitsa,
                        R.raw.rastyajka_na_polu_vlevo, R.raw.rastyajka_na_polu_vpravo, R.raw.gyperekstenzii, R.raw.v_otjimaniya_na_polu, R.raw.otjimaniya_nazad,
                        R.raw.gusenitsa, R.raw.poza_koshki_poza_korovi, R.raw.otjimaniya_na_spine, R.raw.y_podemy, R.raw.otjimaniya_na_spine,
                        R.raw.obratniy_snejniy_angel, R.raw.poza_rebenka
                };
                time_or_number=new boolean[]{
                        false, true, true, true, true,
                        false,false,true,true,true,
                        true,false,true,true,true,
                        true,false
                };
                amount=new int[]{
                        30, 12, 14, 14, 16,
                        30, 30, 12, 12, 10,
                        14, 30, 14, 14, 12,
                        12, 30
                };

                Intent intent = new Intent(Select_training.this, Training.class);
                intent.putExtra("training_videos", training_videos);
                intent.putExtra("training_names", training_names);
                intent.putExtra("time_or_number",time_or_number);
                intent.putExtra("amount", amount);
                startActivity(intent);
            }
        });

    }
}