package com.example.b2c.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment的基类
 * 1.规范代码结构
 * 2.精简代码
 *
 * @author wangdh
 */
public abstract class ZTHBaseFragment extends BaseFragment {

    private View view;
    public ZTHBaseFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            view = View.inflate(getActivity(), getLayoutResID(), null);
            initView(view);
            initData();
            initListener();

        } catch (Exception e) {
            e.getStackTrace();
        }

        return view;
    }


    /**
     * 获取Activity显示的布局：
     *
     * @return：布局id
     */
    public abstract int getLayoutResID();

    /**
     * 初始化View：findViewById
     */
    public abstract void initView(View view);

    /**
     * 初始化监听：点击监听、设置适配器、设置条目点击监听
     */
    public abstract void initListener();

    /**
     * 初始化数据
     */
    public abstract void initData();

    @Override
    public Activity getBaseActivity() {
        return getActivity();
    }
}
