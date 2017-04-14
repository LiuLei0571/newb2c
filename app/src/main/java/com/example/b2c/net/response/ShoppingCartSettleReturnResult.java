package com.example.b2c.net.response;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ShoppingCartSettleReturnResult {
	
	@SerializedName("payMoney")
	private  float  payMoney;
	@SerializedName("orderIdList")
	private  List<Integer> orderIdList;
	@SerializedName("failedList")
	private ArrayList<ShoppingCartSettleReturnDetail> failedList;

	
	private ResponseResult result;

	public ArrayList<ShoppingCartSettleReturnDetail> getSettleList() {
		return failedList;
	}

	public void setSettleList(ArrayList<ShoppingCartSettleReturnDetail> settleList) {
		this.failedList = settleList;
	}
	
	

	public float getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(float payMoney) {
		this.payMoney = payMoney;
	}

	public List<Integer> getOrderIdList() {
		return orderIdList;
	}

	public void setOrderIdList(List<Integer> orderIdList) {
		this.orderIdList = orderIdList;
	}

	public ArrayList<ShoppingCartSettleReturnDetail> getFailedList() {
		return failedList;
	}

	public void setFailedList(ArrayList<ShoppingCartSettleReturnDetail> failedList) {
		this.failedList = failedList;
	}

	public ResponseResult getResult() {
		return result;
	}

	public void setResult(ResponseResult result) {
		this.result = result;
	}
	
}
