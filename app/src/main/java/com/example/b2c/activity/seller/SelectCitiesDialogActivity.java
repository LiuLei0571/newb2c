package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.dialog.AreaDialogHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.ResponseHanderListener;
import com.example.b2c.net.response.CodeDetail;
import com.example.b2c.net.util.Logs;

import org.apache.http.util.TextUtils;

import java.util.List;


/**
 * 选择省份城市两级区域
 */
public class SelectCitiesDialogActivity extends TempBaseActivity implements View.OnClickListener {
    /**
     * 结果码
     */
    public static final int SELECT_CITY = 1002;
    private String mProvinceCode;
    private String mCityCode;
    private String detailAddress;
    private String mCallbackProvinceCode;
    List<CodeDetail> provinceCodeList;
    List<CodeDetail> cityCodeList;

    private String mCurrentProviceName = "";//当前省的名称
    private String mCurrentCityName = "";//当前城市的名称

    private TextView mProvinceView;
    private TextView mCityView;

    private EditText mDetailAddress;

    private Button btn_submit;

    private boolean firstLoad = true;


    @Override
    public int getRootId() {
        return R.layout.activity_select_cities_dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {

        mProvinceView = (TextView) findViewById(R.id.tv_province);
        mCityView = (TextView) findViewById(R.id.tv_city);
        mDetailAddress = (EditText) findViewById(R.id.et_detailAddress);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        mProvinceCode = getIntent().getStringExtra("provinceCode");
        mCityCode = getIntent().getStringExtra("cityCode");
        detailAddress = getIntent().getStringExtra("detailAddress");


        mCallbackProvinceCode = mProvinceCode;
        initProvinceCode();
        mDetailAddress.setText(detailAddress);

        mProvinceView.setOnClickListener(this);
        mCityView.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        initText();

    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_locationinfo());
        mDetailAddress.setHint(mTranslatesString.getCommon_pleaseinputdetaillocation());
        btn_submit.setText(mTranslatesString.getCommon_sure());
    }

    /**
     * 初始化省份选择器
     */
    public void initProvinceCode() {
        rdm.GetAllProvinceCode(UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
        rdm.responseHanderListener = new ResponseHanderListener<CodeDetail>() {
            @Override
            public void onSuccess(final List<CodeDetail> list) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        provinceCodeList = list;
                        if (!TextUtils.isBlank(mProvinceCode)) {
                            for (CodeDetail detail : provinceCodeList) {
                                if (mProvinceCode.equals(detail.getValue())) {
                                    mProvinceView.setText(detail.getText());
                                    mCurrentProviceName = detail.getText();
                                }
                            }
                        } else {
                            mProvinceView.setText(provinceCodeList.get(0).getText());
                            mCurrentProviceName = provinceCodeList.get(0).getText();
                            mProvinceCode = provinceCodeList.get(0).getValue();
                        }
                        if (firstLoad) {
                            changeCode(mProvinceCode, false);
                        }
                    }
                });

            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                showAlertDialog(errorInfo);
            }

            @Override
            public void lose() {
                showAlertDialog(request_failure);
            }
        };
    }

    /**
     * 根据指定省份获取城市选项列表
     *
     * @param code
     */
    public void changeCode(String code, final boolean isClick) {
        rdm.GetNextCode(UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken(), code);
        rdm.responseHanderListener = new ResponseHanderListener<CodeDetail>() {
            @Override
            public void onSuccess(List<CodeDetail> list) {
                cityCodeList = list;
                Logs.d("cityCodeList", cityCodeList.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (firstLoad && !isClick) {
                            if (!TextUtils.isBlank(mCityCode)) {
                                for (CodeDetail detail : cityCodeList) {
                                    if (mCityCode.equals(detail.getValue())) {
                                        Logs.d("detail", detail.toString());
                                        mCityView.setText(detail.getText());
                                        mCurrentCityName = detail.getText();
                                    }
                                }
                            } else {
                                mCityView.setText(cityCodeList.get(0).getText());
                                mCityCode = cityCodeList.get(0).getValue();
                                mCurrentCityName = cityCodeList.get(0).getText();
                            }
                            firstLoad = false;
                        } else if (isClick) {
                            if (cityCodeList.size() == 0) {
                                return;
                            }
                            AreaDialogHelper.showListPopupWindows(SelectCitiesDialogActivity.this, mTranslatesString.getCommon_city(), cityCodeList, new AreaDialogHelper.AbstractOnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position) {
                                    mCityCode = cityCodeList.get(position).getValue();
                                    mCurrentCityName = cityCodeList.get(position).getText();
                                    mCityView.setText(mCurrentCityName);
                                }
                            });
                        }
                    }
                });

            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                showAlertDialog(errorInfo);
            }

            @Override
            public void lose() {
                showAlertDialog(request_failure);
            }
        };
    }

    boolean notChange;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_province:
                AreaDialogHelper.showListPopupWindows(SelectCitiesDialogActivity.this, mTranslatesString.getCommon_province(), provinceCodeList, new AreaDialogHelper.AbstractOnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position) {
                        mCallbackProvinceCode = provinceCodeList.get(position).getValue();
                        mCurrentProviceName = provinceCodeList.get(position).getText();
                        mProvinceView.setText(mCurrentProviceName);
                        Logs.d("mProvinceCode == mCallbackProvinceCode", mProvinceCode + "$" + mCallbackProvinceCode);
                        notChange = (mProvinceCode == mCallbackProvinceCode);
                        mProvinceCode = mCallbackProvinceCode;
                        if (!notChange) {
                            mCityView.setText("");
                            mCityCode = "";
                            mCurrentCityName = "";
                        }
                    }
                });
                break;
            case R.id.tv_city:
                if (!TextUtils.isBlank(mProvinceCode)) {
                    changeCode(mProvinceCode, true);
                }
                break;
            case R.id.btn_submit:
                //非空验证
                if(TextUtils.isBlank(mProvinceCode)||TextUtils.isBlank(mCityCode)){
                    ToastHelper.makeToast(mTranslatesString.getCommon_selectaddressinfo());
                    return;
                }
                if (TextUtils.isBlank(mDetailAddress.getText().toString())) {
                    ToastHelper.makeToast(mTranslatesString.getCommon_pleaseinputdetaillocation());
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("detailAddress", mDetailAddress.getText().toString());
                intent.putExtra("addressShow", mCurrentProviceName + " " + mCurrentCityName + " " + mDetailAddress.getText().toString());
                intent.putExtra("provinceCode", mProvinceCode);
                intent.putExtra("cityCode", mCityCode);

                setResult(SELECT_CITY, intent);
                finish();
                break;
            default:
                break;
        }
    }
}
