package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by yy on 2017/2/7.
 * 卖家财务信息 Cell
 */
public class FinaceCell implements Serializable {
    @SerializedName("type")
    private int type;//类型
    @SerializedName("money")
    private String  money;//金额

    public int getType() {
        return type;
    }

    public String getMoney() {
        return money;
    }

}
