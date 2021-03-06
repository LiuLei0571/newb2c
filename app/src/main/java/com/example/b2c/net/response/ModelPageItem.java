package com.example.b2c.net.response;


import com.example.b2c.temple.ITemplateModel;
import com.example.b2c.temple.Templates;
import com.google.gson.annotations.SerializedName;

/**
 * 请求首页数据模板
 *
 * @author Created by liulei on 2016/05/05.
 */
public class ModelPageItem implements ITemplateModel {
    @SerializedName("itemUrl")
    private String itemUrl;

    // 模板子项标题文字（支持 HTML 标签来定义颜色、下划线之类的简单文字效果）
    @SerializedName("itemTitle")
    private String itemTitle;
    @SerializedName("itemText")
    private String itemText;
    @SerializedName("itemIcon")
    private String itemIcon;
    @SerializedName("itemImage")
    private String itemImage;
    @SerializedName("itemMessage")
    private String itemMessage;
    //模板子项文字前的图标链接地址
    @SerializedName("itemTextIcon")
    private String itemTextIcon;
//    @SerializedName("itemTags")
//    private List<String> itemTags;

    public static final String PRICE_TYPE_PRICE_TOTAL = "PRICE_TOTAL";
    public static final String PRICE_TYPE_PRICE_PERIOD = "PRICE_PERIOD";

    //模板子项图片拉伸方式    SCALEMODE_SCALEFILL   //拉伸，用于上传的自定义图片
    //  SCALEMODE_ASPECTFIT //不拉伸等比居中，用于商品图
    @SerializedName("itemImageScaleMode")
    private String itemImageScaleMode;

    @SerializedName("itemType")
    private String itemType;
    @SerializedName("itemGoodsList")
    private int itemGoodsList;
    @SerializedName("itemStoreId")
    private int itemStoreId;
    @SerializedName("itemGoodsDetailId")
    private int itemGoodsDetailId;

    public String getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(String itemIcon) {
        this.itemIcon = itemIcon;
    }

    public String getItemMessage() {
        return itemMessage;
    }

    public void setItemMessage(String itemMessage) {
        this.itemMessage = itemMessage;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getItemGoodsList() {
        return itemGoodsList;
    }

    public void setItemGoodsList(int itemGoodsList) {
        this.itemGoodsList = itemGoodsList;
    }

    public int getItemStoreId() {
        return itemStoreId;
    }

    public void setItemStoreId(int itemStoreId) {
        this.itemStoreId = itemStoreId;
    }

    public int getItemGoodsDetailId() {
        return itemGoodsDetailId;
    }

    public void setItemGoodsDetailId(int itemGoodsDetailId) {
        this.itemGoodsDetailId = itemGoodsDetailId;
    }

    public static final String SCALE_MODE_CENTER = "SCALEMODE_ASPECTFIT";

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public String getItemTextIcon() {
        return itemTextIcon;
    }

    public void setItemTextIcon(String itemTextIcon) {
        this.itemTextIcon = itemTextIcon;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }


    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }


    public String getItemImageScaleMode() {
        return itemImageScaleMode;
    }

    public void setItemImageScaleMode(String itemImageScaleMode) {
        this.itemImageScaleMode = itemImageScaleMode;
    }

    @Override
    public Templates getTemplate() {
//        return Templates.LOCAL_HOT_SALE_ITEM;
        return null;
    }
}
