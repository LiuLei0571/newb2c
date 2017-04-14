package com.example.b2c.temple;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.example.b2c.adapter.base.BaseHolder;


/**
 * Created by milk on 2016/10/26.
 */

public class TemplateBaseHolder extends BaseHolder {
    public TemplateBaseHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
    }

    public TemplateBaseHolder(View view) {
        super(view);
    }

    public void onViewRecycled() {

    }
}
