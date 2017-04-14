package com.example.b2c.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class HotCategoryListResult {
	@SerializedName("hotCategoryList")
	private ArrayList<HotCategoryDetail> hotCategoryList=new ArrayList<HotCategoryDetail>();
	
	private ResponseResult result;

	public ArrayList<HotCategoryDetail> getHotCategoryList() {
		return hotCategoryList;
	}

	public void setHotCategoryList(ArrayList<HotCategoryDetail> hotCategoryList) {
		this.hotCategoryList = hotCategoryList;
	}

	public ResponseResult getResult() {
		return result;
	}

	public void setResult(ResponseResult result) {
		this.result = result;
	}
	
}
