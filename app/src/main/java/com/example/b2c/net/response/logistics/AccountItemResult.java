package com.example.b2c.net.response.logistics;

import java.io.Serializable;
import java.util.List;

/**
 * 用途：
 * Created by milk on 16/11/13.
 * 邮箱：649444395@qq.com
 */

public class AccountItemResult {
    private List<AccountItem> rows;
    private boolean hasNext;
    public List<AccountItem> getRows() {
        return rows;
    }

    public void setRows(List<AccountItem> rows) {
        this.rows = rows;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
    public class AccountItem  {
        private int accountId;
        private String firstName;
        private String lastName;
        private String remark;
        private String mobile;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        private String email;
        private String loginName;
        private int status;
        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }



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

    }

}
