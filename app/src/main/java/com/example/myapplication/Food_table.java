package com.example.myapplication;

import static android.widget.Toast.makeText;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class Food_table extends AppCompatActivity {
    List<String> Names = new ArrayList<String>();
    List<Double> Water = new ArrayList<Double>();
    List<Double> B = new ArrayList<Double>();
    List<Double> G = new ArrayList<Double>();
    List<Double> U = new ArrayList<Double>();
    List<Double> Kal = new ArrayList<Double>();
    TableLayout Table;
    EditText search;
    TextView textView;
    Context context;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_table);
        String fileName = "kalorii.xls";
        try {
            InputStream myInput;
            AssetManager assetManager = getAssets();
            myInput = getResources().openRawResource(R.raw.kalorii);
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator<Row> rowIter = mySheet.rowIterator();
            int rowno =0;
            while (rowIter.hasNext()) {
                HSSFRow myRow = (HSSFRow) rowIter.next();
                if(rowno !=0) {
                    Iterator<Cell> cellIter = myRow.cellIterator();
                    int colno =0;
                    while (cellIter.hasNext()) {
                        HSSFCell cell = (HSSFCell) cellIter.next();
                        if (cell.getColumnIndex()==0){
                            Names.add(cell.getStringCellValue());
                        }
                        if (cell.getColumnIndex()==1){
                            Water.add(cell.getNumericCellValue());
                        }
                        if (cell.getColumnIndex()==2){
                            B.add(cell.getNumericCellValue());
                        }
                        if (cell.getColumnIndex()==3){
                            G.add(cell.getNumericCellValue());
                        }
                        if (cell.getColumnIndex()==4){
                            U.add(cell.getNumericCellValue());
                        }
                        if (cell.getColumnIndex()==5){
                            Kal.add(cell.getNumericCellValue());
                        }
                        colno++;
                        }
                    }
                  rowno++;
                }
            } catch (Exception e) {}
        context = getApplicationContext();


        Table = findViewById(R.id.Table);
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        Table.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        search=findViewById(R.id.Search);
        for (int i = 0; i<Names.size(); i++){
            TableRow tableRow = new TableRow(context);
            tableRow.setLayoutParams(tableParams);

            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            TextView name = new TextView(context);
            name.setLayoutParams(rowParams);
            name.setText(" "+Names.get(i));
            name.setTextColor(getColor(R.color.black));
            name.setTextSize(40);
            linearLayout.addView(name);

            TextView kal = new TextView(context);
            kal.setLayoutParams(rowParams);
            kal.setText(" "+Double.toString(Kal.get(i)) +" ккал на 100 грамм");
            name.setTextSize(15);
            linearLayout.addView(kal);

            tableRow.addView(linearLayout);


            Table.addView(tableRow);


            int finalI = i;
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    double [] b_g_u_kal_water = new double[] {B.get(finalI), G.get(finalI), U.get(finalI), Kal.get(finalI), Water.get(finalI)};
                    Intent intent = new Intent(Food_table.this, Food_selected.class);
                    intent.putExtra("1", b_g_u_kal_water);
                    intent.putExtra("name", Names.get(finalI));
                    startActivity(intent);
                }
            });
        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int j, int j1, int j2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int j, int j1, int j2) {
                Table.removeAllViews();
                String key = search.getText().toString();
                for (int i = 0; i < Names.size(); i++) {
                    TableRow tableRow = new TableRow(context);
                    tableRow.setLayoutParams(tableParams);

                    LinearLayout linearLayout = new LinearLayout(context);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    TextView name = new TextView(context);
                    name.setLayoutParams(rowParams);
                    name.setText(" " + Names.get(i));
                    name.setTextColor(getColor(R.color.black));
                    name.setTextSize(40);
                    linearLayout.addView(name);

                    TextView kal = new TextView(context);
                    kal.setLayoutParams(rowParams);
                    kal.setText(" " + Double.toString(Kal.get(i)) + " ккал на 100 грамм");
                    name.setTextSize(15);
                    linearLayout.addView(kal);

                    tableRow.addView(linearLayout);

                    if (Names.get(i).toLowerCase(Locale.ROOT).contains(key.toLowerCase(Locale.ROOT)) || key == null) {
                        Table.addView(tableRow);
                        int finalI = i;
                        tableRow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                double[] b_g_u_kal_water = new double[]{B.get(finalI), G.get(finalI), U.get(finalI), Kal.get(finalI), Water.get(finalI)};
                                Intent intent = new Intent(Food_table.this, Food_selected.class);
                                intent.putExtra("1", b_g_u_kal_water);
                                intent.putExtra("name", Names.get(finalI));
                                startActivity(intent);
                            }
                        });
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
    }
}