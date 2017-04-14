package com.example.b2c.activity.logistic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.MainActivity;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.activity.common.UserForgetPasswordActivity;
import com.example.b2c.activity.staff.StaffHomeActivity;
import com.example.b2c.config.Configs;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UIHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.base.BaseUserListener;
import com.example.b2c.net.response.Users;
import com.example.b2c.net.util.DateUtils;
import com.example.b2c.widget.EditTextWithDelete;
import com.example.b2c.widget.SimpleTextWatcher;

import butterknife.Bind;
import butterknife.OnClick;

import static com.example.b2c.R.id.btn_login;

/**
 * 用途：物流公司登录
 * Created by milk on 16/10/24.
 * 邮箱：649444395@qq.com
 */

public class LogisticsLoginActivity extends TempBaseActivity {

    private boolean eyeIndicator = false;//默认闭眼
    private Bitmap[] LocalBitmaps;

    @Bind(R.id.et_username)
    EditTextWithDelete mUsername;
    @Bind(R.id.et_password)
    EditTextWithDelete mTvPassword;
    @Bind(btn_login)
    Button mBtnLogin;
    @Bind(R.id.tv_logistic_load)
    TextView mTvLogisticsRegister;
    @Bind(R.id.tv_fgt_psw)
    TextView mTvForgetPassword;
    @Bind(R.id.iv_eye)
    ImageView iv_eye;

    @Override
    public int getRootId() {
        return R.layout.activity_logistics_login;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserHelper.clearUserLocalAll();
        initView();
        initText();
    }

    public void initView() {
        setTitle(mTranslatesString.getLogistics_logintitle());
        LocalBitmaps = new Bitmap[]{BitmapFactory.decodeResource(getResources(), R.drawable.eye_open), BitmapFactory.decodeResource(getResources(), R.drawable.eye_close)};
        UIHelper.setClickable(mBtnLogin, mUsername, mTvPassword);
        mTvPassword.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                UIHelper.setClickable(mBtnLogin, mUsername, mTvPassword);
            }
        });
    }
    public void initText(){
        mUsername.setHint(mTranslatesString.getCommon_pleaseinputusername());
        mTvPassword.setHint(mTranslatesString.getCommon_pleasesetaccountpassword());
        mBtnLogin.setText(mTranslatesString.getCommon_login());
        mTvLogisticsRegister.setText(mTranslatesString.getLogistic_apply());
        mTvForgetPassword.setText(mTranslatesString.getCommon_forgetpassword());
    }

    @OnClick({ R.id.btn_login,R.id.tv_fgt_psw, R.id.tv_logistic_load, R.id.iv_eye})
    public void getOnclick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_logistic_load:
                break;
            case R.id.tv_fgt_psw:
                Intent intent = new Intent(this, LogisticForgetPasswordActivity.class);
                intent.putExtra(Configs.USER_TYPE.TYPE, Configs.USER_TYPE.EXPRESS);
                startActivity(intent);
                break;
            case R.id.iv_eye:
                if(eyeIndicator){
                    eyeIndicator = false;
                    mTvPassword.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                    mTvPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    iv_eye.setImageBitmap(LocalBitmaps[1]);
                }else{
                    eyeIndicator = true;
                    mTvPassword.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mTvPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    iv_eye.setImageBitmap(LocalBitmaps[0]);
                }
                break;
            default:
                break;
        }
    }

    private void login() {
        SPHelper.putString(Configs.APPNAME,Configs.DOMAIN+"-"+ DateUtils.getTimeStamp());
        showProgressDialog(Waiting);
        if (TextUtils.isEmpty(mTvPassword.getText().toString().trim()) || TextUtils.isEmpty(mTvPassword.getText().toString().trim())) {
            Toast.makeText(this, mTranslatesString.getCommon_inputnotnull(), Toast.LENGTH_LONG).show();
            return;
        }
        showLoading();
        mLogisticsDataConnection.getLogisticsLoginRequest(ConstantS.BASE_URL_EXPRESS + "login.htm", mUsername.getText().toString().trim(), mTvPassword.getText().toString().trim());
        mLogisticsDataConnection.useListener = new BaseUserListener() {
            @Override
            public void onSuccess(Users user, String errorInfo) {
                if (user.getUserType() == 2) {
                    getIntent(LogisticsLoginActivity.this, LogisticsHomeActivity.class);
                    finish();
                } else if (user.getUserType() == 3) {
                    getIntent(LogisticsLoginActivity.this, StaffHomeActivity.class);
                    finish();
                }
                finish();
            }
            @Override
            public void onError(int errorNO,String errorInfo) {
                dissLoading();
                ToastHelper.makeErrorToast(errorInfo);
            }
            @Override
            public void onFinish() {
                dissLoading();
            }
            @Override
            public void onLose() {
                dissLoading();
                ToastHelper.makeErrorToast(request_failure);
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
