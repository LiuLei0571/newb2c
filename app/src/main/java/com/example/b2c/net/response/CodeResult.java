package com.example.b2c.net.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CodeResult {
	@SerializedName("codeList")
	private List<CodeDetail> codeList;
	
	private ResponseResult result;

	public List<CodeDetail> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<CodeDetail> codeList) {
		this.codeList = codeList;
	}

	public ResponseResult getResult() {
		return result;
	}

	public void setResult(ResponseResult result) {
		this.result = result;
	}
	
}
