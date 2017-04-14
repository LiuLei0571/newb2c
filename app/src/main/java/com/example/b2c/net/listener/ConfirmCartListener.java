package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.CartShopDetail;
import com.example.b2c.net.response.ConfirmCartResult;
import com.example.b2c.net.response.ShoppingAddressDetail;

import java.util.List;

public interface ConfirmCartListener extends RequestfinishListener {
	void onSuccess(ConfirmCartResult confirmcart_result);

	void onError(int errorNO, String errorInfo);
}
