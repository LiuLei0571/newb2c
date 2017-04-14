package com.example.b2c.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.config.ConstantS;
import com.example.b2c.net.response.BuyerOrderDetail;

/**
 * 用途：
 * Created by milk on 16/11/3.
 * 邮箱：649444395@qq.com
 */

public class
BuyerOrderDetailAdapter extends BaseAdapter<BuyerOrderDetail> {

    public BuyerOrderDetailAdapter(Context context) {
        super(context, R.layout.item_buyer_order_detail);
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {
        BuyerOrderDetail mDetail = getItem(position);
        TextView tv_good_type_name = viewHolderFactory.findViewById(R.id.tv_good_type_name);
        tv_good_type_name.setText(mTranslatesString.getCommon_goodstype());
        ImageView mIvGoodsHead = viewHolderFactory.findViewById(R.id.iv_goods_head);
        TextView mTvGoodsName = viewHolderFactory.findViewById(R.id.tv_goods_name);
        TextView mTvGoodType = viewHolderFactory.findViewById(R.id.tv_good_type);
        TextView mTvGoodsMoney = viewHolderFactory.findViewById(R.id.tv_goods_moeny);
        TextView mTvGoodsAcount = viewHolderFactory.findViewById(R.id.tv_goods_account);
        if (mDetail != null) {
            mImageLoader.displayImage(ConstantS.BASE_PIC_URL + mDetail.getSampleImage(), mIvGoodsHead, options);
            mTvGoodsName.setText(mDetail.getSampleName());
            mTvGoodsMoney.setText(Configs.CURRENCY_UNIT + mDetail.getSamplePrice());
            mTvGoodsAcount.setText("x" + mDetail.getSampleNum());
            mTvGoodType.setText(mDetail.getSampleProperty());
        }
    }
}
