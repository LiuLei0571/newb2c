package com.example.b2c.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.net.response.ShoppingAddressDetail;


public class AddressAdapter extends BaseAdapter<ShoppingAddressDetail> {

	public AddressAdapter(Context context) {
		super(context, R.layout.addr_item);
	}
	@Override
	public void renderView(ViewHolderFactory viewHolderFactory, int position) {

		ShoppingAddressDetail addressDetail = getItem(position);
		if(addressDetail!=null){
			TextView tv_name = viewHolderFactory.findViewById(R.id.tv_addr_owner_name);
			TextView tv_address_detail =  viewHolderFactory.findViewById(R.id.tv_addr_detail);
			TextView tv_address_mobile= viewHolderFactory.findViewById(R.id.tv_addr_mobile);

			tv_name.setText(addressDetail.getFirstName()+addressDetail.getLastName());
			StringBuilder  detailText=new StringBuilder();
			if(addressDetail.getProvinceName()!=null){
				detailText.append(addressDetail.getProvinceName());
			}
			if(addressDetail.getCityName()!=null){
				detailText.append(addressDetail.getCityName());
			}
			if(addressDetail.getAddress() !=null){
				detailText.append(addressDetail.getAddress());
			}
			tv_address_detail.setText(detailText);
			tv_address_mobile.setText(addressDetail.getMobile());
		}
	}

}
