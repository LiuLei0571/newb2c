package com.example.b2c.activity.common;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.DownloadListener;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSON;
import com.example.b2c.R;
import com.example.b2c.widget.OldWebView;

import butterknife.Bind;

/**
 * 用途：
 * Created by milk on 16/11/14.
 * 邮箱：649444395@qq.com
 */

public class WebViewActivity extends TempBaseActivity {
    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.web_view)
    OldWebView webView;
    private boolean isFirst = true;

    @Override
    public int getRootId() {
        return R.layout.activity_webview;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mUrl = getIntent().getStringExtra("url");
        Log.d("h5_url", mUrl);
        if (TextUtils.isEmpty(mUrl)) {
            finish();
            return;
        }

        webView.init(this, null, progress);
        if (mUrl.startsWith("http")) {
            webView.loadUrl(mUrl);
        } else {
            webView.loadDataWithBaseURL(null, mUrl, "text/html", "utf-8", null);
        }
        webView.setLongClickable(true);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

            }
        });
        webView.setLongClickable(true);
        if (savedInstanceState != null) {
            isFirst = savedInstanceState.getBoolean("is_first", true);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) {

        }
        if (!isFirst) {
            callJs("axdBackFunc");
        } else {
            isFirst = false;
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }

    public void callJs(String axdBackfunc, Object... result) {
        webView.commonLoadUrl(buildJs(axdBackfunc, result));
    }

    private String buildJs(String jsMethod, Object... results) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("javascript:try{").append(jsMethod).append("(");
        if (results != null && results.length > 0) {
            int i = 0;
            for (Object result : results) {
                if (result instanceof String) {
                    stringBuilder.append("'");
                    stringBuilder.append(result);
                    stringBuilder.append("'");
                } else {
                    stringBuilder.append(JSON.toJSON(result));
                }
                i++;
                if (i != results.length) {
                    stringBuilder.append(",");
                }
            }
        }
        stringBuilder.append(");}catch(e){};");
        return stringBuilder.toString();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("is_first", isFirst);
        super.onSaveInstanceState(outState);
    }
}
