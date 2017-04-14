package com.example.b2c.activity.common;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.b2c.R;
import com.example.b2c.config.Configs;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UIHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.listener.ReturnFromServerListener;
import com.example.b2c.widget.SendSmsButton;
import com.example.b2c.widget.util.SendSmsTimerHelper;

import butterknife.Bind;
import butterknife.OnClick;

import static com.example.b2c.R.id.et_login_name;

/**
 * 用途：
 * Created by milk on 16/10/24.
 * 邮箱：649444395@qq.com
 */
public class UserForgetPasswordActivity extends TempBaseActivity {
    private SendSmsTimerHelper mSmsTimerHelper;
    private SendSmsTimerHelper.UiUpdateListener listener;
    @Bind(et_login_name)
    EditText mEtLoginName;
    @Bind(R.id.et_phone)
    EditText mEtPhone;
    @Bind(R.id.et_sms)
    EditText mEtSms;
    @Bind(R.id.btn_sms)
    SendSmsButton mBtnSms;
    @Bind(R.id.et_password)
    EditText mEtPassword;
    @Bind(R.id.btn_froget_password)
    Button mBtnFrogetPassword;
    @Bind(R.id.et_againg_password)
    EditText mEtAgaingPassword;

    private boolean needOutSms = false;
    private int type;

    @Override
    public int getRootId() {
        return R.layout.activity_user_forget_password;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getCommon_forgetpassword());
        initView();
        initText();
        type = getIntent().getExtras().getInt(Configs.USER_TYPE.TYPE, 0);
        mSmsTimerHelper = SendSmsTimerHelper.getInstance();
        listener = new SendSmsTimerHelper.UiUpdateListener() {
            @Override
            public void onUIUpdate(int left) {
                onUpdateMainLeft(left);
            }
        };
        mSmsTimerHelper.addListener(listener);
    }

    private void initText() {
        mEtLoginName.setHint(mTranslatesString.getCommon_pleaseinputloginname());
        mEtPhone.setHint(mTranslatesString.getValidate_inputtellphone());
        mEtSms.setHint(mTranslatesString.getValidate_inputmessagecode());
        mBtnSms.setText(mTranslatesString.getCommon_getauthcode());
        mEtPassword.setHint(mTranslatesString.getValidate_inputpassword());
        mEtAgaingPassword.setHint(mTranslatesString.getValidate_inputsurepassword());
        mBtnFrogetPassword.setText(mTranslatesString.getCommon_submit());
    }

    private void initView() {
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mBtnSms.setPhone(s.toString());
            }
        });

    }

    public void setText(String text, boolean enable) {
        mBtnSms.setState(text, enable);

    }

    public void setNeedOutSend(boolean needOutSend) {
        this.needOutSms = needOutSend;
        mEtSms.setEnabled(!needOutSms);
    }

    public String getPhone() {
        return mEtPhone.getText().toString();
    }

    private void onUpdateMainLeft(int left) {
        if (left > 0) {
            setText("%"+left+"$s "+ mTranslatesString.getCommon_smstitle(), false);
        } else {
            boolean flag = false;
            if (!TextUtils.isEmpty(getPhone())) {
                flag = true;
            }
            setText(mTranslatesString.getCommon_authcode(), flag);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSmsTimerHelper != null) {
            mSmsTimerHelper.removeListener(listener);
            mSmsTimerHelper = null;
        }
    }

    @OnClick({R.id.btn_froget_password, R.id.btn_sms})
    public void getOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_froget_password:
                showLoading();
                if (UIHelper.checkEdit(mEtLoginName, mEtPhone, mEtPassword, mEtSms, mEtAgaingPassword)) {
                    dissLoading();
                    showAlertDialog(mTranslatesString.getCommon_inputnotnull());
                    return;
                }
                rdm.ForgetSellerPassword(mEtLoginName.getText().toString(), mEtPhone.getText().toString(), mEtPassword.getText().toString(), mEtAgaingPassword.getText().toString(), mEtSms.getText().toString());
                rdm.responseListener = new ResponseListener() {


                    @Override
                    public void onSuccess(String errorInfo) {
                        ToastHelper.makeToast(errorInfo);
                        finish();
                    }

                    @Override
                    public void onError(int errorNO, String errorInfo) {
                        showAlertDialog(errorInfo);
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
                break;
            case R.id.btn_sms:
                if (UIHelper.checkEdit(mEtLoginName)) {
                    showAlertDialog(mTranslatesString.getCommon_pleaseinputloginname());
                    return;
                }
                mSmsTimerHelper.start();
                rdm.GetUserMobileCode(mEtLoginName.getText().toString(), mEtPhone.getText().toString());
                rdm.getmobilecodeListener = new ReturnFromServerListener() {
                    @Override
                    public void onSuccess(int errorNO, String errorInfo) {

                    }

                    @Override
                    public void onError(int errorNO, String errorInfo) {
                        showAlertDialog(errorInfo);
                    }

                    @Override
                    public void onFinish() {

                    }
                    @Override
                    public void onLose() {
                        ToastHelper.makeErrorToast(request_failure);
                    }
                };
                break;
        }
    }
}
