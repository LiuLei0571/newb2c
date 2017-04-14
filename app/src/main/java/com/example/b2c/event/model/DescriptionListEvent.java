package com.example.b2c.event.model;

import java.util.List;

/**
 * 用途：
 * 作者：Created by john on 2017/2/8.
 * 邮箱：liulei2@aixuedai.com
 */


public class DescriptionListEvent {
    public List<String> mListData;
    public List<String> localUrl;
    public String name;

    public DescriptionListEvent(List<String> mListData, String name,List<String> localUrl) {
        this.mListData = mListData;
        this.name = name;
        this.localUrl=localUrl;
    }
}
