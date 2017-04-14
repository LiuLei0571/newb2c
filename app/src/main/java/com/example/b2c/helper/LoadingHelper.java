package com.example.b2c.helper;

import android.support.v4.app.FragmentActivity;

import com.example.b2c.widget.Progress;


/**
 * 用途：
 * Created by milk on 16/8/27.
 * 邮箱：649444395@qq.com
 */
public class LoadingHelper {
    public static void showLoading(FragmentActivity fragmentActivity) {
        Progress.show(fragmentActivity, null);
    }

    public static void showLoading(FragmentActivity fragmentActivity, String content) {
        Progress.show(fragmentActivity, content);
    }

    public static void dismiss() {
        Progress.dismiss();
    }
}
