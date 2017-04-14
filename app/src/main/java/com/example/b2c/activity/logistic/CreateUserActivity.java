package com.example.b2c.activity.logistic;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.helper.UIHelper;
import com.example.b2c.net.util.Md5Encrypt;
import com.example.b2c.widget.EditTextWithDelete;
import com.example.b2c.widget.SimpleTextWatcher;
import com.example.b2c.widget.util.SerializableMap;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：创建管理员账号
 * Created by milk on 16/11/9.
 * 邮箱：649444395@qq.com
 */

public class CreateUserActivity extends TempBaseActivity {


    @Bind(R.id.et_username)
    EditTextWithDelete mEtUsername;
    @Bind(R.id.et_passwrod)
    EditTextWithDelete mEtPasswrod;
    @Bind(R.id.et_phone)
    EditTextWithDelete mEtPhone;
    @Bind(R.id.et_mail)
    EditTextWithDelete mEtMail;
    @Bind(R.id.btn_login)
    Button mBtnLogin;
    @Bind(R.id.et_name)
    EditText mEtName;
    @Bind(R.id.tv_title_message)
    TextView tvTitleMessage;
    @Bind(R.id.tv_title_account)
    TextView tvTitleAccount;
    @Bind(R.id.tv_title_password)
    TextView tvTitlePassword;
    @Bind(R.id.tv_title_phone)
    TextView tvTitlePhone;
    @Bind(R.id.tv_title_email)
    TextView tvTitleEmail;
    @Bind(R.id.tv_title_name)
    TextView tvTitleName;
    private String url;
    private String mUserName;
    private String mPassword;
    private String uName;
    private String mMobile;
    private String mEmail;
    Map<String, String> params = new HashMap<>();
    SerializableMap serializableMap = new SerializableMap();

    @Override
    public int getRootId() {
        return R.layout.activity_create_logistics;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getLogistic_createaccount());
        initText();
        serializableMap = (SerializableMap) getIntent().getSerializableExtra("map1");
        UIHelper.setClickable(mBtnLogin, mEtName, mEtPasswrod, mEtPhone, mEtUsername);
        mEtPhone.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                UIHelper.setClickable(mBtnLogin, mEtName, mEtPasswrod, mEtPhone, mEtUsername);
            }
        });
    }

    private void initText() {
        tvTitleMessage.setText(mTranslatesString.getLogistic_createtitle());
        mBtnLogin.setText(mTranslatesString.getLogistic_createaccount());
        tvTitleAccount.setText(mTranslatesString.getCommon_logisticaccount());
        tvTitlePassword.setText(mTranslatesString.getCommon_password());
        tvTitlePhone.setText(mTranslatesString.getCommon_tellphone());
        tvTitleEmail.setText(mTranslatesString.getCommon_email());
        tvTitleName.setText(mTranslatesString.getCommon_username());
        mEtUsername.setHint(mTranslatesString.getCommon_pleasesetaccountloginname());
        mEtPasswrod.setHint(mTranslatesString.getCommon_pleasesetaccountpassword());
        mEtName.setHint(mTranslatesString.getCommon_pleaseinputname());
        mEtPhone.setHint(mTranslatesString.getCommon_pleaseinputcorrectmobile());
        mEtMail.setHint(mTranslatesString.getCommon_pleaseinputemail());
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        mUserName = mEtUsername.getText().toString();
        mPassword = mEtPasswrod.getText().toString();
        mUserName = mEtName.getText().toString();
        mMobile = mEtPhone.getText().toString();
        mEmail = mEtMail.getText().toString();
        params.put("loginName", mUserName);
        params.put("password", Md5Encrypt.md5(mPassword));
        params.put("accountName", mUserName);
        params.put("mobile", mMobile);
        params.put("email", mEmail);
        params.putAll(serializableMap.getParmas());
        serializableMap.setParmas(params);
        Intent intent = new Intent(CreateUserActivity.this, ApplyMoneyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("map2", serializableMap);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
