package com.example.mark.unioil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

public class LoginActivity extends AppCompatActivity {
    private AppCompatButton btnProceed;
    private AppCompatEditText etUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("USERNAME",etUserName.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void init() {
        btnProceed = (AppCompatButton) findViewById(R.id.btnProceed);
        etUserName = (AppCompatEditText) findViewById(R.id.etUserName);

    }

    @Override
    public void onBackPressed() {
    }
}
