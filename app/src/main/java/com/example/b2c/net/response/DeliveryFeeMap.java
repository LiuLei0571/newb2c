package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class DeliveryFeeMap {
	@SerializedName("deliveryFeeType")
	private int deliveryFeeType;
	@SerializedName("deliveryFee")
	private String deliveryFee;
	public int getDeliveryFeeType() {
		return deliveryFeeType;
	}
	public void setDeliveryFeeType(int deliveryFeeType) {
		this.deliveryFeeType = deliveryFeeType;
	}
	public String getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(String deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	
}
