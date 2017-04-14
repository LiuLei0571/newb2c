package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class UploadResult {
    @SerializedName("filePath")
    private String filePath;

    private ResponseResult result;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ResponseResult getResult() {
        return result;
    }

    public void setResult(ResponseResult result) {
        this.result = result;
    }

}
