package com.example.b2c.activity.logistic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;

/**
 * 物流公司信息的Activity
 */
public class LogisticsCompanyMessageActivity extends TempBaseActivity implements View.OnClickListener {

    private ImageButton toolbar_back;
    private TextView toolbar_title;
    private LinearLayout ll_gongsi;
    private LinearLayout ll_lianxiren;
    private LinearLayout jiesuan;
    private TextView gongsixinxi;
    private TextView lianxirenxinxi;
    private TextView jiesuanzhanghao;

    @Override
    public int getRootId() {
        return R.layout.activity_logistics_company_message;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        ll_gongsi = (LinearLayout) findViewById(R.id.ll_gongsi);
        ll_lianxiren = (LinearLayout) findViewById(R.id.ll_lianxiren);
        jiesuan = (LinearLayout) findViewById(R.id.jiesuan);
        toolbar_title.setText(mTranslatesString.getCommon_gongsixinxi());

        toolbar_back.setOnClickListener(this);
        ll_gongsi.setOnClickListener(this);
        ll_lianxiren.setOnClickListener(this);
        jiesuan.setOnClickListener(this);
        gongsixinxi = (TextView) findViewById(R.id.gongsixinxi);
        lianxirenxinxi = (TextView) findViewById(R.id.lianxirenxinxi);
        jiesuanzhanghao = (TextView) findViewById(R.id.jiesuanzhanghao);
        gongsixinxi.setText(mTranslatesString.getCommon_gongsixinxi());
        lianxirenxinxi.setText(mTranslatesString.getCommon_lianxirenxinxi());
        jiesuanzhanghao.setText(mTranslatesString.getCommon_jiesuanzhanghao());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.ll_gongsi://物流公司信息
                startActivity(new Intent(getApplication(), CompanyMessageActivity.class));
                break;
            case R.id.ll_lianxiren://联系人信息
                startActivity(new Intent(getApplication(), ContactsMessageActivity.class));
                break;
            case R.id.jiesuan://结算账号
                startActivity(new Intent(getApplication(), SettlementAccountNumberActivity.class));
                break;

        }
    }
}
