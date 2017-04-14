package com.example.b2c.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.net.response.OrderCell;
import com.example.b2c.net.response.translate.MobileStaticTextCode;

import java.util.List;

public class OrderCellListAdapter extends BaseAdapter{
	
	private List<OrderCell> orderCellList;
	
	private Context  context;
	
	private ViewHolder  viewHolder;
	protected MobileStaticTextCode mTranslatesString = SPHelper.getBean("translate", MobileStaticTextCode.class);

	public OrderCellListAdapter( Context context,List<OrderCell> orderCellList) {
		super();
		this.orderCellList = orderCellList;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return orderCellList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return orderCellList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		// TODO Auto-generated method stub
		
		ViewHolder viewHolder=null;
		if(view==null){
			viewHolder = new ViewHolder();  
			view = View.inflate(context, R.layout.pay_head_item, null);
			viewHolder.lv_order_no=(TextView) view.findViewById(R.id.lv_order_no);
			viewHolder.lv_order_price=(TextView) view.findViewById(R.id.lv_order_price);
			viewHolder.tv_order_no=(TextView) view.findViewById(R.id.tv_order_no);
			viewHolder.tv_order_price=(TextView) view.findViewById(R.id.tv_order_price);
			view.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) view.getTag();
		}
		
		viewHolder.lv_order_no.setText(mTranslatesString.getCommon_ordernumber());
		viewHolder.lv_order_price.setText(mTranslatesString.getCommon_ordermoney());
		
		viewHolder.tv_order_no.setText(orderCellList.get(position).getCode());
		viewHolder.tv_order_price.setText(orderCellList.get(position).getTotalPrice());
		
		return view;
	}
	
	class ViewHolder{
		
		TextView lv_order_no,lv_order_price;
		TextView tv_order_no,tv_order_price;
		
	}
	

}
