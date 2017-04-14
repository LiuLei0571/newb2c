package com.example.b2c.adapter.seller;

import android.content.Context;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.net.response.seller.FreightTemplate;
import com.example.b2c.widget.util.Utils;

/**
 * 用途：
 * Created by milk on 16/10/27.
 * 邮箱：649444395@qq.com
 */

public class TemplateAdapter extends BaseAdapter<FreightTemplate> {

    public TemplateAdapter(Context context) {
        super(context, R.layout.item_template);
    }


    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {
        FreightTemplate freightTemplate = getItem(position);
        TextView tv_label_ship_name = viewHolderFactory.findViewById(R.id.tv_label_ship_name);
        TextView tv_ship_name = viewHolderFactory.findViewById(R.id.tv_ship_name);
        TextView tv_label_receive_name = viewHolderFactory.findViewById(R.id.tv_label_receive_name);
        TextView tv_receive_name = viewHolderFactory.findViewById(R.id.tv_receive_name);
        TextView tv_label_weight = viewHolderFactory.findViewById(R.id.tv_label_weight);
        TextView tv_weight = viewHolderFactory.findViewById(R.id.tv_weight);
        TextView tv_label_days = viewHolderFactory.findViewById(R.id.tv_label_days);
        TextView tv_days = viewHolderFactory.findViewById(R.id.tv_days);
        TextView tv_label_fee = viewHolderFactory.findViewById(R.id.tv_label_fee);
        TextView tv_fee = viewHolderFactory.findViewById(R.id.tv_fee);
        tv_label_ship_name.setText(mTranslatesString.getCommon_fahuo());
        tv_label_receive_name.setText(mTranslatesString.getCommon_shouhuodi());
        tv_label_weight.setText(mTranslatesString.getSeller_weight());
        tv_label_days.setText(mTranslatesString.getCommon_tianshu());
        tv_label_fee.setText(mTranslatesString.getCommon_feiyong());
        if (freightTemplate != null) {
            tv_ship_name.setText(Utils.cutNull(freightTemplate.getShippingProvinceName()+" "+freightTemplate.getShippingCityName()));
            tv_receive_name.setText(Utils.cutNull(freightTemplate.getReceiveProvinceName()+" "+freightTemplate.getReceiveCityName()));
            tv_weight.setText(Utils.cutNull(freightTemplate.getWeight()));
            tv_days.setText(Utils.cutNull(freightTemplate.getDeliveryTimeCost()));
            tv_fee.setText(Configs.CURRENCY_UNIT+": "+Utils.cutNull(freightTemplate.getFee()));
        }
    }
}
