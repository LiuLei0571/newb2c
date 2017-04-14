package com.example.b2c.widget.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import com.example.b2c.B2C;

/**
 * 用途：
 * Created by milk on 16/11/18.
 * 邮箱：649444395@qq.com
 */

public class DeviceHelper {

    /**
     * 判断当前有没有网络连接
     */
    public static boolean getNetworkState() {
        Context context = B2C.getContext();
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkinfo = manager.getActiveNetworkInfo();
            return !(networkinfo == null || !networkinfo.isAvailable());
        }
        return false;
    }

    /**
     * SD卡是否挂载
     */
    public static boolean mountedSdCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
