package com.example.b2c.net.response.logistics;

import com.example.b2c.net.response.ResponseResult;
import com.example.b2c.net.response.StaffOrderList;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetStaffOrderResult {
    private List<StaffOrderList> rows;
    protected boolean hasNext;

    public List<StaffOrderList> getRows() {
        return rows;
    }

    public void setRows(List<StaffOrderList> rows) {
        this.rows = rows;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
    public class StaffOrderList {

        private int id;
        private String orderCode;
        private String deliveryNo;
        private int orderStatus;
        private int orderDetailCount;
        private String samplePic;

        public String getActualPayPrice() {
            return actualPayPrice;
        }

        public void setActualPayPrice(String actualPayPrice) {
            this.actualPayPrice = actualPayPrice;
        }

        private String actualPayPrice;

        public SellerInfo getSellerInfo() {
            return sellerInfo;
        }

        public void setSellerInfo(SellerInfo sellerInfo) {
            this.sellerInfo = sellerInfo;
        }

        public BuyerInfo getBuyerInfo() {
            return buyerInfo;
        }

        public void setBuyerInfo(BuyerInfo buyerInfo) {
            this.buyerInfo = buyerInfo;
        }

        private SellerInfo sellerInfo;


        private BuyerInfo buyerInfo;



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

        public int getOrderDetailCount() {
            return orderDetailCount;
        }

        public void setOrderDetailCount(int orderDetailCount) {
            this.orderDetailCount = orderDetailCount;
        }

        public String getSamplePic() {
            return samplePic;
        }

        public void setSamplePic(String samplePic) {
            this.samplePic = samplePic;
        }



        public class SellerInfo{
            private String sellerName;
            private String sellerMobile;
            private String shippingAddress;
            public String getSellerName() {
                return sellerName;
            }

            public void setSellerName(String sellerName) {
                this.sellerName = sellerName;
            }

            public String getSellerMobile() {
                return sellerMobile;
            }

            public void setSellerMobile(String sellerMobile) {
                this.sellerMobile = sellerMobile;
            }

            public String getShippingAddress() {
                return shippingAddress;
            }

            public void setShippingAddress(String shippingAddress) {
                this.shippingAddress = shippingAddress;
            }


        }

    }
    public class BuyerInfo{
        private String buyerName;
        private String buyerMobile;
        private String receiveAddress;
        private String payAddress;

        public String getBuyerName() {
            return buyerName;
        }

        public void setBuyerName(String buyerName) {
            this.buyerName = buyerName;
        }

        public String getBuyerMobile() {
            return buyerMobile;
        }

        public void setBuyerMobile(String buyerMobile) {
            this.buyerMobile = buyerMobile;
        }

        public String getReceiveAddress() {
            return receiveAddress;
        }

        public void setReceiveAddress(String receiveAddress) {
            this.receiveAddress = receiveAddress;
        }

        public String getPayAddress() {
            return payAddress;
        }

        public void setPayAddress(String payAddress) {
            this.payAddress = payAddress;
        }
    }
}
