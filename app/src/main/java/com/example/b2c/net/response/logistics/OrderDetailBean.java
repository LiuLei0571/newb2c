package com.example.b2c.net.response.logistics;

/**
 * 订单详情的bean
 */

public class OrderDetailBean {
    private String createTime;
    private String deliveryNo;
    private int id;
    private String orderCode;
    private String payAddress;
    private String discount;

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    private String deliveryFee;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPayAddress() {
        return payAddress;
    }

    public void setPayAddress(String payAddress) {
        this.payAddress = payAddress;
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

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
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

    public String getReceiveMobile() {
        return receiveMobile;
    }

    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerMobile() {
        return sellerMobile;
    }

    public void setSellerMobile(String sellerMobile) {
        this.sellerMobile = sellerMobile;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }


    private String payFirstName;
    private String payLastName;
    private String payMobile;
    private String receiveAddress;
    private String receiveFirstName;
    private String receiveLastName;
    private String receiveMobile;
    private int sellerId;
    private String sellerMobile;
    private String shopName;

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    private String totalPrice;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    private int amount;



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
       this.image= (image != null) ? image : "";
    }

    private String image;
}
