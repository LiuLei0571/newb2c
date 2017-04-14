package com.example.b2c.activity.staff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.MainActivity;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.activity.logistic.LanguageSettingActivity;
import com.example.b2c.activity.logistic.ModificationPasswordActivity;
import com.example.b2c.activity.logistic.SettingActivity;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.LogoutListener;

public class StaffSettingActivity extends TempBaseActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private TextView tv_xiugaimima;
    private LinearLayout ll_xiugaimima;
    private TextView tongyongshezhi;
    private LinearLayout ll_tongyong_stting;
    private TextView tv_tuichu;

    @Override
    public int getRootId() {
        return R.layout.activity_staff_setting;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        tv_xiugaimima = (TextView) findViewById(R.id.tv_xiugaimima);
        ll_xiugaimima = (LinearLayout) findViewById(R.id.ll_xiugaimima);
        tongyongshezhi = (TextView) findViewById(R.id.tongyongshezhi);
        ll_tongyong_stting = (LinearLayout) findViewById(R.id.ll_tongyong_stting);
        tv_tuichu = (TextView) findViewById(R.id.tv_tuichu);
        toolbar_title.setText(mTranslatesString.getCommon_setting());
        tv_xiugaimima.setText(mTranslatesString.getCommon_modifypassword());
        tongyongshezhi.setText(mTranslatesString.getCommon_tongyongsetting());
        tv_tuichu.setText(mTranslatesString.getCommon_logout());
        ll_tongyong_stting.setOnClickListener(this);
        ll_xiugaimima.setOnClickListener(this);
        tv_tuichu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_tongyong_stting:
                startActivity(new Intent(getApplication(), LanguageSettingActivity.class));
                break;
            case R.id.ll_xiugaimima:
                startActivity(new Intent(getApplication(), ModificationPasswordActivity.class));
                break;
            case R.id.tv_tuichu:
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
                getIntent(StaffSettingActivity.this, MainActivity.class);
            }
            @Override
            public void onError(int errorNO, String errorInfo) {
                UserHelper.clearUserLocalAll();
                getIntent(StaffSettingActivity.this, MainActivity.class);
            }
            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
            @Override
            public void onLose() {
                UserHelper.clearUserLocalAll();
                getIntent(StaffSettingActivity.this, MainActivity.class);
            }

        };
    }
}
