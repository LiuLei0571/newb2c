package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShopListResult extends ResponseResult {
	@SerializedName("shopList")
	private List<ShopListDetail> shopList;

	@SerializedName("hasNext")
	private boolean hasNext;

	public List<ShopListDetail> getShopList() {
		return shopList;
	}

	public boolean isHasNext() {
		return hasNext;
	}
	
}
