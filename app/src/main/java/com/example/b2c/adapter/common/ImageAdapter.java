package com.example.b2c.adapter.common;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.example.b2c.R;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.net.response.common.ImageItem;

/**
 * 用途：
 * Created by milk on 17/2/7.
 * 邮箱：649444395@qq.com
 */

public class ImageAdapter extends BaseAdapter<ImageItem> {
    private String url;

    public ImageAdapter(Context context) {
        super(context, R.layout.item_fill_image);
    }

    @Override
    public void renderView(ViewHolderFactory viewHolderFactory, int position) {
        ImageView mIv = viewHolderFactory.findViewById(R.id.item_grid_image);
        ImageItem mData = getItem(position);
        if (mData != null) {
            url = "file:/" + mData.getUrl();
            Log.d("imageLocalUrl",url);
            mImageLoader.displayImage(url, mIv);
        }
    }

    public void removeView(int position) {
        if (position + 1 <= getData().size()) {
            getData().remove(position);
        }
    }
}
