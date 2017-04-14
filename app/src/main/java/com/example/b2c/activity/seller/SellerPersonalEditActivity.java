package com.example.b2c.activity.seller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.example.b2c.R;
import com.example.b2c.activity.CommonEditActivity;
import com.example.b2c.activity.CommonItemOneActivity;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.response.seller.GoodSources;
import com.example.b2c.net.response.seller.SellerCompanyEntry;
import com.example.b2c.net.response.translate.CellText;
import com.example.b2c.net.response.translate.OptionList;
import com.example.b2c.net.util.Logs;
import com.example.b2c.widget.SettingItemView2;
import com.example.b2c.widget.util.Utils;

import org.apache.http.util.TextUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 编辑商家信息---类型为个人
 */
public class SellerPersonalEditActivity extends TempBaseActivity {

    public static final int RQ_PERSONAL_LASTNAME = 10;
    public static final int RQ_PERSONAL_FIRSTNAME= 11;
    public static final int RQ_PERSONAL_SEX = 12;
    public static final int RQ_PERSONAL_BIRTHDATE = 13;

    public static final int RQ_PERSONAL_CONTACTMOBILE = 14;
    public static final int RQ_PERSONAL_CONTACTADDRESS = 15;
    public static final int RQ_PERSONAL_BUSINESSSCOPE= 16;

    @Bind(R.id.siv_lastName)
    SettingItemView2 siv_lastName;
    @Bind(R.id.siv_firstName)
    SettingItemView2 siv_firstName;
    @Bind(R.id.siv_sex)
    SettingItemView2 siv_sex;
    @Bind(R.id.siv_birthDate)
    SettingItemView2 siv_birthDate;

    @Bind(R.id.siv_contactMobile)
    SettingItemView2 siv_contactMobile;
    @Bind(R.id.siv_contactLocation)
    SettingItemView2 siv_contactLocation;
    @Bind(R.id.siv_businessScope)
    SettingItemView2 siv_businessScope;
    @Bind(R.id.siv_idpositive)
    SettingItemView2 siv_idpositive;
    @Bind(R.id.siv_idreverse)
    SettingItemView2 siv_idreverse;

    private SellerCompanyEntry sellerCompanyEntry;
    private String contactProvinceCode;
    private String contactCityCode;
    private String contact_location;

    private SellerCompanyEntry sellerCompanyEntryModify = new SellerCompanyEntry();//修改之后的对象

