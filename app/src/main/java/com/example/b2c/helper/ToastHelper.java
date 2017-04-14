package com.example.b2c.helper;


import com.example.b2c.B2C;

/**
 * 用途：
 * Created by milk on 16/8/31.
 * 邮箱：649444395@qq.com
 */
public class ToastHelper {
    private static AppToast appToast;

    static {
        appToast = new AppToast(B2C.getContext());
    }

    public static void makeToast(String content) {
        appToast.makeToast(content);
    }

    public static void makeToast(int content) {
        appToast.makeToast(content);
    }

    public static void makeErrorToast(String content) {
        appToast.makeImgToast(content);
    }

    public static void makeErrorToast(int content) {
        appToast.makeImgToast(content);
    }
}
