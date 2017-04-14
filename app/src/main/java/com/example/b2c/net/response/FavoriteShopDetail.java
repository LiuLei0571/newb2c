package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class FavoriteShopDetail {
	@SerializedName("targetName")
	private String targetName;
	@SerializedName("targetId")
	private int targetId;
	@SerializedName("picPath")
	private String picPath;
	@SerializedName("shopIntro")
	private String shopIntro;
	@SerializedName("shopAddress")
	private String shopAddress;
	@SerializedName("id")
	private int id;

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public int getTargetId() {
		return targetId;
	}

	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getShopIntro() {
		return shopIntro;
	}

	public void setShopIntro(String shopIntro) {
		this.shopIntro = shopIntro;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
