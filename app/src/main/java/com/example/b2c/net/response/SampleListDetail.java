package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class SampleListDetail {
	@SerializedName("name")
	private String name;
	@SerializedName("shopName")
	private String shopName;
	@SerializedName("shopId")
	private int shopId;
	@SerializedName("sampleImage")
	private String sampleImage;
	@SerializedName("price")
	private String price;
	@SerializedName("marketPrice")
	private String marketPrice;
	@SerializedName("amount")
	private int amount;
	@SerializedName("id")
	private int id;
	@SerializedName("defaultPic")
	private String defaultPic;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public String getSampleImage() {
		return sampleImage;
	}

	public void setSampleImage(String sampleImage) {
		this.sampleImage = sampleImage;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDefaultPic() {
		return defaultPic;
	}

	public void setDefaultPic(String defaultPic) {
		this.defaultPic = defaultPic;
	}

}
