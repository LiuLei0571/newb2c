package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class FamousShopDetail {
	@SerializedName("shopId")
	private int shopId;
	@SerializedName("url")
	private String url;
	@SerializedName("id")
	private int id;
	@SerializedName("status")
	private int status;
	@SerializedName("picPath")
	private String picPath;
	@SerializedName("ranking")
	private int ranking;
	@SerializedName("createTime")
	private String createTime;
	@SerializedName("modifyTime")
	private String modifyTime;

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
