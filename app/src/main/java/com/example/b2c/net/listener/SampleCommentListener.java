package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.SampleEvaluationListDetail;

import java.util.List;

public interface SampleCommentListener extends RequestfinishListener {
	void onSuccess(List<SampleEvaluationListDetail> sampleEvaluationList);

	void onError(int errorNO, String errorInfo);
}
