package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class FavoriteSampleDetail {
	@SerializedName("id")
	private int id;
	@SerializedName("targetId")
	private int targetId;
	@SerializedName("targetName")
	private String targetName;
	@SerializedName("marketPrice")
	private String marketPrice;
	@SerializedName("picPath")
	private String picPath;
	@SerializedName("samplePrice")
	private String samplePrice;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTargetId() {
		return targetId;
	}

	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getSamplePrice() {
		return samplePrice;
	}

	public void setSamplePrice(String samplePrice) {
		this.samplePrice = samplePrice;
	}


}
