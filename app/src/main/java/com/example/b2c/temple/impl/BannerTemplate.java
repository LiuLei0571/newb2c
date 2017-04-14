package com.example.b2c.temple.impl;

import android.view.ViewGroup;

import com.example.b2c.R;
import com.example.b2c.activity.IAct;
import com.example.b2c.net.response.ModelPage;
import com.example.b2c.temple.BaseTemplate;
import com.example.b2c.temple.TemplateBaseHolder;
import com.example.b2c.widget.BannerLayout;


/**
 * 广告
 * Created by milk on 2016/10/27.
 */

public class BannerTemplate extends BaseTemplate<ModelPage, BannerTemplate.BannerTempHolder> {
    public BannerTemplate(IAct act) {
        super(act);
    }

    @Override
    public BannerTempHolder createCustomViewHolder(ViewGroup parent) {
        return new BannerTempHolder(parent);
    }

    @Override
    public void bindCustomViewHolder(BannerTempHolder holder, final ModelPage model) {
        holder.banner.setBanners(model.getItems(), getOnClick());
    }


    public static class BannerTempHolder extends TemplateBaseHolder {
        BannerLayout banner;

        public BannerTempHolder(ViewGroup parent) {
            super(parent, R.layout.template_banner);
            banner = (BannerLayout) itemView;
        }
    }
}
