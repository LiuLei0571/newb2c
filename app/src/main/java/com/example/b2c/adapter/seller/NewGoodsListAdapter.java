package com.example.b2c.adapter.seller;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.dialog.DialogHelper;
import com.example.b2c.net.response.seller.OrderList;
import com.example.b2c.widget.util.Utils;

/**
 * 用途：
 * Created by milk on 16/10/30.
 * 邮箱：649444395@qq.com
 */

public abstract class NewGoodsListAdapter extends BaseAdapter<OrderList> {
    private Context mContext;

    public NewGoodsListAdapter(Context context) {
        super(context, R.layout.item_new_order);
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
        TextView mTvTotalPriceTitle = viewHolderFactory.findViewById(R.id.tv_total_price_title);
        TextView mTvTotalPrice = viewHolderFactory.findViewById(R.id.tv_total_price);
        TextView mTvDeliveryFeeTitle = viewHolderFactory.findViewById(R.id.tv_delivery_fee_title);
        TextView mTvDeliveryFee = viewHolderFactory.findViewById(R.id.tv_delivery_fee);


        TextView btn_sure = viewHolderFactory.findViewById(R.id.btn_sure);
        TextView btn_cancel = viewHolderFactory.findViewById(R.id.btn_cancel);
        TextView mTVTitleType = viewHolderFactory.findViewById(R.id.tv_title_type);
        TextView mTvTitleBuyer = viewHolderFactory.findViewById(R.id.tv_title_buyer);
        TextView mTvTitleOrderId = viewHolderFactory.findViewById(R.id.tv_title_order_no);
        TextView mTvTitleTime = viewHolderFactory.findViewById(R.id.tv_title_time);
        TextView mTvTiteMessage = viewHolderFactory.findViewById(R.id.tv_title_message);

        btn_sure.setText(mTranslatesString.getCommon_sure());
        btn_cancel.setText(mTranslatesString.getNotice_cancel());
        mTVTitleType.setText(mTranslatesString.getSeller_homenewtitle());
        mTvTitleBuyer.setText(mTranslatesString.getCommon_buyer() + ":");
        mTvTitleOrderId.setText(mTranslatesString.getCommon_ordernumber() + ":");
        mTvTitleTime.setText(mTranslatesString.getSeller_ordertime() + ":");
        mTvTiteMessage.setText(mTranslatesString.getCommon_buyermessage() + ":");
        mTvTotalPriceTitle.setText(mTranslatesString.getCommon_sum()+":");
        mTvDeliveryFeeTitle.setText(mTranslatesString.getCommon_freight()+":");
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogHelper.BtnTv deleteBtn = new DialogHelper.BtnTv(mTranslatesString.getCommon_makesure(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onSure(orderList);
                    }
                });
                DialogHelper.BtnTv cancelBtn = new DialogHelper.BtnTv(mTranslatesString.getNotice_cancel(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                DialogHelper.showDialog(mContext, mTranslatesString.getNotice_noticename(), mTranslatesString.getCommon_sure()+"?", Gravity.CENTER, deleteBtn, cancelBtn);

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper.BtnTv deleteBtn = new DialogHelper.BtnTv(mTranslatesString.getCommon_makesure(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCancel(orderList);
                    }
                });
                DialogHelper.BtnTv cancelBtn = new DialogHelper.BtnTv(mTranslatesString.getNotice_cancel(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                DialogHelper.showDialog(mContext, mTranslatesString.getNotice_noticename(), mTranslatesString.getCommon_sure()+"?", Gravity.CENTER, deleteBtn, cancelBtn);


            }
        });
        if (orderList != null) {
            mTvOrderId.setText(orderList.getCode() + "");
            mTvOrderTime.setText(orderList.getCreateTime());
            mTvBuyId.setText(orderList.getUserName() + "");
            OrderDetailAdapter mAdapter = new OrderDetailAdapter(mContext);
            mAdapter.setData(orderList.getOrderDetails());
            mList.setAdapter(mAdapter);
            setListViewHeightBasedOnChildren(mList);
            mTvGoodsMessage.setText(Utils.cutNull(orderList.getReceiveLinkman()) + " " + Utils.cutNull(orderList.getReceiveMobile()) + " " + Utils.cutNull(orderList.getReceiveAddress())+ " " + Utils.cutNull(orderList.getRemark()));
            mTvTotalPrice.setText(Configs.CURRENCY_UNIT+" "+Utils.cutNull(orderList.getTotalPrice()));
            mTvDeliveryFee.setText(Configs.CURRENCY_UNIT+" "+Utils.cutNull(orderList.getDeliveryFee()));
        }
    }

//    public abstract void onSelect(OrderList orderList);
    public abstract void onSure(OrderList orderList);
    public abstract void onCancel(OrderList orderList);
}
