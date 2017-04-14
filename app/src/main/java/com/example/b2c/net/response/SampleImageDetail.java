package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SampleImageDetail implements Serializable {
	@SerializedName("title")
	private String title;
	@SerializedName("imagePath")
	private String imagePath;
	@SerializedName("isDefault")
	private int  isDefault;
	public String getImagePath() {
		return imagePath;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public int getIsDefault() {
		return isDefault;
	}
}
