package com.example.b2c.net.response.translate;

import com.google.gson.annotations.SerializedName;

public class Translates {
    @SerializedName("mobileStaticTextCode")
    private MobileStaticTextCode mobileStaticTextCode;
    @SerializedName("optionList")
    private OptionList optionList;

    public MobileStaticTextCode getMobileStaticTextCode() {
        return mobileStaticTextCode;
    }

    public void setMobileStaticTextCode(MobileStaticTextCode mobileStaticTextCode) {
        this.mobileStaticTextCode = mobileStaticTextCode;
    }

    public OptionList getOptionList() {
        return optionList;
    }

    public void setOptionList(OptionList optionList) {
        this.optionList = optionList;
    }
}
