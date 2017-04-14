package com.example.b2c.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;

/**
 * 图标+文本+图标
 */
public class SettingItemView extends RelativeLayout {

//    private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.example.b2c";

    private TextView tv_text;
    private ImageView iv_left;

    private Bitmap mLeftIcon;


    public SettingItemView(Context context) {
        super(context);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获得自定义属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView);
        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            int itemId = ta.getIndex(i); // 获取某个属性的Id值
            switch (itemId) {
                case R.styleable.SettingItemView_lefticon: // 设置当前按钮的状态
                    int drawableId = ta.getResourceId(itemId, -1);
                    mLeftIcon = BitmapFactory.decodeResource(getResources(), drawableId);
                    break;
                default:
                    break;
            }
        }
        ta.recycle();
        initView();

    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        // 将自定义好的布局文件设置给当前的SettingItemView
        View.inflate(getContext(), R.layout.view_setting_item, this);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        tv_text = (TextView) findViewById(R.id.tv_text);
        setIv_left(mLeftIcon);
    }

    /**
     * 设置文本内容
     *
     * @param tv_text
     */
    public void setTv_text(String tv_text) {
        this.tv_text.setText(tv_text);
    }

    /**
     * 设置左边的图片
     *
     * @param bitmap
     */
    public void setIv_left(Bitmap bitmap) {
        if (bitmap != null) {
            iv_left.setImageBitmap(bitmap);
        }
    }
}
