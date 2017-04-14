package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;

/**
 * 商品详情------图文详细页面
 */
public class GoodsDetailWebViewActivity extends TempBaseActivity {

	private WebView wv_detail;
	private String httpDetail;

	@Override
	public int getRootId() {
		return R.layout.goods_detail_webview_layout;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		Bundle bundle = i.getExtras();
		httpDetail = bundle.getString("httpDetail");
		initView();
	}
	public void initView() {

		wv_detail = (WebView) findViewById(R.id.wv_goods_detial);
		initText();
		wv_detail.loadDataWithBaseURL(null, httpDetail, "text/html", "utf-8", null);
	}
	public void initText() {
		setTitle(mTranslatesString.getCommon_imagetextdetail());
	}
}
