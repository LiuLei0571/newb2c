package com.example.b2c.adapter.logistics;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.XRcycleViewAdapterBase;
import com.example.b2c.net.response.logistics.YiJiesuanBean;
import com.example.b2c.net.response.translate.MobileStaticTextCode;

import java.util.List;

/**
 * 未结算的适配
 */

public class TrackingNumberAdapter extends XRcycleViewAdapterBase {
    private Context context;
    private List<YiJiesuanBean.Rows> rows;
    private MobileStaticTextCode mTranslatesString;
    private TextView tv_traching_type;
    private TextView tv_traching_order_number;
    private TextView tv_traching_express_number;
    private TextView tv_traching_zong_money;
    private TextView tv_traching_yi_money;
    private TextView tv_traching_wei_money;
    private TextView tv_traching_time;

    public TrackingNumberAdapter(Context context, List<YiJiesuanBean.Rows> row, MobileStaticTextCode mTranslatesString) {
        this.context = context;
        this.rows=row;
        this.mTranslatesString = mTranslatesString;
    }

    @Override
    public int getItemPostion() {
        return rows.size();
    }

    @Override
    public int getLayoutID() {
        return R.layout.item_tracking_number;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void initData(MyViewHolder holder, int position) {

        tv_traching_time.setText(rows.get(position).getDate());
        tv_traching_order_number.setText(mTranslatesString.getOrder_no()+":"+rows.get(position).getCode());
        tv_traching_express_number.setText(mTranslatesString.getExpress_no()+":"+rows.get(position).getDeliveryNo());
        tv_traching_zong_money.setText(mTranslatesString.getCommon_totalprice()+":Ks:"+
                rows.get(position).getActualPayPrice()+"("+mTranslatesString.getCommon_freight()+rows.get(position).getDeliveryFee()+")");
        if (rows.get(position).getTag()==1){
            tv_traching_type.setVisibility(View.VISIBLE);
            tv_traching_yi_money.setVisibility(View.VISIBLE);
            tv_traching_wei_money.setVisibility(View.VISIBLE);
            tv_traching_type.setText(mTranslatesString.getCommon_portionzhifu());
            tv_traching_yi_money.setText(mTranslatesString.getCommon_yizhifu()+":Ks:"+rows.get(position).getDeliveryActualTransferMoney());
            tv_traching_wei_money.setText(mTranslatesString.getCommon_nopay()+":Ks:"+rows.get(position).getNoPrice());
        }
    }
    @Override
    public void initView(View itemView) {
         tv_traching_type= (TextView) itemView.findViewById(R.id.tv_traching_type);
        tv_traching_order_number= (TextView) itemView.findViewById(R.id.tv_traching_order_number);
        tv_traching_express_number= (TextView) itemView.findViewById(R.id.tv_traching_express_number);
        tv_traching_zong_money= (TextView) itemView.findViewById(R.id.tv_traching_zong_money);
        tv_traching_yi_money= (TextView) itemView.findViewById(R.id.tv_traching_yi_money);
        tv_traching_wei_money= (TextView) itemView.findViewById(R.id.tv_traching_wei_money);
        tv_traching_time= (TextView) itemView.findViewById(R.id.tv_traching_time);
    }
}
