package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 物流详情Cell
 * Created by yy on 2017/2/13.
 */

public class TimeLine implements Serializable{

    @SerializedName("place")
    private String  maddress;
    @SerializedName("createTime")
    private String  mtime;

    public TimeLine( String address ,String time){

        this.maddress = address;
        this.mtime = time;
    }


    public String getMaddress() {
        return maddress;
    }

    public void setMaddress(String maddress) {
        this.maddress = maddress;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

}
