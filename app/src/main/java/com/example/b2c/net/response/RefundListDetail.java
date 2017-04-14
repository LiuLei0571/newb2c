package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class RefundListDetail implements Serializable{
	@SerializedName("id")
	private int id;//id
	@SerializedName("code")
	private String  code;//code
	@SerializedName("orderCode")
	private String  orderCode;//code
	@SerializedName("shopId")
	private int shopId;//shopId

	@SerializedName("shopName")
	private String  shopName;//shopName
	@SerializedName("sampleName")
	private String  sampleName;//sampleName
	@SerializedName("sampleProDetail")
	private String  sampleProDetail;//产品属性
	@SerializedName("samplePic")
	private String  samplePic;//产品图片

	@SerializedName("sampleNum")
	private int sampleNum;//数量
	@SerializedName("samplePrice")
	private String samplePrice;//产品价格
	@SerializedName("totalPrice")
	private String totalPrice;//总价
	@SerializedName("refundPrice")
	private String refundPrice;//退款金额

	@SerializedName("refundReason")
	private int  refundReason;//退款理由
	@SerializedName("picEvidence")
	private String  picEvidence;//图片凭证
	@SerializedName("refundStatus")
	private int  refundStatus;//退款状态
	@SerializedName("refundExplain")
	private String  refundExplain;//退款说明

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	public String getSampleProDetail() {
		return sampleProDetail;
	}

	public void setSampleProDetail(String sampleProDetail) {
		this.sampleProDetail = sampleProDetail;
	}

	public String getSamplePic() {
		return samplePic;
	}

	public void setSamplePic(String samplePic) {
		this.samplePic = samplePic;
	}

	public int getSampleNum() {
		return sampleNum;
	}

	public void setSampleNum(int sampleNum) {
		this.sampleNum = sampleNum;
	}

	public String getSamplePrice() {
		return samplePrice;
	}

	public void setSamplePrice(String samplePrice) {
		this.samplePrice = samplePrice;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(String refundPrice) {
		this.refundPrice = refundPrice;
	}

	public int getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(int refundReason) {
		this.refundReason = refundReason;
	}

	public String getPicEvidence() {
		return picEvidence;
	}

	public void setPicEvidence(String picEvidence) {
		this.picEvidence = picEvidence;
	}

	public int getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(int refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getRefundExplain() {
		return refundExplain;
	}

	public void setRefundExplain(String refundExplain) {
		this.refundExplain = refundExplain;
	}
}
