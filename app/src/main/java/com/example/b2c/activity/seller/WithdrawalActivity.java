package com.example.b2c.activity.seller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：
 * Created by milk on 16/10/27.
 * 邮箱：649444395@qq.com
 */

public class WithdrawalActivity extends TempBaseActivity {

    @Bind(R.id.btn_login)
    Button mBtnLogin;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_can_withdrawal;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("可提现");

    }

    @OnClick(R.id.btn_login)
    protected void getOnclick(View view) {

    }
}
