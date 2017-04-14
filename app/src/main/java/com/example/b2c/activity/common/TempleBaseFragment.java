package com.example.b2c.activity.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.fragment.BaseFragment;
import com.example.b2c.widget.LogisticsDataConnection;
import com.example.b2c.widget.SellerDataConnection;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 用途：
 * Created by milk on 16/10/27.
 * 邮箱：649444395@qq.com
 */

public abstract class TempleBaseFragment extends BaseFragment {
    protected View mRootView;
    protected SellerDataConnection sellerRdm;
    protected RelativeLayout mEmpty;
    protected LogisticsDataConnection mLogisticsDataConnection;

public TempleBaseFragment(){}
    protected TextView tv_nodata;

    protected abstract int getContentViewId();

    protected abstract void initView(View rootView);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getContentViewId(), container, false);
        sellerRdm = SellerDataConnection.getInstance();
        mLogisticsDataConnection = LogisticsDataConnection.getInstance();
        mEmpty = (RelativeLayout) mRootView.findViewById(R.id.empty);
        tv_nodata = (TextView) mRootView.findViewById(R.id.tv_nodata);
        if (tv_nodata != null) {
            tv_nodata.setText(mTranslatesString.getCommon_nodata());
        }
        ButterKnife.bind(this, mRootView);
        initView(mRootView);
        return mRootView;
    }

    protected void getIntent(FragmentActivity baseActivity, Class cls) {
        Intent intent = new Intent(baseActivity, cls);
        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public Activity getBaseActivity() {
        return getActivity();
    }

    public class HomePage {
        public String tag;
        public Class<? extends BaseFragment> fragmentCls;

        public HomePage(String tag, Class<? extends BaseFragment> fragmentCls) {
            this.tag = tag;
            this.fragmentCls = fragmentCls;
        }
    }

    public synchronized BaseFragment setChildCurrentTab(int position, HomePage[] fragmentPage) {
        BaseFragment baseFragment = null;
        if (position >= 0 && position < fragmentPage.length) {
            HomePage page = fragmentPage[position];
            Class<? extends BaseFragment> cls = page.fragmentCls;
            List<Fragment> fragments = getChildFragmentManager().getFragments();
            FragmentTransaction trans = getChildFragmentManager().beginTransaction();
            if (fragments != null && fragments.size() > 0) {
                for (Fragment fragment : fragments) {
                    if (fragment == null) {
                        continue;
                    }
                    if (cls.isInstance(fragment)) {
                        baseFragment = (BaseFragment) fragment;
                        trans.attach(baseFragment);
                    } else {
                        trans.detach(fragment);
                    }
                }
            }
            if (baseFragment == null) {
                try {
                    baseFragment = cls.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (baseFragment != null) {
                    trans.add(R.id.tab_host, baseFragment, page.tag);
                }
            }
            trans.commitAllowingStateLoss();
        }
        return baseFragment;
    }

}
