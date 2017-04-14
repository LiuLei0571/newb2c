package com.example.b2c.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.GoodsDetailActivity;
import com.example.b2c.config.Configs;
import com.example.b2c.helper.ImageHelper;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.net.response.SampleListDetail;
import com.example.b2c.net.response.translate.MobileStaticTextCode;
import com.example.b2c.net.util.HttpClientUtil;
import com.example.b2c.widget.util.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class GoodsListAdapter extends BaseAdapter {
	public ViewHolder viewHolder;
	private Context context;
	private List<SampleListDetail> sample_list = new ArrayList<SampleListDetail>();
	private String symbol = Configs.CURRENCY_UNIT;
	private ImageLoader loader;
	protected MobileStaticTextCode mTranslatesString = SPHelper.getBean("translate", MobileStaticTextCode.class);

	public GoodsListAdapter(Context context, List<SampleListDetail> sample_list) {
		super();
		this.context = context;
		this.sample_list = sample_list;
		loader = ImageLoader.getInstance();
	}

	@Override
	public int getCount() {
		if (sample_list.size() % 2 == 1)
			return (sample_list.size() / 2) + 1;
		else
			return sample_list.size() / 2;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.goods_list_item, null);
			viewHolder.ll_goods_1 = (LinearLayout) convertView
					.findViewById(R.id.ll_goods_1);
			viewHolder.ll_goods_2 = (LinearLayout) convertView
					.findViewById(R.id.ll_goods_2);
			viewHolder.iv_goods_1 = (ImageView) convertView
					.findViewById(R.id.iv_goods_item_pic1);
			viewHolder.iv_goods_2 = (ImageView) convertView
					.findViewById(R.id.iv_goods_item_pic2);
			viewHolder.tv_goods_name_1 = (TextView) convertView
					.findViewById(R.id.tv_goods_item_name1);
			viewHolder.tv_goods_name_2 = (TextView) convertView
					.findViewById(R.id.tv_goods_item_name2);
			viewHolder.tv_goods_price_1 = (TextView) convertView
					.findViewById(R.id.tv_goods_item_price1);
			viewHolder.tv_goods_price_2 = (TextView) convertView
					.findViewById(R.id.tv_goods_item_price2);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_goods_name_1.setText(Utils.cutNull(sample_list.get(position * 2)
				.getName()));
		viewHolder.tv_goods_price_1.setText(Utils.cutNull(mTranslatesString
				.getCommon_marketprice())
				+ ": "
				+ symbol
				+ sample_list.get(position * 2).getPrice());
		ImageHelper.display(HttpClientUtil.BASE_PIC_URL
						+ sample_list.get(position * 2).getDefaultPic(),
				viewHolder.iv_goods_1);
		if ((position * 2 + 1) >= sample_list.size()) {
			viewHolder.ll_goods_2.setVisibility(View.INVISIBLE);
		} else if ((position * 2 + 1) < sample_list.size()) {
			viewHolder.tv_goods_name_2.setText(Utils.cutNull(sample_list
					.get(position * 2 + 1).getName()));
			viewHolder.tv_goods_price_2
					.setText(mTranslatesString
							.getCommon_marketprice()
							+ ": "
							+ symbol
							+ sample_list.get(position * 2 + 1).getPrice());
			ImageHelper.display(HttpClientUtil.BASE_PIC_URL + sample_list.get(position * 2 + 1).getDefaultPic(), viewHolder.iv_goods_2);
		}
		HolderSetOnClick(viewHolder, position);
		return convertView;
	}

	public void HolderSetOnClick(ViewHolder viewHolder, final int position) {
		viewHolder.ll_goods_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(context, GoodsDetailActivity.class);
				int sampleId = sample_list.get(position * 2).getId();
				Bundle bundle = new Bundle();
				bundle.putInt("sampleId", sampleId);
				i.putExtras(bundle);
				context.startActivity(i);
			}
		});
		viewHolder.ll_goods_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(context, GoodsDetailActivity.class);
				int sampleId = sample_list.get(position * 2 + 1).getId();
				Bundle bundle = new Bundle();
				bundle.putInt("sampleId", sampleId);
				i.putExtras(bundle);
				context.startActivity(i);
			}
		});
	}

	class ViewHolder {
		public LinearLayout ll_goods_1, ll_goods_2;
		public ImageView iv_goods_1, iv_goods_2;
		public TextView tv_goods_name_1, tv_goods_name_2, tv_goods_price_1,
				tv_goods_price_2;
	}
}
