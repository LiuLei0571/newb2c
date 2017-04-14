package com.example.b2c.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class FamousShopListResult {
	@SerializedName("famousShopList")
	private ArrayList<FamousShopDetail> famousShopList = new ArrayList<FamousShopDetail>();

	private ResponseResult result;

	public ArrayList<FamousShopDetail> getFamousShopList() {
		return famousShopList;
	}

	public void setFamousShopList(ArrayList<FamousShopDetail> famousShopList) {
		this.famousShopList = famousShopList;
	}

	public ResponseResult getResult() {
		return result;
	}

	public void setResult(ResponseResult result) {
		this.result = result;
	}

}
