package com.example.b2c.net.response.logistics;

import java.util.List;

/**
 * Created by ThinkPad on 2017/2/16.
 */

public class AccessBean {
    private List<CodeList> codeList;

    public List<CodeList> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<CodeList> codeList) {
        this.codeList = codeList;
    }

    public class CodeList{
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        private String value;
    }

}
