package com.example.b2c.activity.web;

import android.app.Activity;
import android.net.Uri;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.b2c.activity.common.WebViewActivity;


/**
 * 用途：
 * Created by milk on 16/9/5.
 * 邮箱：649444395@qq.com
 */
public abstract class WebViewChromeClient extends WebViewEx.WebChromeClientEx {
    ProgressBar progressBar;
    Activity activity;
    OnValueCallBackListener listener;

    public void setOnValueCallBackListener(OnValueCallBackListener onValueCallBackListener) {
        this.listener = onValueCallBackListener;
    }

    public WebViewChromeClient(ProgressBar progressBar, Activity activity) {
        this.progressBar = progressBar;
        this.activity = activity;
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
        callback.invoke(origin, true, false);
        super.onGeolocationPermissionsShowPrompt(origin, callback);
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return super.onConsoleMessage(consoleMessage);
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        if (listener != null) {
            listener.callback(filePathCallback);
        }
        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (progressBar != null) {
            progressBar.setProgress(newProgress);
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if ((activity != null) && (activity instanceof WebViewActivity) && setTitle) {
            activity.setTitle(title);
        }
    }

    public boolean setTitle = true;

    public void setSetTitle(boolean setTitle) {
        this.setTitle = setTitle;
    }

    public interface OnValueCallBackListener {
        void callback(ValueCallback<Uri[]> uploadMsg);

        void callback2(ValueCallback<Uri[]> uploadMsg);
    }
}
