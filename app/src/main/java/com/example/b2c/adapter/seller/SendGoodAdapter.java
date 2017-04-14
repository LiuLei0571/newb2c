package com.example.b2c.adapter.seller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.seller.OrderDetailLogisticActivity;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.net.response.seller.OrderDetail;
import com.example.b2c.net.response.seller.OrderList;
import com.example.b2c.widget.util.Utils;

import java.util.ArrayList;

/**
 * 用途：
 * Created by milk on 16/10/29.
 * 邮箱：649444395@qq.com
 */

public class SendGoodAdapter extends BaseAdapter<OrderList> {
    private Context mContext;

    public SendGoodAdapter(Context context) {
        super(context, R.layout.item_send_good);
        this.mContext = context;

    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {
        final OrderList orderList = getItem(position);
        TextView mTvOrderId = viewHolderFactory.findViewById(R.id.tv_order_id);
        TextView mTvOrderTime = viewHolderFactory.findViewById(R.id.tv_order_time);
        TextView mTvBuyId = viewHolderFactory.findViewById(R.id.tv_buy_id);
        ListView mList = viewHolderFactory.findViewById(R.id.list);
        TextView mTvGoodsMessage = viewHolderFactory.findViewById(R.id.tv_goods_message);
        TextView mBtnSelectLogistic = viewHolderFactory.findViewById(R.id.btn_select_courier);
        TextView mTvTotalPriceTitle = viewHolderFactory.findViewById(R.id.tv_total_price_title);
        TextView mTvTotalPrice = viewHolderFactory.findViewById(R.id.tv_total_price);
        TextView mTvDeliveryFeeTitle = viewHolderFactory.findViewById(R.id.tv_delivery_fee_title);
        TextView mTvDeliveryFee = viewHolderFactory.findViewById(R.id.tv_delivery_fee);


        TextView mTVTitleType = viewHolderFactory.findViewById(R.id.tv_title_type);
        TextView mTvTitleBuyer = viewHolderFactory.findViewById(R.id.tv_title_buyer);
        TextView mTvTitleOrderId = viewHolderFactory.findViewById(R.id.tv_title_order_no);
        TextView mTvTitleTime = viewHolderFactory.findViewById(R.id.tv_title_time);
        TextView mTvTiteMessage = viewHolderFactory.findViewById(R.id.tv_title_message);

        mBtnSelectLogistic.setText(mTranslatesString.getCommon_examineexpress());
        mTVTitleType.setText(mTranslatesString.getSeller_homesendtitle());

        mTvTitleBuyer.setText(mTranslatesString.getCommon_buyer());
        mTvTitleOrderId.setText(mTranslatesString.getCommon_ordernumber()+": ");
        mTvTitleTime.setText(mTranslatesString.getSeller_ordertime()+": ");
        mTvTiteMessage.setText(mTranslatesString.getCommon_buyermessage()+": ");
        mTvTotalPriceTitle.setText(mTranslatesString.getCommon_sum()+":");
        mTvDeliveryFeeTitle.setText(mTranslatesString.getCommon_freight()+":");


        mBtnSelectLogistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderDetailLogisticActivity.class);
                intent.putExtra("orderLogistic", (ArrayList<OrderDetail>) orderList.getOrderDetails());
                intent.putExtra("id", orderList.getId() + "");
                intent.putExtra("code", orderList.getDeliveryNo());
                mContext.startActivity(intent);
            }
        });
        if (orderList != null) {
            mTvOrderId.setText(Utils.cutNull(orderList.getCode() + ""));
            mTvOrderTime.setText(Utils.cutNull(orderList.getCreateTime()));
            mTvBuyId.setText(Utils.cutNull(orderList.getUserName()));
            OrderDetailAdapter mAdapter = new OrderDetailAdapter(mContext);
            mAdapter.setData(orderList.getOrderDetails());
            mList.setAdapter(mAdapter);
            setListViewHeightBasedOnChildren(mList);
            mTvGoodsMessage.setText(Utils.cutNull(orderList.getReceiveLinkman()) + " " + Utils.cutNull(orderList.getReceiveMobile()) + " " + Utils.cutNull(orderList.getReceiveAddress()));
            mTvTotalPrice.setText(Configs.CURRENCY_UNIT+" "+Utils.cutNull(orderList.getTotalPrice()));
            mTvDeliveryFee.setText(Configs.CURRENCY_UNIT+" "+Utils.cutNull(orderList.getDeliveryFee()));
        }
    }


}
