package com.example.b2c.net.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 *  商品详情仓库集合
 * Created by yy on 2017/2/10.
 */

public class ShopWareHouse implements Serializable {

    @SerializedName("shopWarehouseId")
    private String shopWarehouseId;
    @SerializedName("provinceName")
    private String provinceName;
    @SerializedName("provinceCode")
    private String provinceCode;
    @SerializedName("cityName")
    private String cityName;
    @SerializedName("cityCode")
    private String cityCode;
    @SerializedName("address")
    private String address;
    @SerializedName("totalPrice")
    private String totalPrice;

    private String  cartIds;//购物车ids

    @SerializedName("deliveryFeeList")
    private List<DeliveryFeeDetail> deliveryFeeList;

    @SerializedName("shoppingCartList")
    private List<ShoppingCartDetail> shoppingCartList;

    public String getShopWarehouseId() {
        return shopWarehouseId;
    }

    public void setShopWarehouseId(String shopWarehouseId) {
        this.shopWarehouseId = shopWarehouseId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<DeliveryFeeDetail> getDeliveryFeeList() {
        return deliveryFeeList;
    }

    public void setDeliveryFeeList(List<DeliveryFeeDetail> deliveryFeeList) {
        this.deliveryFeeList = deliveryFeeList;
    }

    public List<ShoppingCartDetail> getShoppingCartList() {
        return shoppingCartList;
    }

    public void setShoppingCartList(List<ShoppingCartDetail> shoppingCartList) {
        this.shoppingCartList = shoppingCartList;
    }

    public String getCartIds() {
        return cartIds;
    }

    public void setCartIds(String cartIds) {
        this.cartIds = cartIds;
    }
}
