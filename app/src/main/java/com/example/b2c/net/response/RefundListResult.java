package com.example.b2c.net.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class RefundListResult {
	@SerializedName("rows")
	private List<RefundListDetail> refundList;
	@SerializedName("hasNext")
	protected boolean hasNext;

	public List<RefundListDetail> getRefundList() {
		return refundList;
	}

	public void setRefundList(List<RefundListDetail> refundList) {
		this.refundList = refundList;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
}
