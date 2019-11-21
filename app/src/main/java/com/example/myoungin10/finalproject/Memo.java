package com.example.myoungin10.finalproject;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.myoungin10.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Memo extends Activity implements ColorPickerDialog.OnColorChangedListener {
    private Bitmap mBitmap;
    private Canvas mCanvas = new Canvas();
    private Path mPath;
    private Paint mBitmapPaint;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(new MyView(this));
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);

        mEmboss = new EmbossMaskFilter(new float[]{1, 1, 1}, 0.4f, 6, 3.5f);

        mBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
    }

    private Paint mPaint = new Paint();
    private MaskFilter mEmboss;
    private MaskFilter mBlur;

    public void colorChanged(int color) {
        mPaint.setColor(color);
    }

    public class MyView extends View {
        private static final float MINP = 0.25f;
        private static final float MAXP = 0.75f;
        public MyView(Context c) {
            super(c);
            int width = getWindowManager().getDefaultDisplay().getWidth();
            int height = getWindowManager().getDefaultDisplay().getHeight();

            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
        }

        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

            canvas.drawPath(mPath, mPaint);
            Paint Pnt = new Paint();
            Pnt.setColor(Color.WHITE);

            invalidate();
        }

        private float mX, mY;

        private static final float TOUCH_TOLERANCE = 4;

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
            }
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);
            mCanvas.drawPath(mPath, mPaint);
            mPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
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

    private static final int ERASE_MENU_ID = Menu.FIRST;

    private static final int ONE_WIDTH = Menu.FIRST + 1;
    private static final int TWO_WIDTH = Menu.FIRST + 2;
    private static final int THREE_WIDTH = Menu.FIRST + 3;
    private static final int FOUR_WIDTH = Menu.FIRST + 4;
    private static final int FIVE_WIDTH = Menu.FIRST + 5;

    private static final int BLUR = Menu.FIRST + 6;
    private static final int EMBOSS = Menu.FIRST + 7;
    private static final int NOMAL = Menu.FIRST + 16;

    private static final int COLOR_MENU_ID = Menu.FIRST + 13;

    private static final int RESET_MENU_ID = Menu.FIRST + 14;

    private static final int SAVE_MENU_ID = Menu.FIRST + 15;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        SubMenu widthmenu = menu.addSubMenu("Size");
        widthmenu.add(0, ONE_WIDTH, 0, "3");
        widthmenu.add(0, TWO_WIDTH, 0, "6");
        widthmenu.add(0, THREE_WIDTH, 0, "10");
        widthmenu.add(0, FOUR_WIDTH, 0, "13");
        widthmenu.add(0, FIVE_WIDTH, 0, "16");

        SubMenu linemenu = menu.addSubMenu("Style");
        linemenu.add(0, BLUR, 0, "Blur");
        linemenu.add(0, EMBOSS, 0, "Emboss");
        linemenu.add(0, NOMAL, 0, "Nomal");

        menu.add(0, ERASE_MENU_ID, 0, "Eraser");

        menu.add(0, SAVE_MENU_ID, 0, "Save");

        menu.add(0, COLOR_MENU_ID, 0, "Color");

        menu.add(0, RESET_MENU_ID, 0, "Reset");

        return true;
    }

    public class Reset extends View {
        Reset(Context context) {
            super(context);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xFF);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();

        switch (item.getItemId()) {
            case COLOR_MENU_ID:
                new ColorPickerDialog(this, this, mPaint.getColor()).show();
                return true;
            case ONE_WIDTH:
                mPaint.setStrokeWidth(3);
                return true;
            case TWO_WIDTH:
                mPaint.setStrokeWidth(6);
                return true;
            case THREE_WIDTH:
                mPaint.setStrokeWidth(10);
                return true;
            case FOUR_WIDTH:
                mPaint.setStrokeWidth(13);
                return true;
            case FIVE_WIDTH:
                mPaint.setStrokeWidth(16);
                return true;

            case EMBOSS:
                if (mPaint.getMaskFilter() != mEmboss) {
                    mPaint.setMaskFilter(mEmboss);
                } else {
                    mPaint.setMaskFilter(null);
                }
                return true;
            case BLUR:
                if (mPaint.getMaskFilter() != mBlur) {
                    mPaint.setMaskFilter(mBlur);
                } else {
                    mPaint.setMaskFilter(null);
                }
                return true;
            case NOMAL:
                mCanvas.drawPath(mPath, mPaint);
                mPaint.setMaskFilter(null);
                return true;
            case ERASE_MENU_ID:
                mPaint.setColor(Color.WHITE);
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                return true;
            case RESET_MENU_ID:
                mBitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.cl);
                Bitmap resized = Bitmap.createScaledBitmap(image, width, height, true);
                mBitmap = Bitmap.createBitmap(resized);
                mCanvas = new Canvas(mBitmap);
                mPath = new Path();
                mBitmapPaint = new Paint(Paint.DITHER_FLAG);
                mCanvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
                mCanvas.drawPath(mPath, mPaint);
                return true;
            case SAVE_MENU_ID:
                final String tag = "MySdcardWriteTest";
                String path = "/sdcard/";

                File file = new File(path + "save.jpg");
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write("this is test~!!\nThis Is Test~!!".getBytes());
                    fos.close();
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                } catch (IOException e) {
                    Log.i(tag, e.getMessage());
                }
                return true;
        }
        return super.onPrepareOptionsMenu(null);
    }
}
