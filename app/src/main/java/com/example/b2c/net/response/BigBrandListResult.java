package com.example.b2c.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class BigBrandListResult {
	@SerializedName("bigBrandList")
	private ArrayList<BigBrandDetail> bigBrandList=new ArrayList<BigBrandDetail>();
	
	private ResponseResult result;

	public ArrayList<BigBrandDetail> getBigBrandList() {
		return bigBrandList;
	}

	public void setBigBrandList(ArrayList<BigBrandDetail> bigBrandList) {
		this.bigBrandList = bigBrandList;
	}

	public ResponseResult getResult() {
		return result;
	}

	public void setResult(ResponseResult result) {
		this.result = result;
	}
	
}
