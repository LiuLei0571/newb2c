package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.PicturePreviewActivity;
import com.example.b2c.net.response.seller.SellerCompanyEntry;
import com.example.b2c.widget.SettingItemView2;
import com.example.b2c.widget.util.Utils;

import org.apache.http.util.TextUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 查看商家信息---类型为公司
 */
public class SellerCompanyOverviewActivity extends TempBaseActivity{


    private SellerCompanyEntry sellerCompanyEntry;
    int mYear, mMonth, mDay;
    private TextView tv_company_info;
    private SettingItemView2 siv_companyName;
    private SettingItemView2 siv_regAddress;
    private SettingItemView2 siv_regDate;
    private SettingItemView2 siv_totalPersonNum;
    private SettingItemView2 siv_businessScope;
    private SettingItemView2 siv_license;
    private TextView tv_linkman_info;
    private SettingItemView2 siv_lastName;
    private SettingItemView2 siv_firstName;
    private SettingItemView2 siv_contactEmail;
    private SettingItemView2 siv_contactMobile;
    private SettingItemView2 siv_contactLocation;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_company_overview;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_gongsixinxi());
        tv_company_info.setText(mTranslatesString.getCommon_basicinfo());
        siv_companyName.setTv_text(mTranslatesString.getCommon_companyname());
        siv_regAddress.setTv_text(mTranslatesString.getCommon_zhucedizhi());
        siv_regDate.setTv_text(mTranslatesString.getCommon_zhucetime());
        siv_totalPersonNum.setTv_text(mTranslatesString.getCommon_gongsinumber());
        siv_businessScope.setTv_text(mTranslatesString.getCommon_managefanwei());
        siv_license.setTv_text(mTranslatesString.getCommon_yingyezhizhao());
        siv_license.setTv_doc(mTranslatesString.getCommon_look());
        siv_license.setTv_docColor(R.color.main_green);

        tv_linkman_info.setText(mTranslatesString.getCommon_lianxirenxinxi());

        siv_firstName.setTv_text(mTranslatesString.getCommon_ming());
        siv_lastName.setTv_text(mTranslatesString.getCommon_xing());
        siv_contactEmail.setTv_text(mTranslatesString.getCommon_email());
        siv_contactMobile.setTv_text(mTranslatesString.getCommon_tellphone());
        siv_contactLocation.setTv_text(mTranslatesString.getCommon_lianxidizhi());
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display(int mYear, int mMonth, int mDay) {
        String mYear_str = String.valueOf(mYear);
        String mMonth_str = String.valueOf(mMonth+1);
        String mDay_str = String.valueOf(mDay);
        if (mMonth_str.length() < 2)
            mMonth_str = "0" + mMonth_str;
        if(mDay_str.length() < 2)
            mDay_str = "0"+ mDay_str;
        siv_regDate.setTv_doc(new StringBuffer().append(mYear_str).append("-").append(mMonth_str).append("-").append(mDay_str).append(" ").toString());
    }

    private void initView() {
        tv_company_info = (TextView) findViewById(R.id.tv_company_info);
        siv_companyName = (SettingItemView2) findViewById(R.id.siv_companyName);
        siv_companyName.hide_RightIv();
        siv_regAddress = (SettingItemView2) findViewById(R.id.siv_regAddress);
        siv_regAddress.hide_RightIv();
        siv_regDate = (SettingItemView2) findViewById(R.id.siv_regDate);
        siv_regDate.hide_RightIv();
        siv_totalPersonNum = (SettingItemView2) findViewById(R.id.siv_totalPersonNum);
        siv_totalPersonNum.hide_RightIv();
        siv_businessScope = (SettingItemView2) findViewById(R.id.siv_businessScope);
        siv_businessScope.hide_RightIv();
        siv_license = (SettingItemView2) findViewById(R.id.siv_license);
        siv_license.hide_RightTv();
        tv_linkman_info = (TextView) findViewById(R.id.tv_linkman_info);
        siv_lastName = (SettingItemView2) findViewById(R.id.siv_lastName);
        siv_lastName.hide_RightIv();
        siv_firstName = (SettingItemView2) findViewById(R.id.siv_firstName);
        siv_firstName.hide_RightIv();
        siv_contactEmail = (SettingItemView2) findViewById(R.id.siv_contactEmail);
        siv_contactEmail.hide_RightIv();
        siv_contactMobile = (SettingItemView2) findViewById(R.id.siv_contactMobile);
        siv_contactMobile.hide_RightIv();
        siv_contactLocation = (SettingItemView2) findViewById(R.id.siv_contactLocation);
        siv_contactLocation.hide_RightIv();

        sellerCompanyEntry = (SellerCompanyEntry) getIntent().getSerializableExtra("company");
        siv_companyName.setTv_doc(sellerCompanyEntry.getCompanyName());
        siv_regAddress.setTv_doc(sellerCompanyEntry.getRegAddress());
        Calendar ca = Calendar.getInstance();
        if (!TextUtils.isBlank(sellerCompanyEntry.getRegDate())) {
            ca.setTime(new Date(Long.parseLong(sellerCompanyEntry.getRegDate())));
            mYear = ca.get(Calendar.YEAR);
            mMonth = ca.get(Calendar.MONTH);
            mDay = ca.get(Calendar.DAY_OF_MONTH);
            display(mYear, mMonth, mDay);
        } else {
            mYear = ca.get(Calendar.YEAR);
            mMonth = ca.get(Calendar.MONTH);
            mDay = ca.get(Calendar.DAY_OF_MONTH);
        }
        siv_totalPersonNum.setTv_doc(sellerCompanyEntry.getTotalPersonNum() + "");
        siv_businessScope.setTv_doc(sellerCompanyEntry.getBusinessScope());
        siv_lastName.setTv_doc(sellerCompanyEntry.getLastName());
        siv_firstName.setTv_doc(sellerCompanyEntry.getFirstName());
        siv_contactEmail.setTv_doc(sellerCompanyEntry.getContactEmail());
        siv_contactMobile.setTv_doc(sellerCompanyEntry.getContactMobile());
        if (!TextUtils.isBlank(sellerCompanyEntry.getContactProvinceName()) || !TextUtils.isBlank(sellerCompanyEntry.getContactCityName())) {
            siv_contactLocation.setTv_doc(sellerCompanyEntry.getContactProvinceName() +
                    " " + sellerCompanyEntry.getContactCityName() +
                    " " + Utils.cutNull(sellerCompanyEntry.getContactAddress()));
        }
        addText(mTranslatesString.getCommon_modify(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SellerCompanyOverviewActivity.this, SellerCompanyEditActivity.class);
                i.putExtra("company", sellerCompanyEntry);
                startActivity(i);
                finish();
            }
        });

        siv_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示营业执照
                //跳转到大图界面
                test(ConstantS.BASE_PIC_URL+sellerCompanyEntry.getBusinessLicense());
            }
        });

    }
    /**
     * 获取网络图片进行大图浏览
     */
    private void test(String url) {
        Intent intent = new Intent(this, PicturePreviewActivity.class);
        intent.putExtra("url", url);
        // intent.putExtra("smallPath", getSmallPath());
        intent.putExtra("indentify", getIdentify());
        this.startActivity(intent);
    }
    private int getIdentify() {
        return getResources().getIdentifier("test", "drawable",getPackageName());
    }


}
