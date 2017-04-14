package com.example.b2c.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class FavoriteShopListResult {
	@SerializedName("favoriteShopList")
	private ArrayList<FavoriteShopDetail> favoriteshopList = new ArrayList<FavoriteShopDetail>();

	private ResponseResult result;

	public ArrayList<FavoriteShopDetail> getFavoriteshopList() {
		return favoriteshopList;
	}

	public void setFavoriteshopList(
			ArrayList<FavoriteShopDetail> favoriteshopList) {
		this.favoriteshopList = favoriteshopList;
	}

	public ResponseResult getResult() {
		return result;
	}

	public void setResult(ResponseResult result) {
		this.result = result;
	}

}
