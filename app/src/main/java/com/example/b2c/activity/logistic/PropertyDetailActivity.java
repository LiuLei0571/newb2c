package com.example.b2c.activity.logistic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;

public class PropertyDetailActivity extends TempBaseActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private TextView tv_biandongjilku;
    private LinearLayout ll_biandongjiju;
    private TextView tv_dongjiejili;
    private LinearLayout ll_dongjiejilu;
    private TextView tv_jiesuanjilu;
    private LinearLayout ll_jiesuanjilu;

    @Override
    public int getRootId() {
        return R.layout.activity_property_detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        tv_biandongjilku = (TextView) findViewById(R.id.tv_biandongjilku);
        ll_biandongjiju = (LinearLayout) findViewById(R.id.ll_biandongjiju);
        tv_dongjiejili = (TextView) findViewById(R.id.tv_dongjiejili);
        ll_dongjiejilu = (LinearLayout) findViewById(R.id.ll_dongjiejilu);
        tv_jiesuanjilu = (TextView) findViewById(R.id.tv_jiesuanjilu);
        ll_jiesuanjilu = (LinearLayout) findViewById(R.id.ll_jiesuanjilu);
        tv_biandongjilku.setText(mTranslatesString.getCommon_baozhengjinbiandong());
        tv_dongjiejili.setText(mTranslatesString.getCommon_baozhengjindongjie());
        tv_jiesuanjilu.setText(mTranslatesString.getCommon_jiesuanjilu());
        ll_biandongjiju.setOnClickListener(this);
        ll_dongjiejilu.setOnClickListener(this);
        ll_jiesuanjilu.setOnClickListener(this);
        toolbar_title.setText(mTranslatesString.getCommon_mingxi());
    }
    private void initData(){

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_biandongjiju://保证金变动记录
                startActivity(new Intent(getApplication(),MoneyVariationActivity.class));
                break;
            case R.id.ll_dongjiejilu:
                //保证金冻结记录
                startActivity(new Intent(getApplication(),FreezeCashDepositActivity.class));
                break;
            case R.id.ll_jiesuanjilu:
                //结算记录
                startActivity(new Intent(getApplication(),SettlementRelevanceListActivity.class));
                break;
        }
    }
}
