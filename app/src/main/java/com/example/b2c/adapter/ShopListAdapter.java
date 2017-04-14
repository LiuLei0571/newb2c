package com.example.b2c.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.ShopHomesActivity;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.net.response.ShopListDetail;
import com.example.b2c.widget.util.Utils;

public class ShopListAdapter extends BaseAdapter<ShopListDetail> {

	public ShopListAdapter(Context context) {
		super(context, R.layout.shop_list_item);
	}

	@Override
	public void renderView(ViewHolderFactory viewHolderFactory, int position) {
		final ShopListDetail shop = getItem(position);
		LinearLayout rl_shop = viewHolderFactory.findViewById(R.id.rl_shop_list_item);
		ImageView iv_shop_logo =  viewHolderFactory.findViewById(R.id.iv_shop_item_pic1);
		TextView tv_shop_name =  viewHolderFactory.findViewById(R.id.tv_shop_list_item_name);
		TextView tv_shop_focus =  viewHolderFactory.findViewById(R.id.tv_shop_list_item_focus);
		if (shop != null) {
			if(shop.getLogo()!=null && !"".equals(shop.getLogo())){
				mImageLoader.displayImage(ConstantS.BASE_PIC_URL + shop.getLogo(),iv_shop_logo,options);
			}
			tv_shop_name.setText(mTranslatesString.getCommon_mingcheng()
					+":"+Utils.cutNull(shop.getName()));
			if(shop.getCategoryName() != null && !"".equals(shop.getCategoryName())){
				tv_shop_focus.setText(mTranslatesString.getCommon_mainsell()
						+ ":"+shop.getCategoryName());
			}
			rl_shop.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(getContext(), ShopHomesActivity.class);
					int shopId = shop.getId();
					Bundle bundle = new Bundle();
					bundle.putInt("shopId", shopId);
					i.putExtras(bundle);
					getContext().startActivity(i);
				}
			});
		}
	}

}
