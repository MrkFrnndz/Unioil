package com.example.mark.unioil;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        getSupportActionBar().setTitle("Upload to FTP");

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
                filename = drnumber + "-Signature.jpg";
                new UploadFile().execute("/storage/sdcard0/Unioil/" + filename, FTPHost, user, pass);
                filename = drnumber + "-Document.jpg";
                new UploadFile().execute("/storage/sdcard0/Unioil/" + filename, FTPHost, user, pass);

                Toast.makeText(SubmitActivity.this, "Upload successful.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(SubmitActivity.this, MainActivity.class));
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
        protected void onPostExecute(Boolean sucess) {
            if (sucess)
                Toast.makeText(SubmitActivity.this, "File Sent", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(SubmitActivity.this, "Error", Toast.LENGTH_LONG).show();
        }
    }
}
