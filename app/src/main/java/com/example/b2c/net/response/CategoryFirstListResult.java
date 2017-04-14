package com.example.b2c.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class CategoryFirstListResult {
	@SerializedName("firstCategory")
	private ArrayList<FirstCategoryDetail> firstCategoryList = new ArrayList<FirstCategoryDetail>();

	private ResponseResult result;


	public ArrayList<FirstCategoryDetail> getFirstCategoryList() {
		return firstCategoryList;
	}

	public void setFirstCategoryList(
			ArrayList<FirstCategoryDetail> firstCategoryList) {
		this.firstCategoryList = firstCategoryList;
	}

	public ResponseResult getResult() {
		return result;
	}

	public void setResult(ResponseResult result) {
		this.result = result;
	}

}
