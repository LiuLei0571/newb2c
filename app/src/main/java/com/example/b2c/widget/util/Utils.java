package com.example.b2c.widget.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Created by xinzai on 2015/5/21.
 */
public class Utils {

    /**
     * @param ts 原有类型
     * @return List<T> 返回类型
     * TODO(对集合的非空验证，避免异常)
     */
    public static <T> List<T> cutNull(List<T> ts) {
        List<T> list = (ts != null ? ts : new ArrayList<T>());
        Iterator<T> t = list.iterator();
        while (t.hasNext()) {
            if (t.next() == null) {
                t.remove();
            }
        }
        return list;
    }

    /**
     * @param ts 数据源
     * @return List<T> 返回类型
     * TODO(对集合的非空验证,避免异常)
     */
    public static <T> Set<T> cutNull(Set<T> ts) {
        Set<T> set = (ts != null ? ts : new HashSet<T>());
        Iterator<T> t = set.iterator();
        while (t.hasNext()) {
            if (t.next() == null) {
                t.remove();
            }
        }
        return set;
    }

    public static String cutNull(String str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }
    public static String cutNullWithZero(String str) {
        return TextUtils.isEmpty(str) ? "0" : str;
    }
    /**
     * 数组转队列
     */
    public static <T> List<T> toList(T[] ts) {
        List<T> tss = new ArrayList<>();
        tss.addAll(Arrays.asList(ts));
        return tss;
    }

    public static String emptyDefault(String content, String defaultValue) {

        return content == null || content.trim().equals("") ? defaultValue : content;

    }


    /**
     * 手机号验证
     *
     * @param str 手机号
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p;
        Matcher m;
        boolean b;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }


    /**
     * 检查SD卡是否存在
     *
     * @return
     */
    public static boolean checkSDcard(Context context) {
        boolean flag = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (!flag) {
//            UIHelper.makeImgToast(context, "请插入手机存储卡再使用本功能");
            Toast.makeText(context, "请插入手机存储卡再使用本功能", Toast.LENGTH_LONG);
        }
        return flag;
    }

    /**
     * TODO（在字符串中插入空格）
     *
     * @param str             TODO 需要拆分的字符串
     * @param insertPositions TODO 拆分位置
     */
    public static String spiltNub(String str, int... insertPositions) {
        Arrays.sort(insertPositions);
        List<String> res = new ArrayList<>();
        if (insertPositions.length == 0)
            return str;
        int prev = 0;
        for (int position : insertPositions) {
            if (position > str.length()) {
                res.add(str.substring(prev, str.length()));
                return anyToString(res);
            }
            res.add(str.substring(prev, position));
            prev = position;
        }

        if (insertPositions[insertPositions.length - 1] < str.length()) {
            res.add(str.substring(prev, str.length()));
        }
        return anyToString(res);
    }

    public static <T> String anyToString(List<T> ts) {
        ts = Utils.cutNull(ts);
        String res = "";
        for (T t : ts) {
            res += t + " ";
        }
        return res.trim();
    }

    /***
     * TODO（在字符串中插入空格）
     *
     * @param str    TODO 需要拆分的字符串
     * @param insert 拆分个数
     */
    public static String spiltNub(String str, short insert) {
        int arr[] = new int[(str.length() / insert) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = insert * (i + 1) >= str.length() ? str.length()
                    : insert * (i + 1);
        }
        return spiltNub(str, arr);
    }

