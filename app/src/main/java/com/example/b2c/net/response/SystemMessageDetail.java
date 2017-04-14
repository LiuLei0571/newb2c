package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class SystemMessageDetail {
	@SerializedName("content")
	private String content;
	@SerializedName("title")
	private String title;
	@SerializedName("isReaded")
	private int isReaded;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getIsReaded() {
		return isReaded;
	}
	public void setIsReaded(int isReaded) {
		this.isReaded = isReaded;
	}
	
}
