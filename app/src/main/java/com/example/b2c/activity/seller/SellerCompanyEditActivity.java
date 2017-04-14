package com.example.b2c.activity.seller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.CommonEditActivity;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.response.seller.SellerCompanyEntry;
import com.example.b2c.net.util.Logs;
import com.example.b2c.widget.SettingItemView2;
import com.example.b2c.widget.util.Utils;

import org.apache.http.util.TextUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 编辑商家信息---类型为公司
 */
public class SellerCompanyEditActivity extends TempBaseActivity {

    public static final int RQ_COMPANY_NAME = 10;
    public static final int RQ_COMPANY_REGADDRESS= 11;
    public static final int RQ_COMPANY_TOTAL = 13;
    public static final int RQ_COMPANY_BUSINESSSCOPE = 14;

    public static final int RQ_COMPANY_LASTNAME = 15;
    public static final int RQ_COMPANY_FIRSTNAME= 16;
    public static final int RQ_COMPANY_EMAIL = 17;
    public static final int RQ_COMPANY_MOBILE = 18;
    public static final int RQ_COMPANY_CONTACTADDRESS = 19;

    @Bind(R.id.tv_company_info)
    TextView tv_company_info;

    @Bind(R.id.siv_companyName)
    SettingItemView2 siv_companyName;
    @Bind(R.id.siv_regAddress)
    SettingItemView2 siv_regAddress;
    @Bind(R.id.siv_regDate)
    SettingItemView2 siv_regDate;
    @Bind(R.id.siv_totalPersonNum)
    SettingItemView2 siv_totalPersonNum;
    @Bind(R.id.siv_businessScope)
    SettingItemView2 siv_businessScope;

    @Bind(R.id.tv_linkman_info)
    TextView tv_linkman_info;

    @Bind(R.id.siv_lastName)
    SettingItemView2 siv_lastName;
    @Bind(R.id.siv_firstName)
    SettingItemView2 siv_firstName;
    @Bind(R.id.siv_contactEmail)
    SettingItemView2 siv_contactEmail;
    @Bind(R.id.siv_contactMobile)
    SettingItemView2 siv_contactMobile;
    @Bind(R.id.siv_contactLocation)
    SettingItemView2 siv_contactLocation;

    private SellerCompanyEntry sellerCompanyEntry;
    private String contactProvinceCode;
    private String contactCityCode;
    private String contact_location;
    private SellerCompanyEntry sellerCompanyEntryModify = new SellerCompanyEntry();//修改之后的对象

