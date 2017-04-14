package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 3.11.1获取卖家财务信息
 * Created by yy on 2017/2/7.
 */
public class FinaceCellList implements Serializable {

    @SerializedName("rows")
    private List<FinaceCell> rows;

    public List<FinaceCell> getRows() {
        return rows;
    }
}
