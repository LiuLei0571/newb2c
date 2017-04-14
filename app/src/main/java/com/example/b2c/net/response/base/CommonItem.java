package com.example.b2c.net.response.base;

/**
 * Created by yy on 2017/1/25.
 */
public class CommonItem {

    private String text;
    private String value;
    private boolean  checked;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
