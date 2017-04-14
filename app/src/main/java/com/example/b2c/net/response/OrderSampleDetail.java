package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class OrderSampleDetail {
	@SerializedName("status")
	private int status;
	@SerializedName("samplePrice")
	private String samplePrice;
	@SerializedName("amount")
	private int amount;
	@SerializedName("totalPrice")
	private String totalPrice;
	@SerializedName("sampleImage")
	private String sampleImage;
	@SerializedName("price")
	private String price;
	@SerializedName("sampleId")
	private int sampleId;
	@SerializedName("specification")
	private String specification;
	@SerializedName("sampleName")
	private String sampleName;
	@SerializedName("orderId")
	private int orderId;
	@SerializedName("id")
	private int id;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSamplePrice() {
		return samplePrice;
	}

	public void setSamplePrice(String samplePrice) {
		this.samplePrice = samplePrice;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
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

	public int getSampleId() {
		return sampleId;
	}

	public void setSampleId(int sampleId) {
		this.sampleId = sampleId;
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

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
