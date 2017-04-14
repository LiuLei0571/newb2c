package com.example.b2c.adapter.seller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.seller.ChangeShopDetailActivity;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.config.ConstantS;
import com.example.b2c.net.response.seller.ShopDetail;
import com.example.b2c.widget.util.Utils;

/**
 * 用途：
 * Created by milk on 16/11/4.
 * 邮箱：649444395@qq.com
 */

public class ShopAdapter extends BaseAdapter<ShopDetail> {
    private int type;

    public ShopAdapter(Context context, int type) {
        super(context, R.layout.item_shop);
        this.type = type;
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {
        final ShopDetail shopDetail = getItem(position);

        ImageView mIvGoodsHead = viewHolderFactory.findViewById(R.id.iv_goods_head);
        TextView mTvGoodsName = viewHolderFactory.findViewById(R.id.tv_goods_name);
        TextView mTvGoodCount = viewHolderFactory.findViewById(R.id.tv_good_count);
        TextView mTvGoodsMoney = viewHolderFactory.findViewById(R.id.tv_goods_money);
        TextView mTvChange = viewHolderFactory.findViewById(R.id.btn_select_courier);
        mTvChange.setText(mTranslatesString.getCommon_changeinfo());
        if (shopDetail != null) {
            mImageLoader.displayImage(ConstantS.BASE_PIC_URL + shopDetail.getDefaultPic(), mIvGoodsHead,options);
            mTvGoodsName.setText(Utils.cutNull(shopDetail.getName()));
            mTvGoodsMoney.setText(Configs.CURRENCY_UNIT+": " + shopDetail.getPrice() + "");
            mTvGoodCount.setText(shopDetail.getStock() + mTranslatesString.getCommon_num());
        }
        mTvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangeShopDetailActivity.class);
                intent.putExtra("type", type);
                Bundle bundle = new Bundle();
                bundle.putSerializable("shopDetail", shopDetail);
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });
    }
}
