package com.example.b2c.net.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class GetBankCardListResult {
	@SerializedName("memberBankCardList")
	private List<GetBankCardListDetail> memberBankCardList;

	public List<GetBankCardListDetail> getMemberBankCardList() {
		return memberBankCardList;
	}

	public void setMemberBankCardList(List<GetBankCardListDetail> memberBankCardList) {
		this.memberBankCardList = memberBankCardList;
	}
	
}
