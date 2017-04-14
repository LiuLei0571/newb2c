package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class HotCategoryDetail {
	@SerializedName("status")
	private int status;
	@SerializedName("pic")
	private String pic;
	@SerializedName("ranking")
	private int ranking;
	@SerializedName("id")
	private int id;
	@SerializedName("categoryId")
	private int categoryId;
	@SerializedName("createTime")
	private String createTime;
	@SerializedName("modifyTime")
	private String modifyTime;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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
