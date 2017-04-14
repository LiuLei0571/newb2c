package com.example.b2c.net.response.logistics;

import java.util.List;

/**
 * Created by ThinkPad on 2017/2/14.
 */

public class MuBanBean {
private boolean hasNext;
    private List<Rows>templateDetails;

    public List<Rows> getTemplateDetails() {
        return templateDetails;
    }

    public void setTemplateDetails(List<Rows> templateDetails) {
        this.templateDetails = templateDetails;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public class Rows{
        private String shippingProvinceName;
        private String shippingCityName;
        private String receiveProvinceName;


        public String getShippingProvinceName() {
            return shippingProvinceName;
        }

        public void setShippingProvinceName(String shippingProvinceName) {
            this.shippingProvinceName = shippingProvinceName;
        }

        public String getShippingCityName() {
            return shippingCityName;
        }

        public void setShippingCityName(String shippingCityName) {
            this.shippingCityName = shippingCityName;
        }

        public String getReceiveProvinceName() {
            return receiveProvinceName;
        }

        public void setReceiveProvinceName(String receiveProvinceName) {
            this.receiveProvinceName = receiveProvinceName;
        }

        public String getReceiveCityName() {
            return receiveCityName;
        }

        public void setReceiveCityName(String receiveCityName) {
            this.receiveCityName = receiveCityName;
        }

        public String getDeliveryTimeCost() {
            return deliveryTimeCost;
        }

        public void setDeliveryTimeCost(String deliveryTimeCost) {
            this.deliveryTimeCost = deliveryTimeCost;
        }

        public double getMinWeight() {
            return minWeight;
        }

        public void setMinWeight(double minWeight) {
            this.minWeight = minWeight;
        }

        public double getMaxWeight() {
            return maxWeight;
        }

        public void setMaxWeight(double maxWeight) {
            this.maxWeight = maxWeight;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        private String receiveCityName;
        private String deliveryTimeCost;
        private double minWeight;
        private double maxWeight;
        private String fee;
    }
}
