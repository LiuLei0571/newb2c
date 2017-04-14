package com.example.b2c.net.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class BankListResult {
	@SerializedName("bankList")
	private List<BankListDetail> bankList;

	public List<BankListDetail> getBankList() {
		return bankList;
	}

	public void setBankList(List<BankListDetail> bankList) {
		this.bankList = bankList;
	}
	
}
