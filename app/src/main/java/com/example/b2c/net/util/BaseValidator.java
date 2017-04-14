package com.example.b2c.net.util;

import org.apache.http.util.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by yy on 2017/1/20.
 */

public class BaseValidator {
    // 电子邮件匹配
    public static final Pattern emailPattern = Pattern.compile("^\\w+[[\\.-]?\\w*]*@\\w+([\\.-]\\w+)*(\\.\\w{2,100})+$");
    // 联系电话匹配
    //public static final Pattern telPattern = Pattern.compile("^0\\d{2,3}-\\d{5,9}(-[0-9]{1,5})?+$");
    // 缅甸联系电话匹配   固定电话统一8位。
    public static final Pattern telPattern = Pattern.compile("^\\d{8}$");
    // 邮编匹配
    public static final Pattern zipPattern = Pattern.compile("^(\\d){6}+$");
    // qq
    public static final Pattern qqPattern = Pattern.compile("^(\\d){6,15}+$");
    // 生日日期匹配
    public static final Pattern birthPattern = Pattern.compile("^(\\d){4}-(\\d){1,2}-(\\d){1,2}$");
    // 手机号码匹配
    //public static final Pattern mobilePattern = Pattern.compile("^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+(\\d){8})+$");
    // 缅甸手机号码匹配
    public static final Pattern mobilePattern = Pattern.compile("^((09\\d{7})|(09\\d{8})|(09\\d{9}))$");

    public static final Pattern IDCardPattern1 = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");

    public static final Pattern IDCardPattern2 = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");

    //缅甸银行卡号匹配
//    public static final Pattern bankAccountPattern = Pattern.compile("^\\d{16}$");
    public static final Pattern bankAccountPattern = Pattern.compile("^\\d{16,17}$");;

    // 用户名匹配 (用户名仅支持中英文、数字和下划线,且不能为纯数字)
    public static final Pattern loginNamePattern = Pattern.compile("^[a-z|A-Z|\\d|_]{2,20}$");

    public static final Pattern allNumberPattern = Pattern.compile("^\\d+$");

    /**
     * 检查是否匹配
     * @param value
     * @param pattern
     * @return
     */
    public static boolean isMatch(String value, Pattern pattern){
        if (TextUtils.isBlank(value)) {
            return true;
        }

        if(pattern.matcher(value).find()){
            return true;
        }

        return false;
    }

    public static boolean MobileMatch(String value) {
        if (TextUtils.isBlank(value)) {
            return false;
        }
        if (mobilePattern.matcher(value).find()) {
            return true;
        }
        return false;
    }

    public static boolean TelMatch(String value) {
        if (TextUtils.isBlank(value)) {
            return false;
        }
        if (telPattern.matcher(value).find()) {
            return true;
        }
        return false;
    }


    public static boolean EmailMatch(String value) {
        if (TextUtils.isBlank(value)) {
            return false;
        }
        if (emailPattern.matcher(value).find()) {
            return true;
        }
        return false;
    }


    public static boolean LoginNameMatch(String value) {
        if (TextUtils.isBlank(value)) {
            return false;
        }
        if (loginNamePattern.matcher(value).find()) {
            if(!allNumberPattern.matcher(value).find()) {
                return true;
            }
        }
        return false;
    }

    public static boolean BankAccountMatch(String value) {
        if (TextUtils.isBlank(value)) {
            return false;
        }
        if (bankAccountPattern.matcher(value).find()) {
            return true;
        }
        return false;
    }

    public static boolean IDNumberMatch(String value) {
        if (TextUtils.isBlank(value)) {
            return false;
        }
        if (IDCardPattern1.matcher(value).find() || IDCardPattern2.matcher(value).find()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(BankAccountMatch("123456789876532"));
    }

}
