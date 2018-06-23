package com.example.mark.unioil.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Toast;

import com.example.mark.unioil.R;
import com.example.mark.unioil.submit.SubmitActivity;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Displays the Camera screen.
 */
public class CameraActivity extends AppCompatActivity implements CameraContract.CameraView {

    private CameraPresenter cameraPresenter;
    private AppCompatButton btnCapture,btnSave;
    private AppCompatImageView capturedImage;
    private Intent intent;
    private String drnumber;
    private String username;
    private String customer;
    private Uri mHighQualityImageUri = null;
    private final int REQUEST_CODE_HIGH_QUALITY_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraPresenter = new CameraPresenter(this);

        intent = getIntent();
        drnumber = intent.getExtras().getString("DRNUMBER");
        username = intent.getExtras().getString("USERNAME");
        customer = intent.getExtras().getString("CUSTOMER");

        setContentView(R.layout.activity_camera);
        init();

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mHighQualityImageUri = generateTimeStampPhotoFileUri();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mHighQualityImageUri);
                startActivityForResult(intent, REQUEST_CODE_HIGH_QUALITY_IMAGE);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(CameraActivity.this,SubmitActivity.class);
                intent.putExtra("DRNUMBER",drnumber);
                intent.putExtra("USERNAME",username);
                intent.putExtra("CUSTOMER",customer);
                startActivity(intent);
            }
        });
    }

    private void init() {
        btnCapture = (AppCompatButton) findViewById(R.id.btnCapture);
        btnSave = (AppCompatButton) findViewById(R.id.btnSave);
        capturedImage = (AppCompatImageView) findViewById(R.id.capturedImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode) {
                case REQUEST_CODE_HIGH_QUALITY_IMAGE:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        final Uri contentUri = generateTimeStampPhotoFileUri();
                        scanIntent.setData(contentUri);
                        sendBroadcast(scanIntent);
                    } else {
                        final Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory()));
                        sendBroadcast(intent);
                    }
                    capturedImage.setImageURI(mHighQualityImageUri);
                    break;
                default:
            }

        }
    }

    private Uri generateTimeStampPhotoFileUri() {
        Uri photoFileUri = null;
        File outputDir = new File(Environment.getExternalStorageDirectory() + "/Unioil", "");
        if (outputDir != null) {
            File photoFile = new File(outputDir, drnumber+ "-Document.jpg");
            photoFileUri = Uri.fromFile(photoFile);
        }
        return photoFileUri;
    }
}
