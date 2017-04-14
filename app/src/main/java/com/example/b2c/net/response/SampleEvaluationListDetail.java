package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class SampleEvaluationListDetail {
	@SerializedName("sampleEvaluationReply")
	private String sampleEvaluationReply;
	@SerializedName("type")
	private int type;
	@SerializedName("content")
	private String content;
	@SerializedName("loginName")
	private String loginName;
	@SerializedName("consistentDescription")
	private int consistentDescription;
	@SerializedName("serviceAttitude")
	private int serviceAttitude;
	@SerializedName("deliverySpeed")
	private int deliverySpeed;
	@SerializedName("specification")
	private String specification;
	@SerializedName("createTime")
	private String createTime;
	@SerializedName("modifyTime")
	private String modifyTime;
	public String getSampleEvaluationReply() {
		return sampleEvaluationReply;
	}
	public void setSampleEvaluationReply(String sampleEvaluationReply) {
		this.sampleEvaluationReply = sampleEvaluationReply;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public int getConsistentDescription() {
		return consistentDescription;
	}
	public void setConsistentDescription(int consistentDescription) {
		this.consistentDescription = consistentDescription;
	}
	public int getServiceAttitude() {
		return serviceAttitude;
	}
	public void setServiceAttitude(int serviceAttitude) {
		this.serviceAttitude = serviceAttitude;
	}
	public int getDeliverySpeed() {
		return deliverySpeed;
	}
	public void setDeliverySpeed(int deliverySpeed) {
		this.deliverySpeed = deliverySpeed;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	
}
