package com.example.b2c.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.Configs;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UIHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.util.BaseValidator;
import com.example.b2c.net.util.Md5Encrypt;
import com.example.b2c.widget.EditTextWithDelete;
import com.example.b2c.widget.SendSmsButton;
import com.example.b2c.widget.SimpleTextWatcher;
import com.example.b2c.widget.util.SendSmsTimerHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * 买家、卖家。。忘记密码
 */
public class ForgetPswActivity extends TempBaseActivity implements OnClickListener {

    private EditTextWithDelete et_username;
    private EditTextWithDelete et_email;
    private EditTextWithDelete et_emailCode;
    private EditTextWithDelete et_newPassword;
    private EditTextWithDelete et_rePassword;
    private Button btn_submit;

    private SendSmsButton btn_sms;
    private SendSmsTimerHelper mSmsTimerHelper;
    private SendSmsTimerHelper.UiUpdateListener listener;


    /**
     * 获取验证码类型标示符、、买家忘记密码
     */
    public  static  final int GETEMAILCODE_BUYER = 3;
    /**
     * 获取验证码类型标示符、、卖家忘记密码
     */
    public  static  final int GETEMAILCODE_SELLER = 6;

    //默认买家类型
    private static int type = 0;

    @Override
    public int getRootId() {
        return R.layout.forget_psw_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
    }

    public void initView() {
        type = getIntent().getIntExtra(Configs.USER_TYPE.TYPE,Configs.USER_TYPE.BUYER);
        et_username = (EditTextWithDelete) findViewById(R.id.et_username);
        et_email = (EditTextWithDelete) findViewById(R.id.et_email);
        btn_sms = (SendSmsButton) findViewById(R.id.btn_sms);
        et_emailCode = (EditTextWithDelete) findViewById(R.id.et_emailCode);
        et_newPassword = (EditTextWithDelete) findViewById(R.id.et_newPassword);
        et_rePassword = (EditTextWithDelete) findViewById(R.id.et_rePassword);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

        mSmsTimerHelper = SendSmsTimerHelper.getInstance();
        listener = new SendSmsTimerHelper.UiUpdateListener() {
            @Override
            public void onUIUpdate(int left) {
                onUpdateMainLeft(left);
            }
        };
        mSmsTimerHelper.addListener(listener);
        et_email.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                btn_sms.setPhone(s.toString());
            }
        });
        btn_sms.setOnClickListener(this);
    }

    public void initText() {
        setTitle(mTranslatesString.getCommon_forgetpassword());
        et_username.setHint(mTranslatesString.getCommon_username());
        et_email.setHint(mTranslatesString.getCommon_email());
        btn_sms.setText(mTranslatesString.getCommon_authcode());
        et_emailCode.setHint(mTranslatesString.getCommon_authcode());
        et_newPassword.setHint(mTranslatesString.getCommon_newpassword());
        et_rePassword.setHint(mTranslatesString.getCommon_surepassword());
        btn_submit.setText(mTranslatesString.getCommon_sure());
    }

    private void onUpdateMainLeft(int left) {
        if (left > 0) {
            setText(left+mTranslatesString.getCommon_smstitle(), false);
        } else {
            boolean flag = false;
            if (!TextUtils.isEmpty(et_email.getText().toString())) {
                flag = true;
            }
            setText(mTranslatesString.getCommon_authcode(), flag);
        }
    }

    public void setText(String text, boolean enable) {
        btn_sms.setState(text, enable);
    }

    /**
     * 获取验证码
     */
    public void getEmailVerifyCode() {
        if(UIHelper.checkEdit(et_email)){
            ToastHelper.makeToast(mTranslatesString.getCommon_pleaseinputemail());
            return;
        }
        if(!BaseValidator.EmailMatch(et_email.getText().toString())){
            ToastHelper.makeErrorToast(mTranslatesString.getCommon_email()+mTranslatesString.getCommon_styleerror());
            return;
        }
        mSmsTimerHelper.start();
        if(type == Configs.USER_TYPE.BUYER){
            rdm.GetEmailCode(et_email.getText().toString(),GETEMAILCODE_BUYER);
        }else if(type == Configs.USER_TYPE.SELLER){
            rdm.GetEmailCode(et_email.getText().toString(),GETEMAILCODE_SELLER);
        }

        rdm.getEmailVerifyCodeListener = new ResponseListener() {
            @Override
            public void onSuccess(String errorInfo) {
                ToastHelper.makeToast(errorInfo);
            }
            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeToast(errorInfo);
                mSmsTimerHelper.reset();
            }
            @Override
            public void onLose() {
                ToastHelper.makeToast(request_failure);
                mSmsTimerHelper.reset();
            }
            @Override
            public void onFinish() {
                dissLoading();
            }
        };
    }
    /**
     * 提交新密码
     */
    public void changePsw() {
        if(UIHelper.checkEdit(et_username,et_email,et_emailCode,et_newPassword,et_rePassword)){
            ToastHelper.makeToast(mTranslatesString.getCommon_inputallinfo());
            return;
        }
        if(!et_newPassword.getText().toString().equals(et_rePassword.getText().toString())){
            ToastHelper.makeToast(mTranslatesString.getValidate_passworddifference());
            return;
        }
        showLoading();
        Map map = new HashMap();
        map.put("loginName",et_username.getText().toString());
        map.put("email",et_email.getText().toString());
        map.put("emailCode",et_emailCode.getText().toString());
        map.put("newPassword", Md5Encrypt.md5(et_rePassword.getText().toString()));
        rdm.ForgetPassword(unLogin, userId, token, map);
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
                dissLoading();
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSmsTimerHelper != null) {
            mSmsTimerHelper.removeListener(listener);
            mSmsTimerHelper = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sms:
                getEmailVerifyCode();
                break;
            case R.id.btn_submit:
                changePsw();
                break;
        }
    }

}
