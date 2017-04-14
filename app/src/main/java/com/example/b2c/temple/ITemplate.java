package com.example.b2c.temple;

import android.view.ViewGroup;

import com.example.b2c.activity.IAct;


/**
 * Created by milk on 2016/10/26.
 */

public interface ITemplate<M extends ITemplateModel, VH extends TemplateBaseHolder> {
    /**
     * 创建holder
     *
     * @param parent
     * @return
     */
    VH createCustomViewHolder(ViewGroup parent);

    /**
     * 视图绑定
     *
     * @param holder
     * @param model
     */
    void bindCustomViewHolder(VH holder, M model);

    /**
     * 页面恢复
     */
    void onResume(IAct act);

    void onPause(IAct act);

}