    int mYear, mMonth, mDay;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_company_edit;
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
        sellerCompanyEntry = (SellerCompanyEntry) getIntent().getSerializableExtra("company");
        sellerCompanyEntryModify.setTotalPersonNum(sellerCompanyEntry.getTotalPersonNum());
        siv_companyName.setTv_doc(sellerCompanyEntry.getCompanyName());
        siv_regAddress.setTv_doc(sellerCompanyEntry.getRegAddress());
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
        siv_totalPersonNum.setTv_doc(sellerCompanyEntry.getTotalPersonNum()+"");
        siv_businessScope.setTv_doc(sellerCompanyEntry.getBusinessScope());
        siv_lastName.setTv_doc(sellerCompanyEntry.getLastName());
        siv_firstName.setTv_doc(sellerCompanyEntry.getFirstName());
        siv_contactEmail.setTv_doc(sellerCompanyEntry.getContactEmail());
        siv_contactMobile.setTv_doc(sellerCompanyEntry.getContactMobile());
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
    }
    /**
     * 修改商家信息
     */
    private void doUpdateCompany(SellerCompanyEntry sellerCompanyEntry) {
        Map map =  new HashMap();
        if(sellerCompanyEntry.getCompanyName() != null){
            map.put("companyName",sellerCompanyEntry.getCompanyName());
        }
        if(sellerCompanyEntry.getRegAddress() != null){
            map.put("regAddress",sellerCompanyEntry.getRegAddress());
        }
        if(sellerCompanyEntry.getRegDate() != null){
            map.put("regDate",sellerCompanyEntry.getRegDate());
        }
        map.put("totalPersonNum",sellerCompanyEntry.getTotalPersonNum());
        if(sellerCompanyEntry.getBusinessScope() != null){
            map.put("businessScope",sellerCompanyEntry.getBusinessScope());
        }
        if(sellerCompanyEntry.getFirstName() != null){
            map.put("firstName",sellerCompanyEntry.getFirstName());
        }
        if(sellerCompanyEntry.getLastName() != null){
            map.put("lastName",sellerCompanyEntry.getLastName());
        }
        if(sellerCompanyEntry.getContactEmail() != null){
            map.put("contactEmail",sellerCompanyEntry.getContactEmail());
        }
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
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, mYear);
        a.set(Calendar.MONTH, mMonth);
        a.set(Calendar.DATE, mDay);
        map.put("regDate",a.getTime().getTime());

        sellerRdm.postUpdateCompany(map);
        showLoading();
        sellerRdm.responseListener = new ResponseListener() {
            @Override
            public void onSuccess(String errorInfo) {
                ToastHelper.makeToast(errorInfo);
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

    @OnClick({R.id.siv_companyName,R.id.siv_regAddress,R.id.siv_regDate,R.id.siv_totalPersonNum,R.id.siv_businessScope, R.id.siv_lastName,R.id.siv_firstName,R.id.siv_contactEmail,R.id.siv_contactMobile,R.id.siv_contactLocation})
    protected void OnClick(View view){
        switch (view.getId()){
            case R.id.siv_companyName:
                Intent intent_company_name = new Intent(SellerCompanyEditActivity.this, CommonEditActivity.class);
                intent_company_name.putExtra("title",mTranslatesString.getCommon_companyname());
                intent_company_name.putExtra("text",sellerCompanyEntry.getCompanyName());
                startActivityForResult(intent_company_name,RQ_COMPANY_NAME);
                break;
            case R.id.siv_regAddress:
                Intent intent_company_regAddress = new Intent(SellerCompanyEditActivity.this, CommonEditActivity.class);
                intent_company_regAddress.putExtra("title",mTranslatesString.getCommon_zhucedizhi());
                intent_company_regAddress.putExtra("text",sellerCompanyEntry.getRegAddress());
                startActivityForResult(intent_company_regAddress,RQ_COMPANY_REGADDRESS);
                break;
            case R.id.siv_regDate:
//                showDatePickDlg();
                showDialog(0);
                break;
            case R.id.siv_totalPersonNum:
                Intent intent_company_total = new Intent(SellerCompanyEditActivity.this, CommonEditActivity.class);
                intent_company_total.putExtra("title",mTranslatesString.getCommon_gongsinumber());
                intent_company_total.putExtra("text",sellerCompanyEntry.getTotalPersonNum()+"");
                startActivityForResult(intent_company_total,RQ_COMPANY_TOTAL);
                break;
            case R.id.siv_businessScope:
                Intent intent_company_businessScope = new Intent(SellerCompanyEditActivity.this, CommonEditActivity.class);
                intent_company_businessScope.putExtra("title",mTranslatesString.getCommon_managefanwei());
                intent_company_businessScope.putExtra("text",sellerCompanyEntry.getBusinessScope());
                startActivityForResult(intent_company_businessScope,RQ_COMPANY_BUSINESSSCOPE);
                break;
            case R.id.siv_lastName:
                Intent intent_company_lastName = new Intent(SellerCompanyEditActivity.this, CommonEditActivity.class);
                intent_company_lastName.putExtra("title",mTranslatesString.getCommon_xing());
                intent_company_lastName.putExtra("text",sellerCompanyEntry.getLastName());
                startActivityForResult(intent_company_lastName,RQ_COMPANY_LASTNAME);
                break;
            case R.id.siv_firstName:
                Intent intent_company_firstName = new Intent(SellerCompanyEditActivity.this, CommonEditActivity.class);
                intent_company_firstName.putExtra("title",mTranslatesString.getCommon_ming());
                intent_company_firstName.putExtra("text",sellerCompanyEntry.getFirstName());
                startActivityForResult(intent_company_firstName,RQ_COMPANY_FIRSTNAME);
                break;
            case R.id.siv_contactEmail:
                Intent intent_company_contactEmail = new Intent(SellerCompanyEditActivity.this, CommonEditActivity.class);
                intent_company_contactEmail.putExtra("title",mTranslatesString.getCommon_email());
                intent_company_contactEmail.putExtra("text",sellerCompanyEntry.getContactEmail());
                startActivityForResult(intent_company_contactEmail,RQ_COMPANY_EMAIL);
                break;
            case R.id.siv_contactMobile:
                Intent intent_company_contactMobile = new Intent(SellerCompanyEditActivity.this, CommonEditActivity.class);
                intent_company_contactMobile.putExtra("title",mTranslatesString.getCommon_tellphone());
                intent_company_contactMobile.putExtra("text",sellerCompanyEntry.getContactMobile());
                startActivityForResult(intent_company_contactMobile,RQ_COMPANY_MOBILE);
                break;
            case R.id.siv_contactLocation:
                //跳往选择界面
                Intent intent=new Intent(SellerCompanyEditActivity.this,SelectCitiesDialogActivity.class);
                intent.putExtra("provinceCode", contactProvinceCode);
                intent.putExtra("cityCode", contactCityCode);
                intent.putExtra("detailAddress", contact_location);
                startActivityForResult(intent, RQ_COMPANY_CONTACTADDRESS);
                break;
            default:
                break;
        }

    }
    @Deprecated
    protected void showDatePickDlg() {
        Logs.d("showDatePickDlg",mYear+":"+mMonth+":"+mDay+":");
        final DatePickerDialog datePickerDialog = new DatePickerDialog(SellerCompanyEditActivity.this, new DatePickerDialog.OnDateSetListener() {
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
            case RQ_COMPANY_NAME:
                siv_companyName.setTv_doc(finalText);
                sellerCompanyEntryModify.setCompanyName(finalText);
                break;
            case RQ_COMPANY_REGADDRESS:
                siv_regAddress.setTv_doc(finalText);
                sellerCompanyEntryModify.setRegAddress(finalText);
                break;
            case RQ_COMPANY_TOTAL:
                siv_totalPersonNum.setTv_doc(finalText);
                sellerCompanyEntryModify.setTotalPersonNum(Integer.parseInt(finalText));
                break;
            case RQ_COMPANY_BUSINESSSCOPE:
                siv_businessScope.setTv_doc(finalText);
                sellerCompanyEntryModify.setBusinessScope(finalText);
                break;
            case RQ_COMPANY_LASTNAME:
                siv_lastName.setTv_doc(finalText);
                sellerCompanyEntryModify.setLastName(finalText);
                break;
            case RQ_COMPANY_FIRSTNAME:
                siv_firstName.setTv_doc(finalText);
                sellerCompanyEntryModify.setFirstName(finalText);
                break;
            case RQ_COMPANY_EMAIL:
                siv_contactEmail.setTv_doc(finalText);
                sellerCompanyEntryModify.setContactEmail(finalText);
                break;
            case RQ_COMPANY_MOBILE:
                siv_contactMobile.setTv_doc(finalText);
                sellerCompanyEntryModify.setContactMobile(finalText);
                break;
            case RQ_COMPANY_CONTACTADDRESS:
                contactProvinceCode = data.getStringExtra("provinceCode");
                contactCityCode = data.getStringExtra("cityCode");
                contact_location = data.getStringExtra("detailAddress");
                siv_contactLocation.setTv_doc(data.getStringExtra("addressShow"));
                break;
            default:
                break;
        }

    }
}
