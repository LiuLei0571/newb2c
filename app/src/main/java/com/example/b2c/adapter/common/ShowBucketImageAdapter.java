package com.example.b2c.adapter.common;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.net.response.common.ImageItem;

/**
 * 用途：
 * 作者：Created by john on 2017/2/8.
 * 邮箱：liulei2@aixuedai.com
 */


public abstract class ShowBucketImageAdapter extends BaseAdapter<ImageItem> {
    public ShowBucketImageAdapter(Context context) {
        super(context, R.layout.item_show_image);
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {
        ImageView imageView = viewHolderFactory.findViewById(R.id.iv_image);
        ImageView imageViewSelect = viewHolderFactory.findViewById(R.id.iv_select);
        final ImageItem mItem = getItem(position);
        if (mItem != null) {
            mImageLoader.displayImage("file:/" + mItem.getUrl(), imageView, options);
            if (mItem.isSelected()) {
                imageViewSelect.setImageDrawable(null);
                imageViewSelect.setImageDrawable(getContext().getResources()
                        .getDrawable(R.drawable.tag_selected));
            } else {
                imageViewSelect.setImageDrawable(null);
            }
            imageViewSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(mItem);
                }
            });
        }
    }

    public abstract void onClickItem(  ImageItem mItem);
}
