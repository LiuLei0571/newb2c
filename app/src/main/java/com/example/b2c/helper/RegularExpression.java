package com.example.b2c.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 所有的正则表达
 */

public class RegularExpression {
    //判断email格式是否正确
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    //判断密码输入格式是否正确
    public static boolean isPassword(String password) {
        String str = "^[a-zA-Z0-9_]{6,15}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }
    //判断输入格式是否是数字，字母下划线
    //判断密码输入格式是否正确
    public static boolean isUser(String user) {
        String str = "^[a-z|A-Z|\\\\d|_]{2,20}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(user);
        return m.matches();
    }
    public static boolean isPhone(String phhone){
        String str = "^((09\\\\d{7})|(09\\\\d{8})|(09\\\\d{9}))$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(phhone);
        return m.matches();
    }
    public static boolean isEmoji(String string) {
        Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        return m.find();
    }
}
