package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class BankListDetail {
	@SerializedName("status")
	private int status;
	@SerializedName("code")
	private int code;
	@SerializedName("bankName")
	private String bankName;
	@SerializedName("id")
	private int id;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
