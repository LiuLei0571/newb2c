package com.example.b2c.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.b2c.R;
import com.example.b2c.activity.BuyerLoginActivity;
import com.example.b2c.activity.logistic.LogisticsLoginActivity;
import com.example.b2c.activity.seller.SellerLoginActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：
 * Created by milk on 16/11/8.
 * 邮箱：649444395@qq.com
 */

public class OtherUserLoginActivity extends TempBaseActivity {
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.bnt_seller)
    Button mBntSeller;
    @Bind(R.id.btn_courier)
    Button mBtnCourier;
    @Bind(R.id.bnt_my_maijia)
    Button bnt_my_maijia;

    @Override
    public int getRootId() {
        return R.layout.activity_common_login_select;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getCommon_selectjuese());
        initText();
    }

    private void initText() {
        mTvTitle.setText(mTranslatesString.getPoint_identity());
        mBntSeller.setText(mTranslatesString.getBg_sellertitle());
        mBtnCourier.setText(mTranslatesString.getBg_expresstitle());
        bnt_my_maijia.setText(mTranslatesString.getCommon_mymaijia());
    }

    @OnClick({R.id.bnt_seller, R.id.btn_courier,R.id.bnt_my_maijia})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bnt_seller:
                getIntent(this, SellerLoginActivity.class);
                break;
            case R.id.btn_courier:
                getIntent(this, LogisticsLoginActivity.class);
                break;
            case R.id.bnt_my_maijia:
                Intent i_to_login = new Intent(this, BuyerLoginActivity.class);
                startActivity(i_to_login);
                finish();
                break;
        }
    }
}
