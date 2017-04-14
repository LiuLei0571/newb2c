package com.example.b2c.widget.util;

import java.io.Serializable;
import java.util.Map;

/**
 * 用途：
 * 作者：Created by john on 2016/12/7.
 * 邮箱：liulei2@aixuedai.com
 */


public class SerializableMap implements Serializable {
    private Map<String,String> parmas;

    public Map<String, String> getParmas() {
        return parmas;
    }

    public void setParmas(Map<String, String> parmas) {
        this.parmas = parmas;
    }
}
