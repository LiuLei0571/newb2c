package com.example.b2c.activity.logistic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.MainActivity;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.activity.seller.SecurityActivity;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.LogoutListener;

public class SettingActivity extends TempBaseActivity implements View.OnClickListener {

    private ImageButton toolbar_back;
    private TextView toolbar_title;
    private LinearLayout ll_zhanghao;
    private LinearLayout ll_tongyong_stting;
    private TextView tv_tuichu;
    private TextView tv_zhanghaoxinxi;
    private TextView tongyongshezhi;

    @Override
    public int getRootId() {
        return R.layout.activity_setting;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        tv_zhanghaoxinxi = (TextView) findViewById(R.id.tv_zhanghaoxinxi);
        tongyongshezhi = (TextView) findViewById(R.id.tongyongshezhi);
        ll_zhanghao = (LinearLayout) findViewById(R.id.ll_zhanghao);
        ll_tongyong_stting = (LinearLayout) findViewById(R.id.ll_tongyong_stting);
        tv_tuichu = (TextView) findViewById(R.id.tv_tuichu);
        toolbar_back.setOnClickListener(this);
        ll_tongyong_stting.setOnClickListener(this);
        ll_zhanghao.setOnClickListener(this);
        tv_tuichu.setOnClickListener(this);
        toolbar_title.setText(mTranslatesString.getCommon_setting());
        tv_zhanghaoxinxi.setText(mTranslatesString.getCommon_accountinfo());
        tongyongshezhi.setText(mTranslatesString.getCommon_tongyongsetting());
        tv_tuichu.setText(mTranslatesString.getCommon_logout());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.ll_zhanghao://账号信息
            startActivity(new Intent(getApplication(),ZhangHaoSettingActivity.class));
                break;
            case R.id.ll_tongyong_stting://语言设置
            startActivity(new Intent(getApplication(),LanguageSettingActivity.class));
                break;
            case R.id.tv_tuichu://退出
                loginOut();
                finish();
                Intent intent = new Intent("FinishActivity");
                sendBroadcast(intent);//发送对应的广播
                break;
        }
    }

    /**
     * 用户登出
     */
    public void loginOut() {
        showProgressDialog(Waiting);
        rdm.Logout(UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
        rdm.logoutListener = new LogoutListener() {
            @Override
            public void onSuccess(boolean isSuccess) {
                UserHelper.clearUserLocalAll();
                getIntent(SettingActivity.this, MainActivity.class);
            }
            @Override
            public void onError(int errorNO, String errorInfo) {
                UserHelper.clearUserLocalAll();
                getIntent(SettingActivity.this, MainActivity.class);
            }
            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
            @Override
            public void onLose() {
                UserHelper.clearUserLocalAll();
                getIntent(SettingActivity.this, MainActivity.class);
            }

        };
    }
}
