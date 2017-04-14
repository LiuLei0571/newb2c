package com.example.b2c.net.response;

import com.example.b2c.net.response.common.ImageItem;

import java.util.List;

/**
 * 用途：
 * 作者：Created by john on 2017/2/8.
 * 邮箱：liulei2@aixuedai.com
 */


public class ImageListResult {
    public List<ImageItem> sampleDetailImages;
    public String sampleDetailMessage;
    public ImageListResult(List<ImageItem> mListData, String name) {
        this.sampleDetailImages = mListData;
        this.sampleDetailMessage=name;
    }

    public ImageListResult() {

    }

    public List<ImageItem> getSampleDetailImages() {
        return sampleDetailImages;
    }

    public void setSampleDetailImages(List<ImageItem> sampleDetailImages) {
        this.sampleDetailImages = sampleDetailImages;
    }

    public String getSampleDetailMessage() {
        return sampleDetailMessage;
    }

    public void setSampleDetailMessage(String sampleDetailMessage) {
        this.sampleDetailMessage = sampleDetailMessage;
    }
}
