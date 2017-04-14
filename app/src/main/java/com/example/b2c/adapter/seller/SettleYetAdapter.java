package com.example.b2c.adapter.seller;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.net.response.seller.SettleYetCell;
import com.example.b2c.net.util.DateUtils;
import com.example.b2c.widget.util.Utils;

/**
 * 用途：已结算的适配器
 * Created by yy on 16/11/4.
 */
public class SettleYetAdapter extends BaseAdapter<SettleYetCell> {
    private TextView tv_batch_no;
    private TextView tv_order_money;
    private TextView tv_time;
    private TextView tv_see;

    public SettleYetAdapter(Context context) {
        super(context, R.layout.item_settle_yet);
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {
         tv_batch_no = viewHolderFactory.findViewById(R.id.tv_batch_no);
         tv_order_money = viewHolderFactory.findViewById(R.id.tv_order_money);
         tv_time = viewHolderFactory.findViewById(R.id.tv_time);
         tv_see = viewHolderFactory.findViewById(R.id.tv_see);
         final SettleYetCell cell = getItem(position);
         tv_batch_no.setText(mTranslatesString.getCommon_picinumber()+": "+Utils.cutNull(cell.getBatchesNo()));
         tv_order_money.setText(mTranslatesString.getCommon_cash()+": "+ Configs.CURRENCY_UNIT+Utils.cutNull(cell.getMoney()));
         if(cell.getCreateTime() > 0){
             tv_time.setText(DateUtils.getDateStr(DateUtils.DATE_MIDDLE_STR,cell.getCreateTime()));
         }
         //查看详情
         tv_see.setText(mTranslatesString.getCommon_selectdetail());
         tv_see.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         });
    }
}
