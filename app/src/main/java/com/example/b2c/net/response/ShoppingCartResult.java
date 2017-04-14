package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车列表
 */
public class ShoppingCartResult {
	@SerializedName("cartShopList")
	private List<CartShopCell> cartShopList=new ArrayList<>();
	
	private ResponseResult result;

	public List<CartShopCell> getCartShopList() {
		return cartShopList;
	}

	public void setCartShopList(List<CartShopCell> cartShopList) {
		this.cartShopList = cartShopList;
	}

	public ResponseResult getResult() {
		return result;
	}

	public void setResult(ResponseResult result) {
		this.result = result;
	}
	
	
}
