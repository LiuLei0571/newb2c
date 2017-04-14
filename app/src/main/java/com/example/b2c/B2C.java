package com.example.b2c;

import android.app.Application;
import android.content.Context;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class B2C extends Application {
     private static Context mContext;
    boolean unLogin = false;
    private static B2C _instance;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initImageLoad();
    }
    public static B2C getInstance() {
        return _instance;
    }
    private void initImageLoad() {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(mContext);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.memoryCacheSizePercentage(20);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(100 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config.build());
    }

    public static Context getContext() {
        return mContext;
    }
}
