package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Select_training extends AppCompatActivity {
    private Button press, ruki, spina, nogi, vse;
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
                        getString(R.string.press1), getString(R.string.press2), getString(R.string.press3), getString(R.string.press4), getString(R.string.press5),
                        getString(R.string.press6), getString(R.string.press7), getString(R.string.press8), getString(R.string.press9), getString(R.string.press10),
                        getString(R.string.press11),  getString(R.string.press12), getString(R.string.press13), getString(R.string.press14),
                        getString(R.string.press15), getString(R.string.press16), getString(R.string.press17), getString(R.string.press18),
                        getString(R.string.press19), getString(R.string.press20)
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
                        getString(R.string.ruki1), getString(R.string.ruki2), getString(R.string.ruki3), getString(R.string.ruki4), getString(R.string.ruki5),
                        getString(R.string.ruki6), getString(R.string.ruki7), getString(R.string.ruki8), getString(R.string.ruki9), getString(R.string.ruki10),
                        getString(R.string.ruki11),  getString(R.string.ruki12), getString(R.string.ruki13), getString(R.string.ruki14), getString(R.string.ruki15),
                        getString(R.string.ruki16), getString(R.string.ruki17), getString(R.string.ruki18), getString(R.string.ruki19), getString(R.string.ruki20),
                        getString(R.string.ruki21),getString(R.string.ruki22),getString(R.string.ruki23),getString(R.string.ruki24),getString(R.string.ruki25),
                        getString(R.string.ruki26),getString(R.string.ruki27)
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
                        getString(R.string.spina1), getString(R.string.spina2), getString(R.string.spina3), getString(R.string.spina4), getString(R.string.spina5),
                        getString(R.string.spina6), getString(R.string.spina7), getString(R.string.spina8), getString(R.string.spina9), getString(R.string.spina10),
                        getString(R.string.spina11),  getString(R.string.spina12), getString(R.string.spina13), getString(R.string.spina14), getString(R.string.spina15),
                        getString(R.string.spina16), getString(R.string.spina17)
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

        nogi=findViewById(R.id.nogi);
        nogi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                training_names= new String[]{
                        getString(R.string.nogi1), getString(R.string.nogi2), getString(R.string.nogi3), getString(R.string.nogi4), getString(R.string.nogi5),
                        getString(R.string.nogi6), getString(R.string.nogi7), getString(R.string.nogi8), getString(R.string.nogi9), getString(R.string.nogi10),
                        getString(R.string.nogi11),  getString(R.string.nogi12), getString(R.string.nogi13), getString(R.string.nogi14), getString(R.string.nogi15),
                        getString(R.string.nogi16), getString(R.string.nogi17)
                };
                training_videos = new int[]{
                        R.raw.berpi, R.raw.prisedaniya, R.raw.prijki, R.raw.podem_nogi_levaya, R.raw.podem_nogi_pravaya,
                        R.raw.prisedaniya_vipadi, R.raw.krugi_lega_na_boku_levaya, R.raw.prisedaniya_vipadi, R.raw.krugi_lega_na_boku_pravaya, R.raw.prijki_na_kortochkah,
                        R.raw.otvedenie_nog_na_chetverenkah_pravaya, R.raw.otvedenie_nog_na_chetverenkah_levaya, R.raw.prisedaniya_u_steni, R.raw.rastyajka_xhetirex_mijzi_levaya, R.raw.rastyajka_xhetirex_mijzi_pravaya,
                        R.raw.podem_na_nosok_levoy_nogi, R.raw.podem_na_nosok_pravoy_nogi
                };
                time_or_number=new boolean[]{
                        true, true, false, true, true,
                        true,true,true,true,true,
                        true,true,false,false,false,
                        true,true
                };
                amount=new int[]{
                        10, 14, 30, 12, 12,
                        14, 12, 14, 12, 14,
                        12, 12, 40, 30, 30,
                        16, 16
                };

                Intent intent = new Intent(Select_training.this, Training.class);
                intent.putExtra("training_videos", training_videos);
                intent.putExtra("training_names", training_names);
                intent.putExtra("time_or_number",time_or_number);
                intent.putExtra("amount", amount);
                startActivity(intent);
            }
        });

        vse=findViewById(R.id.vse);
        vse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                training_names= new String[]{
                        getString(R.string.vse1), getString(R.string.vse2), getString(R.string.vse3), getString(R.string.vse4), getString(R.string.vse5),
                        getString(R.string.vse6), getString(R.string.vse7), getString(R.string.vse8), getString(R.string.vse9), getString(R.string.vse10),
                        getString(R.string.vse11),  getString(R.string.vse12), getString(R.string.vse13), getString(R.string.vse14), getString(R.string.vse15),
                        getString(R.string.vse16), getString(R.string.vse17)
                };
                training_videos = new int[]{
                        R.raw.prijki, R.raw.voennie_otjimaniya, R.raw.planka, R.raw.v_otjimaniya_na_polu, R.raw.gusenitsa,
                        R.raw.rastyajka_na_polu_vlevo, R.raw.rastyajka_na_polu_vpravo, R.raw.gyperekstenzii, R.raw.prisedaniya, R.raw.ugolok,
                        R.raw.prijki_na_kortochkah, R.raw.poza_koshki_poza_korovi, R.raw.rastyajka_xhetirex_mijzi_levaya, R.raw.rastyajka_xhetirex_mijzi_pravaya, R.raw.rastyajka_levogo_bizepsa,
                        R.raw.rastyajka_pravogo_bizepsa, R.raw.prijki
                };
                time_or_number=new boolean[]{
                        false, true, false, true, true,
                        false,false,true,true,true,
                        true,false,false,false,false,
                        false,false
                };
                amount=new int[]{
                        30, 14, 60, 14, 16,
                        30, 30, 12, 16, 16,
                        14, 30, 30, 30, 30,
                        30, 30
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