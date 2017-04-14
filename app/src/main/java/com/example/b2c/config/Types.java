package com.example.b2c.config;

import com.alibaba.fastjson.TypeReference;
import com.example.b2c.net.response.BannerAdDetail;
import com.example.b2c.net.response.BigBrandDetail;
import com.example.b2c.net.response.FamousShopDetail;
import com.example.b2c.net.response.HotCategoryDetail;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 用途：
 * Created by milk on 16/11/6.
 * 邮箱：649444395@qq.com
 */

public interface Types {
    Type listString = new TypeReference<List<Object>>() {
    }.getType();
    Type listBannerAd = new TypeReference<List<BannerAdDetail>>() {
    }.getType();
    Type listBigBrand = new TypeReference<List<BigBrandDetail>>() {
    }.getType();
    Type listFamousShop = new TypeReference<List<FamousShopDetail>>() {
    }.getType();
    Type listHotCategory = new TypeReference<List<HotCategoryDetail>>() {
    }.getType();
}