    public static boolean isEmail(String str) {
        if (TextUtils.isEmpty(str)) return false;
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


    public static void setEmptyText(EditText editText, CharSequence s, int count, int... insertPositions) {
        int length = editText.getText().toString().length();
        int position = editText.getSelectionEnd();

        if (count > 0) {
            if (position > 0) {
                String str = editText.getText().toString().replaceAll(" ", "");
                String sp = Utils.spiltNub(str, insertPositions);//3, 7, 11
                if (!sp.equals(editText.getText().toString())) {
                    editText.setText(sp);
                    editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                    int len = editText.getText().toString().length();
                    editText.setSelection(sp.length() > editText.getText().toString().length() ? len : sp.length());
                }
                if (position < length) editText.setSelection(position);
            }
        } else if (count == 0 && s.length() > 0) {
            if (position > 0 && position < length) {
                String str = editText.getText().toString().replaceAll(" ", "");
                String sp = Utils.spiltNub(str, 3, 7, 11);
                if (!sp.equals(editText.getText().toString().trim())) {
                    editText.setText(sp);
                }
                editText.setSelection(position);
            }
        }
    }

    public static void setEmptyText(EditText editText, CharSequence s, int count, String insertPosition) {
        int length = editText.getText().toString().length();
        int position = editText.getSelectionEnd();
        int inpo = Integer.valueOf(insertPosition);
        if (count > 0) {
            if (position > 0) {
                String str = editText.getText().toString().replaceAll(" ", "");
                String sp = Utils.spiltNub(str, inpo);//3, 7, 11
                if (!sp.equals(editText.getText().toString())) {
                    editText.setText(sp);
                    editText.setSelection(sp.length());
                }
                if (position < length) editText.setSelection(position);
            }
        } else if (count == 0 && s.length() > 0) {
            if (position > 0 && position < length) {
                String str = editText.getText().toString().replaceAll(" ", "");
                String sp = Utils.spiltNub(str, inpo);
                if (!sp.equals(editText.getText().toString().trim())) {
                    editText.setText(sp);
                }
                editText.setSelection(position);
            }
        }
    }


    public static <T> List<T> overTurn(List<T> ts) {
        List<T> tst = new ArrayList<>();
        for (int i = ts.size() - 1; i >= 0; i--) {
            tst.add(ts.get(i));
        }
        return tst;
    }


    public static String getChannelCode(Context context) {

        String code = getMetaData(context, "CHANNEL");
        if (code != null) {
            return code;
        }
        return "AXD";
    }

    private static String getMetaData(Context context, String key) {

        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            Object value = ai.metaData.get(key);
            if (value != null) {
                return value.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 去除汉字,归正编码
     */
    public static String replaceHanzi(String input) {
        if (TextUtils.isEmpty(input)) {
            return "";
        }

        /**
         * 归正编码
         */
        byte[] bytes = input.getBytes();
        String info = "";

        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] < 0) {
                bytes[i] = 32;
            }
            info = info + new String(new byte[]{bytes[i]});
        }

        /**
         * 去除中文
         */
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(info);
        List<String> inputs = new ArrayList<>();

        if (m.find()) {
            for (int i = 0; i < info.length(); i++) {
                String ever = info.substring(i, i + 1);
                Matcher m1 = p.matcher(ever);
                if (m1.find()) {
                    ever = "";
                }
                inputs.add(ever);
            }

            String inputNew = "";
            for (int i = 0; i < inputs.size(); i++) {
                inputNew = inputNew + inputs.get(i);
            }
            return inputNew.trim();

        }
        return info.trim();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) {
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    /**
     * 判断服务是否开启
     */
    public static boolean isServiceAlive(Context context, String serviceClassName) {
        android.app.ActivityManager manager = (android.app.ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<android.app.ActivityManager.RunningServiceInfo> running = manager
                .getRunningServices(30);
        for (int i = 0; i < running.size(); i++) {
            if (serviceClassName.equals(running.get(i).service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取宽高
     */
    public static String getPhonePixels(Activity activity) {
        if (activity != null) {
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            int widthPixels = dm.widthPixels;
            int heightPixels = dm.heightPixels;
            return widthPixels + "-" + heightPixels;
        }
        return "unKnown";
    }

    public static DisplayMetrics getMetrics(Activity activity) {
        if (activity != null) {
            DisplayMetrics metric = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
            return metric;
        } else {
            return null;
        }
    }


    /**
     * 检测系统是否为MIUI
     */
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    public static boolean isMIUI() {
        try {
            BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String substringName(String name) {
        if (TextUtils.isEmpty(name)) return "";
        int length = name.length();
        String str = "*";
        if (length == 2) {
            return str + name.substring(length - 1, length);
        } else if (length == 3) {
            return str + name.substring(length - 2, length);
        } else if (length == 4) {
            return str + str + name.substring(length - 2, length);
        } else if (name.contains("·")) {
            return str + name.substring(name.indexOf("·"), length);
        }

        return name;
    }

    public static String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 展现html格式内容
     *
     * @param content
     * @return
     */
    static public void showHtmlContent(TextView tv, String content) {
        if (TextUtils.isEmpty(content)) {
            tv.setText("");
        } else {
            tv.setText(Html.fromHtml(content));
        }
    }

    public static <T> ArrayList<T> fromJsonList(String json, Class<T> cls) {
        ArrayList<T> mList = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            mList.add(new Gson().fromJson(elem, cls));
        }
        return mList;
    }
}


