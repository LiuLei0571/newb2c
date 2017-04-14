package com.example.b2c.adapter.seller;

import android.content.Context;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.seller.SettlesActivity;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.net.response.seller.SettleCell;
import com.example.b2c.net.util.DateUtils;
import com.example.b2c.widget.util.Utils;

/**
 * 用途：待结算和已逾期结算的适配器
 * Created by yy on 16/11/4.
 */
public class SettleAdapter extends BaseAdapter<SettleCell> {
    private int type;
    private TextView tv_order_no;
    private TextView tv_status;
    private TextView tv_delivery_no;
    private TextView tv_order_money;
    private TextView tv_time;

    public SettleAdapter(Context context,int type) {
        super(context, R.layout.item_settle);
        this.type = type;
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {
         tv_order_no = viewHolderFactory.findViewById(R.id.tv_order_no);
         tv_status = viewHolderFactory.findViewById(R.id.tv_status);
         tv_delivery_no = viewHolderFactory.findViewById(R.id.tv_delivery_no);
         tv_order_money = viewHolderFactory.findViewById(R.id.tv_order_money);
         tv_time = viewHolderFactory.findViewById(R.id.tv_time);
         final SettleCell cell = getItem(position);
         tv_order_no.setText(mTranslatesString.getOrder_no()+": "+Utils.cutNull(cell.getCode()));
         tv_delivery_no.setText(mTranslatesString.getExpress_no()+": "+Utils.cutNull(cell.getDeliveryNo()));
         if (type ==  SettlesActivity.DELAY_SETTLE){
             if(cell.getType() == 1){
                 tv_status.setText(mTranslatesString.getCommon_portionzhifu());
             }else if(cell.getType() == 2){
                 tv_status.setText(mTranslatesString.getCommon_nopay());
             }
         } else if (type ==  SettlesActivity.WAIT_SETTLE){
            if(cell.getType() == 1){
                tv_status.setText(mTranslatesString.getCommon_portionzhifu());
            }else if(cell.getType() == 2){
                tv_status.setText(mTranslatesString.getCommon_nopay());
            }
         }
         tv_order_money.setText(mTranslatesString.getCommon_ordermoney()+": "+ Configs.CURRENCY_UNIT+Utils.cutNull(cell.getTotalPrice()));
         if(cell.getCreateTime() > 0){
             tv_time.setText(DateUtils.getDateStr(DateUtils.DATE_MIDDLE_STR,cell.getCreateTime()));
         }

    }
}
