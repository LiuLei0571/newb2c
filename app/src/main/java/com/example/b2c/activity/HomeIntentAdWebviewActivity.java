package com.example.b2c.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.graphics.Bitmap;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.b2c.R;
import com.nostra13.universalimageloader.utils.L;

public class HomeIntentAdWebviewActivity extends Activity implements OnClickListener{
	
	public static final String LOG="com.example.b2c.activity.HomeIntentAdWebview";
	
	private ImageView iv_adwebview_back;
	
	private WebView  ad_webView;
	
	private String url;

	private ProgressBar pbProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home_ad_webview);
		Bundle bundle =getIntent().getExtras();
		url=bundle.getString("url");
		initView();
		
	}

	private void initView() {
		iv_adwebview_back=(ImageView) findViewById(R.id.iv_adwebview_back);
		ad_webView=(WebView) findViewById(R.id.ad_webView);
		pbProgress = (ProgressBar) findViewById(R.id.pb_progress);
		
		iv_adwebview_back.setOnClickListener(this);
		
		WebSettings settings=ad_webView.getSettings();
		settings.setJavaScriptEnabled(true);//支持js
		settings.setBuiltInZoomControls(true);//支持放大缩小按钮
		settings.setUseWideViewPort(true);// 支持双击缩放
		
		ad_webView.setWebViewClient(new WebViewClient(){
		   @Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
			   L.i(LOG, "网页开始加载");
			   super.onPageStarted(view, url, favicon);
			   pbProgress.setVisibility(View.VISIBLE);
			}
		   @Override
			public void onPageFinished(WebView view, String url) {
			    L.i(LOG, "网页结束加载");
				super.onPageFinished(view, url);
				pbProgress.setVisibility(View.GONE);
			}
		   @Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
			   L.i("跳转url:"+LOG,url);
			   view.loadUrl(url);
//			   super.shouldOverrideUrlLoading(view, url);
			   return  true;
			}
			
		});
		ad_webView.setWebChromeClient(new WebChromeClient() {
			/**
			 * 进度发生变化
			 */
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				L.i(LOG, "加载进度:" + newProgress);
				super.onProgressChanged(view, newProgress);
			}

			/**
			 * 获取网页标题
			 */
			@Override
			public void onReceivedTitle(WebView view, String title) {
				L.i(LOG, "网页标题:" + title);
				super.onReceivedTitle(view, title);
			}
		});
		
		ad_webView.loadUrl(url);// 加载网页
	}

	@Override
	public void onClick(View view) {
		
		switch (view.getId()) {
		case R.id.iv_adwebview_back:
			finish();
			break;
		default:
			break;
		}
		
		
	}
	
	

}
