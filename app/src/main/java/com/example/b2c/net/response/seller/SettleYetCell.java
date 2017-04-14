package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 已结算信息cell
 * Created by yy on 2017/2/7.
 */

public class SettleYetCell implements Serializable{

    @SerializedName("batchesNo")
    private String batchesNo;
    @SerializedName("money")
    private String money;
    @SerializedName("createTime")
    private long createTime;

    public String getBatchesNo() {
        return batchesNo;
    }

    public String getMoney() {
        return money;
    }

    public long getCreateTime() {
        return createTime;
    }
}
