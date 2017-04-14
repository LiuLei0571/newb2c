package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProDetail implements Serializable{
	@SerializedName("propertyId")
	private int propertyId;
	@SerializedName("proDetailId")
	private int proDetailId;
	@SerializedName("proDetailName")
	private String name;
	@SerializedName("pic")
	private String pic;
	private boolean isChecked;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public int getProDetailId() {
		return proDetailId;
	}

	public void setProDetailId(int proDetailId) {
		this.proDetailId = proDetailId;
	}

	@Override
	public String toString() {
		return "ProDetail{" +
				"isChecked=" + isChecked +
				", proDetailId=" + proDetailId +
				", propertyId=" + propertyId +
				'}';
	}
}
