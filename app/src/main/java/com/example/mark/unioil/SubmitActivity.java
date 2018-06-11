package com.example.mark.unioil;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class SubmitActivity extends AppCompatActivity {

    final String FTPHost = "files.000webhost.com";
    final String user = "attendancemonitor";
    final String pass = "darksalad12";
    final int PORT = 21;
    //    final int PICK_FILE = 1;
    AppCompatButton btnUpload;
    String filename;

    private String drnumber;
    private String username;
    private String customer;

    private int fileSend;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initialize();

        drnumber = getIntent().getExtras().getString("DRNUMBER");
        username = getIntent().getExtras().getString("USERNAME");
        customer = getIntent().getExtras().getString("CUSTOMER");

        writeOutput();

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//                i.setType("file/*");
//                startActivityForResult(i, PICK_FILE);

                //File Sample
                //    /storage/sdcard0/Datascan/datascan_03_09_18 10_08_47.csv
                //    datascan_03_09_18 10_08_47.csv

                filename = drnumber + "-Data.txt";
                new UploadFile().execute("/storage/sdcard0/Unioil/" + filename, FTPHost, user, pass);
                fileSend = 1;
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PICK_FILE && data.getData() != null) {
//            filepath = data.getData().getPath();
//            filename = data.getData().getLastPathSegment();
//            Log.d("File", filepath +"\n"+ filename);
//            new UploadFile().execute(filepath, FTPHost, user, pass);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }


    private void initialize() {
        btnUpload = (AppCompatButton) findViewById(R.id.btnUpload);
        progressBar = (ProgressBar) findViewById(R.id.determinateBar);
    }

    private void writeOutput() {
        try {
            File sdCardDir = new File(Environment.getExternalStorageDirectory() + "/Unioil", "");
            String filename = drnumber + "-Data.txt"; // the name of the file to export with
            File saveFile = new File(sdCardDir, filename);
            FileWriter fw = new FileWriter(saveFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(drnumber).append(",").append(username).append(",").append(customer).append("\n");
            bw.close();
            Log.d("Error in writeOutput: ", sdCardDir.toString());

        } catch (Exception e) {
            Log.d("Error in writeOutput: ", e.toString());
        }

    }

    private class UploadFile extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            FTPClient client = new FTPClient();
            try {
                client.connect(params[1], PORT);
                client.login(params[2], params[3]);
                client.enterLocalPassiveMode();
                client.changeWorkingDirectory("/public_html/CSVFiles");
                client.setFileType(FTP.BINARY_FILE_TYPE);
                Log.d("FTP", filename);
                return client.storeFile(filename, new FileInputStream(new File(params[0])));
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("FTP", e.toString());
                return false;
            } finally {
                try {
                    client.logout();
                    client.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                progressBar.incrementProgressBy(33);
                if (fileSend == 1) {
                    filename = drnumber + "-Signature.jpg";
                    new UploadFile().execute("/storage/sdcard0/Unioil/" + filename, FTPHost, user, pass);
                    fileSend = 2;
                } else if (fileSend == 2) {
                    filename = drnumber + "-Document.jpg";
                    new UploadFile().execute("/storage/sdcard0/Unioil/" + filename, FTPHost, user, pass);
                    fileSend = 3;
                } else if (fileSend == 3) {
                    Toast.makeText(SubmitActivity.this, "Upload successful.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SubmitActivity.this, MainActivity.class));
                }
            } else
                Toast.makeText(SubmitActivity.this, R.string.error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }
}


//<android.support.v7.widget.AppCompatButton
//        android:elevation="2dp"
//        android:id="@+id/btnUpload"
//        android:layout_width="300dp"
//        android:layout_height="wrap_content"
//        android:layout_marginTop="15dp"
//        android:background="@drawable/custom_button_violet"
//        android:text="UPLOAD"
//        app:layout_flexBasisPercent="@fraction/fraction100percent" />
