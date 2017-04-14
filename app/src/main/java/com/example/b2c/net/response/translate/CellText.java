package com.example.b2c.net.response.translate;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/23.
 * text value
 */
public class CellText implements Serializable{

    @SerializedName("text")
    private String  text;
    @SerializedName("value")
    private String  value;

    private boolean isChecked;

    public CellText() {
    }

    public CellText(String text, String value) {
        this.text = text;
        this.value = value;
    }

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
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "CellText{" +
                "text='" + text + '\'' +
                ", value='" + value + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
