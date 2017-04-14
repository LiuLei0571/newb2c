package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class ShoppingCartSettleReturnDetail {
	@SerializedName("sampleName")
	private String sampleName;
	@SerializedName("sampleId")
	private int sampleId;
	public String getSampleName() {
		return sampleName;
	}
	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}
	public int getSampleId() {
		return sampleId;
	}
	public void setSampleId(int sampleId) {
		this.sampleId = sampleId;
	}
	
}
