package com.example.mark.unioil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SignatureActivity extends AppCompatActivity {

    DrawingView dv;
    Context context;
    private Path circlePath;
    private Paint circlePaint;
    private Paint mBitmapPaint;
    private Path mPath;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mPaint;
    private boolean thereIsDrawing;
    private boolean drawingSaved;
    private Intent intent;
    private String drnumber;
    private String username;
    private String customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();
        drnumber = intent.getExtras().getString("DRNUMBER");
        username = intent.getExtras().getString("USERNAME");
        customer = intent.getExtras().getString("CUSTOMER");

        dv = new DrawingView(this);
        dv.setBackgroundColor(Color.WHITE);
        dv.setDrawingCacheEnabled(true);

        setContentView(dv);
        getSupportActionBar().setTitle("Signature");

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, Menu.FIRST, 0, "Clear").setShortcut('1', 'c');
        menu.add(0, Menu.FIRST + 1, 0, "Save").setShortcut('2', 's');
        menu.add(0, Menu.FIRST + 2, 0, "Take Photo").setShortcut('3', 'p');
        menu.add(0, Menu.FIRST + 3, 0, "Upload").setShortcut('4', 'u');

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xFF);

        switch (menuItem.getItemId()) {
            case Menu.FIRST:
                dv.clearDrawing();
                return true;
            case Menu.FIRST + 1:
                saveFunction();
                if(thereIsDrawing && drawingSaved){
                    dv.clearDrawing();
                    intent = new Intent(this, CameraActivity.class);
                    intent.putExtra("DRNUMBER",drnumber);
                    intent.putExtra("USERNAME",username);
                    intent.putExtra("CUSTOMER",customer);
                    startActivity(intent);
                } else {
                    Toast.makeText( this, "Incomplete Signature", Toast.LENGTH_SHORT).show();
                }
                return true;
            case Menu.FIRST + 2:

                return true;
            case Menu.FIRST + 3:
                    intent = new Intent(this, SubmitActivity.class);
                    startActivity(intent);
                return true;


        }
        return super.onOptionsItemSelected(menuItem);
    }

    void saveFunction() {
        if (thereIsDrawing) {
            Bitmap bitmap = dv.getDrawingCache();
            File exportDir = new File(Environment.getExternalStorageDirectory() + "/Datascan", "");
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }
            DateFormat dateFormat = new SimpleDateFormat("MM_dd_yy HH_mm_ss", Locale.ENGLISH);
            Date date = new Date();

            File file = new File(exportDir, "datascan_" + dateFormat.format(date) + ".jpg");
//        fileToSend = "datascan_" + dateFormat.format(date);
            try {
                file.createNewFile();
                FileOutputStream ostream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, ostream);
                ostream.flush();
                ostream.close();
                dv.invalidate();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                dv.setDrawingCacheEnabled(false);
                drawingSaved = true;
                Toast.makeText(this, "SAVED!", Toast.LENGTH_SHORT).show();
//            exportBaKamo(value);
            }
        }
        else{
            Toast.makeText(this,"Empty Canvas!",Toast.LENGTH_SHORT).show();
        }
    }

    private class DrawingView extends View {
        private static final float TOUCH_TOLERANCE = 4;
        private float mX, mY;

        public DrawingView(Context c) {
            super(c);
            context = c;
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            circlePaint = new Paint();
            circlePath = new Path();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.BLACK);
            circlePaint.setAlpha(128);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);
        }

        public void clearDrawing() {
            thereIsDrawing = false;
            setDrawingCacheEnabled(false);

            onSizeChanged(getWidth(), getHeight(), getWidth(), getHeight());
            invalidate();

            setDrawingCacheEnabled(true);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath(mPath, mPaint);
            canvas.drawPath(circlePath, circlePaint);
        }

        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);

            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;

                circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
            }
        }

        private void touch_up() {
            thereIsDrawing = true;
            mPath.lineTo(mX, mY);
            circlePath.reset();
            mCanvas.drawPath(mPath, mPaint);
            mPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }
    }

}
