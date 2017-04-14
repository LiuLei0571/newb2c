package com.example.b2c.net.response.logistics;

import java.util.Date;
import java.util.List;

/**
 * 保证金变动明细
 */

public class BaozhengjinBandong {

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

           private List<Rows> rows;

    public class Rows{

        public int getDeliveryAccountId() {
            return deliveryAccountId;
        }

        public void setDeliveryAccountId(int deliveryAccountId) {
            this.deliveryAccountId = deliveryAccountId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        private String createTime;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        private int deliveryAccountId;
        private String money;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        private int type;
        private int status;


        public String getAuditFeedback() {
            return AuditFeedback;
        }

        public void setAuditFeedback(String auditFeedback) {
            AuditFeedback = auditFeedback;
        }

        private String AuditFeedback;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
