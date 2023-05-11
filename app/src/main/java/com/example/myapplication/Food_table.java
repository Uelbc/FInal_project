package com.example.myapplication;

import static android.widget.Toast.makeText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

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

public class Food_table extends AppCompatActivity  {
    List<String> Names = new ArrayList<String>();
    List<Double> Water = new ArrayList<Double>();
    List<Double> B = new ArrayList<Double>();
    List<Double> G = new ArrayList<Double>();
    List<Double> U = new ArrayList<Double>();
    List<Double> Kal = new ArrayList<Double>();
    List<Food_element> food_elements= new ArrayList<Food_element>();
    TableLayout Table;
    EditText search;
    TextView textView;
    Context context;
    RecyclerView recyclerView;
    public static FirebaseDatabase database;
    public static DatabaseReference myRef;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    FoodListAdapter foodListAdapter;
    public FirebaseAuth mAuth;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_table);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://strong-and-healthy-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("users");
        String id = mAuth.getCurrentUser().getUid();
        myRef.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                User user = task.getResult().getValue(User.class);
                try {
                    InputStream myInput;
                    AssetManager assetManager = getAssets();
                    if (user.language.equals("")){
                        myInput = getResources().openRawResource(R.raw.kalorii);
                    }
                    else {
                        myInput = getResources().openRawResource(R.raw.kalorii_en);
                    }
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
                            String name = null;
                            Double water=null, b=null,g=null,u=null,kal=null;
                            while (cellIter.hasNext()) {
                                HSSFCell cell = (HSSFCell) cellIter.next();
                                if (colno==0){
                                    name=cell.getStringCellValue();
                                }
                                if (colno==1){
                                    water=cell.getNumericCellValue();
                                }
                                if (colno==2){
                                    b=cell.getNumericCellValue();
                                }
                                if (colno==3){
                                    g=cell.getNumericCellValue();
                                }
                                if (colno==4){
                                    Log.w("RRR", "1");
                                    u=cell.getNumericCellValue();
                                }
                                if (colno==5){
                                    kal=cell.getNumericCellValue();
                                    Food_element food_element=new Food_element(name, kal.intValue(), b,g,u);
                                    food_elements.add(food_element);
                                }
                                colno++;
                            }
                        }
                        rowno++;
                    }
                } catch (Exception e) {}
                context = getApplicationContext();
                foodListAdapter = new FoodListAdapter(context, food_elements);
                recyclerView=findViewById(R.id.food_list);
                recyclerView.setAdapter(foodListAdapter);
            }
        });

        search=findViewById(R.id.Search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int j, int j1, int j2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int j, int j1, int j2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                String key = search.getText().toString();
                List<Food_element> changing_list=new ArrayList<Food_element>();
                for (int i=0; i<food_elements.size(); i++){
                    if (food_elements.get(i).getName().toLowerCase(Locale.ROOT).contains(key.toLowerCase(Locale.ROOT)) || key == null){
                        changing_list.add(food_elements.get(i));
                    }
                }

                foodListAdapter = new FoodListAdapter(context, changing_list);
                recyclerView.setAdapter(foodListAdapter);

            }
        });



        Button photoButton = (Button) this.findViewById(R.id.scan);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(Food_table.this);
                integrator.setPrompt("Разместите штрих-код в указанной области");
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.setOrientationLocked(true);
                integrator.setBeepEnabled(true);
                integrator.setCaptureActivity(CaptureActivityPortrait.class);
                integrator.initiateScan();
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
                        if (scanContent.length()!=13){
                            Toast toast = makeText(getApplicationContext(),
                                    "Некорректный штрих-код",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        else {
                            Intent intent1 = new Intent(Food_table.this, Scanned_food.class);
                            intent1.putExtra("code", scanContent);
                            startActivity(intent1);
                        }
                    }
                }
            });
            }
        }


}

