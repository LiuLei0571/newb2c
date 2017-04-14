package com.example.b2c.adapter;

public class GoodsListNode {
	private String GoodsName;
	private float GoodsPrice;
	private int GoodsNum;
	private String PicPath;
	private boolean isChecked;
	private String GoodsDes;
	private int GoodsId;

	public String getGoodsName() {
		return GoodsName;
	}

	public void setGoodsName(String goodsName) {
		GoodsName = goodsName;
	}

	public float getGoodsPrice() {
		return GoodsPrice;
	}

	public void setGoodsPrice(float goodsPrice) {
		GoodsPrice = goodsPrice;
	}

	public int getGoodsNum() {
		return GoodsNum;
	}

	public void setGoodsNum(int goodsNum) {
		GoodsNum = goodsNum;
	}

	public String getPicPath() {
		return PicPath;
	}

	public void setPicPath(String picPath) {
		PicPath = picPath;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getGoodsDes() {
		return GoodsDes;
	}

	public void setGoodsDes(String goodsDes) {
		GoodsDes = goodsDes;
	}

	public int getGoodsId() {
		return GoodsId;
	}

	public void setGoodsId(int goodsId) {
		GoodsId = goodsId;
	}
	

}
