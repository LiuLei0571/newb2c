package com.example.b2c.net.util;

import org.apache.http.util.TextUtils;

import java.text.DecimalFormat;

/**
 * Created by yy on 2017/2/26.
 */
public class NumberUtil {
    public static final String DEFALUE_STYLE="###,###";

    public static double GetDoubleValue(String strValue){
        double value = 0;
        if(TextUtils.isBlank(strValue)){
            return value;
        }
        try {
            DecimalFormat decimalFormat = new DecimalFormat();
            value = decimalFormat.parse(strValue).doubleValue();
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }
    public static String  GetStringValue(double doubleValue,String defalutStyle){
        String str = "";
        DecimalFormat decimalFormat = null;
        if(defalutStyle == null){
             decimalFormat = new DecimalFormat(DEFALUE_STYLE);
        }else {
            decimalFormat = new DecimalFormat(defalutStyle);
        }
        try {
            str = decimalFormat.format(doubleValue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return str;
    }
}
