package com.example.b2c.adapter.staff;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.b2c.fragment.staff.StaffOrderBaseFragment;

import java.util.List;

/**
 * 用途：
 * Created by milk on 17/2/4.
 * 邮箱：649444395@qq.com
 */

public class StaffTabPageAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private List<StaffOrderBaseFragment> fragmentList;
    private List<String> pageTitleList;

    public StaffTabPageAdapter(FragmentManager fm, Context context, List<StaffOrderBaseFragment> fragmentList, List<String> pageTitleList) {
        super(fm);
        mContext = context;
        this.fragmentList = fragmentList;
        this.pageTitleList = pageTitleList;
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
    public CharSequence getPageTitle(int position) {
        return pageTitleList.get(position);
    }


    public void clear() {
         notifyDataSetChanged();
    }
}
