package com.example.b2c.adapter.seller;

import android.content.Context;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.net.response.seller.OrderList;
import com.example.b2c.widget.util.Utils;

/**
 * 用途：
 * Created by milk on 16/10/30.
 * 邮箱：649444395@qq.com
 */

public class RefuseAllAdapter extends BaseAdapter<OrderList> {
    private Context mContext;

    public RefuseAllAdapter(Context context) {
        super(context, R.layout.item_refuse_all);
        this.mContext = context;
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {
        OrderList orderList = getItem(position);
        TextView mTvOrderId = viewHolderFactory.findViewById(R.id.tv_order_id);
        TextView mTvBuyId = viewHolderFactory.findViewById(R.id.tv_buy_id);
        ListView mList = viewHolderFactory.findViewById(R.id.list);
        TextView mTvGoodsMessage = viewHolderFactory.findViewById(R.id.tv_goods_message);
        TextView mTvCreateTime = viewHolderFactory.findViewById(R.id.tv_order_time);


        TextView mTVTitleType = viewHolderFactory.findViewById(R.id.tv_title_type);
        TextView mTvTitleTime = viewHolderFactory.findViewById(R.id.tv_title_time);
        TextView mTvTitleBuyer = viewHolderFactory.findViewById(R.id.tv_title_buyer);
        TextView mTvTitleOrderId = viewHolderFactory.findViewById(R.id.tv_title_order_no);
        TextView mTvTiteMessage = viewHolderFactory.findViewById(R.id.tv_title_message);

        mTVTitleType.setText(mTranslatesString.getBuyer_refuse());

        mTvTitleBuyer.setText(mTranslatesString.getCommon_buyer()+":");
        mTvTitleOrderId.setText(mTranslatesString.getCommon_ordernumber()+": ");
        mTvTiteMessage.setText(mTranslatesString.getCommon_buyermessage()+": ");
        mTvTitleTime.setText(mTranslatesString.getSeller_ordertime() + ": ");


        if (orderList != null) {
            mTvOrderId.setText(orderList.getCode() + "");
            mTvCreateTime.setText(Utils.cutNull(orderList.getCreateTime()));
            mTvBuyId.setText(Utils.cutNull(orderList.getUserName()));

            OrderDetailAdapter mAdapter = new OrderDetailAdapter(mContext);
            mAdapter.setData(orderList.getOrderDetails());
            mList.setAdapter(mAdapter);
            setListViewHeightBasedOnChildren(mList);
            mTvGoodsMessage.setText(Utils.cutNull(orderList.getReceiveLinkman()) + " " + Utils.cutNull(orderList.getReceiveMobile()) + " " + Utils.cutNull(orderList.getReceiveAddress())+ " " + Utils.cutNull(orderList.getRemark()));
        }
    }
}
