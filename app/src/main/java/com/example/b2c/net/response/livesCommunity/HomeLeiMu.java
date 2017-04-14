package com.example.b2c.net.response.livesCommunity;

import java.util.List;

/**
 * 生活专区首页类目
 */

public class HomeLeiMu {
    private List<Rows>rows;

    public List<Rows> getRows() {
        return rows;
    }

    public void setRows(List<Rows> rows) {
        this.rows = rows;
    }

    public class Rows{
        private int id;
        private String categoryName;

        public String getCategoryPicPath() {
            return categoryPicPath;
        }

        public void setCategoryPicPath(String categoryPicPath) {
            this.categoryPicPath = categoryPicPath;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        private String categoryPicPath;
    }
}
