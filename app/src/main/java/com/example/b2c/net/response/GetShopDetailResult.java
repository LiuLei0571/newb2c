package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class GetShopDetailResult {
	@SerializedName("shopName")
	private String shopName;
	@SerializedName("shopId")
	private int shopId;
	@SerializedName("isFavorite")
	private int isFavorite;
	@SerializedName("serviceAttitude")
	private float serviceAttitude;
	@SerializedName("consistentDescription")
	private float consistentDescription;
	@SerializedName("contactTel")
	private String contactTel;
	@SerializedName("deliverySpeed")
	private float deliverySpeed;
	@SerializedName("rateOfPraise")
	private float rateOfPraise;
	@SerializedName("contactAddress")
	private String contactAddress;
	@SerializedName("shopLogo")
	private String shopLogo;
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public int getIsFavorite() {
		return isFavorite;
	}
	public void setIsFavorite(int isFavorite) {
		this.isFavorite = isFavorite;
	}
	public float getServiceAttitude() {
		return serviceAttitude;
	}
	public void setServiceAttitude(float serviceAttitude) {
		this.serviceAttitude = serviceAttitude;
	}
	public float getConsistentDescription() {
		return consistentDescription;
	}
	public void setConsistentDescription(float consistentDescription) {
		this.consistentDescription = consistentDescription;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public float getDeliverySpeed() {
		return deliverySpeed;
	}
	public void setDeliverySpeed(float deliverySpeed) {
		this.deliverySpeed = deliverySpeed;
	}
	public float getRateOfPraise() {
		return rateOfPraise;
	}
	public void setRateOfPraise(float rateOfPraise) {
		this.rateOfPraise = rateOfPraise;
	}
	public String getContactAddress() {
		return contactAddress;
	}
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}
	public String getShopLogo() {
		return shopLogo;
	}
	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}
	
}
