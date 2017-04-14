package com.example.b2c.net.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class HomePageDataObject {
	
	@SerializedName("bannerAdvertisingList")
	private List<BannerAdDetail> bannerAdvertisingList;
	@SerializedName("bigBrandList")
	private List<BigBrandDetail> bigBrandList;
	@SerializedName("hotCategoryList")
	private List<HotCategoryDetail> hotCategoryList;
	@SerializedName("famousShopList")
	private List<FamousShopDetail> famousShopList;
	
	private ResponseResult result;

	public List<BannerAdDetail> getBannerAdvertisingList() {
		return bannerAdvertisingList;
	}

	public void setBannerAdvertisingList(List<BannerAdDetail> bannerAdvertisingList) {
		this.bannerAdvertisingList = bannerAdvertisingList;
	}

	public List<BigBrandDetail> getBigBrandList() {
		return bigBrandList;
	}

	public void setBigBrandList(List<BigBrandDetail> bigBrandList) {
		this.bigBrandList = bigBrandList;
	}

	public List<HotCategoryDetail> getHotCategoryList() {
		return hotCategoryList;
	}

	public void setHotCategoryList(List<HotCategoryDetail> hotCategoryList) {
		this.hotCategoryList = hotCategoryList;
	}

	public List<FamousShopDetail> getFamousShopList() {
		return famousShopList;
	}

	public void setFamousShopList(List<FamousShopDetail> famousShopList) {
		this.famousShopList = famousShopList;
	}

	public ResponseResult getResult() {
		return result;
	}

	public void setResult(ResponseResult result) {
		this.result = result;
	}
	
	

}
