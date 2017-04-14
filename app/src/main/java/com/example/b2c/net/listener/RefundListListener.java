package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.RefundListDetail;

import java.util.List;

public interface RefundListListener extends RequestfinishListener {
	void onSuccess(List<RefundListDetail> refundList);

	void onError(int errorNO, String errorInfo);
}
