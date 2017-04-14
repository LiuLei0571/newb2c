package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SampleListResult extends ResponseResult{
	@SerializedName("sampleList")
	private List<SampleListDetail> sampleList;

	@SerializedName("hasNext")
	private boolean hasNext;

	public List<SampleListDetail> getSampleList() {
		return sampleList;
	}

	public boolean isHasNext() {
		return hasNext;
	}
}
