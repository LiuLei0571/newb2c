package com.example.b2c.net.response.logistics;

import com.example.b2c.net.response.seller.OrderDetailLogistic;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用途：
 * Created by milk on 16/11/15.
 * 邮箱：649444395@qq.com
 */

public class LogisticsOrderDetail {
    @SerializedName("id")
    private int id;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("totalPrice")
    private String totalPrice;
    @SerializedName("orderCode")
    private String orderCode;
    @SerializedName("deliveryNo")
    private String deliveryNo;
    @SerializedName("buyerName")
    private String buyerName;
    @SerializedName("buyerAddress")
    private String buyerAddress;
    @SerializedName("buyerMobile")
    private String buyerMobile;
    @SerializedName("sellerName")
    private String sellerName;
    @SerializedName("sellerAddress")
    private String sellerAddress;
    @SerializedName("sellerMobile")
    private String sellerMobile;
    @SerializedName("rows")
    private List<OrderDetailLogistic> rows;
    @SerializedName("isFinish")
    private int isFinish;
    @SerializedName("finishTime")
    private String finishTime;
    @SerializedName("shopName")
    private String shopName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getBuyerMobile() {
        return buyerMobile;
    }

    public void setBuyerMobile(String buyerMobile) {
        this.buyerMobile = buyerMobile;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getSellerMobile() {
        return sellerMobile;
    }

    public void setSellerMobile(String sellerMobile) {
        this.sellerMobile = sellerMobile;
    }

    public List<OrderDetailLogistic> getRows() {
        return rows;
    }

    public void setRows(List<OrderDetailLogistic> rows) {
        this.rows = rows;
    }

    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
