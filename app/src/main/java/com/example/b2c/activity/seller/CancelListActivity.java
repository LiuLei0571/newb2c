package com.example.b2c.activity.seller;

import com.example.b2c.activity.common.SellerBaseOrderActivity;

/**
 * 已取消订单
 */
public class CancelListActivity extends SellerBaseOrderActivity {

    @Override
    protected int getType() {
        return 100;
    }

    @Override
    protected String getActivityTitle() {
        return mTranslatesString.getCommon_yetcancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
