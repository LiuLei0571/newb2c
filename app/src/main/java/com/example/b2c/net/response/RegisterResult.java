package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * 买卖家，注册结果
 */
public class RegisterResult  {
	@SerializedName("userId")
	private int userId;
	@SerializedName("token")
	private String token;
	@SerializedName("userType")
	private int userType;

	public int getUserId() {
		return userId;
	}

	public int getUserType() {
		return userType;
	}

	public String getToken() {
		return token;
	}


}
