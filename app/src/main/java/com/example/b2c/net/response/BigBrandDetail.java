package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class BigBrandDetail {
	@SerializedName("id")
	private int id;
	@SerializedName("name")
	private String name;
	@SerializedName("status")
	private int status;
	@SerializedName("url")
	private String url;
	@SerializedName("sampleId")
	private int sampleId;
	@SerializedName("picPath")
	private String picPath;
	@SerializedName("ranking")
	private int ranking;
	@SerializedName("createTime")
	private String createTime;
	@SerializedName("modifyTime")
	private String modifyTime;
	@SerializedName("type")//若为1则为大牌主图
	private int type;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getSampleId() {
		return sampleId;
	}
	public void setSampleId(int sampleId) {
		this.sampleId = sampleId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	
}
