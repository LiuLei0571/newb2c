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
import com.example.b2c.helper.UIHelper;
import com.example.b2c.widget.SendSmsButton;
import com.example.b2c.widget.util.SendSmsTimerHelper;
import com.example.b2c.widget.util.Utils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：
 * Created by milk on 16/10/24.
 * 邮箱：649444395@qq.com
 */
public class UserRegisterPasswordActivity extends TempBaseActivity {
    @Bind(R.id.et_againg_password)
    EditText mEtAgaingPassword;
    private SendSmsTimerHelper mSmsTimerHelper;
    private SendSmsTimerHelper.UiUpdateListener listener;
    @Bind(R.id.et_phone)
    EditText mEtPhone;
    @Bind(R.id.et_sms)
    EditText mEtSms;
    @Bind(R.id.btn_sms)
    SendSmsButton mBtnSms;
    @Bind(R.id.et_password)
    EditText mEtPassword;
    @Bind(R.id.btn_froget_password)
    Button mBtnRegister;
    private boolean needOutSms = false;
    private int type;

    @Override
    public int getRootId() {
        return R.layout.activity_user_forget_password;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("注册");
        type = getIntent().getExtras().getInt(Configs.USER_TYPE.TYPE, 0);
        initView();
        mSmsTimerHelper = SendSmsTimerHelper.getInstance();
        listener = new SendSmsTimerHelper.UiUpdateListener() {
            @Override
            public void onUIUpdate(int left) {
                onUpdateMainLeft(left);
            }
        };
        mSmsTimerHelper.addListener(listener);
    }

    private void initView() {
        mTop.setBackgroundColor(getResources().getColor(R.color.cadetblue));
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
        mBtnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSmsTimerHelper.start();
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
            setText("%"+left+"$s "+mTranslatesString.getCommon_smstitle(), false);
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

    @OnClick(R.id.btn_froget_password)
    public void getOnclick() {
        if (UIHelper.checkEdit(mEtPhone, mEtPassword, mEtSms, mEtAgaingPassword)) {
            showToast(Utils.cutNull(mTranslatesString.getCommon_inputnotnull()));
        }
    }
}
