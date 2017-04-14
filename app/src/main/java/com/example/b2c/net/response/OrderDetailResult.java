package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailResult {
    @SerializedName("shopName")
    private String shopName = "";
    @SerializedName("shopId")
    private int shopId;
    @SerializedName("orderCode")
    private String orderCode = "";
    @SerializedName("receiveAddress")
    private String receiveAddress = "";
    @SerializedName("receiveLinkman")
    private String receiveLinkman = "";
    @SerializedName("receiveFirstName")
    private String receiveFirstName;
    @SerializedName("receiveLastName")
    private String receiveLastName;
    @SerializedName("receiveMobile")
    private String receiveMobile = "";
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("payTime")
    private String payTime;
    @SerializedName("orderDetailList")
    private List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
    @SerializedName("deliveryFee")
    private String deliveryFee;
    @SerializedName("totalPrice")
    private String totalPrice;
    @SerializedName("payFirstName")
    private String payFirstName;
    @SerializedName("payLastName")
    private String payLastName;
    @SerializedName("payMobile")
    private String payMobile;
    @SerializedName("payAddress")
    private String payAddress;
    @SerializedName("deliveryNo")
    private String deliveryNo;
    @SerializedName("hasEvaluated")
    private int hasEvaluated;//已评价、未评价



    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceiveLinkman() {
        return receiveLinkman;
    }

    public void setReceiveLinkman(String receiveLinkman) {
        this.receiveLinkman = receiveLinkman;
    }

    public String getReceiveMobile() {
        return receiveMobile;
    }

    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getReceiveFirstName() {
        return receiveFirstName;
    }

    public void setReceiveFirstName(String receiveFirstName) {
        this.receiveFirstName = receiveFirstName;
    }

    public String getReceiveLastName() {
        return receiveLastName;
    }

    public void setReceiveLastName(String receiveLastName) {
        this.receiveLastName = receiveLastName;
    }

    public String getPayFirstName() {
        return payFirstName;
    }

    public void setPayFirstName(String payFirstName) {
        this.payFirstName = payFirstName;
    }

    public String getPayLastName() {
        return payLastName;
    }

    public void setPayLastName(String payLastName) {
        this.payLastName = payLastName;
    }

    public String getPayMobile() {
        return payMobile;
    }

    public void setPayMobile(String payMobile) {
        this.payMobile = payMobile;
    }

    public String getPayAddress() {
        return payAddress;
    }

    public void setPayAddress(String payAddress) {
        this.payAddress = payAddress;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public int getHasEvaluated() {
        return hasEvaluated;
    }

    public void setHasEvaluated(int hasEvaluated) {
        this.hasEvaluated = hasEvaluated;
    }
}
