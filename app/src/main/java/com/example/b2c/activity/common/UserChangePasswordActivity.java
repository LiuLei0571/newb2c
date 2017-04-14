package com.example.b2c.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.logistic.LogisticsLoginActivity;
import com.example.b2c.activity.seller.SellerLoginActivity;
import com.example.b2c.config.Configs;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UIHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.ResultListener;
import com.example.b2c.net.listener.base.NoDataListener;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：
 * Created by milk on 16/10/24.
 * 邮箱：649444395@qq.com
 */
public class UserChangePasswordActivity extends TempBaseActivity {


    @Bind(R.id.et_old_phone)
    EditText mEtOldPhone;
    @Bind(R.id.et_new_password)
    EditText mEtNewPassword;
    @Bind(R.id.et_again_password)
    EditText mEtAgainPassword;
    @Bind(R.id.btn_froget_password)
    Button mBtnFrogetPassword;
    @Bind(R.id.tv_title_old)
    TextView tvTitleOld;
    @Bind(R.id.tv_title_new)
    TextView tvTitleNew;
    @Bind(R.id.tv_title_again_password)
    TextView tvTitleAgainPassword;
    private int type;

    @Override
    public int getRootId() {
        return R.layout.activity_user_change_password;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getCommon_modifypassword());
        type = getIntent().getIntExtra(Configs.USER_TYPE.TYPE, 0);
        initView();
    }

    private void initView() {
        tvTitleOld.setText(mTranslatesString.getCommon_oldpsd());
        tvTitleNew.setText(mTranslatesString.getCommon_password());
        tvTitleAgainPassword.setText(mTranslatesString.getCommon_surepassword());
        mEtOldPhone.setHint(mTranslatesString.getCommon_inputoldpassword());
        mEtNewPassword.setHint(mTranslatesString.getCommon_hintpassword());
        mEtAgainPassword.setHint(mTranslatesString.getValidate_inputsurepassword());
        mBtnFrogetPassword.setText(mTranslatesString.getCommon_submit());
    }

    @OnClick(R.id.btn_froget_password)
    public void onClick() {
        if (UIHelper.checkEdit(mEtOldPhone, mEtNewPassword, mEtAgainPassword)) {
            showToast(mTranslatesString.getCommon_inputnotnull());
            return;
        }
        if (type == 1) {
            showLoading();

            rdm.ChangeSellerPassword(mEtOldPhone.getText().toString(), mEtNewPassword.getText().toString(), mEtAgainPassword.getText().toString());
            rdm.resultListener = new ResultListener() {
                @Override
                public void onSuccess(String errorInfo) {
                    ToastHelper.makeToast(errorInfo);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getIntent(UserChangePasswordActivity.this, SellerLoginActivity.class);
                        }
                    });
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
        } else if (type == 2) {
            showLoading();
            mLogisticsDataConnection.ChangeLogisticsPassword(mEtOldPhone.getText().toString(), mEtNewPassword.getText().toString(), ConstantS.BASE_URL_EXPRESS + "updatePassword.htm");
            mLogisticsDataConnection.mNodataListener = new NoDataListener() {
                @Override
                public void onSuccess(String errorInfo) {
                    ToastHelper.makeToast(success);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            UserHelper.clearUserLocalAll();
                            Intent intent = new Intent(UserChangePasswordActivity.this, LogisticsLoginActivity.class);
                            startActivity(intent);
                            onFinish();
                        }
                    });
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
    }
}
