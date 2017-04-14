package com.example.b2c.net.response.logistics;

import java.util.List;

/**
 * 冻结保证金的实体
 */

public class DongjieBaozhengjinBean {
    private boolean hasNext;
    private List<Rows>rows;

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

    public class Rows{
        private String createTime;
        private String Money;
        private int id;

        public String getMoney() {
            return Money;
        }

        public void setMoney(String money) {
            Money = money;
        }

        private int orderId;
        private String remark;
        private String AuditFeedback;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        private int type;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getAuditFeedback() {
            return AuditFeedback;
        }

        public void setAuditFeedback(String auditFeedback) {
            AuditFeedback = auditFeedback;
        }
    }
}
