package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UploadListResult {
    @SerializedName("filePath")
    private List<String> filePath;

    private ResponseResult result;

    public List<String> getFilePath() {
        return filePath;
    }

    public void setFilePath(List<String> filePath) {
        this.filePath = filePath;
    }

    public ResponseResult getResult() {
        return result;
    }

    public void setResult(ResponseResult result) {
        this.result = result;
    }

}
