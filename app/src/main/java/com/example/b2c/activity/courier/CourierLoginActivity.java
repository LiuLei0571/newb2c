package com.example.b2c.activity.courier;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.MainActivity;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.activity.common.UserForgetPasswordActivity;
import com.example.b2c.config.Configs;
import com.example.b2c.widget.EditTextWithDelete;
import com.example.b2c.widget.util.Utils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：
 * Created by milk on 16/10/24.
 * 邮箱：649444395@qq.com
 */

public class CourierLoginActivity extends TempBaseActivity {

    @Bind(R.id.username)
    EditTextWithDelete mUsername;
    @Bind(R.id.tv_password)
    EditTextWithDelete mTvPassword;
    @Bind(R.id.btn_login)
    Button mBtnLogin;
    @Bind(R.id.tv_forget_password)
    TextView mTvForgetPassword;

    @Override
    public int getRootId() {
        return R.layout.activity_courier_login;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mTop.setBackgroundColor(getResources().getColor(R.color.cadetblue));
        mBack.setVisibility(View.GONE);
        setTitle(Utils.cutNull(mTranslatesString.getLogistics_logintitle()));
        mTvForgetPassword.setText(mTranslatesString.getCommon_forgetpassword());
    }

    @OnClick({R.id.btn_login, R.id.tv_forget_password})
    public void getOnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_password:
                Intent intent = new Intent(this, UserForgetPasswordActivity.class);
                intent.putExtra(Configs.USER_TYPE.TYPE, Configs.USER_TYPE.COURIER);
                startActivity(intent);
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }

    private void login() {
        if (mUsername.getText().toString().trim().equals("") || mTvPassword.getText().toString().trim().equals("")) {
            Toast.makeText(this, mTranslatesString.getCommon_inputnotnull(), Toast.LENGTH_LONG).show();
            return;
        }
        showLoading();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
