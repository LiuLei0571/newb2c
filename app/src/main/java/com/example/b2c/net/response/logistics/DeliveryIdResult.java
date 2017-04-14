package com.example.b2c.net.response.logistics;

import com.google.gson.annotations.SerializedName;

/**
 * 用途：
 * Created by milk on 16/11/9.
 * 邮箱：649444395@qq.com
 */

public class DeliveryIdResult {
    @SerializedName("deliveryCompanyId")
    int deliveryCompanyId;

    public int getDeliveryCompanyId() {
        return deliveryCompanyId;
    }

    public void setDeliveryCompanyId(int deliveryCompanyId) {
        this.deliveryCompanyId = deliveryCompanyId;
    }
}
