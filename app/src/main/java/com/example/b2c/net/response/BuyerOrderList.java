package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BuyerOrderList implements Serializable{

    @SerializedName("orderId")
    private int orderId;
    @SerializedName("shopId")
    private int shopId;
    @SerializedName("shopName")
    private String shopName;
    @SerializedName("totalPrice")
    private String totalPrice;
    @SerializedName("actualPayPrice")
    private String actualPayPrice;
    @SerializedName("discount")
    private String discount;
    @SerializedName("orderStatus")
    private int orderStatus;
    @SerializedName("orderDetailList")
    private List<BuyerOrderDetail> orderDetailList;
    @SerializedName("statusName")
    private String statusName;
    @SerializedName("deliveryFee")
    private String deliveryFee;
    @SerializedName("deliveryNo")
    private String deliveryNo;
    @SerializedName("sellerId")
    private String sellerId;
    @SerializedName("hasEvaluated")
    private int hasEvaluated;//已评价、未评价

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public List<BuyerOrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<BuyerOrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }


    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public int getHasEvaluated() {
        return hasEvaluated;
    }

    public void setHasEvaluated(int hasEvaluated) {
        this.hasEvaluated = hasEvaluated;
    }

    public String getActualPayPrice() {
        return actualPayPrice;
    }
    public void setActualPayPrice(String actualPayPrice) {
        this.actualPayPrice = actualPayPrice;
    }
    public String getDiscount() {
        return discount;
    }
    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
