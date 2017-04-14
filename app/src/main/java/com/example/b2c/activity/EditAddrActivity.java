package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.activity.seller.SelectCitiesDialogActivity;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.response.ShoppingAddressDetail;
import com.example.b2c.net.util.BaseValidator;
import com.example.b2c.net.util.Logs;
import com.example.b2c.widget.SettingItemView2;
import com.example.b2c.widget.util.Utils;

import org.apache.http.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 买家  新增/编辑 收货地址信息/收款地址信息
 */
public class EditAddrActivity extends TempBaseActivity implements OnClickListener {
    private EditText et_firstName,et_lastName,et_tel,et_mobile;
    private SettingItemView2 siv_location;
    private CheckBox cb_isDefault;
    private TextView tv_cb_text;
    private Button   btn_add;
    private int      addressType = 10;//默认新增，默认收货地址
    private ShoppingAddressDetail detail;
    private String contactProvinceCode,contactCityCode,contact_location;

    @Override
    public int getRootId() {
        return R.layout.add_addr_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressType = getIntent().getExtras().getInt("addressType");
        detail = (ShoppingAddressDetail) getIntent().getSerializableExtra("address");
        if(detail != null){
            addressType = detail.getType();
            contactProvinceCode = detail.getProvinceCode();
            contactCityCode = detail.getCityCode();
            contact_location = detail.getAddress();
        }
        initView();
    }

    public void initView() {

        et_firstName = (EditText) findViewById(R.id.et_firstName);
        et_lastName = (EditText) findViewById(R.id.et_lastName);
        et_tel = (EditText) findViewById(R.id.et_tel);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        siv_location = (SettingItemView2) findViewById(R.id.siv_location);
        cb_isDefault = (CheckBox) findViewById(R.id.cb_isDefault);
        tv_cb_text = (TextView) findViewById(R.id.tv_cb_text);
        btn_add = (Button) findViewById(R.id.btn_add);
        cb_isDefault.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        siv_location.setOnClickListener(this);
        initText();
    }

    public void initText() {
        if(addressType == 10){
            setTitle(mTranslatesString.getCommon_managereceivelocation());
        }else{
            setTitle(mTranslatesString.getCommon_managecashaddr());
        }
        et_firstName.setHint(mTranslatesString.getCommon_ming());
        et_lastName.setHint(mTranslatesString.getCommon_xing());
        et_tel.setHint(mTranslatesString.getCommon_dianhua()+"("+mTranslatesString.getCommon_cannull()+")");
        et_mobile.setHint(mTranslatesString.getCommon_tellphone());
        siv_location.setTv_text(mTranslatesString.getCommon_locationname());
        siv_location.setTv_doc(mTranslatesString.getCommon_pleaseselect());
        tv_cb_text.setText(mTranslatesString.getCommon_setdefaultlocation());
        btn_add.setText(mTranslatesString.getCommon_save());
        if(detail != null){
            et_firstName.setText(detail.getFirstName());
            et_lastName.setText(detail.getLastName());
            et_tel.setText(detail.getTel());
            et_mobile.setText(detail.getMobile());

            String location = Utils.cutNull(detail.getProvinceName())+" "
                    +Utils.cutNull(detail.getCityName())+" "
                    +Utils.cutNull(detail.getAddress());
            if(!TextUtils.isBlank(location)){
                siv_location.setTv_doc(location);
            }
            if (detail.getIsDefault() == 1) {
                cb_isDefault.setChecked(true);
            }else{
                cb_isDefault.setChecked(false);
            }
        }
    }
    public void addShoppingAddr() {
        String firstName = et_firstName.getText().toString();
        String lastName = et_lastName.getText().toString();
        String tel = et_tel.getText().toString();
        String mobile = et_mobile.getText().toString();
        //非空验证
        if (TextUtils.isBlank(firstName)
                || TextUtils.isBlank(lastName)
                || TextUtils.isBlank(mobile)) {
            ToastHelper.makeToast(mTranslatesString.getCommon_inputnotnull());
            return;
        }
        if(TextUtils.isBlank(contactProvinceCode)||TextUtils.isBlank(contactCityCode)||TextUtils.isBlank(contact_location)){
            ToastHelper.makeToast(mTranslatesString.getCommon_selectaddressinfo());
            return;
        }
        //规则验证
//        if(!TextUtils.isBlank(tel)){
//            if(!BaseValidator.TelMatch(tel)){
//
//            }
//            return;
//        }
        if(!BaseValidator.MobileMatch(mobile)){
            ToastHelper.makeToast(mTranslatesString.getCommon_mobilestyleerror());
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("firstName", firstName);
        map.put("lastName", lastName);
        map.put("tel", tel);
        map.put("mobile", mobile);
        map.put("provinceCode", contactProvinceCode);
        map.put("cityCode", contactCityCode);
        map.put("address", contact_location);
        map.put("type", addressType+"");
        if(cb_isDefault.isChecked()){
            map.put("isDefault", 1 + "");
        }else{
            map.put("isDefault", 0 + "");
        }
        Logs.d("com.example.b2c.activity.addAddrActivity", map.toString());
        if(detail != null){
            map.put("id",detail.getId()+"");
            rdm.ChangeShoppingAddress(map);
            rdm.responseListener = new ResponseListener() {
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
                }
                @Override
                public void onLose() {
                    ToastHelper.makeErrorToast(request_failure);
                }
            };
        }else{
            rdm.AddShoppingAddress(map);
            rdm.responseListener = new ResponseListener() {
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

                }

                @Override
                public void onLose() {
                    ToastHelper.makeErrorToast(request_failure);
                }
            };
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                addShoppingAddr();
                break;
            case R.id.siv_location:
                //跳往选择界面
                Intent intent=new Intent(EditAddrActivity.this,SelectCitiesDialogActivity.class);
                intent.putExtra("provinceCode", contactProvinceCode);
                intent.putExtra("cityCode", contactCityCode);
                intent.putExtra("detailAddress", contact_location);
                startActivityForResult(intent, 1001);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1001 && resultCode==1002){
            contactProvinceCode = data.getStringExtra("provinceCode");
            contactCityCode = data.getStringExtra("cityCode");
            contact_location = data.getStringExtra("detailAddress");
            siv_location.setTv_doc(data.getStringExtra("addressShow"));
        }
    }
}
