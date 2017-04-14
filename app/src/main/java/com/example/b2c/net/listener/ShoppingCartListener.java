package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.CartShopCell;

import java.util.List;

public interface ShoppingCartListener extends RequestfinishListener {
	void onSuccess(List<CartShopCell> cartShopCellList);

	void onError(int errorNO, String errorInfo);
}
