package com.example.b2c.net.response.logistics;

import android.inputmethodservice.Keyboard;

import java.util.List;

/**
 *
 */

public class MyOrderAdapterBean {
    private boolean hasNext;

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<Rows> getRows() {
        return rows;
    }

    public void setRows(List<Rows> rows) {
        this.rows = rows;
    }

    private List<Rows>rows;
    public class Rows{
        private double actualPayPrice;
        private String code;
        private String createTime;
        private double deliveryFee;
        private String deliveryNo;
        private int id;
        private String modifyTime;
        private String payAddress;
        private String payFirstName;
        private String payLastName;
        private String payMobile;
        private String receiveAddress;
        private String receiveFirsName;
        private String deliveryAccountFirstName;

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        private int orderStatus;

        public String getDeliveryAccountLateName() {
            return deliveryAccountLateName;
        }

        public void setDeliveryAccountLateName(String deliveryAccountLateName) {
            this.deliveryAccountLateName = deliveryAccountLateName;
        }

        public String getDeliveryAccountFirstName() {
            return deliveryAccountFirstName;
        }

        public void setDeliveryAccountFirstName(String deliveryAccountFirstName) {
            this.deliveryAccountFirstName = deliveryAccountFirstName;
        }

        private String deliveryAccountLateName;

        public double getActualPayPrice() {
            return actualPayPrice;
        }

        public void setActualPayPrice(double actualPayPrice) {
            this.actualPayPrice = actualPayPrice;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public double getDeliveryFee() {
            return deliveryFee;
        }

        public void setDeliveryFee(double deliveryFee) {
            this.deliveryFee = deliveryFee;
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

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
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

        public String getReceiveFirsName() {
            return receiveFirsName;
        }

        public void setReceiveFirsName(String receiveFirsName) {
            this.receiveFirsName = receiveFirsName;
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

        public String getSellerMobile() {
            return sellerMobile;
        }

        public void setSellerMobile(String sellerMobile) {
            this.sellerMobile = sellerMobile;
        }

        public String getSellerName() {
            return sellerName;
        }

        public void setSellerName(String sellerName) {
            this.sellerName = sellerName;
        }

        private String receiveLastName;
        private String receiveMobile;
        private String sellerMobile;
        private String sellerName;
    }
}
