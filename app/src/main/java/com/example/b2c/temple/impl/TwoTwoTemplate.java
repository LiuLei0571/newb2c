package com.example.b2c.temple.impl;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.IAct;
import com.example.b2c.config.ConstantS;
import com.example.b2c.net.response.ModelPage;
import com.example.b2c.net.response.ModelPageItem;
import com.example.b2c.temple.BaseTitleTemplate;
import com.example.b2c.temple.TemplateTitleBaseHolder;
import com.example.b2c.widget.util.Utils;


/**
 * Created by milk on 2016/11/1.
 */

public class TwoTwoTemplate extends BaseTitleTemplate<ModelPage, TwoTwoTemplate.TwoTwoHolder> {
    public TwoTwoTemplate(IAct act) {
        super(act);
    }

    @Override
    public TwoTwoHolder createCustomViewHolder(ViewGroup parent) {
        return new TwoTwoHolder(parent);
    }

    @Override
    public void bindCustomViewHolder(TwoTwoHolder holder, ModelPage forum) {
        super.showTitle(holder, forum);
        updateItem(holder.item1, getItem(forum, 0) );
        updateItem(holder.item2, getItem(forum, 1) );
        updateItem(holder.item3, getItem(forum, 2) );
        updateItem(holder.item4, getItem(forum, 3) );
    }

    protected void updateItem(View view, ModelPageItem item ) {
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        TextView tv= (TextView) view.findViewById(R.id.tv_title);
        TextView  mTvMessage = (TextView) view.findViewById(R.id.tv_message);
        ImageView  leftIcon = (ImageView) view.findViewById(R.id.icon_left);
        mImageLoader.displayImage(item == null ? "" : ConstantS.BASE_PIC_URL + item.getItemImage(), leftIcon, options);
        mImageLoader.displayImage(item == null ? "" : ConstantS.BASE_PIC_URL + item.getItemIcon(), icon, options);
        view.setTag(R.id.item, item);
        view.setOnClickListener(getOnClick());
        if (item != null) {
            Utils.showHtmlContent(tv, Utils.cutNull(item.getItemTitle()));
            Utils.showHtmlContent(mTvMessage, Utils.cutNull(item.getItemMessage()));
        }
    }

    public static class TwoTwoHolder extends TemplateTitleBaseHolder {
        View item1, item2, item3, item4;

        public TwoTwoHolder(ViewGroup parent) {
            super(parent, R.layout.template_two_two);
            super.initTitle(itemView);
            item1 = itemView.findViewById(R.id.item1);
            item2 = itemView.findViewById(R.id.item2);
            item3 = itemView.findViewById(R.id.item3);
            item4 = itemView.findViewById(R.id.item4);

        }

        @Override
        public void onViewRecycled() {
            ((ImageView) item1.findViewById(R.id.icon)).setImageDrawable(null);
            ((ImageView) item2.findViewById(R.id.icon)).setImageDrawable(null);
            ((ImageView) item3.findViewById(R.id.icon)).setImageDrawable(null);
            ((ImageView) item4.findViewById(R.id.icon)).setImageDrawable(null);
        }
    }
}
