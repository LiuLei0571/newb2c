package com.example.b2c.config;

/**
 * 用途：
 * Created by milk on 16/10/25.
 * 邮箱：649444395@qq.com
 */

public interface Configs {

    String CURRENCY_UNIT = "Ks";
    String isLogin = "isLogin";
    String USERID = "userId";
    String TOKEN = "token";
    String NAME = "username";
    String LANGUAGE = "language";
    int MAX_IMAGE_SIZE = 6;
    String APPNAME = "appName";
    String DOMAIN ="android.jinqiao.com";

    interface USER_TYPE {
        String TYPE = "userType";
        int BUYER = 0;
        int SELLER = 1;
        int EXPRESS = 2;
        int COURIER = 3;
    }
    interface LIVES{
        String INFO = "livesInfo";
    }
    interface BUYER {
        String INFO = "buyerInfo";
    }

    interface SELLER {
        String INFO = "sellerInfo";
    }

    interface COURIER {
        String INFO = "courier";
    }

    interface EXPRESS {
        String INFO = "express";
    }

    interface ORDER {
        String TYPE = "staff_order_type";
    }
    interface RELEASE{
        interface PRODUCT{
            String FROMTYPE="choose";
            String Bucket="multi";
        }
    }
}
