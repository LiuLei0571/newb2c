package com.example.b2c.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by yh on 2016/8/23.
 */
public interface IAct {
    Context getContext();

    Activity getActivity();

    Activity getBaseActivity();

    void startActivity(Intent intent);

    void startActivityForResult(Intent intent, int requestCode);
}
