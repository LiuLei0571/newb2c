package com.example.b2c.adapter.common;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.net.response.seller.CategoryModule;

/**
 * 用途：
 * Created by milk on 16/11/6.
 * 邮箱：649444395@qq.com
 */

public abstract class CategoryAdapter extends BaseAdapter<CategoryModule> {
    public CategoryAdapter(Context context) {
        super(context, R.layout.item_string);
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {

        TextView textView = viewHolderFactory.findViewById(R.id.text);
        ImageView mIvNext = viewHolderFactory.findViewById(R.id.iv_right);
        final CategoryModule district = getItem(position);
        textView.setText(district.getName());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSelect(district);
            }
        });
        if (district.isHasNext()) {
            mIvNext.setVisibility(View.VISIBLE);
        } else {
            mIvNext.setVisibility(View.GONE);
        }
        mIvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemNext(district);
            }
        });

    }

    public abstract void onItemSelect(CategoryModule district);

    public abstract void onItemNext(CategoryModule district);

}
