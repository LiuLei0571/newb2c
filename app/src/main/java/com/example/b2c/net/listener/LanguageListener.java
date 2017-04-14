package com.example.b2c.net.listener;

import com.example.b2c.net.listener.base.RequestfinishListener;
import com.example.b2c.net.response.common.LanguageResult;

import java.util.List;

/**
 * 用途：
 * Created by milk on 17/2/6.
 * 邮箱：649444395@qq.com
 */

public interface LanguageListener extends RequestfinishListener {
    void onSuccess(List<LanguageResult> mList, String errorInfo);
    void onFailure(String errorInfo);
}
