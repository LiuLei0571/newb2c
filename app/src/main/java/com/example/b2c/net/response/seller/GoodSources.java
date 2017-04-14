package com.example.b2c.net.response.seller;

import com.example.b2c.net.response.translate.CellText;

import java.io.Serializable;
import java.util.List;

/**
 * item选择集合
 * 卖家货物来源/性别之类。。
 * Created by yy on 2017/1/25.
 */
public class GoodSources implements Serializable {

    private List<CellText> cells;

    public GoodSources() {
    }
    public GoodSources(List<CellText> cells) {
        this.cells = cells;
    }

    public List<CellText> getCells() {
        return cells;
    }

    public void setCells(List<CellText> cells) {
        this.cells = cells;
    }
}
