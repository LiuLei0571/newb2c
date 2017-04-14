package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.activity.logistic.LanguageSettingActivity;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.LogoutListener;
import com.example.b2c.widget.SettingItemView2;

/***
 * 买家设置页面
 */
public class BuyerSetActivity extends TempBaseActivity implements View.OnClickListener{

    private SettingItemView2 siv_language;
    private TextView tv_tuichu;

    @Override
    public int getRootId() {
        return R.layout.activity_buyer_set;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_setcenter());
        siv_language.setTv_text(mTranslatesString.getCommon_language());
        tv_tuichu.setText(mTranslatesString.getCommon_exit());
    }

    private void initView() {
        siv_language = (SettingItemView2) findViewById(R.id.siv_language);
        siv_language.hide_RightTv();
        tv_tuichu = (TextView) findViewById(R.id.tv_tuichu);
        siv_language.setOnClickListener(this);
        tv_tuichu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.siv_language:
                getIntent(BuyerSetActivity.this, LanguageSettingActivity.class);
                break;
            case R.id.tv_tuichu:
                UserHelper.clearUserLocalAll();
                Logout();
                break;
        }

    }
    /**
     * 不管登出是否成功，客户端都会清理信息回到首页
     */
    public void Logout() {
        showLoading();
        rdm.Logout(UserHelper.isBuyerLogin(),UserHelper.getBuyerId(),UserHelper.getBuyertoken());
        rdm.logoutListener = new LogoutListener() {
            @Override
            public void onFinish() {
                dissLoading();
                shipToHome();
            }
            @Override
            public void onSuccess(boolean isSuccess) {
            }
            @Override
            public void onError(int errorNO, String errorInfo) {
            }
            @Override
            public void onLose() {
            }
        };
    }
    /**
     * 跳到首页
     */
    private void shipToHome(){
        Intent intent = new Intent(BuyerSetActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
