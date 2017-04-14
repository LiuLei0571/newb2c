package com.example.b2c.net.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class OrderCellListResult {
	@SerializedName("orderList")
	private List<OrderCell>  orderList;

	public List<OrderCell> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderCell> orderList) {
		this.orderList = orderList;
	}

}
