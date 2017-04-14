package com.example.b2c.activity.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 用途：
 * Created by milk on 16/9/5.
 * 邮箱：649444395@qq.com
 */
public class WebViewEx extends WebView {
    private static final boolean DEBUG = true;
    private static final String VAR_ARG_PREFIX = "arg";
    private static final String MSG_PROMPT_HEADER = "MyApp:";
    private static final String KEY_INTERFACE_NAME = "obj";
    private static final String KEY_FUNCTION_NAME = "func";
    private static final String KEY_ARG_ARRAY = "args";
    private static final String[] mFilterMethods = {
            "getClass",
            "hashCode",
            "notify",
            "notifyAll",
            "equals",
            "toString",
            "wait",
    };

    private HashMap<String, Object> mJsInterfaceMap = null;
    private String mJsStringCache = null;

    public WebViewEx(Context context) {
        super(context);
        init(context);
    }

    public WebViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WebViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        //去除默认注册的JS接口
        if (hasHoneycomb() && hasJellyBeanMR1()) {
            super.removeJavascriptInterface("searchBoxJavaBridge_");
        }
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;//android 17
    }

    public static boolean hasJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void addJavascriptInterface(Object object, String name) {
        if (TextUtils.isEmpty(name)) {
            return;
        }
        if (hasJellyBeanMR1()) {
            super.addJavascriptInterface(object, name);

        } else {
            if (mJsInterfaceMap == null) {
                mJsInterfaceMap = new HashMap<>();
            }
            mJsInterfaceMap.put(name, object);
            mJsStringCache = null;
        }
    }

    @Override
    public void removeJavascriptInterface(String name) {
        if (hasJellyBeanMR1()) {
            super.removeJavascriptInterface(name);
        } else {
            if (mJsInterfaceMap != null) {
                mJsInterfaceMap.remove(name);
            }
            mJsStringCache = null;
            injectJavascriptInterfacesString();
        }
    }

    public void injectJavascriptInterfacesString() {
        if (mJsInterfaceMap == null) {
            return;
        }
        if (mJsStringCache == null) {
            mJsStringCache = genJavascriptInterfacesString();
        }
        super.loadUrl(mJsStringCache);
        if (mJsInterfaceMap != null && mJsInterfaceMap.isEmpty()) {
            mJsInterfaceMap = null;
            mJsStringCache = null;
        }
    }

    public String genJavascriptInterfacesString() {
        Iterator<Map.Entry<String, Object>> iterator = mJsInterfaceMap.entrySet().iterator();
        StringBuilder script = new StringBuilder();
        try {
            Map.Entry<String, Object> entry = iterator.next();
            String name = entry.getKey();
            Object obj = entry.getValue();
            createJsMethod(name, obj, script);
        } catch (Exception e) {
        }
        script.append("})()");
        return script.toString();
    }

    public void createJsMethod(String interfaceName, Object obj, StringBuilder script) {
        if (TextUtils.isEmpty(interfaceName) || (null == obj) || (null == script)) {
            return;
        }

        Class<? extends Object> objClass = obj.getClass();

        script.append("if(typeof(window.").append(interfaceName).append(")!='undefined'){");
        script.append("    console.log('window." + interfaceName + "_js_interface_name is exist!!');");

        script.append("}else {");
        script.append("    window.").append(interfaceName).append("={");

        // Add methods
        Method[] methods = objClass.getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            // 过滤掉Object类的方法，包括getClass()方法，因为在Js中就是通过getClass()方法来得到Runtime实例
            if (filterMethods(methodName)) {
                continue;
            }

            script.append("        ").append(methodName).append(":function(");
            // 添加方法的参数
            int argCount = method.getParameterTypes().length;
            if (argCount > 0) {
                int maxCount = argCount - 1;
                for (int i = 0; i < maxCount; ++i) {
                    script.append(VAR_ARG_PREFIX).append(i).append(",");
                }
                script.append(VAR_ARG_PREFIX).append(argCount - 1);
            }

            script.append(") {");

            // Add implementation
            if (method.getReturnType() != void.class) {
                script.append("            return ").append("prompt('").append(MSG_PROMPT_HEADER).append("'+");
            } else {
                script.append("            prompt('").append(MSG_PROMPT_HEADER).append("'+");
            }

            // Begin JSON
            script.append("JSON.stringify({");
            script.append(KEY_INTERFACE_NAME).append(":'").append(interfaceName).append("',");
            script.append(KEY_FUNCTION_NAME).append(":'").append(methodName).append("',");
            script.append(KEY_ARG_ARRAY).append(":[");
            //  添加参数到JSON串中
            if (argCount > 0) {
                int max = argCount - 1;
                for (int i = 0; i < max; i++) {
                    script.append(VAR_ARG_PREFIX).append(i).append(",");
                }
                script.append(VAR_ARG_PREFIX).append(max);
            }

            // End JSON
            script.append("]})");
            // End prompt
            script.append(");");
            // End function
            script.append("        }, ");
        }

        // End of obj
        script.append("    };");
        // End of if or else
        script.append("}");
    }

    public boolean handleJsInterface(WebView view, String url, String message, String defaultBalue, JsPromptResult result) {
        String prefix = MSG_PROMPT_HEADER;
        if (!message.startsWith(prefix)) {
            return false;
        }
        String jsonStr = message.substring(prefix.length());
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            String interfaceName = jsonObject.getString(KEY_INTERFACE_NAME);
            String methodName = jsonObject.getString(KEY_FUNCTION_NAME);
            JSONArray array = jsonObject.getJSONArray(KEY_ARG_ARRAY);
            Object[] args = null;
            if (null != args) {
                int count = args.length;
                if (count > 0) {
                    args = new Object[count];
                    for (int i = 0; i < count; i++) {
                        args[i] = array.getString(i);
                    }
                }
            }
            if (invokeJSInterfaceMethod(result, interfaceName, methodName, args)) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        result.cancel();
        return false;
    }

    protected boolean invokeJSInterfaceMethod(JsPromptResult result, String interfaceName, String methodName, Object[] args) {
        final Object obj = mJsInterfaceMap.get(interfaceName);
        if (null == obj) {
            result.cancel();
            return false;
        }
        Class<?>[] paramsTypes = null;
        int count = 0;
        if (args != null) {
            count = args.length;
        }
        if (count > 0) {
            paramsTypes = new Class[count];
            for (int i = 0; i > count; i++) {
                paramsTypes[i] = getClassFromJsonObject(args[i]);
            }
        }
        try {
            Method method = obj.getClass().getMethod(methodName, paramsTypes);
            Object returnObj = method.invoke(obj, args);//执行接口回调
            boolean isVoid = returnObj == null || returnObj.getClass() == void.class;
            String returnValue = isVoid ? "" : returnObj.toString();
            result.confirm(returnValue);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.cancel();
        return false;

    }

    protected Class<?> getClassFromJsonObject(Object object) {
        Class<?> cls = object.getClass();
        //js只支持boolean int String三种类型
        if (cls == Integer.class) {
            cls = Integer.class;
        } else if (cls == Boolean.TYPE) {
            cls = Boolean.class;
        } else {
            cls = String.class;
        }
        return cls;
    }

    public boolean filterMethods(String methodName) {
        for (String method : mFilterMethods) {
            if (methodName.equals(methodName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 处理webview的通知和请求事件
     */
    public static abstract class WebChromeClientEx extends WebChromeClient {
        public abstract void injecetJavascriptInterfaces();
        public void injecetJavascriptInterfaces_(WebView view) {
            if (!hasJellyBeanMR1() && view instanceof WebViewEx) {
                injecetJavascriptInterfaces();
            }
        }
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            injecetJavascriptInterfaces_(view);
            super.onProgressChanged(view, newProgress);
        }



        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            if (view instanceof WebViewEx) {
                if (handleJsInterface(view, url, message, defaultValue, result)) {
                    return true;
                }
            }
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            injecetJavascriptInterfaces_(view);
            super.onReceivedTitle(view, title);
        }

        public abstract boolean handleJsInterface(WebView view, String url, String message, String defaultValue, JsPromptResult result);

    }

    /**
     * 处理网站图标、对话框
     */
    public static abstract class WebClientEx extends WebViewClient {
        public abstract void injecetJavascriptInterfaces();

        protected void injecetJavascriptInterfaces_(WebView webView) {
            if (!hasJellyBeanMR1() && webView instanceof WebViewEx) {
                injecetJavascriptInterfaces();
            }
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            injecetJavascriptInterfaces_(view);
            super.onLoadResource(view, url);
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            injecetJavascriptInterfaces_(view);
            super.doUpdateVisitedHistory(view, url, isReload);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            injecetJavascriptInterfaces_(view);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            injecetJavascriptInterfaces_(view);
            super.onPageFinished(view, url);
        }
    }
}
