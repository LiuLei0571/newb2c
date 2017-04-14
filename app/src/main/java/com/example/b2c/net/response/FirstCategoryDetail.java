package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FirstCategoryDetail {
	@SerializedName("id")
	private int id;
	@SerializedName("name")
	private String name;
	@SerializedName("idPath")
	private String idPath;
	@SerializedName("categoryLevel")
	private int categoryLevel;
	@SerializedName("secondCategoryList")
	private ArrayList<SecondCategoryDetail> secondCategoryList=new ArrayList<SecondCategoryDetail>();
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getIdPath() {
		return idPath;
	}
	public int getCategoryLevel() {
		return categoryLevel;
	}
	public ArrayList<SecondCategoryDetail> getSecondCategoryList() {
		return secondCategoryList;
	}
	
}
