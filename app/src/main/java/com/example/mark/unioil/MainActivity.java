package com.example.mark.unioil;

import android.content.ActivityNotFoundException;
import android.content.Intent;
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
import java.io.FileReader;

public class MainActivity extends AppCompatActivity {

    public static final int requestcode = 1;
    private AppCompatEditText etDrNumber;
    private AppCompatEditText etUserName;
    private AppCompatEditText etCustomerName;
    private AppCompatButton   btnProceed;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        etDrNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                try{
                    FileReader file = new FileReader(Environment.getExternalStorageDirectory()+ "/Download/unioil_data.csv");
                    BufferedReader bfr = new BufferedReader(file);
                    String line;
                    while ((line = bfr.readLine()) != null) {
                        String[] data = line.split(";");
                        if(data[0].equals(etDrNumber.getText().toString())){
                            etCustomerName.setText(data[1]);
                            Snackbar.make(coordinatorLayout, R.string.found, Snackbar.LENGTH_SHORT).show();
                            break;
                        }
                        else if(!line.equals(etDrNumber.getText().toString())){
                            etCustomerName.setText("");
                            Snackbar.make(coordinatorLayout, R.string.drnotfound, Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    bfr.close();
                }catch (Exception e){
                    Log.d("Error: ", e.toString());
                }



                if(!etDrNumber.getText().toString().equals("") &&
                        !etUserName.getText().toString().equals("") &&
                        !etCustomerName.getText().toString().equals("")){
                    btnProceed.setEnabled(true);
                    btnProceed.setText(R.string.proceedtosignature);
                } else {
                    btnProceed.setEnabled(false);
                    btnProceed.setText(R.string.fillall);
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
                    btnProceed.setText(R.string.proceedtosignature);
                } else {
                    btnProceed.setEnabled(false);
                    btnProceed.setText(R.string.fillall);
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
                    btnProceed.setText(R.string.proceedtosignature);
                } else {
                    btnProceed.setEnabled(false);
                    btnProceed.setText(R.string.fillall);
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
                    FileReader file = new FileReader(Environment.getExternalStorageDirectory()+ "/Download/unioil_data.csv");
                    BufferedReader bfr = new BufferedReader(file);
                    String line;
//                    int nCount = 0;
                    while ((line = bfr.readLine()) != null) {
                        String[] data = line.split(";");
                        if(data[0].equals(etDrNumber.getText().toString())){
                            Snackbar.make(coordinatorLayout, R.string.found, Snackbar.LENGTH_SHORT).show();
//                            writeOutput();
                            intent.putExtra("DRNUMBER",etDrNumber.getText().toString());
                            intent.putExtra("USERNAME",etUserName.getText().toString());
                            intent.putExtra("CUSTOMER",etCustomerName.getText().toString());
                            startActivity(intent);
                            break;
                        }
                        else if(!line.equals(etDrNumber.getText().toString())){
                            Snackbar.make(coordinatorLayout, R.string.drnotfound, Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    bfr.close();
                }catch (Exception e){
                    Log.d("Error: ", e.toString());
                }

            }
        });
    }

//    private void writeOutput() {
//        try {
//            File sdCardDir = Environment.getExternalStorageDirectory();
//            String filename = "output.txt"; // the name of the file to export with
//            File saveFile = new File(sdCardDir, filename);
//            FileWriter fw = new FileWriter(saveFile, true);
//            BufferedWriter bw = new BufferedWriter(fw);
//            bw.append(etDrNumber.getText().toString()).append(",").append(etUserName.getText().toString()).append(",").append(etCustomerName.getText().toString()).append("\n");
//
//            bw.close();
//
//        } catch (Exception e){
//            Log.d("Error in writeOutput: ",e.toString());
//        }
//
//    }

    private void init() {
        etDrNumber = (AppCompatEditText) findViewById(R.id.etDrNumber);
        etUserName = (AppCompatEditText) findViewById(R.id.etUserName);
        etCustomerName = (AppCompatEditText) findViewById(R.id.etCustomerName);
        btnProceed = (AppCompatButton) findViewById(R.id.btnProceed);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);

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
            Snackbar.make(coordinatorLayout, R.string.failedtoimport, Snackbar.LENGTH_SHORT).show();
//            Toast.makeText(this, "Failed to import", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
