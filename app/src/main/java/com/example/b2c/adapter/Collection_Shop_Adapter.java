package com.example.b2c.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.ShopHomesActivity;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.fragment.HomeCollectionFragment;
import com.example.b2c.net.response.FavoriteShopDetail;


public class Collection_Shop_Adapter extends BaseAdapter<FavoriteShopDetail> {

    private HomeCollectionFragment.UnCollectAdapterListener uncollectListener;

    public Collection_Shop_Adapter(Context context, HomeCollectionFragment.UnCollectAdapterListener unCollectAdapterListener) {
        super(context, R.layout.collection_shop_item);
        this.uncollectListener = unCollectAdapterListener;
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, final int position) {

        final FavoriteShopDetail item_shop = getItem(position);

        ImageView iv_shop_pic = viewHolderFactory.findViewById(R.id.iv_shop_pic);
        TextView tv_collection_name = viewHolderFactory.findViewById(R.id.tv_shop_name);
        TextView tv_cancel_collection = viewHolderFactory.findViewById(R.id.tv_cancel_shop_collection);

        tv_cancel_collection.setText(mTranslatesString.getNotice_cancelfavorite());

        mImageLoader.displayImage(ConstantS.BASE_PIC_URL + item_shop.getPicPath(), iv_shop_pic, options);
        tv_collection_name.setText(item_shop.getTargetName());

        tv_cancel_collection.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                uncollectListener.onClick(position);
            }
        });

        iv_shop_pic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_to_shop = new Intent(getContext(), ShopHomesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("shopId", item_shop.getTargetId());
                i_to_shop.putExtras(bundle);
                getContext().startActivity(i_to_shop);
            }
        });

    }

}
