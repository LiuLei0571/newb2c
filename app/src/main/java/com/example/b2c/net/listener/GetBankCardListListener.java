package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.GetBankCardListDetail;

import java.util.List;

public interface GetBankCardListListener extends RequestfinishListener {
	void onSuccess(List<GetBankCardListDetail> BankCardList);

	void onError(int errorNO, String errorInfo);
}
