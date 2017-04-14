package com.example.b2c.activity.web;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import com.example.b2c.widget.OldWebView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

/**
 * 用途：
 * Created by milk on 16/9/5.
 * 邮箱：649444395@qq.com
 */
public class WebViewPlugin implements Handler.Callback {

    private OnOpenUrlListener listener;
    private Activity mContext;
    private Fragment mFragment;
    private OldWebView oldWebView;

    public WebViewPlugin(Activity activity, Fragment fragment, OldWebView view) {
        this.mContext = activity;
        this.mFragment = fragment;
        this.oldWebView = view;
    }

    public void setListener(OnOpenUrlListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    public boolean onOldEvent(String url) {
        if (!url.startsWith("b2c://")) {
            return false;
        }
        HashMap<String, String> paramsMap;
        String action = getAction(url);
        paramsMap = parseMap(url);
        onEvent(action, paramsMap);
        return true;
    }


    private HashMap<String, String> parseMap(String url) {
        String content = url.substring(6);
        String[] contents = content.split("\\?");
        HashMap<String, String> paramsMap = new HashMap<>();
        if (contents.length >= 2) {
            String params = contents[1];
            String[] paramsArray = params.split("&");
            String key = "";
            String value = "";
            for (String pair : paramsArray) {
                String[] kvs = pair.split("=");
                if (kvs.length >= 1) {
                    key = kvs[0];
                    if (kvs.length >= 2) {
                        value = kvs[1];
                        try {
                            value = URLDecoder.decode(value, "UTF-8");
                        } catch (UnsupportedEncodingException e) {

                        }
                    } else {
                        value = "";
                    }
                    paramsMap.put(key, value);
                }
            }
        }

        return paramsMap;
    }

    private String getAction(String url) {
        String action = "";
        String content = url.substring(6);
        String[] contents = content.split("\\?");
        if (contents.length >= 1) {
            action = contents[0];
        }
        return action;
    }

    public interface OnOpenUrlListener {
        void onOpenUrl();
    }

    public void onEvent(String action, HashMap<String, String> paramsMap) {
        Log.d("h5_web", action + paramsMap);
        switch (action) {
            case "login":
                login(paramsMap);
                break;
            case "back":
                back(paramsMap);
            case "close":
                if (mFragment == null) {
                    mContext.finish();
                }
                break;
            case "refresh":
                refresh();
                break;
            default:
                break;
        }
    }

    private void refresh() {
        String reloadUrl = oldWebView.getUrl();
        if (reloadUrl.contains("uid==null") && (reloadUrl.contains("sign==null"))) {
            reloadUrl.replaceAll("uid=null", "uid=6995");
            oldWebView.loadUrl(reloadUrl);
        } else {
            oldWebView.reload();
        }
    }

    private void back(HashMap<String, String> paramsMap) {
        if (oldWebView != null && oldWebView.canGoBack()) {
            oldWebView.goBack();
        } else {
            if (mFragment == null) {
                mContext.finish();
            }
        }
    }

    public void login(HashMap<String, String> paramsMap) {
        String callback = paramsMap.get("callback");
        if (TextUtils.isEmpty(callback)) {
            callback = "onAxdLogin";
        }
        final String funcName = callback;
//        LoginActivity.startLogin(mContext, new LoginActivity.OnLoginListener() {
//            @Override
//            public void OnSuccess(String name, String pasd) {
//                //64248f30e7f4a6ba78c7442c2fcb3910 6995
//                if (name.equals("admin")) {
//                    String uid = "6995";
//                    String sign = "64248f30e7f4a6ba78c7442c2fcb3910";
//                    oldWebView.loadUrl("javascript:try{" + funcName + "('" + uid + "','" + sign + "');}catch(e){};");
//                } else {
//                    oldWebView.loadUrl("javascript:try{" + funcName + "('null','null');}catch(e){};");
//                }
//            }
//
//            @Override
//            public void OnFail() {
//
//            }
//        });
    }
}
