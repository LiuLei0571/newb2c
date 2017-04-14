package com.example.b2c.net.response;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class SamplePro implements Serializable{
	@SerializedName("id")
	private int id;
	@SerializedName("proId")
	private int proId;
	@SerializedName("proName")
	private String proName;
	@SerializedName("proDetailList")
	private List<ProDetail> proDetailList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProId() {
		return proId;
	}

	public void setProId(int proId) {
		this.proId = proId;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public List<ProDetail> getProDetailList() {
		return proDetailList;
	}

	public void setProDetailList(List<ProDetail> proDetailList) {
		this.proDetailList = proDetailList;
	}

}
