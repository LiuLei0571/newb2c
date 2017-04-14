package com.example.b2c.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.net.response.SystemMessageDetail;

import java.util.List;

public class MyMessageAdapter extends BaseAdapter {
	private ViewHolder viewHolder;
	private Context context;
	private List<SystemMessageDetail> message_list;

	public MyMessageAdapter(Context context,
			List<SystemMessageDetail> message_list) {
		super();
		this.context = context;
		this.message_list = message_list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return message_list.size();
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
	public View getView(int position, View convertView, ViewGroup group) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.my_message_item, null);
			viewHolder.tv_title = (TextView) convertView
					.findViewById(R.id.tv_my_message_title);
			viewHolder.tv_content = (TextView) convertView
					.findViewById(R.id.tv_my_message_content);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_title.setText(message_list.get(position).getTitle());
		viewHolder.tv_content.setText(message_list.get(position).getContent());
		return convertView;
	}

	class ViewHolder {
		public TextView tv_title;
		public TextView tv_content;
	}
}
