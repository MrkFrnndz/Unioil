package com.example.mark.unioil.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
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

import com.example.mark.unioil.R;
import com.example.mark.unioil.databinding.ActivityMainBinding;
import com.example.mark.unioil.signature.SignatureActivity;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Displays the Main screen.
 */
public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    public static final int requestcode = 1;
    private MainPresenter mainPresenter;
    private AppCompatEditText etDrNumber;
    private AppCompatEditText etUserName;
    private AppCompatEditText etCustomerName;
    private AppCompatButton btnProceed;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainPresenter = new MainPresenter(this);
        activityMainBinding.setPresenter(mainPresenter);

        etDrNumber = (AppCompatEditText) findViewById(R.id.etDrNumber);
        etUserName = (AppCompatEditText) findViewById(R.id.etUserName);
        etCustomerName = (AppCompatEditText) findViewById(R.id.etCustomerName);
        btnProceed = (AppCompatButton) findViewById(R.id.btnProceed);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);

        etDrNumber.addTextChangedListener(mainPresenter.handleDROnTextChanged());
        etUserName.addTextChangedListener(mainPresenter.handleEditTextOnTextChanged());
        etCustomerName.addTextChangedListener(mainPresenter.handleEditTextOnTextChanged());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_import:
                mainPresenter.handleImportProductMenuClick();
                return true;
            case R.id.action_clear:
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //      MainView Methods        //
    @Override
    public void showSignatureScreen() {
        Intent intent = new Intent(MainActivity.this, SignatureActivity.class);
        try {
            FileReader file = new FileReader(Environment.getExternalStorageDirectory() + "/Download/unioil_data.csv");
            BufferedReader bfr = new BufferedReader(file);
            String line;
            while ((line = bfr.readLine()) != null) {
                String[] data = line.split(";");
                if (data[0].equals(etDrNumber.getText().toString())) {
                    Snackbar.make(coordinatorLayout, R.string.found, Snackbar.LENGTH_SHORT).show();
                    intent.putExtra("DRNUMBER", etDrNumber.getText().toString());
                    intent.putExtra("USERNAME", etUserName.getText().toString());
                    intent.putExtra("CUSTOMER", etCustomerName.getText().toString());
                    startActivity(intent);
                    break;
                } else if (!line.equals(etDrNumber.getText().toString())) {
                    Snackbar.make(coordinatorLayout, R.string.drnotfound, Snackbar.LENGTH_SHORT).show();
                }
            }

            bfr.close();
        } catch (Exception e) {
            Log.d("Error: ", e.toString());
        }
    }

    @Override
    public TextWatcher checkDRContent() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mainPresenter.handleSearchDR();
                if (!etDrNumber.getText().toString().equals("") &&
                        !etUserName.getText().toString().equals("") &&
                        !etCustomerName.getText().toString().equals("")) {
                    btnProceed.setEnabled(true);
                    btnProceed.setText(R.string.proceedtosignature);
                } else {
                    btnProceed.setEnabled(false);
                    btnProceed.setText(R.string.fillall);
                }
            }
        };
    }

    @Override
    public TextWatcher checkEditTextContent() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!etDrNumber.getText().toString().equals("") &&
                        !etUserName.getText().toString().equals("") &&
                        !etCustomerName.getText().toString().equals("")) {
                    btnProceed.setEnabled(true);
                    btnProceed.setText(R.string.proceedtosignature);
                } else {
                    btnProceed.setEnabled(false);
                    btnProceed.setText(R.string.fillall);
                }
            }
        };
    }

    @Override
    public void searchDR() {
        try {
            FileReader file = new FileReader(Environment.getExternalStorageDirectory() + "/Download/unioil_data.csv");
            BufferedReader bfr = new BufferedReader(file);
            String line;
            while ((line = bfr.readLine()) != null) {
                String[] data = line.split(";");
                if (data[0].equals(etDrNumber.getText().toString())) {
                    etCustomerName.setText(data[1]);
                    Snackbar.make(coordinatorLayout, R.string.found, Snackbar.LENGTH_SHORT).show();
                    break;
                } else if (!line.equals(etDrNumber.getText().toString())) {
                    etCustomerName.setText("");
                    Snackbar.make(coordinatorLayout, R.string.drnotfound, Snackbar.LENGTH_SHORT).show();
                }
            }
            bfr.close();
        } catch (Exception e) {
            Log.d("Error: ", e.toString());
        }
    }

    @Override
    public void importProduct() {
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
