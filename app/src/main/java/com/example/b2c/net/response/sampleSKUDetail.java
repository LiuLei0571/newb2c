package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class sampleSKUDetail implements Serializable{
	@SerializedName("id")
	private int id;
	@SerializedName("propertyIds")
	private String propertyIds;
	@SerializedName("proDetailIds")
	private String proDetailIds;
	@SerializedName("amount")
	private int amount;
	@SerializedName("price")
	private String price;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProDetailIds() {
		return proDetailIds;
	}
	public void setProDetailIds(String proDetailIds) {
		this.proDetailIds = proDetailIds;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPropertyIds() {
		return propertyIds;
	}
	public void setPropertyIds(String propertyId) {
		this.propertyIds = propertyIds;
	}
}
