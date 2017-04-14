package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetShopHomeResult {
	@SerializedName("shopName")
	private String shopName;
	@SerializedName("isFavorite")
	private int isFavorite;
	@SerializedName("shopId")
	private int shopId;
	@SerializedName("sellerId")
	private int sellerId;
	@SerializedName("logo")
	private String logo;
	@SerializedName("sampleList")
	private List<SampleListDetail> sampleList;

	private ResponseResult result;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public int getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(int isFavorite) {
		this.isFavorite = isFavorite;
	}

	public ResponseResult getResult() {
		return result;
	}

	public void setResult(ResponseResult result) {
		this.result = result;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public List<SampleListDetail> getSampleList() {
		return sampleList;
	}

	public void setSampleList(List<SampleListDetail> sampleList) {
		this.sampleList = sampleList;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
}
