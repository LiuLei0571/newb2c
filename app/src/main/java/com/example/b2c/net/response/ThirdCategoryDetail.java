package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class ThirdCategoryDetail {
	@SerializedName("id")
	private int id;
	@SerializedName("name")
	private String name;
	@SerializedName("parentId")
	private int parentId;
	@SerializedName("categoryLevel")
	private int categoryLevel;
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getParentId() {
		return parentId;
	}
	public int getCategoryLevel() {
		return categoryLevel;
	}
	
}
