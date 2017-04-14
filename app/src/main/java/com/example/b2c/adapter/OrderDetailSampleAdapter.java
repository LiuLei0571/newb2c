package com.example.b2c.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.helper.ImageHelper;
import com.example.b2c.net.response.OrderDetail;
import com.example.b2c.net.util.HttpClientUtil;
import com.example.b2c.widget.util.Utils;

import java.util.List;


public class OrderDetailSampleAdapter extends BaseAdapter {
	private ViewHolder viewHolder;
	private Context context;
	private List<OrderDetail> list;

	public OrderDetailSampleAdapter(Context context,
			List<OrderDetail> orderDetailList) {
		super();
		this.context = context;
		this.list = orderDetailList;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int postition, View convertView, ViewGroup group) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = View.inflate(context,
					R.layout.order_detail_sample_list_detail, null);
			viewHolder.iv_sample_pic = (ImageView) convertView
					.findViewById(R.id.iv_sample_pic);
			viewHolder.tv_sample_name = (TextView) convertView
					.findViewById(R.id.tv_sample_name);
			viewHolder.tv_sample_amount = (TextView) convertView
					.findViewById(R.id.tv_sample_num);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_sample_name.setText(Utils.cutNull(list.get(postition).getSampleName()));
		viewHolder.tv_sample_amount.setText("x"
				+ list.get(postition).getAmount());
		ImageHelper.display(HttpClientUtil.BASE_PIC_URL+ list.get(postition).getSampleImage(),viewHolder.iv_sample_pic);
		return convertView;
	}

	class ViewHolder {
		ImageView iv_sample_pic;
		TextView tv_sample_name, tv_sample_amount;
	}
}
