package com.example.b2c.adapter.seller;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.net.response.seller.PropertyDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * 用途：
 * 作者：Created by john on 2017/2/14.
 * 邮箱：liulei2@aixuedai.com
 */


public abstract class GoodsSpecificationDetailAdapter extends BaseAdapter<PropertyDetails> {
    private List<PropertyDetails> mList = new ArrayList<>();
    private boolean isSelecet;
    private int oldPosition;

    public GoodsSpecificationDetailAdapter(Context context) {
        super(context, R.layout.item_goods_specification_detail);
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, final int position) {
        final TextView mTvMessage = viewHolderFactory.findViewById(R.id.tv_message);
        ImageView imageViewSelect = viewHolderFactory.findViewById(R.id.iv_select);

        final PropertyDetails mData = getItem(position);
        if (mData != null) {
            mTvMessage.setText(mData.getName());
            if (mData.isSelect()) {
                mTvMessage.setTextColor(Color.BLUE);
            } else {
                mTvMessage.setTextColor(Color.GRAY);
            }
        }

        mTvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemOnClickListener(mData);
            }
        });
    }

    public abstract void itemOnClickListener(PropertyDetails mList);
}
