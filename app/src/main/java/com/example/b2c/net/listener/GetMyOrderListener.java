package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.BuyerOrderList;

import java.util.List;

public interface GetMyOrderListener extends RequestfinishListener {
	void onSuccess(List<BuyerOrderList> orderList, boolean hasNext);

	void onError(int errorNO, String errorInfo);
}
