package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.ShoppingAddressDetail;

import java.util.List;

public interface GetShoppingAddressListListener extends RequestfinishListener {
	void onSuccess(List<ShoppingAddressDetail> shoppingAddressList);

	void onError(int errorNO, String errorInfo);
}
