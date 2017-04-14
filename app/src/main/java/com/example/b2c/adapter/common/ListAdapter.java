package com.example.b2c.adapter.common;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.helper.UIHelper;

import java.util.List;

/**
 * 用途：公共选择类
 * Created by milk on 17/2/6.
 * 邮箱：649444395@qq.com
 */

public class ListAdapter extends BaseAdapter<String> {
    private int gravity;
    private List<String> dataImg;
    private int sellextItemPosition=-1;

    public ListAdapter(Context context) {
        super(context, R.layout.dialog_simple_list_item);
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {
        TextView textView = viewHolderFactory.findViewById(R.id.text1);
        textView.setTextColor(UIHelper.getColor(R.color.text_dark));
        textView.setBackgroundResource(R.drawable.item_selector);
        ImageView imageView = viewHolderFactory.findViewById(R.id.iv);
        String text = getItem(position);
        textView.setText(text);
        if (position == sellextItemPosition) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }

    public void setSellextItemPosition(int sellextItemPosition) {
        this.sellextItemPosition = sellextItemPosition;
    }
}
