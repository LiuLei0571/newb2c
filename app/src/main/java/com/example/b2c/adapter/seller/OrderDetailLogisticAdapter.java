package com.example.b2c.adapter.seller;

import android.content.Context;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.net.response.seller.OrderDetailLogistic;
import com.example.b2c.widget.util.Utils;

import butterknife.Bind;

/**
 * 用途：
 * Created by milk on 16/11/4.
 * 邮箱：649444395@qq.com
 */

public class OrderDetailLogisticAdapter extends BaseAdapter<OrderDetailLogistic> {
    @Bind(R.id.time)
    TextView mTime;
    @Bind(R.id.name)
    TextView mName;
    @Bind(R.id.place)
    TextView mPlace;

    public OrderDetailLogisticAdapter(Context context) {
        super(context, R.layout.item_logisitic_detail);
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {
        OrderDetailLogistic mData = getItem(position);
        TextView mTvTime = viewHolderFactory.findViewById(R.id.time);
        TextView mTvName = viewHolderFactory.findViewById(R.id.name);
        TextView mTvPlace = viewHolderFactory.findViewById(R.id.place);
        if (mData != null) {
            mTvName.setText(Utils.cutNull(mData.getDeliveryCompanyName()));
            mTvPlace.setText(Utils.cutNull(mData.getPlace()));
            mTvTime.setText(Utils.cutNull(mData.getCreateTime()));
        }
    }
}
