package com.example.b2c.net.response.logistics;

import com.example.b2c.net.response.base.BaseHasnext;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * 用途：结算记录
 * Created by milk on 16/11/17.
 * 邮箱：649444395@qq.com
 */

public class SettlementDetailResult {
    private boolean hasNext;

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<SettlementDetail> getRows() {
        return rows;
    }

    public void setRows(List<SettlementDetail> rows) {
        this.rows = rows;
    }

    private List<SettlementDetail> rows;

    public class SettlementDetail {
        private String batchesNo;
        private String money;
        private String actualMoney;
        private int status;

        public String getBatchesNo() {
            return batchesNo;
        }

        public void setBatchesNo(String batchesNo) {
            this.batchesNo = batchesNo;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getActualMoney() {
            return actualMoney;
        }

        public void setActualMoney(String actualMoney) {
            this.actualMoney = actualMoney;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getAuditFeedback() {
            return auditFeedback;
        }

        public void setAuditFeedback(String auditFeedback) {
            this.auditFeedback = auditFeedback;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        private String auditFeedback;
        private String modifyTime;




    }
}
