package com.example.b2c.net.response.logistics;

/**
 * 服务器返回的信息的bean实体
 */

public class MetaBean {
    private Meta meta;
    public Meta getMeta() {
        return meta;
    }
    public void setMeta(Meta meta) {
        this.meta = meta;
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
