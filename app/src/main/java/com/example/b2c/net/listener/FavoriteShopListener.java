package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.FavoriteShopDetail;

import java.util.List;

public interface FavoriteShopListener  extends RequestfinishListener {
	void onSuccess(List<FavoriteShopDetail> favoriteshopList);

	void onError(int errorNO, String errorInfo);
}
