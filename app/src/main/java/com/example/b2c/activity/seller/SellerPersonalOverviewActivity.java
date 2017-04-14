package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.PicturePreviewActivity;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.net.response.seller.GoodSources;
import com.example.b2c.net.response.seller.SellerCompanyEntry;
import com.example.b2c.net.response.translate.CellText;
import com.example.b2c.net.response.translate.OptionList;
import com.example.b2c.widget.SettingItemView2;
import com.example.b2c.widget.util.Utils;

import org.apache.http.util.TextUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 编辑商家信息---类型为个人
 */
public class SellerPersonalOverviewActivity extends TempBaseActivity {


    private SellerCompanyEntry sellerCompanyEntry;

    int mYear, mMonth, mDay;
    /**
     * 性别列表
     */
    private List<CellText> cells;
    GoodSources goodSources = new GoodSources();
    CellText cell;
    private SettingItemView2 siv_lastName;
    private SettingItemView2 siv_firstName;
    private SettingItemView2 siv_sex;
    private SettingItemView2 siv_birthDate;
    private SettingItemView2 siv_contactMobile;
    private SettingItemView2 siv_contactLocation;
    private SettingItemView2 siv_businessScope;
    private SettingItemView2 siv_idpositive;
    private SettingItemView2 siv_idreverse;


    @Override
    public int getRootId() {
        return R.layout.activity_seller_personal_edit;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_businessinfo());
        siv_firstName.setTv_text(mTranslatesString.getCommon_ming());
        siv_lastName.setTv_text(mTranslatesString.getCommon_xing());
        siv_sex.setTv_text(mTranslatesString.getCommon_sex());
        siv_birthDate.setTv_text(mTranslatesString.getCommon_birthdaydate());
        siv_contactMobile.setTv_text(mTranslatesString.getCommon_tellphone());
        siv_contactLocation.setTv_text(mTranslatesString.getCommon_lianxidizhi());
        siv_businessScope.setTv_text(mTranslatesString.getCommon_managefanwei());
        siv_idpositive.setTv_text(mTranslatesString.getCommon_idpositive());
        siv_idreverse.setTv_text(mTranslatesString.getCommon_idreverse());
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
        siv_birthDate.setTv_doc(new StringBuffer().append(mYear_str).append("-").append(mMonth_str).append("-").append(mDay_str).append(" ").toString());
    }

    private void initView() {
        siv_lastName = (SettingItemView2) findViewById(R.id.siv_lastName);
        siv_lastName.hide_RightIv();
        siv_firstName = (SettingItemView2) findViewById(R.id.siv_firstName);
        siv_firstName.hide_RightIv();
        siv_sex = (SettingItemView2) findViewById(R.id.siv_sex);
        siv_sex.hide_RightIv();
        siv_birthDate = (SettingItemView2) findViewById(R.id.siv_birthDate);
        siv_birthDate.hide_RightIv();
        siv_contactMobile = (SettingItemView2) findViewById(R.id.siv_contactMobile);
        siv_contactMobile.hide_RightIv();
        siv_contactLocation = (SettingItemView2) findViewById(R.id.siv_contactLocation);
        siv_contactLocation.hide_RightIv();
        siv_businessScope = (SettingItemView2) findViewById(R.id.siv_businessScope);
        siv_businessScope.hide_RightIv();
        siv_idpositive = (SettingItemView2) findViewById(R.id.siv_idpositive);
        siv_idpositive.setTv_doc(mTranslatesString.getCommon_look());
        siv_idpositive.setTv_docColor(R.color.main_green);
        siv_idreverse = (SettingItemView2) findViewById(R.id.siv_idreverse);
        siv_idreverse.setTv_doc(mTranslatesString.getCommon_look());
        siv_idreverse.setTv_docColor(R.color.main_green);

        sellerCompanyEntry = (SellerCompanyEntry) getIntent().getSerializableExtra("company");
        siv_lastName.setTv_doc(sellerCompanyEntry.getLastName());
        siv_firstName.setTv_doc(sellerCompanyEntry.getFirstName());
        OptionList optionList = SPHelper.getBean("options", OptionList.class);
        cells = optionList.getSexTypeCode();
        goodSources.setCells(cells);
        for (CellText cell : cells) {
            if (Integer.parseInt(cell.getValue()) == sellerCompanyEntry.getSex()) {
                cell.setChecked(true);
                siv_sex.setTv_doc(cell.getText());
            }
        }
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
        siv_contactMobile.setTv_doc(sellerCompanyEntry.getContactMobile());
        siv_businessScope.setTv_doc(sellerCompanyEntry.getBusinessScope());
        if (!TextUtils.isBlank(sellerCompanyEntry.getContactProvinceName()) || !TextUtils.isBlank(sellerCompanyEntry.getContactCityName())) {
            siv_contactLocation.setTv_doc(sellerCompanyEntry.getContactProvinceName() +
                    " " + sellerCompanyEntry.getContactCityName() +
                    " " + Utils.cutNull(sellerCompanyEntry.getContactAddress()));
        }
        addText(mTranslatesString.getCommon_modify(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SellerPersonalOverviewActivity.this, SellerPersonalEditActivity.class);
                i.putExtra("company", sellerCompanyEntry);
                startActivity(i);
                finish();
            }
        });
        siv_idpositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test(ConstantS.BASE_PIC_URL+sellerCompanyEntry.getCorporateFrontIdCardPic());
            }
        });
        siv_idreverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test(ConstantS.BASE_PIC_URL+sellerCompanyEntry.getCorporateBackIdCardPic());
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
        return getResources().getIdentifier("test", "drawable",
                getPackageName());
    }


}
