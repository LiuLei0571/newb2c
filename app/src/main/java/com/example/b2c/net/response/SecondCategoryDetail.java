package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SecondCategoryDetail {
	@SerializedName("id")
	private int id;
	@SerializedName("name")
	private String name;
	@SerializedName("categoryLevel")
	private int categoryLevel;
	@SerializedName("thirdCategoryList")
	private ArrayList<ThirdCategoryDetail> thirdCategoryList=new ArrayList<ThirdCategoryDetail>();
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getCategoryLevel() {
		return categoryLevel;
	}
	public ArrayList<ThirdCategoryDetail> getThirdCategoryList() {
		return thirdCategoryList;
	}
	
}
