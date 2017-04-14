package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * 我的收藏页面 收藏的产品列表 结果
 */
public class FavoriteSampleListResult extends  ResponseResult {
	@SerializedName("favoriteSampleList")
	private ArrayList<FavoriteSampleDetail> favoritesampleList=new ArrayList<FavoriteSampleDetail>();

	@SerializedName("hasNext")
	private boolean hasNext;

	public ArrayList<FavoriteSampleDetail> getFavoritesampleList() {
		return favoritesampleList;
	}

	public boolean isHasNext() {
		return hasNext;
	}
}
