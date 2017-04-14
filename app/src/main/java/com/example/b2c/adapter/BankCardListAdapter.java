package com.example.b2c.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.ChangeCardActivity;
import com.example.b2c.net.response.GetBankCardListDetail;

import java.util.List;

public class BankCardListAdapter extends BaseAdapter {
	private Activity context;
	private List<GetBankCardListDetail> bankcardList;
	private ViewHolder viewHolder;

	public BankCardListAdapter(Activity context,
			List<GetBankCardListDetail> bankCardList) {
		super();
		this.context = context;
		this.bankcardList = bankCardList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bankcardList.size();
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
			convertView = View.inflate(context, R.layout.bankcard_item, null);
			viewHolder.rl_bankcard = (RelativeLayout) convertView
					.findViewById(R.id.rl_bankcard_item);
			viewHolder.tv_bankcard_owner_name = (TextView) convertView
					.findViewById(R.id.tv_bankcard_owner_name);
			viewHolder.tv_bankcard_mobile = (TextView) convertView
					.findViewById(R.id.tv_bankcard_mobile);
			viewHolder.tv_bankcard_detail = (TextView) convertView
					.findViewById(R.id.tv_bankcard_detail);
			viewHolder.tv_bankcard_owner_name.setText(bankcardList
					.get(position).getHolderName());
			viewHolder.tv_bankcard_detail.setText(bankcardList.get(position)
					.getMobile());
			viewHolder.tv_bankcard_detail.setText(bankcardList.get(position)
					.getBankName()
					+ " "
					+ bankcardList.get(position).getBankCard());

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		HolderSetOnClick(viewHolder, position);
		return convertView;
	}

	public void HolderSetOnClick(ViewHolder viewHolder, final int position) {
		viewHolder.rl_bankcard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i_change_card = new Intent(context,
						ChangeCardActivity.class);
				int bankId = bankcardList.get(position).getId();
				String holderName = bankcardList.get(position).getHolderName();
				String mobile = bankcardList.get(position).getMobile();
				String bankCard = bankcardList.get(position).getBankCard();
				int isDefault = bankcardList.get(position).getIsDefault();
				int id=bankcardList.get(position).getId();
				Bundle bundle = new Bundle();
				bundle.putInt("bankId", bankId);
				bundle.putInt("isDefault", isDefault);
				bundle.putString("holderName", holderName);
				bundle.putString("mobile", mobile);
				bundle.putString("bankCard", bankCard);
				bundle.putInt("id", id);
				i_change_card.putExtras(bundle);
				context.startActivity(i_change_card);
				context.finish();
			}
		});
	}

	class ViewHolder {
		RelativeLayout rl_bankcard;
		TextView tv_bankcard_owner_name, tv_bankcard_detail,
				tv_bankcard_mobile;
	}
}
