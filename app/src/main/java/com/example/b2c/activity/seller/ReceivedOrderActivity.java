package com.example.b2c.activity.seller;

import com.example.b2c.activity.common.SellerBaseOrderActivity;

/**
 * 用途：
 * 作者：Created by john on 2016/12/5.
 * 邮箱：liulei2@aixuedai.com
 */


public class ReceivedOrderActivity extends SellerBaseOrderActivity {
    @Override
    protected int getType() {
        return 141;
    }

    @Override
    protected String getActivityTitle() {
        return mTranslatesString.getSeller_homereceived();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
