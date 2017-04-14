package com.example.b2c.net.response.translate;

import com.google.gson.annotations.SerializedName;


public class TranslatesResult {
	@SerializedName("translates")
	private Translates translates;

	public Translates getTranslates() {
		return translates;
	}

	public void setTranslates(Translates translates) {
		this.translates = translates;
	}
}
