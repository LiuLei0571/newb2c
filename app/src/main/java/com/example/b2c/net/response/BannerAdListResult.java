package com.example.b2c.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class BannerAdListResult {
	@SerializedName("bannerAdvertisingList")
	private ArrayList<BannerAdDetail> bannerAdvertisingList = new ArrayList<BannerAdDetail>();

	private ResponseResult result;

	public ArrayList<BannerAdDetail> getBannerAdvertisingList() {
		return bannerAdvertisingList;
	}

	public void setBannerAdvertisingList(
			ArrayList<BannerAdDetail> bannerAdvertisingList) {
		this.bannerAdvertisingList = bannerAdvertisingList;
	}

	public ResponseResult getResult() {
		return result;
	}

	public void setResult(ResponseResult result) {
		this.result = result;
	}
}
