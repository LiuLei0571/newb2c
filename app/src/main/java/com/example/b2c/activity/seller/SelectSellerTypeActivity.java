package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.MainActivity;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.helper.NotifyHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.observer.module.HomeObserver;

import butterknife.Bind;
import butterknife.OnClick;

import static com.example.b2c.activity.seller.BaseSetCompanyActivity.REGISTER;

/**
 * 卖家申请入驻角色选择
 * 用途：卖家申请入驻选择 公司入驻分为个人和公司
 * Created by milk on 16/11/7.
 * 邮箱：yy@qq.com
 */
public class SelectSellerTypeActivity extends TempBaseActivity {
    @Bind(R.id.btn_role_personal)
    Button btn_role_personal;
    @Bind(R.id.btn_role_company)
    Button btn_role_company;
    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_select_roller;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(mTranslatesString.getCommon_selectjuese());
        tvTitle.setText(mTranslatesString.getLogistic_titlemessage());
        btn_role_company.setText(mTranslatesString.getLogistics_minecompany());
        btn_role_personal.setText(mTranslatesString.getLogistics_mineperson());
//      mBack.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onBackClick() {
        //不允许退出选择
        UserHelper.clearUserLocalAll();
        getIntent(this, MainActivity.class);
        NotifyHelper.setNotifyData("home", new HomeObserver(0, ""));
        finish();
    }

    @OnClick({R.id.btn_role_personal, R.id.btn_role_company})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_role_personal:
                Intent intent0 =new Intent(SelectSellerTypeActivity.this,Setup1CompanyPersonalActivity.class);
                intent0.putExtra("type", REGISTER);
                intent0.putExtra("sellerType",BaseSetCompanyActivity.PERSONAL);
                startActivity(intent0);
                break;
            case R.id.btn_role_company:
                Intent intent1 =new Intent(SelectSellerTypeActivity.this,Setup1CompanyActivity.class);
                intent1.putExtra("type", REGISTER);
                intent1.putExtra("sellerType",BaseSetCompanyActivity.COMPANY);
                startActivity(intent1);
                break;
        }
    }
}
