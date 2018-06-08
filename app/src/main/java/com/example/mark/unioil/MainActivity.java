package com.example.mark.unioil;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import static com.example.mark.unioil.SQLiteHelper.DR_NUMBER;

public class MainActivity extends AppCompatActivity {

    public static final int requestcode = 1;
    private AppCompatEditText etDrNumber;
    private AppCompatEditText etUserName;
    private AppCompatEditText etCustomerName;
    private AppCompatButton   btnProceed;
    private CoordinatorLayout coordinatorLayout;
    private SQLiteHelper sqlite;
    private SQLiteDatabase dbReader;
    private SQLiteDatabase dbWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Delivery Receipt");
        init();

        etDrNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!etDrNumber.getText().toString().equals("") &&
                        !etUserName.getText().toString().equals("") &&
                        !etCustomerName.getText().toString().equals("")){
                    btnProceed.setEnabled(true);
                    btnProceed.setText("Proceed to Signature");
                } else {
                    btnProceed.setEnabled(false);
                    btnProceed.setText("Fill all Fields!");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!etDrNumber.getText().toString().equals("") &&
                        !etUserName.getText().toString().equals("") &&
                        !etCustomerName.getText().toString().equals("")){
                    btnProceed.setEnabled(true);
                    btnProceed.setText("Proceed to Signature");
                } else {
                    btnProceed.setEnabled(false);
                    btnProceed.setText("Fill all Fields!");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        etCustomerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!etDrNumber.getText().toString().equals("") &&
                        !etUserName.getText().toString().equals("") &&
                        !etCustomerName.getText().toString().equals("")){
                    btnProceed.setEnabled(true);
                    btnProceed.setText("Proceed to Signature");
                } else {
                    btnProceed.setEnabled(false);
                    btnProceed.setText("Fill all Fields!");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignatureActivity.class);
                try{
                    FileReader file = new FileReader(Environment.getExternalStorageDirectory()+ "/Download/unioil_data.txt");
                    BufferedReader bfr = new BufferedReader(file);
                    String line;
                    int nCount = 0;
                    while ((line = bfr.readLine()) != null) {
                        nCount++;
                        if(line.equals(etDrNumber.getText().toString())){
                            Snackbar.make(coordinatorLayout,"FOUND!",Snackbar.LENGTH_SHORT).show();
                            writeOutput();
                            intent.putExtra("DRNUMBER",etDrNumber.getText().toString());
                            intent.putExtra("USERNAME",etUserName.getText().toString());
                            intent.putExtra("CUSTOMER",etCustomerName.getText().toString());
                            startActivity(intent);
                            break;
                        }
                        else if(!line.equals(etDrNumber.getText().toString())){
                            Snackbar.make(coordinatorLayout,"DR number not found!",Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    bfr.close();
                }catch (Exception e){
                    Log.d("Error: ", e.toString());
                }

            }
        });
    }

    private void writeOutput() {
        try {
            File sdCardDir = Environment.getExternalStorageDirectory();
            String filename = "output.txt"; // the name of the file to export with
            File saveFile = new File(sdCardDir, filename);
            FileWriter fw = new FileWriter(saveFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(etDrNumber.getText().toString()).append(",").append(etUserName.getText().toString()).append(",").append(etCustomerName.getText().toString()).append("\n");

            bw.close();

        } catch (Exception e){
            Log.d("Error in writeOutput: ",e.toString());
        }

    }

    private void init() {
        etDrNumber = (AppCompatEditText) findViewById(R.id.etDrNumber);
        etUserName = (AppCompatEditText) findViewById(R.id.etUserName);
        etCustomerName = (AppCompatEditText) findViewById(R.id.etCustomerName);
        btnProceed = (AppCompatButton) findViewById(R.id.btnProceed);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);
        sqlite = new SQLiteHelper(this);
        dbReader = sqlite.getReadableDatabase();
        dbWriter = sqlite.getWritableDatabase();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_import:
                importProduct();
                return true;
            case R.id.action_clear:
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void importProduct() {
        Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        fileIntent.setType("gagt/sdf");
        try {
            startActivityForResult(fileIntent, requestcode);
        } catch (ActivityNotFoundException e) {
            Snackbar.make(coordinatorLayout,"Failed to import", Snackbar.LENGTH_SHORT).show();
//            Toast.makeText(this, "Failed to import", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;
        switch (requestCode)
        {
            case requestcode:
                String filepath = data.getData().getPath();
                SQLiteDatabase db = sqlite.getWritableDatabase();
                String tableName = SQLiteHelper.TABLE_DR;
                db.execSQL("delete from " + tableName);
                try {
                    if (resultCode == RESULT_OK) {
                        try {
                            FileReader file = new FileReader(filepath);
                            BufferedReader buffer = new BufferedReader(file);
                            ContentValues contentValues = new ContentValues();
                            db.beginTransaction();
                            String line;
                            while ((line = buffer.readLine()) != null) {
                                String[] str = line.split(",", 1);  // defining 3 columns with null or blank field //values acceptance
                                String accNum = str[0];
                                /*String userName = str[1];
                                String customerName = str[2];*/
                                contentValues.put(DR_NUMBER, accNum);
//                                contentValues.put(DR_USERNAME, userName);
//                                contentValues.put(DR_CUSTOMERNAME, customerName);
                                db.insert(tableName, null, contentValues);
                            }
                        } catch (Exception e) {
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
    }
