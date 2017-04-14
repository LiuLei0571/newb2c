package com.example.b2c.activity.seller;

import android.os.Bundle;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.net.response.seller.LogisticContactInfo;
import com.example.b2c.widget.SettingItemView2;
import com.example.b2c.widget.util.Utils;

/**
 * 卖家查看快递公司 联系人信息页面
 */
public class CourierContactInfoActivity extends TempBaseActivity {



    private SettingItemView2 siv_contact_name;
    private SettingItemView2 siv_contactEmail;
    private SettingItemView2 siv_contactMobile;
    private SettingItemView2 siv_contactTel;
    private LogisticContactInfo mLogisticContactInfo;

    @Override
    public int getRootId() {
        return R.layout.activity_courier_contact_info;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_linkinfo());
        siv_contact_name.setTv_text(mTranslatesString.getCommon_linkman());
        siv_contact_name.setTv_doc(Utils.cutNull(mLogisticContactInfo.getContactFirstName())+" "+Utils.cutNull(mLogisticContactInfo.getContactLastName()));
        siv_contactEmail.setTv_text(mTranslatesString.getCommon_email());
        siv_contactEmail.setTv_doc(Utils.cutNull(mLogisticContactInfo.getContactEmail()));
        siv_contactMobile.setTv_text(mTranslatesString.getCommon_tellphone());
        siv_contactMobile.setTv_doc(Utils.cutNull(mLogisticContactInfo.getContactMobile()));
        siv_contactTel.setTv_text(mTranslatesString.getCommon_dianhua());
        siv_contactTel.setTv_doc(Utils.cutNull(mLogisticContactInfo.getContactTel()));
    }

    private void initView() {
        mLogisticContactInfo = (LogisticContactInfo) getIntent().getSerializableExtra("contactInfo");
        siv_contact_name = (SettingItemView2) findViewById(R.id.siv_contact_name);
        siv_contact_name.hide_RightIv();
        siv_contactEmail = (SettingItemView2) findViewById(R.id.siv_contactEmail);
        siv_contactEmail.hide_RightIv();
        siv_contactMobile = (SettingItemView2) findViewById(R.id.siv_contactMobile);
        siv_contactMobile.hide_RightIv();
        siv_contactTel = (SettingItemView2) findViewById(R.id.siv_contactTel);
        siv_contactTel.hide_RightIv();
    }
}
