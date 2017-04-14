package com.example.b2c.net.response;

import java.util.ArrayList;

public class TransFirstCategory {
	private  int  id;
	private String name;
	private String idPath;
	private int categoryLevel;
	private ArrayList<TransSecondCategory> secondCategoryList = new ArrayList<TransSecondCategory>();

	
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

	public String getIdPath() {
		return idPath;
	}

	public void setIdPath(String idPath) {
		this.idPath = idPath;
	}

	public int getCategoryLevel() {
		return categoryLevel;
	}

	public void setCategoryLevel(int categoryLevel) {
		this.categoryLevel = categoryLevel;
	}

	public ArrayList<TransSecondCategory> getSecondCategoryList() {
		return secondCategoryList;
	}

	public void setSecondCategoryList(
			ArrayList<TransSecondCategory> secondCategoryList) {
		this.secondCategoryList = secondCategoryList;
	}
}
