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
 * 用途：
 * 作者：Created by milk on 2017/1/25.
 * 邮箱：liulei2@aixuedai.com
 */


public class FourFourTemplate extends BaseTitleTemplate<ModelPage, FourFourTemplate.FourFourTemplateHolder> {

    public FourFourTemplate(IAct act) {
        super(act);
    }

    @Override
    public FourFourTemplateHolder createCustomViewHolder(ViewGroup parent) {
        return new FourFourTemplateHolder(parent);
    }

    @Override
    public void bindCustomViewHolder(FourFourTemplateHolder holder, ModelPage forum) {
        super.showTitle(holder, forum);
        updateItem(holder.item1, getItem(forum, 0));
        updateItem(holder.item2, getItem(forum, 1));
        updateItem(holder.item3, getItem(forum, 2));
        updateItem(holder.item4, getItem(forum, 3));
        updateItem(holder.item5, getItem(forum, 4));
        updateItem(holder.item6, getItem(forum, 5));
        updateItem(holder.item7, getItem(forum, 6));
        updateItem(holder.item8, getItem(forum, 7));
    }

    protected void updateItem(View view, ModelPageItem item) {
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        TextView mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView mTvMessage = (TextView) view.findViewById(R.id.tv_message);
        mTvMessage.setVisibility(View.GONE);
        mImageLoader.displayImage(item == null ? "" : ConstantS.BASE_PIC_URL + item.getItemImage(), icon, options);
        if (item != null) {
            Utils.showHtmlContent(mTvTitle, Utils.cutNull(item.getItemTitle()));
            Utils.showHtmlContent(mTvMessage, Utils.cutNull(item.getItemMessage()));
        }

        view.setTag(R.id.item, item);
        view.setOnClickListener(getOnClick());
    }

    public class FourFourTemplateHolder extends TemplateTitleBaseHolder {
        View item1, item2, item3, item4, item5, item6, item7, item8;

        public FourFourTemplateHolder(ViewGroup parent) {
            super(parent, R.layout.template_fourfour);
            super.initTitle(itemView);
            item1 = itemView.findViewById(R.id.item1);
            item2 = itemView.findViewById(R.id.item2);
            item3 = itemView.findViewById(R.id.item3);
            item4 = itemView.findViewById(R.id.item4);
            item5 = itemView.findViewById(R.id.item5);
            item6 = itemView.findViewById(R.id.item6);
            item7 = itemView.findViewById(R.id.item7);
            item8 = itemView.findViewById(R.id.item8);

        }

        @Override
        public void onViewRecycled() {
            ((ImageView) item1.findViewById(R.id.icon)).setImageDrawable(null);
            ((ImageView) item2.findViewById(R.id.icon)).setImageDrawable(null);
            ((ImageView) item3.findViewById(R.id.icon)).setImageDrawable(null);
            ((ImageView) item4.findViewById(R.id.icon)).setImageDrawable(null);
            ((ImageView) item5.findViewById(R.id.icon)).setImageDrawable(null);
            ((ImageView) item6.findViewById(R.id.icon)).setImageDrawable(null);
            ((ImageView) item7.findViewById(R.id.icon)).setImageDrawable(null);
            ((ImageView) item8.findViewById(R.id.icon)).setImageDrawable(null);
        }
    }


}
