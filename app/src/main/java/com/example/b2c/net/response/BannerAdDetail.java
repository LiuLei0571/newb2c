package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class BannerAdDetail {
	@SerializedName("status")
	private int status;
	@SerializedName("picPath")
	private String picPath;
	@SerializedName("ranking")
	private int ranking;
	@SerializedName("id")
	private int id;
	@SerializedName("picLink")
	private String picLink;
	@SerializedName("createTime")
	private String createTime;
	@SerializedName("modifyTime")
	private String modifyTime;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPicLink() {
		return picLink;
	}
	public void setPicLink(String picLink) {
		this.picLink = picLink;
	}
	
}
