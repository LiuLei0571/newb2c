package com.example.b2c.net.response.base;

import com.google.gson.annotations.SerializedName;

/**
 * 用途：
 * Created by milk on 16/11/15.
 * 邮箱：649444395@qq.com
 */

public class BaseHasnext  {
    @SerializedName("hasNext")
    protected boolean hasNext;

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
}
