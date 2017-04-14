package com.example.b2c.adapter.seller;

import android.content.Context;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.net.response.seller.LogisticDetail;

/**
 * 用途：
 * Created by milk on 16/10/27.
 * 邮箱：649444395@qq.com
 */

public class CourierAdapter extends BaseAdapter<LogisticDetail> {

    public CourierAdapter(Context context) {
        super(context, R.layout.item_courier);
    }


    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {
        LogisticDetail mLogisticDetail = getItem(position);
        TextView mTvCourierName = viewHolderFactory.findViewById(R.id.tv_courier_name);
        TextView mTvDefaultCourier = viewHolderFactory.findViewById(R.id.tv_default_courier);
        if (getItem(position) != null) {
            mTvCourierName.setText(mLogisticDetail.getDeliveryName());
            if (mLogisticDetail.isCorperateStatus()) {
                mTvDefaultCourier.setText(mTranslatesString.getCommon_yetcorperate());
            }else {
                mTvDefaultCourier.setText("");
            }
        }
    }
}
