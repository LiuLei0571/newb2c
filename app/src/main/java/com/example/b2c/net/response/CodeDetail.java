package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

public class CodeDetail {
	@SerializedName("text")
	private String text;
	@SerializedName("value")
	private String value;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
