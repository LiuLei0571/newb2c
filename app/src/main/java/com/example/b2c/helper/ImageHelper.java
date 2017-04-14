package com.example.b2c.helper;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.b2c.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

/**
 * 用途：
 * Created by milk on 16/11/21.
 * 邮箱：649444395@qq.com
 */

public class ImageHelper {
    public static ImageLoader loader;
    public static DisplayImageOptions options;

    public static void display(String url, ImageView imageView) {
        options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.ic_fail_image)
                .showImageForEmptyUri(R.drawable.ic_no_image)
                .cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(false)
                .displayer(new SimpleBitmapDisplayer()).build();
        loader = ImageLoader.getInstance();
        loader.displayImage(url, imageView, options);
    }
}
