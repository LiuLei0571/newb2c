package com.example.b2c.net.response.logistics;


import java.util.List;

/**
 * Created by ThinkPad on 2017/2/23.
 */

public class OrderBean {
//    private Data data;
//
//    public Data getData() {
//        return data;
//    }
//
//    public void setData(Data data) {
//        this.data = data;
//    }
//
//    public Meta getMeta() {
//        return meta;
//    }
//
//    public void setMeta(Meta meta) {
//        this.meta = meta;
//    }
//
//    private Meta meta;
//    public class Data{
        private boolean hasNext;
        private List<Rows> rows;

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
//    }
//    public class Meta{
//        private String errorInfo;
//        private boolean success;
//        private int errorNO;
//
//        public String getErrorInfo() {
//            return errorInfo;
//        }
//
//        public void setErrorInfo(String errorInfo) {
//            this.errorInfo = errorInfo;
//        }
//
//        public boolean isSuccess() {
//            return success;
//        }
//
//        public void setSuccess(boolean success) {
//            this.success = success;
//        }
//
//        public int getErrorNO() {
//            return errorNO;
//        }
//
//        public void setErrorNO(int errorNO) {
//            this.errorNO = errorNO;
//        }
//    }
    public class Rows{
        private String actualPayPrice;
        private String code;
        private String createTime;
        private String deliveryFee;
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
        private String orderStatus;
        private String receiveLastName;
        private String receiveMobile;
        private String sellerMobile;
        private String sellerName;
private String deliveryAccountLateName;
        public String getDeliveryFee() {
            return deliveryFee;
        }
        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = (orderStatus != null) ? orderStatus : "";
        }

        public String getDeliveryAccountLateName() {
            return deliveryAccountLateName;
        }

        public void setDeliveryAccountLateName(String deliveryAccountLateName) {
            this.deliveryAccountLateName = (deliveryAccountLateName != null) ? deliveryAccountLateName : "";
        }

        public String getDeliveryAccountFirstName() {
            return deliveryAccountFirstName;
        }

        public void setDeliveryAccountFirstName(String deliveryAccountFirstName) {
            this.deliveryAccountFirstName = (deliveryAccountFirstName != null) ? deliveryAccountFirstName : "";
        }



        public String getActualPayPrice() {
            return actualPayPrice;
        }

        public void setActualPayPrice(String actualPayPrice) {
            this.actualPayPrice = (actualPayPrice != null) ? actualPayPrice : "";

        }

        public void setDeliveryFee(String deliveryFee) {
            this.deliveryFee = (deliveryFee != null) ? deliveryFee : "";
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = (code != null) ? code : "";
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = (createTime != null) ? createTime : "";
        }


        public String getDeliveryNo() {
            return deliveryNo;
        }

        public void setDeliveryNo(String deliveryNo) {
            this.deliveryNo = (deliveryNo != null) ? deliveryNo : "";
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
            this.modifyTime = (modifyTime != null) ? modifyTime : "";
        }

        public String getPayAddress() {
            return payAddress;
        }

        public void setPayAddress(String payAddress) {
            this.payAddress = (payAddress != null) ? payAddress : "";
        }

        public String getPayFirstName() {
            return payFirstName;
        }

        public void setPayFirstName(String payFirstName) {
            this.payFirstName = (payFirstName != null) ? payFirstName : "";
        }

        public String getPayLastName() {
            return payLastName;
        }

        public void setPayLastName(String payLastName) {
            this.payLastName = (payLastName != null) ? payLastName : "";
        }

        public String getPayMobile() {
            return payMobile;
        }

        public void setPayMobile(String payMobile) {
            this.payMobile = (payMobile != null) ? payMobile : "";
        }

        public String getReceiveAddress() {
            return receiveAddress;
        }

        public void setReceiveAddress(String receiveAddress) {
            this.receiveAddress = (receiveAddress != null) ? receiveAddress : "";
        }

        public String getReceiveFirsName() {
            return receiveFirsName;
        }

        public void setReceiveFirsName(String receiveFirsName) {
            this.receiveFirsName = (receiveFirsName != null) ? receiveFirsName : "";
        }

        public String getReceiveLastName() {
            return receiveLastName;
        }

        public void setReceiveLastName(String receiveLastName) {
            this.receiveLastName = (receiveLastName != null) ? receiveLastName : "";
        }

        public String getReceiveMobile() {
            return receiveMobile;
        }

        public void setReceiveMobile(String receiveMobile) {
            this.receiveMobile = (receiveMobile != null) ? receiveMobile : "";
        }

        public String getSellerMobile() {
            return sellerMobile;
        }

        public void setSellerMobile(String sellerMobile) {
            this.sellerMobile = (sellerMobile != null) ? sellerMobile : "";
        }

        public String getSellerName() {
            return sellerName;
        }

        public void setSellerName(String sellerName) {
            this.sellerName = (sellerName != null) ? sellerName : "";
        }


    }
}
