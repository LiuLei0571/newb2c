package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class ShopListDetail {
	@SerializedName("name")
	private String name;
	@SerializedName("id")
	private int id;
	@SerializedName("companyName")
	private String companyName;
	@SerializedName("companyId")
	private int companyId;
	@SerializedName("logo")
	private String logo;
	@SerializedName("briefIntro")
	private String briefIntro;
	@SerializedName("contactAddress")
	private String contactAddress;
	@SerializedName("goodsSource")
	private int goodsSource;
	@SerializedName("introduction")
	private String introduction;
	@SerializedName("consistentDescription")
	private float consistentDescription;
	@SerializedName("serviceAttitude")
	private float serviceAttitude;
	@SerializedName("deliverySpeed")
	private float deliverySpeed;
	@SerializedName("categoryName")
	private String categoryName;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getBriefIntro() {
		return briefIntro;
	}
	public void setBriefIntro(String briefIntro) {
		this.briefIntro = briefIntro;
	}
	public String getContactAddress() {
		return contactAddress;
	}
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}
	public int getGoodsSource() {
		return goodsSource;
	}
	public void setGoodsSource(int goodsSource) {
		this.goodsSource = goodsSource;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public float getConsistentDescription() {
		return consistentDescription;
	}
	public void setConsistentDescription(float consistentDescription) {
		this.consistentDescription = consistentDescription;
	}
	public float getServiceAttitude() {
		return serviceAttitude;
	}
	public void setServiceAttitude(float serviceAttitude) {
		this.serviceAttitude = serviceAttitude;
	}
	public float getDeliverySpeed() {
		return deliverySpeed;
	}
	public void setDeliverySpeed(float deliverySpeed) {
		this.deliverySpeed = deliverySpeed;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
