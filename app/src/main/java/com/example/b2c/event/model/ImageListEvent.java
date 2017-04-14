package com.example.b2c.event.model;

import com.example.b2c.net.response.common.ImageItem;

import java.util.List;

/**
 * 用途：
 * 作者：Created by john on 2017/2/8.
 * 邮箱：liulei2@aixuedai.com
 */


public class ImageListEvent {
    public List<ImageItem> mListData;
    public String name;
    public ImageListEvent(List<ImageItem> mListData,String name) {
        this.mListData = mListData;
        this.name=name;
    }
}
