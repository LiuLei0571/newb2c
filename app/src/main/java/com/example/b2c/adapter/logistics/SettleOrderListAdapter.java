package com.example.b2c.adapter.logistics;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.net.response.logistics.YiJiesuanBean;
import com.example.b2c.net.response.translate.MobileStaticTextCode;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * 已结算逾期结算的适配
 */

public class SettleOrderListAdapter extends XRecyclerView.Adapter<SettleOrderListAdapter.ViewHolder> {
    private SettleOrderListAdapter() {
    }

    private Context context;
    private List<YiJiesuanBean.Rows> rows;
    private String tag;
    private MobileStaticTextCode mTranslatesString;
    public SettleOrderListAdapter(Context context,List<YiJiesuanBean.Rows> rows,String tag,MobileStaticTextCode mTranslatesString) {
        this.context = context;
        this.rows=rows;
        this.tag=tag;
        this.mTranslatesString=mTranslatesString;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SettleOrderListAdapter.ViewHolder holder = new SettleOrderListAdapter.ViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_order_myadapter, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (tag.equals("yuqi")){
            //点击逾期跳转过来的
            holder.tv_order_type.setText(mTranslatesString.getCommon_yuqikouchu());
            holder.tv_order_type.setTextColor(Color.parseColor("#ffc426"));
            holder.tv_order_money.setText(mTranslatesString.getCommon_ordermoney()+":Ks"+rows.get(position).getDeliveryActualTransferMoney());
            //TODO  订单编号
            holder.tv_order_pici.setText(mTranslatesString.getCommon_ordernumber()+":"+rows.get(position).getCode());
        }
        if(tag.equals("yesjiesuan")){
            //点击已结算跳转过来对的
            holder.tv_order_type.setText(mTranslatesString.getCommon_yijiesuan());
            holder.tv_order_type.setTextColor(Color.parseColor("#44B0A0"));
            holder.tv_order_money.setText(mTranslatesString.getCommon_settlementmoney()+":Ks"+rows.get(position).getDeliveryActualTransferMoney());
            //Todo 批次号
            holder.tv_order_pici.setText(mTranslatesString.getCommon_picinumber()+":"+rows.get(position).getBatchesNo());
        }
        //快递单号
        holder.tv_kuaidi_num.setText(mTranslatesString.getExpress_no()+":"+rows.get(position).getDeliveryNo());
        holder.tv_order_date.setText(rows.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    public static class ViewHolder extends XRecyclerView.ViewHolder {
        public View rootView;
        public TextView tv_order_type;
        public TextView tv_order_money;
        public TextView tv_order_pici;
        public TextView tv_order_date;
        public TextView tv_kuaidi_num;

        public ViewHolder(View rootView) {
            super(rootView);
            this.tv_order_type = (TextView) rootView.findViewById(R.id.tv_order_type);
            this.tv_order_money = (TextView) rootView.findViewById(R.id.tv_order_money);
            this.tv_order_pici = (TextView) rootView.findViewById(R.id.tv_order_pici);
            this.tv_order_date = (TextView) rootView.findViewById(R.id.tv_order_date);
            this.tv_kuaidi_num = (TextView) rootView.findViewById(R.id.tv_kuaidi_num);
        }
    }


}
