package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetShoppingAddressResult {
	@SerializedName("shoppingAddressList")
	private List<ShoppingAddressDetail> shoppingAddressList;
	public List<ShoppingAddressDetail> getShoppingAddressList() {
		return shoppingAddressList;
	}
	
}
