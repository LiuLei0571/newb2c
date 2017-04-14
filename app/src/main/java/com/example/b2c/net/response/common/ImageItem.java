package com.example.b2c.net.response.common;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * 用途：
 * Created by milk on 17/2/7.
 * 邮箱：649444395@qq.com
 */

public class ImageItem implements Serializable {
    private boolean isSelected = false;
    private String url;
    public String imageId;
    private String newUrl;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private Bitmap bitmap;
    public String getNewUrl() {
        return newUrl;
    }

    public void setNewUrl(String newUrl) {
        this.newUrl = newUrl;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
