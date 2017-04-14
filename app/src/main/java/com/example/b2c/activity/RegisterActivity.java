package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.activity.seller.BaseSetCompanyActivity;
import com.example.b2c.activity.seller.SelectSellerTypeActivity;
import com.example.b2c.activity.seller.SellerLoginActivity;
import com.example.b2c.config.Configs;
import com.example.b2c.helper.NotifyHelper;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.RegisterListener;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.response.Users;
import com.example.b2c.net.util.BaseValidator;
import com.example.b2c.net.util.DateUtils;
import com.example.b2c.observer.module.HomeObserver;
import com.example.b2c.widget.EditTextWithDelete;

import org.apache.http.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 买家、卖家注册
 */
public class RegisterActivity extends TempBaseActivity implements OnClickListener {

    /**
     * 注册来源
     */
    public static final int SOURCE = 20;

    /**
     * 获取验证码类型标示符、、买家
     */
    public static final int GETEMAILCODE_BUYER = 1;
    /**
     * 获取验证码类型标示符、、卖家
     */
    public static final int GETEMAILCODE_SELLER = 5;

    /**
     * 注册买家还是注册卖家
     */
    private int type;

    private Button btn_register_ok;
    private Button btn_register_code;
    private EditTextWithDelete et_register_username, et_register_email, et_register_code,
            et_register_phone, et_register_password, et_register_repassword;

    private String loginName, email;//登录名，邮箱
    private TextView protocol_label;
    private TextView protocol_link;

