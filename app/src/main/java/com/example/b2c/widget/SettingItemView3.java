package com.example.b2c.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;

/**
 * 文本+图标
 */
public class SettingItemView3 extends RelativeLayout{

    private TextView  tv_text;
    private ImageView iv_right;


    public SettingItemView3(Context context) {
        super(context);
        initView();
    }

    public SettingItemView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    public SettingItemView3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        // 将自定义好的布局文件设置给当前的SettingItemView
        View.inflate(getContext(), R.layout.view_setting_item3, this);
        tv_text = (TextView) findViewById(R.id.tv_text);
        iv_right = (ImageView) findViewById(R.id.iv_right);
    }

    /**
     * 设置☜文本内容
     * @param tv_text
     */
    public void setTv_text(String tv_text) {
        this.tv_text.setText(tv_text);
    }

    /**
     * 影藏右边的图片
     */
    public void hide_RightIv() {
        this.iv_right.setVisibility(View.INVISIBLE);
    }



}
