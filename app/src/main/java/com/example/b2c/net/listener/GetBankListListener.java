package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.BankListDetail;

import java.util.List;

public interface GetBankListListener extends RequestfinishListener {
	void onSuccess(List<BankListDetail> BankList);

	void onError(int errorNO, String errorInfo);
}
