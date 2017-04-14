package com.example.b2c.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.b2c.B2C;
import com.example.b2c.helper.JsonHelper;
import com.example.b2c.widget.util.Strings;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by yh on 2016/8/9.
 */
public enum Sps {
    defaults("b2c_share"), h5("b2c_h5");
    private SharedPreferences sharedPreferences;

    Sps(String spFile) {
        sharedPreferences = B2C.getContext().getSharedPreferences(spFile, Context.MODE_PRIVATE);
    }

    public SharedPreferences getPrefs() {
        return sharedPreferences;
    }

    public <T> void put(String key, T value) {
        if (value == null) {
            return;
        }
        SharedPreferences.Editor editor = getPrefs().edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else {
//            String str = JsonHelper.toJSONString(value);
//            editor.putString(key, str);
        }
        editor.apply();
    }

    public Object get(String key) {
        Map<String, ?> values = Sps.h5.getAll();
        Object result = null;
        if (values != null && values.containsKey(key)) {
            result = values.get(key);
        }
        return result;
    }

    public String getString(String key) {
        return getPrefs().getString(key, Strings.EMPTY);
    }

    public String getString(String key,String defaultValue) {
        return getPrefs().getString(key,defaultValue);
    }

    public void putString(String key, String value) {
        getPrefs().edit().putString(key, value).apply();
    }

    public int getInt(String key) {
        return getPrefs().getInt(key, 0);
    }

    public void putInt(String key, int value) {
        getPrefs().edit().putInt(key, value).apply();
    }

    public long getLong(String key, long defaultLong) {
        return getPrefs().getLong(key, defaultLong);
    }

    public void putLong(String key, long value) {
        getPrefs().edit().putLong(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return getPrefs().getBoolean(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        getPrefs().edit().putBoolean(key, value).apply();
    }
    public <T> T getBean(String key, Type type) {
        String json = getPrefs().getString(key, null);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return JsonHelper.fromJson(json, type);
    }

    public void putBean(String key, Object bean) {
        getPrefs().edit().putString(key, JsonHelper.toJsonString(bean)).apply();
    }

    public void remove(String key) {
        getPrefs().edit().remove(key).apply();
    }

    public Map<String, ?> getAll() {
        return getPrefs().getAll();
    }
}
