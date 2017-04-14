package com.example.b2c.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class SampleCommentResult {
	@SerializedName("sampleEvaluationList")
	private ArrayList<SampleEvaluationListDetail> sampleEvaluationList = new ArrayList<SampleEvaluationListDetail>();

	public ArrayList<SampleEvaluationListDetail> getSampleEvaluationList() {
		return sampleEvaluationList;
	}

	public void setSampleEvaluationList(
			ArrayList<SampleEvaluationListDetail> sampleEvaluationList) {
		this.sampleEvaluationList = sampleEvaluationList;
	}
	
}
