package com.example.b2c.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class MyMessageResult {
	@SerializedName("systemMessageList")
	private ArrayList<SystemMessageDetail> systemMessageList = new ArrayList<SystemMessageDetail>();

	public ArrayList<SystemMessageDetail> getSystemMessageList() {
		return systemMessageList;
	}

	public void setSystemMessageList(
			ArrayList<SystemMessageDetail> systemMessageList) {
		this.systemMessageList = systemMessageList;
	}

}
