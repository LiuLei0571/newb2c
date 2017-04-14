package com.example.b2c.adapter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.activity.common.TempleBaseFragment;

/**
 * 主界面viewpager的适配器
 * @author wangdh
 *
 */
public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    /**
     * 数据源，需要通过构造传递
     */
    private List<TempleBaseFragment> fragmentList = new ArrayList<TempleBaseFragment>();
    private FragmentManager fm;
    public MyFragmentPagerAdapter(FragmentManager fm,List<TempleBaseFragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.fm=fm;
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        return super.instantiateItem(container, position);


    }

}
