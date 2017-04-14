package com.example.b2c.net.response.seller;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 卖家仓库集合实体
 * Created by yy on 2017/2/6.
 */
public class DepotListResult implements Serializable {

    @SerializedName("rows")
    private List<DepotEntry> rows;

    public List<DepotEntry> getRows() {
        return rows;
    }
}
