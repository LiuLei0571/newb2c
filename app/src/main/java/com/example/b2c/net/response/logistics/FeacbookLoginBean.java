package com.example.b2c.net.response.logistics;

/**
 * Created by ThinkPad on 2017/2/25.
 */

public class FeacbookLoginBean {
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
    private Data data;
    private Meta meta;
    public class Data{
        private Res res;

        public Res getRes() {
            return res;
        }

        public void setRes(Res res) {
            this.res = res;
        }
    }
    public class Res{
        private String token;
        private int userId;
        private String loginName;
        private int userType;
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }
    }
    public class Meta{
        private String errorInfo;
        private boolean success;
        private int errorNO;

        public String getErrorInfo() {
            return errorInfo;
        }

        public void setErrorInfo(String errorInfo) {
            this.errorInfo = errorInfo;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public int getErrorNO() {
            return errorNO;
        }

        public void setErrorNO(int errorNO) {
            this.errorNO = errorNO;
        }
    }

}
