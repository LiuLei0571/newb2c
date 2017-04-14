package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class OrderDetail {
	@SerializedName("status")
	private int status;
	@SerializedName("amount")
	private int amount;
	@SerializedName("sampleImage")
	private String sampleImage = "";
	@SerializedName("price")
	private String price;
	@SerializedName("specification")
	private String specification = "";
	@SerializedName("sampleName")
	private String sampleName;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getSampleImage() {
		return sampleImage;
	}

	public void setSampleImage(String sampleImage) {
		this.sampleImage = sampleImage;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

}
