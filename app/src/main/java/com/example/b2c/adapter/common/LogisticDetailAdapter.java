package com.example.b2c.adapter.common;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.net.response.seller.LogisticDetail;

/**
 * 用途：
 * Created by milk on 16/11/6.
 * 邮箱：649444395@qq.com
 */

public abstract class LogisticDetailAdapter extends BaseAdapter<LogisticDetail> {
    public LogisticDetailAdapter(Context context) {
        super(context, R.layout.item_string);
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {

        TextView textView = viewHolderFactory.findViewById(R.id.text);

        final LogisticDetail district = getItem(position);
        textView.setText(district.getDeliveryName());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSelect(district);
            }
        });

    }

    public abstract void onItemSelect(LogisticDetail district);
}
