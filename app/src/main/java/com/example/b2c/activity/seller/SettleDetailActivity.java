package com.example.b2c.activity.seller;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.fragment.seller.SettleDelayFragment;
import com.example.b2c.fragment.seller.SettleWaitingFragment;
import com.example.b2c.fragment.seller.SettleYetFragment;

public class SettleDetailActivity extends TempBaseActivity {

    private final HomePage[] fragmentPage = new HomePage[]{
            new HomePage("待结算", SettleWaitingFragment.class),
            new HomePage("已结算", SettleYetFragment.class),
            new HomePage("逾期结算", SettleDelayFragment.class)
    };
    private FrameLayout tabcontent;
    private int settleType;

    @Override
    public int getRootId() {
        return R.layout.activity_settle_detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        settleType = getIntent().getExtras().getInt("type");
        tabcontent = (FrameLayout) findViewById(android.R.id.tabcontent);
        if(settleType == SettlesActivity.WAIT_SETTLE){
            setTitle(mTranslatesString.getCommon_tvdaijiesuan());
            setCurrentTab(0,fragmentPage);
        }else if(settleType == SettlesActivity.YET_SETTLE){
            setTitle(mTranslatesString.getCommon_yijiesuan());
            setCurrentTab(1,fragmentPage);
        }else if(settleType == SettlesActivity.DELAY_SETTLE){
            setTitle(mTranslatesString.getCommon_yuqijiesuan());
            setCurrentTab(2,fragmentPage);
        }
    }
}
