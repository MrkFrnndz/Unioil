package com.example.mark.unioil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SubmitActivity extends AppCompatActivity {

    final String FTPHost = "files.000webhost.com";
    final String user = "attendancemonitor";
    final String pass = "darksalad12";
    final int PORT = 21;
    final int PICK_FILE = 1;
    AppCompatButton btnUpload;
    String filename;
    private String DRNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        getSupportActionBar().setTitle("Upload to FTP");

        initialize();

        DRNumber = getIntent().getStringExtra("DR");

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//                i.setType("file/*");
//                startActivityForResult(i, PICK_FILE);

                //File Sample
                //    /storage/sdcard0/Datascan/datascan_03_09_18 10_08_47.csv
                //    datascan_03_09_18 10_08_47.csv

                filename = DRNumber + "-Data.csv";
                new UploadFile().execute("/storage/sdcard0/Unioil/" + filename, FTPHost, user, pass);
                filename = DRNumber + "-Signature.jpg";
                new UploadFile().execute("/storage/sdcard0/Unioil/" + filename, FTPHost, user, pass);
                filename = DRNumber + "-Document.jpg";
                new UploadFile().execute("/storage/sdcard0/Unioil/" + filename, FTPHost, user, pass);
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
    }
}
