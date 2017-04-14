package com.example.b2c.net.response.translate;

import com.example.b2c.net.response.common.TranslationResult;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/23.
 */
public class OptionList implements Serializable {

    @SerializedName("cargoSourceTypeCode")//货物来源
    private List<CellText> cargoSourceTypeCode;
    @SerializedName("sexTypeCode")//性别类型
    private List<CellText> sexTypeCode;
    @SerializedName("deliveryType")
    private List<TranslationResult> deliveryType;
    @SerializedName("deliveryTypeNew")
    private List<TranslationResult> deliveryTypeNew;
    @SerializedName("refuseReason")//退货理由
    private List<CellText> refuseReason;
    @SerializedName("returnGoodStatus")//退货状态
    private List<CellText> returnGoodStatus;
    @SerializedName("couponType")
    private List<CellText> couponType;//优惠券类型


    public List<TranslationResult> getDeliveryTypeNew() {
        return deliveryTypeNew;
    }

    public void setDeliveryTypeNew(List<TranslationResult> deliveryTypeNew) {
        this.deliveryTypeNew = deliveryTypeNew;
    }

    public List<TranslationResult> getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(List<TranslationResult> deliveryType) {
        this.deliveryType = deliveryType;
    }
    public List<CellText> getCargoSourceTypeCode() {
        return cargoSourceTypeCode;
    }

    public void setCargoSourceTypeCode(List<CellText> cargoSourceTypeCode) {
        this.cargoSourceTypeCode = cargoSourceTypeCode;
    }

    public List<CellText> getSexTypeCode() {
        return sexTypeCode;
    }

    public void setSexTypeCode(List<CellText> sexTypeCode) {
        this.sexTypeCode = sexTypeCode;
    }

    public List<CellText> getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(List<CellText> refuseReason) {
        this.refuseReason = refuseReason;
    }

    public List<CellText> getReturnGoodStatus() {
        return returnGoodStatus;
    }

    public void setReturnGoodStatus(List<CellText> returnGoodStatus) {
        this.returnGoodStatus = returnGoodStatus;
    }

    public List<CellText> getCouponType() {
        return couponType;
    }

    public void setCouponType(List<CellText> couponType) {
        this.couponType = couponType;
    }
}
