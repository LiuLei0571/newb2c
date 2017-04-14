package com.example.b2c.activity.web;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.b2c.widget.OldWebView;

/**
 * 用途：
 * Created by milk on 16/9/5.
 * 邮箱：649444395@qq.com
 */
public abstract class WebViewClient extends WebViewEx.WebClientEx {
    private WebPlugin webPlugin;
    private ProgressBar progressBar;
    private Activity activity;
    private Fragment fragment;
    private WebViewPlugin mWebViewPlugin;
    private WebViewPlugin.OnOpenUrlListener listener;

    public void setListener(WebViewPlugin.OnOpenUrlListener listener) {
        this.listener = listener;
    }

    public WebViewClient(ProgressBar progressBar, Activity activity, Fragment fragment) {
        this.progressBar = progressBar;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d("h5_web",url);
        if (url.startsWith("b2c://")) {
            if (mWebViewPlugin == null) {
                mWebViewPlugin = new WebViewPlugin(activity, fragment, (OldWebView) view);
                mWebViewPlugin.setListener(listener);
            }
            return mWebViewPlugin.onOldEvent(url);
        } else if (url.startsWith("http://") || url.startsWith("https://")) {
            ((OldWebView) view).loadUrl(url, false);
        } else {
            return true;
        }
        return true;
    }
}
