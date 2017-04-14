package com.example.b2c.net.response.seller;

import java.util.List;

/**
 * 用途：
 * 作者：Created by john on 2017/2/27.
 * 邮箱：liulei2@aixuedai.com
 */


public class SampleProInfosModule {
    private String price;
    private String amount;
    private String propertyIdsSTR;
    private List<Integer> propertyIds;
    private String proDetailIdsSTR;
    private List<Integer> proDetailIds;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<Integer> getPropertyIds() {
        return propertyIds;
    }

    public void setPropertyIds(List<Integer> propertyIds) {
        this.propertyIds = propertyIds;
    }

    public List<Integer> getProDetailIds() {
        return proDetailIds;
    }

    public void setProDetailIds(List<Integer> proDetailIds) {
        this.proDetailIds = proDetailIds;
    }

    public String getPropertyIdsSTR() {
        return propertyIdsSTR;
    }

    public void setPropertyIdsSTR(String propertyIdsSTR) {
        this.propertyIdsSTR = propertyIdsSTR;
    }

    public String getProDetailIdsSTR() {
        return proDetailIdsSTR;
    }

    public void setProDetailIdsSTR(String proDetailIdsSTR) {
        this.proDetailIdsSTR = proDetailIdsSTR;
    }
}
