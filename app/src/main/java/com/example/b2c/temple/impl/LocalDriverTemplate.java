package com.example.b2c.temple.impl;

import android.view.View;
import android.view.ViewGroup;

import com.example.b2c.B2C;
import com.example.b2c.R;
import com.example.b2c.activity.IAct;
import com.example.b2c.net.response.ModelPage;
import com.example.b2c.temple.BaseTemplate;
import com.example.b2c.temple.TemplateBaseHolder;


/**
 * Created by milk on 2016/10/28.
 */

public class LocalDriverTemplate extends BaseTemplate<ModelPage, TemplateBaseHolder> {
    public LocalDriverTemplate(IAct act) {
        super(act);
    }

    @Override
    public TemplateBaseHolder createCustomViewHolder(ViewGroup parent) {
        View view = new View(parent.getContext());
        int height = B2C.getContext().getResources().getDimensionPixelSize(R.dimen.template_divider);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                height);
        view.setLayoutParams(lp);
        return new TemplateBaseHolder(view);
    }

}
