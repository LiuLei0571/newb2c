package com.example.b2c.net.response.logistics;

import java.util.List;

/**
 * 子账户列表
 */

public class ChildAccountListBean {
            private Boolean hasNext;
            private List<Rows> rows;
            public Boolean getHasNext() {
                return hasNext;
            }

            public void setHasNext(Boolean hasNext) {
                this.hasNext = hasNext;
            }

            public List<Rows> getRows() {
                return rows;
            }

            public void setRows(List<Rows> rows) {
                this.rows = rows;
            }

      public  class Rows{
            private int accountId;
            private String firstName;
            private String lastName;
            private String remark;
            private String mobile;

            public int getAccountId() {
                return accountId;
            }

            public void setAccountId(int accountId) {
                this.accountId = accountId;
            }

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }
        }

}
