package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.FavoriteSampleDetail;

import java.util.List;

public interface FavoriteSampleListener extends RequestfinishListener {
	void onSuccess(List<FavoriteSampleDetail> favoritesampleList);

	void onError(int errorNO, String errorInfo);
}
