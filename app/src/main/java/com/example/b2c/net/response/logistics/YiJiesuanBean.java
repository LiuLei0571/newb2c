package com.example.b2c.net.response.logistics;

import java.util.List;

/**
 * Created by ThinkPad on 2017/2/13.
 */

public class YiJiesuanBean {
    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    private boolean hasNext;
    private List<Rows> rows;

    public List<Rows> getRows() {
        return rows;
    }

    public void setRows(List<Rows> rows) {
        this.rows = rows;
    }

    public class Rows{
        private String deliveryActualTransferMoney;
        private String code;
        private String date;
        private String deliveryFee;
        private int tag;
        private String actualPayPrice;

        public String getDeliveryFee() {
            return deliveryFee;
        }

        public void setDeliveryFee(String deliveryFee) {
            this.deliveryFee = deliveryFee;
        }

        public String getActualPayPrice() {
            return actualPayPrice;
        }

        public void setActualPayPrice(String actualPayPrice) {
            this.actualPayPrice = actualPayPrice;
        }

        private String deliveryNo;

        public String getBatchesNo() {
            return batchesNo;
        }

        public void setBatchesNo(String batchesNo) {
            this.batchesNo = batchesNo;
        }

        private String batchesNo;
        private String noPrice;

        public String getNoPrice() {
            return noPrice;
        }

        public void setNoPrice(String noPrice) {
            this.noPrice = noPrice;
        }

        public String getDeliveryNo() {
            return deliveryNo;
        }

        public void setDeliveryNo(String deliveryNo) {
            this.deliveryNo = deliveryNo;
        }



        public String getDeliveryActualTransferMoney() {
            return deliveryActualTransferMoney;
        }

        public void setDeliveryActualTransferMoney(String deliveryActualTransferMoney) {
            this.deliveryActualTransferMoney = deliveryActualTransferMoney;
        }



        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }



        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
