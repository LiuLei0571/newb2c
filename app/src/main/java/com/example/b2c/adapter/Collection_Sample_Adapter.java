package com.example.b2c.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.GoodsDetailActivity;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.config.ConstantS;
import com.example.b2c.fragment.HomeCollectionFragment;
import com.example.b2c.net.response.FavoriteSampleDetail;


public class Collection_Sample_Adapter extends BaseAdapter<FavoriteSampleDetail> {
    public HomeCollectionFragment.UnCollectAdapterListener uncollectListener;

    public Collection_Sample_Adapter(Context context, HomeCollectionFragment.UnCollectAdapterListener uncollectListener) {
        super(context, R.layout.collection_sample_item);
        this.uncollectListener = uncollectListener;
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, final int position) {
        final FavoriteSampleDetail sampleDetail = getItem(position);
        TextView tv_collection_name = viewHolderFactory.findViewById(R.id.tv_goods_name);
        ImageView iv_goods_pic = viewHolderFactory.findViewById(R.id.iv_goods_pic);
        TextView tv_goods_price = viewHolderFactory.findViewById(R.id.tv_goods_price);
        TextView tv_cancel_collection = viewHolderFactory.findViewById(R.id.tv_cancel_collection);


        tv_collection_name.setText(sampleDetail.getTargetName());
        tv_goods_price.setText(Configs.CURRENCY_UNIT + sampleDetail.getSamplePrice());
        tv_cancel_collection.setText(mTranslatesString.getNotice_cancelfavorite());
        mImageLoader.displayImage(ConstantS.BASE_PIC_URL + sampleDetail.getPicPath(), iv_goods_pic, options);

        iv_goods_pic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_to_detail = new Intent(getContext(), GoodsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("sampleId", sampleDetail.getTargetId());
                i_to_detail.putExtras(bundle);
                getContext().startActivity(i_to_detail);
            }
        });

        tv_cancel_collection.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                uncollectListener.onClick(position);
            }
        });


    }


}
