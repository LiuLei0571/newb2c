package com.example.b2c.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.B2C;
import com.example.b2c.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 用途：
 * Created by milk on 16/10/8.
 * 邮箱：649444395@qq.com
 */

public class UIHelper {
    private static Context context = B2C.getContext();

    /**
     * 获取图片资源文件
     *
     * @param drawableId
     * @return
     */
    public static Drawable getDrawable(int drawableId) {
        context = B2C.getContext();
        return context.getResources().getDrawable(drawableId);
    }

    /**
     * 获取颜色资源
     *
     * @param color
     * @return
     */
    public static int getColor(int color) {
        return context.getResources().getColor(color);
    }

    public static String getString(int stringId) {
        return context.getResources().getString(stringId);
    }

    public static boolean hasEmpty(TextView[] edits) {
        for (TextView edit : edits) {
            if (TextUtils.isEmpty(edit.getText().toString().trim())) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkEdit(EditText... editTexts) {
        return hasEmpty(editTexts);
    }

    public static void displayImage(ImageView imageView, String url) {
        imageView.setTag(R.id.tag_image_url, url == null ? "" : url);//暂存url
        if (TextUtils.isEmpty(url)) {
            imageView.setImageDrawable(null);
        } else {
            DisplayImageOptions.Builder options = new DisplayImageOptions.Builder();
            options.cacheInMemory(true);
            options.cacheOnDisk(true);
            ImageLoader.getInstance().displayImage(url, imageView, options.build());
        }
    }

    public static boolean hasEmpty(ImageView[] edits) {
        for (ImageView edit : edits) {
            if (TextUtils.isEmpty(edit.getTag().toString().trim())) {
                return true;
            }
        }
        return false;
    }

    public static void setClickableLogin(Button action, EditText... edits) {
        if (hasEmpty(edits)) {
            action.setClickable(false);
            action.setTextColor(UIHelper.getColor(R.color.text_gray));
            action.setBackgroundResource(R.drawable.bg_red_un_login);
        } else {
            action.setClickable(true);
            action.setTextColor(UIHelper.getColor(R.color.white));
            action.setBackgroundResource(R.drawable.bg_red_login);
        }
    }

    public static void setClickableLogin(Button action, ImageView... edits) {
        if (hasEmpty(edits)) {
            action.setClickable(false);
            action.setTextColor(UIHelper.getColor(R.color.text_gray));
            action.setBackgroundResource(R.drawable.bg_red_un_login);
        } else {
            action.setClickable(true);
            action.setTextColor(UIHelper.getColor(R.color.white));
            action.setBackgroundResource(R.drawable.bg_red_login);
        }
    }
    public static void setClickableLogin(Button action, TextView... edits) {
        if (hasEmpty(edits)) {
            action.setClickable(false);
            action.setTextColor(UIHelper.getColor(R.color.white));
            action.setBackgroundResource(R.drawable.bg_red_un_login);
        } else {
            action.setClickable(true);
            action.setTextColor(UIHelper.getColor(R.color.white));
            action.setBackgroundResource(R.drawable.bg_red_login);
        }
    }

    public static void setClickable(Button action, TextView... edits) {
        if (hasEmpty(edits)) {
            action.setClickable(false);
            action.setTextColor(UIHelper.getColor(R.color.white));
            action.setBackgroundResource(R.drawable.bg_btn_unclickable);
        } else {
            action.setClickable(true);
            action.setTextColor(UIHelper.getColor(R.color.white));
            action.setBackgroundResource(R.drawable.selector_btn_press);
        }
    }

    public static void setClickable(Button action, boolean isTrue) {
        if (!isTrue) {
            action.setClickable(false);
            action.setTextColor(UIHelper.getColor(R.color.white));
            action.setBackgroundResource(R.drawable.bg_btn_unclickable);
        } else {
            action.setClickable(true);
            action.setTextColor(UIHelper.getColor(R.color.white));
            action.setBackgroundResource(R.drawable.selector_btn_press);
        }
    }
     public static void showHtmlContent(TextView tv, String content) {
        if (TextUtils.isEmpty(content)) {
            tv.setText("");
        } else {
            tv.setText(Html.fromHtml(content));
        }
    }
    /**
     * dp转px
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 字符串转16进制整数
     */
    public static int getColor(String color) {
        if (TextUtils.isEmpty(color) || !checkColor(color)) {
            return 0;
        }
        return Color.parseColor(color);
    }
    public static boolean checkColor(String color) {
        if (TextUtils.isEmpty(color))
            return false;
        return color.matches("^#([0-9a-fA-F]{6}|[0-9a-fA-F]{8})$");
    }
//    public static void setClickable(Button action, boolean isTrue) {
//        if (!isTrue) {
//            action.setClickable(false);
//            action.setTextColor(UIHelper.getColor(R.color.white));
//            action.setBackgroundResource(R.drawable.bg_red_un_login);
//        } else {
//            action.setClickable(true);
//            action.setTextColor(UIHelper.getColor(R.color.white));
//            action.setBackgroundResource(R.drawable.bg_red_login);
//        }
//    }

}
