package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.StaffOrderList;

import java.util.List;

public interface GetStaffOrderListener extends RequestfinishListener {
	void onSuccess(List<StaffOrderList> orderList, boolean hasNext);

	void onError(int errorNO, String errorInfo);
}
