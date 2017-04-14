package com.example.b2c.observer;

import java.util.HashSet;
import java.util.Set;

/**
 * 用途：
 * 作者：Created by john on 2017/2/22.
 * 邮箱：liulei2@aixuedai.com
 */


public enum ObserverTypes {
    HOME("home"),
    DISCOVERY("discovery"),
    BUYERLOGIN("buyerlogin"),
    GoodsSpecification(" goodsSpecification");
    ObserverTypes(String type) {
        this.type = type;
    }

    public static Set<String> setPls;
    private String type = "";

    static {
        setPls = new HashSet<>();
        ObserverTypes[] values = values();
        for (ObserverTypes typer : values) {
            setPls.add(typer.getType());
        }
    }

    public String getType() {
        return type;
    }

    public static Set<String> getValue() {
        return setPls;
    }
}
