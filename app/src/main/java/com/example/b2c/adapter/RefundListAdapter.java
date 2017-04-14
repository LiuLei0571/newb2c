package com.example.b2c.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.net.response.RefundListDetail;
import com.example.b2c.net.response.translate.CellText;
import com.example.b2c.widget.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefundListAdapter  extends BaseAdapter<RefundListDetail> {

	public Map<String,String> statusLabels = null;

	public RefundListAdapter(Context context) {
		super(context,R.layout.refund_list_item);
		List<CellText> ls= optionList.getReturnGoodStatus();
		initMap(ls);
	}

	private void initMap(List<CellText> ls) {
		statusLabels =new HashMap();
		for(CellText cell:ls){
			statusLabels.put(cell.getValue(),cell.getText());
		}
	}

	@Override
	public void renderView(ViewHolderFactory viewHolderFactory, int position) {
		TextView tv_shop_name = viewHolderFactory.findViewById(R.id.refund_item_shop_name);
		TextView tv_refund_status = viewHolderFactory.findViewById(R.id.tv_refund_item_status);
		TextView tv_refund_code = viewHolderFactory.findViewById(R.id.tv_refund_code);
		TextView tv_orderCode = viewHolderFactory.findViewById(R.id.tv_orderCode);
		TextView tv_sample_name = viewHolderFactory.findViewById(R.id.tv_sample_name);
		TextView tv_sample_price = viewHolderFactory.findViewById(R.id.tv_sample_price);
		TextView tv_refund_reason = viewHolderFactory.findViewById(R.id.tv_refund_reason);
		TextView tv_refund_total = viewHolderFactory.findViewById(R.id.tv_refund_total);
		TextView tv_refundExplain = viewHolderFactory.findViewById(R.id.tv_refundExplain);
		RefundListDetail refundDatail = getItem(position);
		if(refundDatail != null ){
			tv_shop_name.setText(Utils.cutNull(refundDatail.getShopName()));
			tv_refund_status.setText(statusLabels.get(refundDatail.getRefundStatus()+""));
			tv_refund_code.setText(mTranslatesString.getCommon_returnbianhao()+":"+Utils.cutNull(refundDatail.getCode()));
			tv_orderCode.setText(mTranslatesString.getOrder_no()+":"+Utils.cutNull(refundDatail.getOrderCode()));
			tv_sample_name.setText(mTranslatesString.getCommon_samplename()+":"+Utils.cutNull(refundDatail.getSampleName()));
			tv_sample_price.setText(mTranslatesString.getCommon_price()+":"+ Configs.CURRENCY_UNIT +Utils.cutNull(refundDatail.getSamplePrice())+"x" +Utils.cutNull(refundDatail.getSampleNum()+""));
			for(CellText cell : optionList.getRefuseReason()){
				if(cell.getValue().equals(refundDatail.getRefundReason()+"")){
					tv_refund_reason.setText(mTranslatesString.getCommon_reason()+":"+Utils.cutNull(cell.getText()));
					break;
				}
			}
			tv_refundExplain.setText(mTranslatesString.getCommon_returnshuoming()+":"+Utils.cutNull(refundDatail.getRefundExplain()));
			tv_refund_total.setText(mTranslatesString.getCommon_totalprice()+":"+ Configs.CURRENCY_UNIT +Utils.cutNull(refundDatail.getTotalPrice()+""));
		}
	}

}
