package com.example.mark.unioil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SubmitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        getSupportActionBar().setTitle("Upload to FTP");
    }
}
