package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class OrderCell {
	
	@SerializedName("id")
	private   int id;
	@SerializedName("code")
	private  String code;
	@SerializedName("totalPrice")
	private  String totalPrice;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	

}
