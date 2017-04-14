package com.example.b2c.helper.indicator;

import android.content.Context;
import android.view.View;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.fragment.BaseFragment;

/**
 * 自定义pager的基类：
 * 初始化view和初始化数据的方法
 * @author wangdh
 *
 */
public abstract class BasePager extends BaseFragment {
    public Context context;
    public BasePager(){}
    public BasePager(Context context){
        this.context = context;
    }

    /**
     * 让子类重写，子类确定子view，初始化view
     * @return
     */
    public abstract View initView();
    /**
     * 让子类重写，填充子view，初始化数据
     * @return
     */
    public abstract void initData();
    
   
}
