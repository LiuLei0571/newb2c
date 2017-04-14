package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class ResponseResult {
	@SerializedName("success")
	private boolean success;
	@SerializedName("errorNO")
	private int errorNO;
	@SerializedName("errorInfo")
	private String errorInfo;

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getErrorNO() {

		return errorNO;
	}

	public void setErrorNO(int errorNO) {
		this.errorNO = errorNO;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

}
