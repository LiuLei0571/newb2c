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
import com.example.b2c.activity.GoodsDetailActivity;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.config.ConstantS;
import com.example.b2c.net.response.SampleListDetail;

public class SampleListAdapter extends BaseAdapter<SampleListDetail> {

	public SampleListAdapter(Context context){
		super(context, R.layout.sample_list_item);
	}

	@Override
	public void renderView(ViewHolderFactory viewHolderFactory, int position) {
		final SampleListDetail  sample =getItem(position);
		LinearLayout rl_sample_1 = viewHolderFactory.findViewById(R.id.rl_sample_list_item);
		ImageView iv_sample_1 = viewHolderFactory.findViewById(R.id.iv_sample_item_pic1);
		TextView  tv_goods_name_1 = viewHolderFactory.findViewById(R.id.tv_sample_list_item_name);
		TextView  tv_goods_price_1 = viewHolderFactory.findViewById(R.id.tv_sample_list_item_price);
		TextView  tv_goods_sales_1 = viewHolderFactory.findViewById(R.id.tv_sample_list_item_sales);

		tv_goods_name_1.setText(mTranslatesString.getCommon_mingcheng()+":"+sample.getName());
		tv_goods_price_1.setText(mTranslatesString.getCommon_price()+":"+Configs.CURRENCY_UNIT+sample.getPrice());
		tv_goods_sales_1.setText(mTranslatesString.getCommon_sellamount()+":"+sample.getAmount()+mTranslatesString.getCommon_num());
		if(sample.getDefaultPic() != null &&! "".equals(sample.getDefaultPic())){
			mImageLoader.displayImage(ConstantS.BASE_PIC_URL+ sample.getDefaultPic(),iv_sample_1,options);
		}
		rl_sample_1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getContext(), GoodsDetailActivity.class);
				int sampleId = sample.getId();
				Bundle bundle = new Bundle();
				bundle.putInt("sampleId", sampleId);
				i.putExtras(bundle);
				getContext().startActivity(i);
			}
		});
	}
}
