package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.ShoppingAddressDetail;

public interface ShoppingAddressDetailListener extends RequestfinishListener {
	void onSuccess(ShoppingAddressDetail detail);

	void onError(int errorNO, String errorInfo);
}
