package com.example.b2c.adapter.seller;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.dialog.DialogHelper;
import com.example.b2c.net.response.seller.OrderList;
import com.example.b2c.widget.util.Utils;

/**
 * 用途：
 * Created by milk on 16/10/30.
 * 邮箱：649444395@qq.com
 */

public abstract class RefuseWaitingAdapter extends BaseAdapter<OrderList> {
    private Context mContext;

    public RefuseWaitingAdapter(Context context) {
        super(context, R.layout.item_refuse_waiting_goods);
        mContext = context;
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {
        final OrderList orderList = getItem(position);
        TextView mTvOrderId = viewHolderFactory.findViewById(R.id.tv_order_id);
        TextView mTvBuyId = viewHolderFactory.findViewById(R.id.tv_buy_id);
        ListView mList = viewHolderFactory.findViewById(R.id.list);
        TextView mTvGoodsMessage = viewHolderFactory.findViewById(R.id.tv_goods_message);
        TextView mBtnSelectLogistic = viewHolderFactory.findViewById(R.id.btn_select_courier);
        TextView mTvCreateTime = viewHolderFactory.findViewById(R.id.tv_order_time);

        TextView mTVTitleType = viewHolderFactory.findViewById(R.id.tv_title_type);
        TextView mTvTitleBuyer = viewHolderFactory.findViewById(R.id.tv_title_buyer);
        TextView mTvTitleOrderId = viewHolderFactory.findViewById(R.id.tv_title_order_no);
        TextView mTvTitleTime = viewHolderFactory.findViewById(R.id.tv_title_time);
        TextView mTvTiteMessage = viewHolderFactory.findViewById(R.id.tv_title_message);


        mBtnSelectLogistic.setText(mTranslatesString.getSeller_getgoods());
        mTVTitleType.setText(mTranslatesString.getBuyer_refuse());

        mTvTitleBuyer.setText(mTranslatesString.getCommon_buyer() + ":");
        mTvTitleOrderId.setText(mTranslatesString.getCommon_ordernumber() + ": ");
        mTvTitleTime.setText(mTranslatesString.getSeller_ordertime() + ": ");
        mTvTiteMessage.setText(mTranslatesString.getCommon_buyermessage() + ": ");


        mBtnSelectLogistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper.BtnTv deleteBtn = new DialogHelper.BtnTv(mTranslatesString.getCommon_makesure(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onSelect(orderList);
                    }
                });
                DialogHelper.BtnTv cancelBtn = new DialogHelper.BtnTv(mTranslatesString.getNotice_cancel(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                DialogHelper.showDialog(mContext, mTranslatesString.getNotice_noticename(), mTranslatesString.getCommon_sure() + "?", Gravity.CENTER, deleteBtn, cancelBtn);
            }
        });
        if (orderList != null) {
            mTvOrderId.setText(orderList.getCode() + "");
            mTvBuyId.setText(Utils.cutNull(orderList.getUserName()));
            mTvCreateTime.setText(Utils.cutNull(orderList.getCreateTime()));

            OrderDetailAdapter mAdapter = new OrderDetailAdapter(mContext);
            mAdapter.setData(orderList.getOrderDetails());
            mList.setAdapter(mAdapter);
            setListViewHeightBasedOnChildren(mList);
            mTvGoodsMessage.setText(Utils.cutNull(orderList.getReceiveLinkman()) + " " + Utils.cutNull(orderList.getReceiveMobile()) + " " + Utils.cutNull(orderList.getReceiveAddress()));
        }
    }

    protected abstract void onSelect(OrderList orderList);
}
