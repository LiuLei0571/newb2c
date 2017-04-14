package com.example.b2c.adapter.base;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.UIHelper;
import com.example.b2c.net.response.ModelPageItem;


/**
 * @author Created by zhangzh on 2016/05/25.
 */
public class CategoryAdapter extends BaseAdapter<ModelPageItem> {
    private View.OnClickListener listener;

    public CategoryAdapter(Context context, View.OnClickListener mOnClick) {
        super(context, R.layout.template_category_item);
        this.listener = mOnClick;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {
        ModelPageItem category = getItem(position);
        TextView text = viewHolderFactory.findViewById(R.id.text);
        ImageView icon = viewHolderFactory.findViewById(R.id.icon);
        mImageLoader.displayImage(category == null ? "" : ConstantS.BASE_PIC_URL+ category.getItemImage(), icon,options);

        if (category != null) {
            UIHelper.showHtmlContent(text, category.getItemTitle());
            viewHolderFactory.getConvertView().setTag(R.id.item, category);
            viewHolderFactory.getConvertView().setOnClickListener(listener);
        }
    }
}
