package com.example.b2c.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.ReturnFromServerListener;
import com.example.b2c.net.listener.base.NoDataListener;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 获取验证码
 */
public class BindNumActivity extends TempBaseActivity {

    @Bind(R.id.et_num)
    EditText etNum;
    @Bind(R.id.et_verify)
    EditText etVerify;
    @Bind(R.id.btn_get_verify)
    Button btnGetVerify;
    @Bind(R.id.btn_ok)
    Button btnOk;

    @Override
    public int getRootId() {
        return R.layout.bind_num_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setTitle(mTranslatesString.getCommon_bindphone());
        etNum.setHint(mTranslatesString.getValidate_inputtellphone());
        etVerify.setHint(mTranslatesString.getValidate_inputmessagecode());
        btnGetVerify.setText(mTranslatesString.getCommon_getauthcode());
        btnOk.setText(mTranslatesString.getCommon_submit());
    }


    @OnClick({R.id.btn_get_verify, R.id.btn_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_verify:
                getVerifyCode();
                break;
            case R.id.btn_ok:
                bindPhone();
                break;
        }
    }

    private void bindPhone() {
        showLoading();
        rdm.getBindPhoneRequest(ConstantS.BASE_URL + "user/bindMobile.htm", etNum.getText().toString().trim(), etVerify.getText().toString().trim());
        rdm.mNodataListener = new NoDataListener() {
            @Override
            public void onSuccess(String success) {
                ToastHelper.makeToast(success);
                finish();

            }

            @Override
            public void onError(int errorNo,String errorInfo) {
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

    public void getVerifyCode() {
        rdm.GetMobileCode(username, false, -1, "");
        rdm.getmobilecodeListener = new ReturnFromServerListener() {

            @Override
            public void onSuccess(int errorNO, String errorInfo) {
                ToastHelper.makeToast(errorInfo);
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(mTranslatesString.getCommon_neterror());
            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
        };
    }

}
