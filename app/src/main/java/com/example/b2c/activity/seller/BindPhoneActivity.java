package com.example.b2c.activity.seller;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.ResponseListener;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：
 * Created by milk on 16/11/3.
 * 邮箱：649444395@qq.com
 */

public class BindPhoneActivity extends TempBaseActivity {
    @Bind(R.id.et_phone)
    EditText mEtPhone;
    @Bind(R.id.btn_submit)
    Button mBtnSubmit;
    @Bind(R.id.tv_title_phone)
    TextView tvTitlePhone;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_bind_phone;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getCommon_changephone());
        tvTitlePhone.setText(mTranslatesString.getCommon_tellphone());
        mBtnSubmit.setText(mTranslatesString.getCommon_submit());
        mEtPhone.setHint(mTranslatesString.getValidate_inputtellphone());
    }

    @OnClick(R.id.btn_submit)
    public void onClick() {
        if (mEtPhone.getText().toString().trim().isEmpty()) {
            ToastHelper.makeErrorToast(mTranslatesString.getValidate_inputtellphone());
            return;
        }
        showLoading();
        sellerRdm.getChangePhoneRequest(ConstantS.BASE_URL + "user/mobile/update.htm", mEtPhone.getText().toString());
        sellerRdm.mResponseListener = new ResponseListener() {
            @Override
            public void onSuccess(final String errorInfo) {
                ToastHelper.makeToast("登出成功");
                finish();
            }

            @Override
            public void onError(int errorNO, final String errorInfo) {
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
}
