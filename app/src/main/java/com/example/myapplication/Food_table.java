package com.example.myapplication;

import static android.widget.Toast.makeText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
import org.checkerframework.checker.units.qual.C;

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
    public static FirebaseDatabase database;
    public static DatabaseReference myRef;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;

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
            name.setText("  "+Names.get(i));
            name.setTextColor(getColor(R.color.black));
            name.setTextSize(40);
            linearLayout.addView(name);

            TextView kal = new TextView(context);
            kal.setLayoutParams(rowParams);
            kal.setText("  "+Double.toString(Kal.get(i)) +" ккал на 100 грамм");
            name.setTextSize(15);
            linearLayout.addView(kal);

            tableRow.addView(linearLayout);
            AppCompatButton button = new AppCompatButton(context);
            button.setText("Добавить");
            button.setTextColor(Color.WHITE);
            button.setWidth(300);
            button.setBackground(getDrawable(R.drawable.button_food));
            button.setGravity(Gravity.CENTER);
            button.setHeight(120);
            tableRow.addView(button);
            tableRow.setPadding(0,15,0,0);
            tableRow.setBackground(getResources().getDrawable(R.drawable.food_element));
            tableRow.setMinimumHeight(150);
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
                    AppCompatButton button = new AppCompatButton(context);
                    button.setText("Добавить");
                    button.setTextColor(Color.WHITE);
                    button.setWidth(300);
                    button.setBackground(getDrawable(R.drawable.button_food));
                    button.setGravity(Gravity.CENTER);
                    button.setHeight(120);
                    tableRow.addView(button);
                    tableRow.setPadding(0,15,10,0);
                    tableRow.setBackground(getResources().getDrawable(R.drawable.food_element));
                    tableRow.setMinimumHeight(150);

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
        Button photoButton = (Button) this.findViewById(R.id.scan);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(Food_table.this);
                scanIntegrator.initiateScan();
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            database = FirebaseDatabase.getInstance("https://strong-and-healthy-default-rtdb.europe-west1.firebasedatabase.app/");
            myRef = database.getReference("food");
            myRef.child(scanContent).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.getResult().exists()){
                        Food_element food = task.getResult().getValue(Food_element.class);
                        double[] b_g_u_kal_water = new double[]{food.getB(), food.getG(), food.getU(), food.getKal()};
                        Intent intent2 = new Intent(Food_table.this, Food_selected.class);
                        intent2.putExtra("1", b_g_u_kal_water);
                        intent2.putExtra("name", food.getName());
                        startActivity(intent2);
                    }
                    else{
                        Intent intent1 = new Intent(Food_table.this, Scanned_food.class);
                        intent1.putExtra("code", scanContent);
                        startActivity(intent1);
                    }
                }
            });
            }
        }
    }