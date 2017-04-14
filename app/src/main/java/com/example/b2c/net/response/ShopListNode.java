package com.example.b2c.net.response;

import com.example.b2c.adapter.GoodsListNode;

import java.util.ArrayList;
import java.util.List;

public class ShopListNode {
	private String ShopName;
	private int ShopId;
	private boolean isChecked;
	private List<GoodsListNode> goods_list;

	public String getShopName() {
		return ShopName;
	}

	public void setShopName(String shopName) {
		ShopName = shopName;
	}

	public List<GoodsListNode> getGoods_list() {
		return goods_list;
	}

	public void setGoods_list(List<GoodsListNode> g_list1) {
		this.goods_list = g_list1;
	}

	public int getShopId() {
		return ShopId;
	}

	public void setShopId(int shopId) {
		ShopId = shopId;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

}
