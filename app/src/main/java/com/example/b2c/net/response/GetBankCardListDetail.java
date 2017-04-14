package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class GetBankCardListDetail {
	@SerializedName("id")
	private int id;
	@SerializedName("holderName")
	private String holderName;
	@SerializedName("mobile")
	private String mobile;
	@SerializedName("bankId")
	private int bankId;
	@SerializedName("bankName")
	private String bankName;
	@SerializedName("bankCard")
	private String bankCard;
	@SerializedName("isDefault")
	private int isDefault;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}

}
