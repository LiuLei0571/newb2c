package com.example.b2c.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.net.response.ShoppingAddressDetail;

import java.util.List;

public class SelectAddrAdapter extends BaseAdapter {
	private ViewHolder viewHolder;
	private Activity context;
	private List<ShoppingAddressDetail> shoppingAddressList;
	private final int SELECT_ADDR = 1;

	public SelectAddrAdapter(Activity context,
			List<ShoppingAddressDetail> shoppingAddressList) {
		super();
		this.context = context;
		this.shoppingAddressList = shoppingAddressList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return shoppingAddressList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.addr_item, null);
			viewHolder.rl_addr_item = (RelativeLayout) convertView
					.findViewById(R.id.ll_addr_item);
			viewHolder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_addr_owner_name);
			viewHolder.tv_addr_detail = (TextView) convertView
					.findViewById(R.id.tv_addr_detail);
			viewHolder.tv_addr_mobile = (TextView) convertView
					.findViewById(R.id.tv_addr_mobile);
//			viewHolder.tv_name.setText(shoppingAddressList.get(position)
//					.getName());
//			viewHolder.tv_addr_detail.setText(shoppingAddressList.get(position)
//					.getCoutryName()
//					+ shoppingAddressList.get(position).getProvinceName()
//					+ shoppingAddressList.get(position).getCityName()
//					+ shoppingAddressList.get(position).getAreaName()
//					+ shoppingAddressList.get(position).getAddress());
			viewHolder.tv_addr_mobile.setText(shoppingAddressList.get(position)
					.getMobile());
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		HolderSetOnClick(viewHolder, position);
		return convertView;
	}

	public void HolderSetOnClick(ViewHolder viewHolder, final int position) {
		viewHolder.rl_addr_item.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int addressId = shoppingAddressList.get(position).getId();
				Bundle bundle = new Bundle();
				bundle.putInt("addressId", addressId);
				context.getIntent().putExtras(bundle);
				context.setResult(1, context.getIntent());
				context.finish();
			}
		});
	}

	class ViewHolder {
		RelativeLayout rl_addr_item;
		TextView tv_name, tv_addr_detail, tv_addr_mobile;
	}
}
