package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 快递详情
 * Created by yy on 2017/2/5.
 */
public class LogisticDetailFarther implements Serializable {

    @SerializedName("deliveryCompanyInfo")
    private LogisticDetail logisticDetail;


    @SerializedName("contactPersonInfo")
    private LogisticContactInfo ogisticContactInfo;

    public LogisticContactInfo getOgisticContactInfo() {
        return ogisticContactInfo;
    }

    public LogisticDetail getLogisticDetail() {
        return logisticDetail;
    }
}
