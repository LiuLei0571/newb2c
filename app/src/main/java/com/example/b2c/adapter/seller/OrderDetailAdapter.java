package com.example.b2c.adapter.seller;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.config.ConstantS;
import com.example.b2c.net.response.seller.OrderDetail;
import com.example.b2c.widget.util.Utils;

/**
 * 用途：
 * Created by milk on 16/11/3.
 * 邮箱：649444395@qq.com
 */

public class
OrderDetailAdapter extends BaseAdapter<OrderDetail> {

    public OrderDetailAdapter(Context context) {
        super(context, R.layout.item_order_detail);
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {
        OrderDetail mDEtail = getItem(position);
        ImageView mIvGoodsHead = viewHolderFactory.findViewById(R.id.iv_goods_head);
        TextView mTvGoodsName = viewHolderFactory.findViewById(R.id.tv_goods_name);
        TextView mTvGoodType = viewHolderFactory.findViewById(R.id.tv_good_type);
        TextView mTvGoodsMoney = viewHolderFactory.findViewById(R.id.tv_goods_moeny);
        TextView mTvTitleType = viewHolderFactory.findViewById(R.id.tv_title_type);

        mTvTitleType.setText(mTranslatesString.getCommon_goodstype());
        if (mDEtail != null) {
            mImageLoader.displayImage(ConstantS.BASE_PIC_URL + mDEtail.getSampleImage(), mIvGoodsHead, options);
            mTvGoodsName.setText(Utils.cutNull(mDEtail.getName()));
            mTvGoodsMoney.setText(Configs.CURRENCY_UNIT+ ":" + Utils.cutNull(mDEtail.getPrice())+"X"+mDEtail.getAmount());
            mTvGoodType.setText(Utils.cutNull(mDEtail.getSpecification()));
        }

    }
}
