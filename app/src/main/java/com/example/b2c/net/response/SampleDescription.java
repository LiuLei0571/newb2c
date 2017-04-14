package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yy on 2017/2/28.
 */

public class SampleDescription implements Serializable {

    @SerializedName("sampleDetailMessage")
    private String sampleDetailMessage;

    @SerializedName("sampleDetailImageList")
    private List<String> sampleDetailImageList;

    public String getSampleDetailMessage() {
        return sampleDetailMessage;
    }

    public void setSampleDetailMessage(String sampleDetailMessage) {
        this.sampleDetailMessage = sampleDetailMessage;
    }

    public List<String> getSampleDetailImageList() {
        return sampleDetailImageList;
    }

    public void setSampleDetailImageList(List<String> sampleDetailImageList) {
        this.sampleDetailImageList = sampleDetailImageList;
    }
}
