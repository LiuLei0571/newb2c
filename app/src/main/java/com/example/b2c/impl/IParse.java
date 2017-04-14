package com.example.b2c.impl;

import java.lang.reflect.Type;

/**
 * 用途：
 * Created by milk on 16/8/23.
 * 邮箱：649444395@qq.com
 */
public interface IParse {
    String toJson(Object bean);

    <T> T fromJson(String json, Class<T> clazz);

    <T> T fromJson(String json, Type type);
}
