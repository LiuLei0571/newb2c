package com.example.b2c.temple;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.b2c.R;
import com.example.b2c.activity.IAct;
import com.example.b2c.net.response.ModelPage;
import com.example.b2c.net.response.ModelPageItem;
import com.example.b2c.widget.util.Strings;
import com.example.b2c.widget.util.TypeParse;
import com.example.b2c.widget.util.Utils;

import java.util.List;

/**
 * Created by milk on 2016/10/27.
 */

public abstract class BaseTitleTemplate<M extends ITemplateModel, VH extends TemplateBaseHolder>
        extends BaseTemplate<M, VH> {

    public BaseTitleTemplate(IAct act) {
        super(act);
    }


    /**
     * 处理头部展现
     *
     * @param forum 模板
     */
    protected void showTitle(TemplateTitleBaseHolder holder, ModelPage forum) {
        //标题栏或图片bar唯一显示
        if ( forum.isTitleVisible()) {
            holder.bannerBar.setVisibility(View.GONE);
            holder.titleBar.setVisibility(View.VISIBLE);
            //标题图片样式
            if ("TITLE_IMAGE".equals(forum.getTitleType())) {
                holder.textTitleBar.setVisibility(View.GONE);
                holder.imgTitleBar.setVisibility(View.VISIBLE);
                ImageView bgImg = (ImageView) holder.imgTitleBar.findViewById(R.id.icon);
//                ImgHelper.displayImage(bgImg, forum.getTitleImage());
            } else {
                //文字样式
                holder.textTitleBar.setVisibility(View.VISIBLE);
                holder.imgTitleBar.setVisibility(View.GONE);
                holder.title.setText(forum.getTitleText());
                Utils.showHtmlContent(holder.title, forum.getTitleText());
                if (!TextUtils.isEmpty(forum.getTitleIcon())) {
                    holder.leftImg.setVisibility(View.VISIBLE);
//                    ImgHelper.displayImage(holder.leftImg, forum.getTitleIcon());
                } else {
                    holder.leftImg.setVisibility(View.GONE);
                }
                TypeParse.setBackground(holder.textTitleBar, forum.getTitleBackground());
            }
        } else {
            holder.titleBar.setVisibility(View.GONE);
            if (Strings.TRUE.equals(forum.getBannerVisible())) {
                holder.bannerBar.setVisibility(View.VISIBLE);
                //重置高度
//                if (forum.getBannerHeight() > 0) {
//                    setViewHeight(holder.bannerBar.getContext(), forum.getBannerHeight(), holder.bannerBar);
//                }
                ImageView bgImg = (ImageView) holder.bannerBar.findViewById(R.id.icon);
//                ImgHelper.displayImage(bgImg, forum.getBannerImage());
                bgImg.setTag(R.id.href, forum.getBannerUrl());
                bgImg.setOnClickListener(getOnClick());
            } else {
                holder.bannerBar.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 重置视图高度
     *
     * @param context context
     * @param height  模板高度
     * @param view    视图
     */
    private static void setViewHeight(Context context, long height, View view) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int newHeight = (int) (wm.getDefaultDisplay().getWidth() * height / 1080);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = newHeight;
        view.setLayoutParams(layoutParams);
    }

    protected ModelPageItem getItem(ModelPage forum, int index) {
        List<ModelPageItem> items = forum.getItems();
        if (items == null) {
            return null;
        }
        if (index >= items.size()) {
            return null;
        }
        return items.get(index);
    }
}
