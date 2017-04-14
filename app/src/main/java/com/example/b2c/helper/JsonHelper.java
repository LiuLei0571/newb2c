package com.example.b2c.helper;


import com.alibaba.fastjson.JSON;
import com.example.b2c.impl.IParse;
import com.example.b2c.widget.util.Strings;

import java.lang.reflect.Type;

/**
 * 用途：
 * Created by milk on 16/8/23.
 * 邮箱：649444395@qq.com
 */
public class JsonHelper {
    private static IParse iParse;

    protected static void init(IParse iparse) {
        iParse = iparse;
    }

    public static String toJsonString(Object bean) {
        if (bean != null) {
            return JSON.toJSONString(bean);
        } else {
            return Strings.EMPTY;
        }

    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (clazz != null) {
            String typeString = clazz.getSimpleName();
            if (isString(typeString)) {
                return (T) json;
            }
        }
        if (json != null) {
            return JSON.parseObject(json, clazz);
        } else {
            return null;
        }

    }

    public static <T> T fromJson(String json, Type type) {
        if (type != null) {
            String typeString = Strings.EMPTY;
            if (type instanceof Class) {
                typeString = ((Class) type).getSimpleName();
            }
            if (isString(typeString)) {
                return (T) json;
            }
        }
        if (json != null) {
            return JSON.parseObject(json,type);
        }
        else {
            return  null;
        }
     }

    public static boolean isString(String typeString) {
        return typeString.startsWith("String");
    }
}
