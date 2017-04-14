package com.example.b2c.adapter.logistics;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.net.response.logistics.LogisticsOrderDetail;
import com.example.b2c.widget.util.Utils;

/**
 * 用途：
 * Created by milk on 16/11/15.
 * 邮箱：649444395@qq.com
 */

public abstract class LogisticsOrderListAdapter extends BaseAdapter<LogisticsOrderDetail> {
    public LogisticsOrderListAdapter(Context context) {
        super(context, R.layout.item_logistics_order);
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {
        final LogisticsOrderDetail orderDetail = getItem(position);
        TextView mTvTime = viewHolderFactory.findViewById(R.id.tv_order_time);
        TextView mTvNo = viewHolderFactory.findViewById(R.id.tv_order_no);
        TextView mTvMoney = viewHolderFactory.findViewById(R.id.tv_order_money);
        TextView mTvBuyerMessage = viewHolderFactory.findViewById(R.id.tv_buyer_message);
        TextView mTvSellerMessage = viewHolderFactory.findViewById(R.id.tv_seller_message);
        TextView mTvOrderDetail = viewHolderFactory.findViewById(R.id.tv_order_detail);

        TextView mTvTitleTime = viewHolderFactory.findViewById(R.id.tv_title_time);
        TextView mTvTitleOrderNo = viewHolderFactory.findViewById(R.id.tv_title_order_no);
        TextView mTvBuyerInfo = viewHolderFactory.findViewById(R.id.tv_title_buyer);
        TextView mTvSellerInfo = viewHolderFactory.findViewById(R.id.tv_title_seller);
        mTvOrderDetail.setText(mTranslatesString.getCommon_selectdetail());

        mTvTitleTime.setText(mTranslatesString.getSeller_ordertime());
        mTvTitleOrderNo.setText(mTranslatesString.getCommon_ordernumber());
        mTvBuyerInfo.setText(mTranslatesString.getCommon_buyermessage());
        mTvSellerInfo.setText(mTranslatesString.getCommon_sellermessage());

        if (orderDetail != null) {
            mTvTime.setText(Utils.cutNull(orderDetail.getCreateTime()));
            mTvMoney.setText(orderDetail.getTotalPrice());
            mTvNo.setText(Utils.cutNull(orderDetail.getOrderCode()));
            mTvBuyerMessage.setText(Utils.cutNull(orderDetail.getBuyerAddress()) + " " + Utils.cutNull(orderDetail.getBuyerName()) + " " + Utils.cutNull(orderDetail.getBuyerMobile()));
            mTvSellerMessage.setText(Utils.cutNull(orderDetail.getSellerAddress()) + " " + Utils.cutNull(orderDetail.getSellerName()) + " " + Utils.cutNull(orderDetail.getSellerMobile()));
            mTvOrderDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelect(orderDetail);
                }
            });
        }
    }

    public abstract void onSelect(LogisticsOrderDetail orderDetail);
}
