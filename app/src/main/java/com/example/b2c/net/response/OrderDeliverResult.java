package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yy on 2017/2/13.
 */
public class OrderDeliverResult implements Serializable {

    @SerializedName("deliveryName")
    private String deliveryName;
    @SerializedName("deliveryNo")
    private String deliveryNo;
    @SerializedName("orderStatus")
    private int orderStatus;
    @SerializedName("rows")
    private List<TimeLine> rows;

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<TimeLine> getRows() {
        return rows;
    }

    public void setRows(List<TimeLine> rows) {
        this.rows = rows;
    }
}
