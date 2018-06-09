package com.example.mark.unioil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class CameraActivity extends AppCompatActivity {
    static final int REQUEST_PICTURE_CAPTURE = 1;
    static final int CAMERA_REQUEST = 0;
    private AppCompatButton btnCapture,btnSave;
    private AppCompatImageView capturedImage;
    private Intent intent;
    private String drnumber;
    private String username;
    private String customer;
    private String pictureFilePath;
    private CoordinatorLayout coordinatorLayout;
    private boolean captured;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        drnumber = intent.getExtras().getString("DRNUMBER");
        username = intent.getExtras().getString("USERNAME");
        customer = intent.getExtras().getString("CUSTOMER");

        setContentView(R.layout.activity_camera);
        getSupportActionBar().setTitle("Capture");
        init();

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                intent.putExtra("DRNUMBER",drnumber);
//                intent.putExtra("USERNAME",username);
//                intent.putExtra("CUSTOMER",customer);
//                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//                String pictureName = getPictureName();
//                File imageFile = new File(pictureDirectory,pictureName);
//                Uri pictureUri = Uri.fromFile(imageFile);
//                intent.putExtra( MediaStore.EXTRA_OUTPUT, pictureUri);
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(CameraActivity.this,SubmitActivity.class);
                intent.putExtra("DRNUMBER",drnumber);
                intent.putExtra("USERNAME",username);
                intent.putExtra("CUSTOMER",customer);
//                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//                String pictureName = getPictureName();
//                File imageFile = new File(pictureDirectory,pictureName);
//                Uri pictureUri = Uri.fromFile(imageFile);
//                intent.putExtra( MediaStore.EXTRA_OUTPUT, pictureUri);
                startActivity(intent);
            }
        });
    }

    private void init() {
        btnCapture = (AppCompatButton) findViewById(R.id.btnCapture);
        btnSave = (AppCompatButton) findViewById(R.id.btnSave);
        capturedImage = (AppCompatImageView) findViewById(R.id.capturedImage);
    }

//    private String getPictureName(){
//        String pictureFile = drnumber;
////        File storageDir = getExternalFilesDir(Environment.getExternalStorageDirectory()+ "/Unioil");
////        File image = File.createTempFile(pictureFile,  ".jpg", storageDir);
////        pictureFilePath = image.getAbsolutePath();
//        return  pictureFile + ".jpg";
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_PICTURE_CAPTURE && resultCode == RESULT_OK) {
//            File imgFile = new  File(pictureFilePath);
//            if(imgFile.exists())            {
//                capturedImage.setImageURI(Uri.fromFile(imgFile));
//            }
//        }


        if(resultCode == RESULT_OK){
            if(requestCode == CAMERA_REQUEST){
                Bitmap cameraImage = (Bitmap) data.getExtras().get("data");
                capturedImage.setImageBitmap(cameraImage);
                File exportDir = new File(Environment.getExternalStorageDirectory() + "/Unioil", "");
                if (!exportDir.exists()) {
                    exportDir.mkdirs();
                }

                File file = new File(exportDir, drnumber + "-Document.jpg");
                try {
                    file.createNewFile();
                    FileOutputStream ostream = new FileOutputStream(file);
                    cameraImage.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    ostream.flush();
                    ostream.close();
                    captured = true;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Toast.makeText(this, "SAVED!", Toast.LENGTH_SHORT).show();
//            exportBaKamo(value);
                }
            }

        }
    }
}