    private CountDownTimer timer;
    private final long countDownTime = 60 * 1000;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_register;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
    }

    public void initView() {
        type = getIntent().getIntExtra(Configs.USER_TYPE.TYPE, Configs.USER_TYPE.BUYER);

        btn_register_ok = (Button) findViewById(R.id.btn_register_ok);
        btn_register_code = (Button) findViewById(R.id.btn_register_code);

        et_register_username = (EditTextWithDelete) findViewById(R.id.et_register_username);
        et_register_email = (EditTextWithDelete) findViewById(R.id.et_register_email);

        et_register_email.setmOnAfterTextChange(new EditTextWithDelete.OnAfterTextChange() {
            @Override
            public void doOnAfterTextChange() {
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                    btn_register_code.setEnabled(true);
                    btn_register_code.setText(mTranslatesString.getCommon_authcode());
                }
            }
        });

        et_register_code = (EditTextWithDelete) findViewById(R.id.et_register_code);
        et_register_phone = (EditTextWithDelete) findViewById(R.id.et_register_phone);
        et_register_password = (EditTextWithDelete) findViewById(R.id.et_register_password);
        et_register_repassword = (EditTextWithDelete) findViewById(R.id.et_register_repassword);

        protocol_label = (TextView) findViewById(R.id.protocol_label);
        protocol_label.setText(mTranslatesString.getCommon_registerisaggree());
        protocol_link = (TextView) findViewById(R.id.protocol_link);
        protocol_link.setText(Html.fromHtml("<u>" + "《" + mTranslatesString.getCommon_userprotocol() + "》" + "</u>"));

        btn_register_ok.setOnClickListener(this);
        btn_register_code.setOnClickListener(this);

        protocol_link.setOnClickListener(this);
    }

    public void initText() {
        if (type == Configs.USER_TYPE.BUYER) {
            setTitle(mTranslatesString.getCommon_buyerregister());
        } else if (type == Configs.USER_TYPE.SELLER) {
            setTitle(mTranslatesString.getCommon_sellerregister());
        }
        et_register_username.setHint(mTranslatesString.getCommon_username());
        et_register_email.setHint(mTranslatesString.getCommon_email());
        et_register_code.setHint(mTranslatesString.getCommon_authcode());
        et_register_phone.setHint(mTranslatesString.getCommon_tellphone());
        et_register_password.setHint(mTranslatesString.getCommon_password());
        et_register_repassword.setHint(mTranslatesString.getCommon_surepassword());
        btn_register_ok.setText(mTranslatesString.getCommon_sure());
        btn_register_code.setText(mTranslatesString.getCommon_getauthcode());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent;
        if (type == Configs.USER_TYPE.BUYER) {
            intent = new Intent(this, BuyerLoginActivity.class);
        } else {
            intent = new Intent(this, SellerLoginActivity.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register_code:
                getEmailVerifyCode();
                break;
            case R.id.btn_register_ok:
                register();
                break;
            case R.id.protocol_link:
                seeProtocol();
                break;
            default:
                break;
        }
    }

    /**
     * 查看用户协议
     */
    private void seeProtocol() {
        if (type == Configs.USER_TYPE.BUYER) {
            Intent intent = new Intent(RegisterActivity.this, ProtocolActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
        } else if (type == Configs.USER_TYPE.SELLER) {
            Intent intent = new Intent(RegisterActivity.this, ProtocolActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
        }

    }
    public void register() {
        /**
         * 注册之前拼接好时间戳
         */
        SPHelper.putString(Configs.APPNAME, Configs.DOMAIN + "-" + DateUtils.getTimeStamp());

        loginName = et_register_username.getText().toString();
        //email 提到全局了
        String emailCode = et_register_code.getText().toString();
        String mobile = et_register_phone.getText().toString();
        String password = et_register_password.getText().toString();
        String rePassword = et_register_repassword.getText().toString();

        if (TextUtils.isBlank(loginName) || TextUtils.isBlank(email) || TextUtils.isBlank(emailCode) || TextUtils.isBlank(password)) {
            ToastHelper.makeToast(mTranslatesString.getCommon_inputnotnull());
            return;
        } else if (!password.equals(rePassword)) {
            ToastHelper.makeToast(mTranslatesString.getValidate_passworddifference());
            return;
        }
        showProgressDialog(Waiting);
        Map map = new HashMap();
        map.put("loginName", loginName);
        map.put("email", email);
        map.put("emailCode", emailCode);
        map.put("mobile", mobile);
        map.put("password", password);
        map.put("type", type);
        map.put("source", SOURCE);
        rdm.Register(map);
        rdm.registerListener = new RegisterListener() {

            @Override
            public void onSuccess(int userId, String token, int type) {
                if (type == Configs.USER_TYPE.BUYER) {
                    /**
                     * 买家注册成功，直接进入买家app首页面
                     */
                    ToastHelper.makeToast(mTranslatesString.getNotice_registersuccess());
                    Users user = new Users();
                    user.setLoginName(loginName);
                    user.setToken(token);
                    user.setUserId(userId);
                    user.setUserType(type);
                    SPHelper.putInt(Configs.USER_TYPE.TYPE, type);
                    SPHelper.putBean(Configs.BUYER.INFO, user);
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    NotifyHelper.setNotifyData("home", new HomeObserver(0, ""));
                } else if (type == Configs.USER_TYPE.SELLER) {

                    /**
                     * 卖家注册成功,需要判断填写公司信息
                     */
                    ToastHelper.makeToast(mTranslatesString.getNotice_registersuccess());
                    Users user = new Users();
                    user.setLoginName(loginName);
                    user.setToken(token);
                    user.setUserId(userId);
                    user.setUserType(type);
                    SPHelper.putInt(Configs.USER_TYPE.TYPE, type);
                    SPHelper.putBean(Configs.SELLER.INFO, user);
                    /**
                     * 进入公司商家信息设置页面
                     */
                    Intent intent = new Intent(RegisterActivity.this, SelectSellerTypeActivity.class);
                    intent.putExtra("type", BaseSetCompanyActivity.REGISTER);
                    startActivity(intent);
                    finish();
//                    Intent intent = new Intent(RegisterActivity.this, SellerLoginActivity.class);
//                    startActivity(intent);
//                    finish();
                }

            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }

        };
    }

    public void getEmailVerifyCode() {
        email = et_register_email.getText().toString();
        if(TextUtils.isBlank(email)){
            ToastHelper.makeToast(mTranslatesString.getCommon_pleaseinputemail());
            return;
        }
        if (!BaseValidator.EmailMatch(email)) {
            ToastHelper.makeToast(mTranslatesString.getCommon_email() + mTranslatesString.getCommon_styleerror());
            return;
        }
        btn_register_code.setEnabled(false);

        if(type == Configs.USER_TYPE.BUYER){
            rdm.GetEmailCode(email, GETEMAILCODE_BUYER);
        }else if(type == Configs.USER_TYPE.SELLER){
            rdm.GetEmailCode(email, GETEMAILCODE_SELLER);
        }

        rdm.getEmailVerifyCodeListener = new ResponseListener() {

            @Override
            public void onSuccess(String errorInfo) {
                ToastHelper.makeToast(errorInfo);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getVcodeSuccess();
                    }
                });
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeToast(errorInfo);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_register_code.setEnabled(true);
                    }
                });
            }

            @Override
            public void onLose() {
                ToastHelper.makeToast(request_failure);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_register_code.setEnabled(true);
                    }
                });
            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }

        };
    }

    private void getVcodeSuccess() {
        btn_register_code.setText(countDownTime / 1000 + "");
        timer = new CountDownTimer(countDownTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btn_register_code.setText(millisUntilFinished / 1000 + mTranslatesString.getCommon_smstitle());
            }

            @Override
            public void onFinish() {
                btn_register_code.setEnabled(true);
                btn_register_code.setText(mTranslatesString.getCommon_authcode());
            }
        };
        timer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


}
