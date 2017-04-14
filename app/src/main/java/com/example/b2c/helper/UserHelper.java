package com.example.b2c.helper;

import android.text.TextUtils;

import com.example.b2c.config.Configs;
import com.example.b2c.net.response.Users;

/**
 * 用途：
 * Created by milk on 16/10/26.
 * 邮箱：649444395@qq.com
 */

public class UserHelper {
    public static Users sEmptyUsers = new Users();

    public static Users getBuyerFromLocal() {
        Users users = SPHelper.getBean(Configs.BUYER.INFO, Users.class);
        if (users != null) {
            return users;
        }
        return sEmptyUsers;
    }
    public static boolean isLivesLogin() {
        Users users = getLivesFromLocal();

        return !((users == sEmptyUsers) || TextUtils.isEmpty(users.getToken()));
    }
    public static boolean isBuyerLogin() {
        Users users = getBuyerFromLocal();

        return !((users == sEmptyUsers) || TextUtils.isEmpty(users.getToken()));
    }

    public static String getBuyertoken() {
        Users user = getBuyerFromLocal();
        if (user == sEmptyUsers) {
            return "";
        } else {
            return user.getToken();
        }
    }

    public static String getBuyerName() {
        Users user = getBuyerFromLocal();
        if (user == sEmptyUsers) {
            return "";
        } else {
            return user.getLoginName();
        }
    }

    public static int getBuyerId() {
        Users user = getBuyerFromLocal();
        if (user == sEmptyUsers) {
            return -1;
        } else {
            return user.getUserId();
        }
    }

    public static void saveBuyerUpdateInfoLocal(Users users) {
        Users oldUsers = getBuyerFromLocal();
        if (oldUsers != null) {
            users.setUserId(oldUsers.getUserId());
            users.setLoginName(oldUsers.getLoginName());
            users.setToken(oldUsers.getToken());
        }
    }

    public static void saveButerReplaceInfoLocal(Users users) {
        SPHelper.putBean(Configs.SELLER.INFO, Users.class);
    }
    public static Users getLivesFromLocal() {
        Users users = SPHelper.getBean(Configs.LIVES.INFO, Users.class);
        if (users != null) {
            return users;
        }
        return sEmptyUsers;
    }
    public static Users getSellerFromLocal() {
        Users users = SPHelper.getBean(Configs.SELLER.INFO, Users.class);
        if (users != null) {
            return users;
        }
        return sEmptyUsers;
    }

    public static boolean isSellerLogin() {
        Users users = getSellerFromLocal();

        return !((users == sEmptyUsers) || TextUtils.isEmpty(users.getToken()));
    }
    public static  int getLivesID(){
        Users user = getLivesFromLocal();
        if (user == sEmptyUsers) {
            return -1;
        } else {
            return user.getUserId();
        }
    }
    public static int getSellerID() {
        Users user = getSellerFromLocal();
        if (user == sEmptyUsers) {
            return -1;
        } else {
            return user.getUserId();
        }
    }
    public static int getSellerStep() {
        Users user = getSellerFromLocal();
        if (user == sEmptyUsers) {
            return -1;
        } else {
            return user.getStep();
        }
    }
    public static String getLivesToken(){
        Users user = getLivesFromLocal();
        if (user == sEmptyUsers) {
            return "";
        } else {
            return user.getToken();
        }
    }
    public static String getSellerToken() {
        Users user = getSellerFromLocal();
        if (user == sEmptyUsers) {
            return "";
        } else {
            return user.getToken();
        }
    }

    public static String getSellerLoginName() {
        Users user = getSellerFromLocal();
        if (user == sEmptyUsers) {
            return "";
        } else {
            return user.getLoginName();
        }
    }

    public static void saveSellerUpdateInfoLocal(Users users) {
        Users oldUsers = getBuyerFromLocal();
        if (oldUsers != null) {
            users.setUserId(oldUsers.getUserId());
            users.setLoginName(oldUsers.getLoginName());
            users.setToken(oldUsers.getToken());
        }
    }

    public static void saveSellerReplaceInfoLocal(Users users) {
        SPHelper.putBean(Configs.SELLER.INFO, Users.class);
    }

    public static void clearUserLocalAll() {
        SPHelper.remove(Configs.BUYER.INFO);
        SPHelper.remove(Configs.SELLER.INFO);
        SPHelper.remove(Configs.COURIER.INFO);
        SPHelper.remove(Configs.EXPRESS.INFO);
        SPHelper.remove(Configs.LIVES.INFO);
        SPHelper.remove(Configs.USER_TYPE.TYPE);
        SPHelper.remove(Configs.isLogin);
        SPHelper.remove(Configs.TOKEN);
        SPHelper.remove(Configs.USERID);
        SPHelper.remove(Configs.NAME);
        SPHelper.remove("countryCode");
        SPHelper.remove("deliveryCompanyPhone");
        SPHelper.remove("staffPhone");
    }

    public static void clear(String name) {
        SPHelper.remove(name);
    }

    public static Users getExpressFromLocal() {
        Users users = SPHelper.getBean(Configs.EXPRESS.INFO, Users.class);
        if (users != null) {
            return users;
        }
        return sEmptyUsers;
    }


    public static boolean isExpressLogin() {
        Users users = getExpressFromLocal();

        return !((users == sEmptyUsers) || TextUtils.isEmpty(users.getToken()));
    }

    public static int getExpressID() {
        Users user = getExpressFromLocal();
        if (user == sEmptyUsers) {
            return -1;
        } else {
            return user.getUserId();
        }
    }
    public static int getType(){
        int users = SPHelper.getInt(Configs.USER_TYPE.TYPE);
        return users;
    }
    public static int getExpressType() {
        Users user = getExpressFromLocal();
        if (user == sEmptyUsers) {
            return -1;
        } else {
            return user.getUserType();
        }
    }
public static int getLivesLoginId(){
    Users livesFromLocal = getLivesFromLocal();
    if (livesFromLocal == sEmptyUsers) {
        return -1;
    } else {
        return livesFromLocal.getDeliveryCompanyId();
    }
}
    public static int getExpressLoginId() {
        Users user = getExpressFromLocal();
        if (user == sEmptyUsers) {
            return -1;
        } else {
            return user.getDeliveryCompanyId();
        }
    }

    public static String getSExpressToken() {
        Users user = getExpressFromLocal();
        if (user == sEmptyUsers) {
            return "";
        } else {
            return user.getToken();
        }
    }

    public static String getExpressLoginName() {
        Users user = getExpressFromLocal();
        if (user == sEmptyUsers) {
            return "";
        } else {
            return user.getLoginName();
        }
    }

    public static void saveExpressUpdateInfoLocal(Users users) {
        Users oldUsers = getExpressFromLocal();
        if (oldUsers != null) {
            users.setUserId(oldUsers.getUserId());
            users.setLoginName(oldUsers.getLoginName());
            users.setToken(oldUsers.getToken());
        }
    }

    public static void saveExpressReplaceInfoLocal(Users users) {
        SPHelper.putBean(Configs.EXPRESS.INFO, users);
    }

}
