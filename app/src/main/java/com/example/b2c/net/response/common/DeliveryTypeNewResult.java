package com.example.b2c.net.response.common;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用途：
 * 作者：Created by john on 2017/2/9.
 * 邮箱：liulei2@aixuedai.com
 */


public class DeliveryTypeNewResult {
    @SerializedName("deliveryTypeNew")
    private List<TranslationResult> deliveryTypeNew;

    public List<TranslationResult> getDeliveryTypeNew() {
        return deliveryTypeNew;
    }

    public void setDeliveryTypeNew(List<TranslationResult> deliveryTypeNew) {
        this.deliveryTypeNew = deliveryTypeNew;
    }
}
