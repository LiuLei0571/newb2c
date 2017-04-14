package com.example.b2c.temple.impl;

import android.view.View;
import android.view.ViewGroup;

import com.example.b2c.activity.IAct;
import com.example.b2c.temple.BaseTemplate;
import com.example.b2c.temple.ITemplateModel;
import com.example.b2c.temple.TemplateBaseHolder;


/**
 * Created by milk on 2016/10/27.
 */

public class LocalEmptyTemplate extends BaseTemplate<ITemplateModel, TemplateBaseHolder> {

    public LocalEmptyTemplate(IAct act) {
        super(act);
    }

    @Override
    public TemplateBaseHolder createCustomViewHolder(ViewGroup parent) {
        View emptyView = new View(parent.getContext());
        return new TemplateBaseHolder(emptyView);
    }
}
