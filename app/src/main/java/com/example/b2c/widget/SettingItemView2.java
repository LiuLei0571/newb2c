package com.example.b2c.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;

/**
 * 文本+文本+图标
 */
public class SettingItemView2 extends RelativeLayout {

    private RelativeLayout rl_siv;
    private TextView tv_text;
    private TextView tv_doc;
    private ImageView iv_right;


    public SettingItemView2(Context context) {
        super(context);
        initView();
    }

    public SettingItemView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    public SettingItemView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        // 将自定义好的布局文件设置给当前的SettingItemView
        View.inflate(getContext(), R.layout.view_setting_item2, this);
        rl_siv = (RelativeLayout) findViewById(R.id.rl_siv);
        tv_text = (TextView) findViewById(R.id.tv_text);
        tv_doc = (TextView) findViewById(R.id.tv_doc);
        iv_right = (ImageView) findViewById(R.id.iv_right);
    }

    /**
     * 设置☜文本内容
     *
     * @param tv_text
     */
    public void setTv_text(String tv_text) {
        this.tv_text.setText(tv_text);
    }

    public String getTv_text() {
        return this.tv_text.getText().toString();
    }

    /**
     * 设置→_→边文本内容
     *
     * @param tv_text
     */
    public void setTv_doc(String tv_text) {
        this.tv_doc.setText(tv_text);
    }

    /**
     * 设置→_→边文本颜色
     *
     * @param color
     */
    public void setTv_docColor(int color) {
        this.tv_doc.setTextColor(color);
    }

    /**
     * 影藏右边的图片
     */
    public void hide_RightIv() {
        this.iv_right.setVisibility(View.INVISIBLE);
    }

    /**
     * 隐藏右边的文本
     */
    public void hide_RightTv() {
        this.tv_doc.setVisibility(View.INVISIBLE);
    }

    /**
     * 隐藏
     */
    public void  hide(){
        rl_siv.setVisibility(INVISIBLE);
    }



}
