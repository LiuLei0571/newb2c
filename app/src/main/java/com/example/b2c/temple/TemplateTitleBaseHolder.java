package com.example.b2c.temple;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;


/**
 * Created by milk on 2016/10/26.
 */

public class TemplateTitleBaseHolder extends TemplateBaseHolder {
    public TemplateTitleBaseHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
    }

    public TemplateTitleBaseHolder(View view) {
        super(view);
    }

    protected TextView title;
    protected ImageView leftImg;
    protected View titleBar, textTitleBar, imgTitleBar, bannerBar;

    /**
     * 初始化标题栏目
     *
     * @param itemView 标题视图
     */
    protected void initTitle(View itemView) {
        titleBar = itemView.findViewById(R.id.title_bar);
        textTitleBar = itemView.findViewById(R.id.text_title_bar);
        imgTitleBar = itemView.findViewById(R.id.img_title_bar);
        bannerBar = itemView.findViewById(R.id.banner_bar);
        leftImg = (ImageView) itemView.findViewById(R.id.title_left_img);
        title = (TextView) itemView.findViewById(R.id.title_tv);
     }
}
