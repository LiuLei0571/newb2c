package com.example.b2c.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.net.response.SampleEvaluationListDetail;
import com.example.b2c.net.response.translate.MobileStaticTextCode;

import java.util.List;

public class SampleCommentAdapter extends BaseAdapter {
	private Context context;
	private List<SampleEvaluationListDetail> sampleEvaluationList;
	private ViewHolder viewHolder;
	protected MobileStaticTextCode mTranslatesString = SPHelper.getBean("translate", MobileStaticTextCode.class);

	private String[] comment_type = { "",
			mTranslatesString.getCommon_goodcomment(),
			mTranslatesString.getCommon_normalcomment(),
			mTranslatesString.getCommon_badcomment() };

	public SampleCommentAdapter(Context context,
			List<SampleEvaluationListDetail> sampleEvaluationList) {
		super();
		this.context = context;
		this.sampleEvaluationList = sampleEvaluationList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sampleEvaluationList.size();
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
			convertView = View.inflate(context, R.layout.sample_comment_item,
					null);
			viewHolder.tv_login_name = (TextView) convertView
					.findViewById(R.id.tv_comment_loginname);
			viewHolder.tv_comment_type = (TextView) convertView
					.findViewById(R.id.tv_comment_type);
			viewHolder.tv_comment_content = (TextView) convertView
					.findViewById(R.id.tv_comment_content);
			viewHolder.tv_comment_specification = (TextView) convertView
					.findViewById(R.id.tv_comment_specification);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_login_name.setText(sampleEvaluationList.get(position)
				.getLoginName());
		viewHolder.tv_comment_type.setText(comment_type[sampleEvaluationList
				.get(position).getType()]);
		viewHolder.tv_comment_content.setText(sampleEvaluationList
				.get(position).getContent());
		viewHolder.tv_comment_specification.setText(sampleEvaluationList.get(
				position).getSpecification());
		return convertView;
	}

	class ViewHolder {
		public TextView tv_login_name;
		public TextView tv_comment_type;
		public TextView tv_comment_content;
		public TextView tv_comment_specification;
	}
}
