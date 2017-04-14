package com.example.b2c.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.b2c.R;

import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShowNativeImageActivity extends Activity {
    private ZoomImageView zoomView;
    private Context ctx;
    private GestureDetector gestureDetector;
    private LinearLayout datu;
    private ImageView iv_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册手机监听
        setContentView(R.layout.activity_show_native_image);
        ctx = this;
        zoomView = (ZoomImageView) findViewById(R.id.native_zoom);
        iv_delete = (ImageView) findViewById(R.id.native_delete);
        zoomView.setOnRefreshListener(new ZoomImageView.OnRefreshListeners() {
            @Override
            public void onLoadMore() {
                finish();
            }
        });

        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除本张图片
                setResult(10);
                finish();
            }
        });
		/* 大图的下载地址 */
		/* 缩略图存储在本地的地址 */
        Intent intent = getIntent();
        final String smallPath = intent.getStringExtra("smallPath");
        final String tag = intent.getStringExtra("tag");
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        final int widthPixels = metrics.widthPixels;
        final int heightPixels = metrics.heightPixels;
        if (!TextUtils.isEmpty(tag)){
            ImageHelper.display(smallPath,zoomView);
        }else{
            zoomView.setImageBitmap(zoomBitmap(
                    BitmapFactory.decodeFile(smallPath), widthPixels,
                    heightPixels));
        }


        gestureDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2,
                                           float velocityX, float velocityY) {
                        float x = e2.getX() - e1.getX();
                        if (x > 0) {
                            prePicture();
                        } else if (x < 0) {

                            nextPicture();
                        }
                        return true;
                    }

                });


    }

    protected void nextPicture() {
        // TODO Auto-generated method stub

    }

    protected void prePicture() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onResume() {
        super.onResume();
        // recycle();
    }

    public void recycle() {
        if (zoomView != null && zoomView.getDrawable() != null) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) zoomView
                    .getDrawable();
            if (bitmapDrawable != null && bitmapDrawable.getBitmap() != null
                    && !bitmapDrawable.getBitmap().isRecycled())

            {
                bitmapDrawable.getBitmap().recycle();
            }
        }
    }
    /**
     * Resize the bitmap
     *
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        if (bitmap == null)
            return bitmap;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        if (scaleWidth < scaleHeight) {
            matrix.postScale(scaleWidth, scaleWidth);
        } else {
            matrix.postScale(scaleHeight, scaleHeight);
        }
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

}
