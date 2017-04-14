package com.example.b2c.widget.util;

import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.b2c.widget.SimpleTextWatcher;

import java.util.Map;


/**
 * @author Created by Xinzai on 2016/3/9.
 */
public class TypeParse {

    private static final String[] keys = {
            "maskType",
            "maxLength",
            "valueType",
    };

    /**
     * 设置背景颜色
     **/
    public static void setBackground(View view, String color) {
        if (TextUtils.isEmpty(color) || !color.startsWith("#") || color.length() < 3) return;
        if (color.length() >= 7) {
            color = color.substring(0, 7);
        } else {
            color = color.substring(0, 4);

        }
        view.setBackgroundColor(Color.parseColor(color));
    }

    public static void setTextColor(TextView view, String color) {
        if (TextUtils.isEmpty(color) || !color.startsWith("#") || color.length() < 3) return;
        if (color.length() >= 7) {
            color = color.substring(0, 7);
        } else {
            color = color.substring(0, 4);

        }
        view.setTextColor(Color.parseColor(color));

    }

    /**
     * 设置文本输入长度
     **/
    public static void setMaxLength(EditText editText, int length) {
        if (length < 1) return;
        InputFilter[] inputFilters = {new InputFilter.LengthFilter(length)};
        editText.setFilters(inputFilters);
    }

    /**
     * 设置输入类型
     */
    public static void setInputType(EditText editText, String type) {
        if (TextUtils.isEmpty(type)) return;
        switch (type) {
            case "number":
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case "password":
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case "text":
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case "string":
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
        }
    }

    public static void setMaskType(final EditText editText, String type) {
        if (TextUtils.isEmpty(type)) return;
        switch (type) {
            case "telephone":
                editText.addTextChangedListener(new SimpleTextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Utils.setEmptyText(editText, s, count, 3, 7, 11);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                break;
            case "IDCard":
                editText.addTextChangedListener(new SimpleTextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        int a = editText.getText().toString().length();
                        if (count == 1 && (a == 6 || a == 11 || a == 16)) {
                            editText.setText(String.format("%s ", s));
                            editText.setSelection(s.length() + 1);
                        } else if (count == 0 && (s.length() > 0 && (a == 6 || a == 11 || a == 16))) {
                            editText.setText(s.subSequence(0, s.length() - 1));
                            editText.setSelection(s.length() - 1);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
        }

    }

    public static void setUserAction(Map<String, String> map, EditText value) {
        if (map == null || map.isEmpty()) return;
        for (String key : keys) {
            if (map.containsKey(key)) {
                switch (key) {
                    case "maskType":
                        TypeParse.setMaskType(value, map.get(key));
                        break;
                    case "maxLength":
                        TypeParse.setMaxLength(value, Integer.valueOf(map.get(key)));
                        break;
                    case "valueType":
                        TypeParse.setInputType(value, map.get(key));
                        break;
                }
            }
        }
    }

}
