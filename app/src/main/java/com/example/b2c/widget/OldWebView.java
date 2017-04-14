package com.example.b2c.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.b2c.activity.web.WebViewChromeClient;
import com.example.b2c.activity.web.WebViewClient;
import com.example.b2c.activity.web.WebViewEx;

import java.util.HashMap;
import java.util.Map;

/**
 * 用途：
 * Created by milk on 16/9/5.
 * 邮箱：649444395@qq.com
 */
public class OldWebView extends WebViewEx {
    private WebChromeClient webChromeClient;
    private WebViewClient mWebViewClient;

    public OldWebView(Context context) {
        super(context);
    }

    public OldWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OldWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void commonLoadUrl(String url) {
        super.loadUrl(url);
    }

    @Override
    public void loadUrl(String url) {
        loadUrl(url, null);
    }

    @Override
    public void loadUrl(String url, Map<String, String> head) {
        if (url == null) {
            return;
        }
        if (url.toLowerCase().startsWith("http") || url.toLowerCase().startsWith("https")) {
            String uid = "null";
            String sign = "null";
//            if (UserInfo.isIsLogin()) {
//                uid = UserInfo.getUid();
//                sign = UserInfo.getSign();
//            }
//            if (url.contains("?")) {
//                String[] contents = url.split("\\?");
//                String header = "";
//                String body = "";
//                List<List<String>> paramsList = new ArrayList<>();
//                if (contents.length >= 1) {
//                    header = contents[0];
//                }
//                if (contents.length >= 2) {
//                    String params = contents[1];
//                    String[] paramsArray = params.split("&");
//                    String key = "";
//                    String value = "";
//                    boolean hasUID = false;
//                    boolean hasSign = false;
//
//                    for (String pair : paramsArray) {
//                        String[] kvs = pair.split("=");
//                        List<String> param = new ArrayList<>();
//                        if (kvs.length >= 1) {
//                            key = kvs[0];
//                            if (kvs.length >= 2) {
//                                value = kvs[1];
//                            } else {
//                                value = "";
//                            }
//
//                            if (key.equals("uid")) {
//                                value = uid;
//                                hasUID = true;
//                            }
//
//                            if (key.equals("sign")) {
//                                value = sign;
//                                hasSign = true;
//                            }
//                            param.add(key);
//                            param.add(value);
//                        }
//                        paramsList.add(param);
//                    }
//
//                    if (!hasUID) {
//                        List<String> list = new ArrayList<>();
//                        list.add("uid");
//                        list.add(uid);
//                        paramsList.add(list);
//                    }
//
//                    if (!hasSign) {
//                        List<String> list = new ArrayList<>();
//                        list.add("sign");
//                        list.add(sign);
//                        paramsList.add(list);
//                    }
//                }
//                for (List<String> next : paramsList) {
//                    String key = next.get(0);
//                    String value = next.get(1);
//                    if (TextUtils.isEmpty(body)) {
//                        body = key + "=" + value;
//                    } else {
//                        body = body + "&" + key + "=" + value;
//                    }
//                }
//                url = header + "?" + body;
//
//
//            }
            if (head == null) {
                super.loadUrl(url);
            } else {
                super.loadUrl(url, head);
            }
        }
    }

    public void loadUrl(String url, boolean needAgent) {
        if (needAgent) {
            Map<String, String> head = new HashMap<>();
            head.put("Referer", getUrl());
            loadUrl(url, head);
        } else {
            Map<String, String> head = new HashMap<>();
            head.put("Referer", getUrl());
            super.loadUrl(url, head);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void init(Activity activity, Fragment fragment, ProgressBar progressBar) {
        String ua = "axd/f88cd7f7-0932-4a65-99d2-7d83a0adb82d imei/f88cd7f7-0932-4a65-99d2-7d83a0adb82d platforms/android version/2.0.3 channel/AXD system/(Android 21 5.0) device/(samsung SM-N900) screen/(1080-1920) native/n";
        getSettings().setUserAgentString(ua);
        getSettings().setDomStorageEnabled(true);
        getSettings().setGeolocationEnabled(true);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setAppCacheEnabled(true);
        getSettings().setAllowFileAccess(true);
        getSettings().setAppCacheMaxSize(1024 * 1024 * 10);
        String appCachePath = getContext().getCacheDir().getAbsolutePath();
        getSettings().setAppCachePath(appCachePath);
        webChromeClient = new WebChromeClient2(progressBar, activity);

        mWebViewClient = new WebViewClient2(progressBar, activity, fragment);
        setWebViewClient(mWebViewClient);
        setWebChromeClient(webChromeClient);
    }

    public Map<String, Object> titleMap = new HashMap<>();//存放标题，key是url,value是标题
    private boolean isOnReceivedTitle = false;//是否触发

    public class WebViewClient2 extends WebViewClient {
        private Activity activity;

        public WebViewClient2(ProgressBar progressBar, Activity activity, Fragment fragment) {
            super(progressBar, activity, fragment);
            this.activity = activity;
        }

        @Override
        public void injecetJavascriptInterfaces() {
            OldWebView.this.injectJavascriptInterfacesString();
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (!isOnReceivedTitle && null != titleMap.get(url)) {
                activity.setTitle(titleMap.get(url).toString());
                isOnReceivedTitle = true;
            }
        }
    }

    public class WebChromeClient2 extends WebViewChromeClient {
        public WebChromeClient2(ProgressBar progressBar, Activity activity) {
            super(progressBar, activity);

        }

        @Override
        public void injecetJavascriptInterfaces() {
            OldWebView.this.injectJavascriptInterfacesString();
        }

        @Override
        public boolean handleJsInterface(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            return OldWebView.this.handleJsInterface(view, url, message, defaultValue, result);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            String url = view.getUrl();
            if (url != null && setTitle) {
                titleMap.put(url, title);
                isOnReceivedTitle = true;
            }
        }

    }

    @Override
    public void goBack() {
        isOnReceivedTitle = false;
        super.goBack();
    }
}