    int mYear, mMonth, mDay;
    /**
     * 性别列表
     */
    private List<CellText> cells;
    GoodSources goodSources =new GoodSources();
    CellText cell;

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
        sellerCompanyEntry = (SellerCompanyEntry) getIntent().getSerializableExtra("company");
        siv_lastName.setTv_doc(sellerCompanyEntry.getLastName());
        siv_firstName.setTv_doc(sellerCompanyEntry.getFirstName());
        OptionList optionList = SPHelper.getBean("options", OptionList.class);
        cells = optionList.getSexTypeCode();
        goodSources.setCells(cells);
        for(CellText cell : cells){
            if(Integer.parseInt(cell.getValue()) == sellerCompanyEntry.getSex()){
                cell.setChecked(true);
                siv_sex.setTv_doc(cell.getText());
            }
        }
        Calendar ca = Calendar.getInstance();
        if(!TextUtils.isBlank(sellerCompanyEntry.getRegDate())){
            ca.setTime(new Date(Long.parseLong(sellerCompanyEntry.getRegDate())));
            mYear = ca.get(Calendar.YEAR);
            mMonth = ca.get(Calendar.MONTH);
            mDay = ca.get(Calendar.DAY_OF_MONTH);
            display(mYear,mMonth,mDay);
        }else {
            mYear = ca.get(Calendar.YEAR);
            mMonth = ca.get(Calendar.MONTH);
            mDay = ca.get(Calendar.DAY_OF_MONTH);
        }
        siv_contactMobile.setTv_doc(sellerCompanyEntry.getContactMobile());
        siv_businessScope.setTv_doc(sellerCompanyEntry.getBusinessScope());
        if(!TextUtils.isBlank(sellerCompanyEntry.getContactProvinceName())||!TextUtils.isBlank(sellerCompanyEntry.getContactCityName())){
            siv_contactLocation.setTv_doc(sellerCompanyEntry.getContactProvinceName()+
                    " "+sellerCompanyEntry.getContactCityName()+
                    " "+ Utils.cutNull(sellerCompanyEntry.getContactAddress()));
        }
        contactProvinceCode = sellerCompanyEntry.getContactProvinceCode();
        contactCityCode = sellerCompanyEntry.getContactCityCode();
        contact_location = sellerCompanyEntry.getContactAddress();
        addText(mTranslatesString.getCommon_submit(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUpdateCompany(sellerCompanyEntryModify);
            }
        });

        siv_idpositive.hide();
        siv_idreverse.hide();
    }
    /**
     * 修改商家信息
     */
    private void doUpdateCompany(SellerCompanyEntry sellerCompanyEntry) {
        Map map =  new HashMap();
        if(sellerCompanyEntry.getFirstName() != null){
            map.put("firstName",sellerCompanyEntry.getFirstName());
        }
        if(sellerCompanyEntry.getLastName() != null){
            map.put("lastName",sellerCompanyEntry.getLastName());
        }
        map.put("sex",sellerCompanyEntry.getSex());
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, mYear);
        a.set(Calendar.MONTH, mMonth);
        a.set(Calendar.DATE, mDay);
        map.put("birthDate",a.getTime().getTime());
        if(sellerCompanyEntry.getContactMobile() != null){
            map.put("contactMobile",sellerCompanyEntry.getContactMobile());
        }
        if(contactProvinceCode != null){
            map.put("contactProvinceCode",contactProvinceCode);
        }
        if(contactCityCode != null){
            map.put("contactCityCode",contactCityCode);
        }
        if(contact_location != null){
            map.put("contactAddress",contact_location);
        }
        if(sellerCompanyEntry.getBusinessScope() != null){
            map.put("businessScope",sellerCompanyEntry.getBusinessScope());
        }
        sellerRdm.postUpdateCompany(map);
        showLoading();
        sellerRdm.responseListener = new ResponseListener() {
            @Override
            public void onSuccess(String errorInfo) {
                finish();
            }
            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }
            @Override
            public void onFinish() {
                dissLoading();
            }
            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }
        };

    }

    @OnClick({ R.id.siv_lastName,R.id.siv_firstName,R.id.siv_sex,R.id.siv_birthDate,R.id.siv_contactMobile,R.id.siv_contactLocation,R.id.siv_businessScope})
    protected void OnClick(View view){
        switch (view.getId()){

            case R.id.siv_lastName:
                Intent intent_company_lastName = new Intent(SellerPersonalEditActivity.this, CommonEditActivity.class);
                intent_company_lastName.putExtra("title",mTranslatesString.getCommon_xing());
                intent_company_lastName.putExtra("text",sellerCompanyEntry.getLastName());
                startActivityForResult(intent_company_lastName,RQ_PERSONAL_LASTNAME);
                break;
            case R.id.siv_firstName:
                Intent intent_company_firstName = new Intent(SellerPersonalEditActivity.this, CommonEditActivity.class);
                intent_company_firstName.putExtra("title",mTranslatesString.getCommon_ming());
                intent_company_firstName.putExtra("text",sellerCompanyEntry.getFirstName());
                startActivityForResult(intent_company_firstName,RQ_PERSONAL_FIRSTNAME);
                break;
            case R.id.siv_sex:
                Intent intent_goods_source = new Intent(SellerPersonalEditActivity.this, CommonItemOneActivity.class);
                intent_goods_source.putExtra("title",mTranslatesString.getCommon_sex());
                intent_goods_source.putExtra("items",goodSources);
                startActivityForResult(intent_goods_source,RQ_PERSONAL_SEX);
                break;
            case R.id.siv_birthDate:
//                showDatePickDlg();
                showDialog(0);
                break;
            case R.id.siv_contactMobile:
                Intent intent_company_contactEmail = new Intent(SellerPersonalEditActivity.this, CommonEditActivity.class);
                intent_company_contactEmail.putExtra("title",mTranslatesString.getCommon_tellphone());
                intent_company_contactEmail.putExtra("text",sellerCompanyEntry.getContactEmail());
                startActivityForResult(intent_company_contactEmail,RQ_PERSONAL_CONTACTMOBILE);
                break;
            case R.id.siv_contactLocation:
                //跳往选择界面
                Intent intent=new Intent(SellerPersonalEditActivity.this,SelectCitiesDialogActivity.class);
                intent.putExtra("provinceCode", contactProvinceCode);
                intent.putExtra("cityCode", contactCityCode);
                intent.putExtra("detailAddress", contact_location);
                startActivityForResult(intent, RQ_PERSONAL_CONTACTADDRESS);
                break;
            case R.id.siv_businessScope:
                Intent intent_company_businessScope = new Intent(SellerPersonalEditActivity.this, CommonEditActivity.class);
                intent_company_businessScope.putExtra("title",mTranslatesString.getCommon_managefanwei());
                intent_company_businessScope.putExtra("text",sellerCompanyEntry.getBusinessScope());
                startActivityForResult(intent_company_businessScope,RQ_PERSONAL_BUSINESSSCOPE);
                break;
            default:
                break;
        }

    }
    protected void showDatePickDlg() {
        Logs.d("showDatePickDlg",mYear+":"+mMonth+":"+mDay+":");
        final DatePickerDialog datePickerDialog = new DatePickerDialog(SellerPersonalEditActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                display(mYear,mMonth,mDay);
            }
        },mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    //选择日期
    @Override
    protected Dialog onCreateDialog(int id) {
//        final Calendar c = Calendar.getInstance();
//        mYear = c.get(Calendar.YEAR);
//        mMonth = c.get(Calendar.MONTH);
//        mDay = c.get(Calendar.DAY_OF_MONTH);
        switch (id) {
            case 0:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
                        mDay);
        }
        return null;
    }

    private final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if(mYear == year && mMonth == monthOfYear && dayOfMonth == mDay ){
                return;
            }
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            Calendar a = Calendar.getInstance();
            a.set(Calendar.YEAR, mYear);
            a.set(Calendar.MONTH, mMonth);
            a.set(Calendar.DATE, mDay);
            sellerCompanyEntry.setRegDate(a.getTime().getTime() + "");
            display(mYear,mMonth,mDay);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (data == null) {
            return;
        }
        final String finalText = (!TextUtils.isBlank(data.getStringExtra("text")))?data.getStringExtra("text").toString():"";
        switch (requestCode){
            case RQ_PERSONAL_LASTNAME:
                siv_lastName.setTv_doc(finalText);
                sellerCompanyEntryModify.setLastName(finalText);
                break;
            case RQ_PERSONAL_FIRSTNAME:
                siv_firstName.setTv_doc(finalText);
                sellerCompanyEntryModify.setFirstName(finalText);
                break;
            case RQ_PERSONAL_SEX:
                cell = (CellText) data.getSerializableExtra("object");
                sellerCompanyEntryModify.setSex(Integer.parseInt(cell.getValue()));
                siv_sex.setTv_doc(cell.getText());
                break;
            case RQ_PERSONAL_CONTACTMOBILE:
                siv_contactMobile.setTv_doc(finalText);
                sellerCompanyEntryModify.setContactMobile(finalText);
                break;
            case RQ_PERSONAL_CONTACTADDRESS:
                contactProvinceCode = data.getStringExtra("provinceCode");
                contactCityCode = data.getStringExtra("cityCode");
                contact_location = data.getStringExtra("detailAddress");
                siv_contactLocation.setTv_doc(data.getStringExtra("addressShow"));
                break;
            case RQ_PERSONAL_BUSINESSSCOPE:
                siv_businessScope.setTv_doc(finalText);
                sellerCompanyEntryModify.setFirstName(finalText);
                break;
            default:
                break;
        }

    }


}
