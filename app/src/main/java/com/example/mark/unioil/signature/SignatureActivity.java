package com.example.mark.unioil.signature;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mark.unioil.R;
import com.example.mark.unioil.camera.CameraActivity;

import java.io.File;
import java.io.FileOutputStream;

public class SignatureActivity extends AppCompatActivity implements SignatureContract.SignatureView {

    DrawingView drawingView;
    private SignaturePresenter signaturePresenter;

    private Intent intent;
    private String drnumber;
    private String username;
    private String customer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signaturePresenter = new SignaturePresenter(this);
        drawingView = new DrawingView(this);
        drawingView.setBackgroundColor(Color.WHITE);
        drawingView.setDrawingCacheEnabled(true);
        setContentView(drawingView);

        intent = getIntent();
        drnumber = intent.getExtras().getString("DRNUMBER");
        username = intent.getExtras().getString("USERNAME");
        customer = intent.getExtras().getString("CUSTOMER");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, Menu.FIRST, 0, "Clear").setShortcut('1', 'c');
        menu.add(0, Menu.FIRST + 1, 0, "Save").setShortcut('2', 's');

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        drawingView.getmPaint().setXfermode(null);
        drawingView.getmPaint().setAlpha(0xFF);

        switch (menuItem.getItemId()) {
            case Menu.FIRST:
                signaturePresenter.handleClearMenuClick();
                return true;
            case Menu.FIRST + 1:
                signaturePresenter.handleSaveMenuClick();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    ////        SignatureView Methods       ////
    @Override
    public void showCaptureScreen() {
        if (drawingView.isThereIsDrawing()) {
            Bitmap bitmap = drawingView.getDrawingCache();
            File exportDir = new File(Environment.getExternalStorageDirectory() + "/Unioil", "");
            if (!exportDir.exists()) {
                if (exportDir.mkdirs())
                    Toast.makeText(this, R.string.foldercreated, Toast.LENGTH_SHORT);
            }
            File file = new File(exportDir, drnumber + "-Signature.jpg");
            try {
                if (file.createNewFile()) {
                    FileOutputStream ostream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 10, ostream);
                    ostream.flush();
                    ostream.close();
                }
                drawingView.invalidate();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                drawingView.setDrawingCacheEnabled(false);
                drawingView.setDrawingSaved(true);
                Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.emptycanvas, Toast.LENGTH_SHORT).show();
        }
        if (drawingView.isThereIsDrawing() && drawingView.isDrawingSaved()) {
            drawingView.clearDrawing();
            intent = new Intent(this, CameraActivity.class);
            intent.putExtra("DRNUMBER", drnumber);
            intent.putExtra("USERNAME", username);
            intent.putExtra("CUSTOMER", customer);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.incompletesignature, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void clearSignatureScreen() {
        drawingView.clearDrawing();
    }

}
