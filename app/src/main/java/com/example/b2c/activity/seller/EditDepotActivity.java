package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.util.TimingLogger;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.CommonEditActivity;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.response.seller.DepotEntry;
import com.example.b2c.net.util.BaseValidator;
import com.example.b2c.net.util.Logs;
import com.example.b2c.widget.SettingItemView2;
import com.example.b2c.widget.util.Utils;

import org.apache.http.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

/**
 * 编辑仓库、新建仓库页面
 */
public class EditDepotActivity extends TempBaseActivity {

    public static final int RQ_LOCATION = 1001;
    public static final int RQ_FIRSTNAME = 1002;
    public static final int RQ_LASTNAME = 1003;
    public static final int RQ_MOBILE = 1004;

    private SettingItemView2 siv_location;
    private SettingItemView2 siv_firstName;
    private SettingItemView2 siv_lastName;
    private SettingItemView2 siv_mobile;

    private int depotId;
    private DepotEntry depotEntry;
    private CheckBox cb_isDefault;
    private TextView tv_cb_text;

    @Override
    public int getRootId() {
        return R.layout.activity_edit_depot;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
    }

    private void initText() {
        if(depotId>0){
            setTitle(mTranslatesString.getCommon_depotdetail());
        }else{
            setTitle(mTranslatesString.getCommon_newinsertdepot());
        }
        siv_location.setTv_text(mTranslatesString.getCommon_locationname());
        String location = Utils.cutNull(depotEntry.getProvinceName())+" "
                +Utils.cutNull(depotEntry.getCityName())+" "
                +Utils.cutNull(depotEntry.getAddress());
        if(!TextUtils.isBlank(location)){
            siv_location.setTv_doc(location);
        }

        siv_firstName.setTv_text(mTranslatesString.getCommon_linkmanming());
        if(!"".equals(depotEntry.getContactFirstName()) && depotEntry.getContactFirstName()!=null){
            siv_firstName.setTv_doc(depotEntry.getContactFirstName());
        }
        siv_lastName.setTv_text(mTranslatesString.getCommon_linkmanxing());
        if(!"".equals(depotEntry.getContactLastName()) && depotEntry.getContactLastName()!=null){
            siv_lastName.setTv_doc(depotEntry.getContactLastName());
        }
        siv_mobile.setTv_text(mTranslatesString.getCommon_linktel());
        if(!"".equals(depotEntry.getContactMobile()) && depotEntry.getContactMobile()!=null){
            siv_mobile.setTv_doc(depotEntry.getContactMobile());
        }
        if(depotEntry.getIsDefault()==1){
            cb_isDefault.setChecked(true);
        }else {
            cb_isDefault.setChecked(false);
        }
        tv_cb_text.setText(mTranslatesString.getCommon_setdefaultdepot());
    }

    private void initView() {
        depotEntry = (DepotEntry) getIntent().getExtras().getSerializable("depot");
        depotId = depotEntry.getId();
        siv_location = (SettingItemView2) findViewById(R.id.siv_location);
        siv_firstName = (SettingItemView2) findViewById(R.id.siv_firstName);
        siv_lastName = (SettingItemView2) findViewById(R.id.siv_lastName);
        siv_mobile = (SettingItemView2) findViewById(R.id.siv_mobile);
        cb_isDefault = (CheckBox) findViewById(R.id.cb_isDefault);
        tv_cb_text = (TextView) findViewById(R.id.tv_cb_text);
        addText(mTranslatesString.getCommon_save(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyDepot();
            }
        });
    }
    private void modifyDepot() {
        Map map = new HashMap();
        if(depotId>0){
            map.put("shopWarehouseId",depotEntry.getId());
        }
        map.put("provinceCode",depotEntry.getProvinceCode());
        map.put("cityCode",depotEntry.getCityCode());
        map.put("address",depotEntry.getAddress());
        map.put("contactFirstName",depotEntry.getContactFirstName());
        map.put("contactLastName",depotEntry.getContactLastName());
        map.put("contactMobile",depotEntry.getContactMobile());
        if(cb_isDefault.isChecked()){
            map.put("isDefault",1);
        }else {
            map.put("isDefault",0);
        }

        showLoading();
        sellerRdm.modifyDepot(map);
        sellerRdm.mResponseListener = new ResponseListener() {
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
                ToastHelper.makeErrorToast(net_error);
            }
        };


    }
    @OnClick({R.id.siv_location , R.id.siv_firstName , R.id.siv_lastName , R.id.siv_mobile})
    protected void OnClick(View view){
        switch (view.getId()){
            case R.id.siv_location:
                //跳往选择界面
                Intent intent=new Intent(EditDepotActivity.this,SelectCitiesDialogActivity.class);
                intent.putExtra("provinceCode", depotEntry.getProvinceCode());
                intent.putExtra("cityCode", depotEntry.getCityCode());
                intent.putExtra("detailAddress", depotEntry.getAddress());
                startActivityForResult(intent, RQ_LOCATION);
                break;
            case R.id.siv_firstName:
                Intent intent_first_name = new Intent(EditDepotActivity.this, CommonEditActivity.class);
                intent_first_name.putExtra("title", mTranslatesString.getCommon_linkmanming());
                intent_first_name.putExtra("text", depotEntry.getContactFirstName());
                startActivityForResult(intent_first_name, RQ_FIRSTNAME);
                break;
            case R.id.siv_lastName:
                Intent intent_last_name = new Intent(EditDepotActivity.this, CommonEditActivity.class);
                intent_last_name.putExtra("title", mTranslatesString.getCommon_linkmanxing());
                intent_last_name.putExtra("text", depotEntry.getContactLastName());
                startActivityForResult(intent_last_name, RQ_LASTNAME);
                break;
            case R.id.siv_mobile:
                Intent intent_mobile = new Intent(EditDepotActivity.this, CommonEditActivity.class);
                intent_mobile.putExtra("title", mTranslatesString.getCommon_linktel());
                intent_mobile.putExtra("text", depotEntry.getContactMobile());
                startActivityForResult(intent_mobile, RQ_MOBILE);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (data == null) {
            return;
        }
        final String finalText = (!TextUtils.isBlank(data.getStringExtra("text"))) ? data.getStringExtra("text").toString() : "";
        switch (requestCode){
            case RQ_LOCATION:
                depotEntry.setProvinceCode(data.getStringExtra("provinceCode"));
                depotEntry.setCityCode(data.getStringExtra("cityCode"));
                depotEntry.setAddress(data.getStringExtra("detailAddress"));
                siv_location.setTv_doc(data.getStringExtra("addressShow"));
                break;
            case RQ_FIRSTNAME:
                depotEntry.setContactFirstName(finalText);
                siv_firstName.setTv_doc(finalText);
                break;
            case RQ_LASTNAME:
                depotEntry.setContactLastName(finalText);
                siv_lastName.setTv_doc(finalText);
                break;
            case RQ_MOBILE:
                if(BaseValidator.MobileMatch(finalText)){
                    depotEntry.setContactMobile(finalText);
                    siv_mobile.setTv_doc(finalText);
                }else {
                    ToastHelper.makeToast(mTranslatesString.getCommon_mobilestyleerror());
                }
                break;
            default:
                break;
        }


    }
}
