package com.example.b2c.net.response.seller;

import java.io.Serializable;
import java.util.List;

/**
 * 用途：
 * 作者：Created by john on 2017/2/27.
 * 邮箱：liulei2@aixuedai.com
 */


public class DescriptionModule implements Serializable {
    private String sampleDetailMessage;
    private List<String> sampleDetailImages;
    private List<String> localUrl;
    public String getSampleDetailMessage() {
        return sampleDetailMessage;
    }

    public void setSampleDetailMessage(String sampleDetailMessage) {
        this.sampleDetailMessage = sampleDetailMessage;
    }

    public List<String> getSampleDetailImages() {
        return sampleDetailImages;
    }

    public void setSampleDetailImages(List<String> sampleDetailImages) {
        this.sampleDetailImages = sampleDetailImages;
    }

    public List<String> getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(List<String> localUrl) {
        this.localUrl = localUrl;
    }
}
