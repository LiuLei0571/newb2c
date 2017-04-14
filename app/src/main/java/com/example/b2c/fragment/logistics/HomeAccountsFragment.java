package com.example.b2c.fragment.logistics;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.activity.logistic.SettlementRelevanceListActivity;
import com.example.b2c.activity.logistic.SettlementDetailActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.fragment.ZTHBaseFragment;
import com.example.b2c.fragment.staff.StaffViewPagerBaseFragment;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.indicator.BasePager;
import com.example.b2c.helper.indicator.TabPageIndicator;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.logistics.TotalPriceResult;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
暂时废弃不用，用来viewpagerintore
 */

public class HomeAccountsFragment extends ZTHBaseFragment {
    private ImageButton toolbar_back;
    private TextView toolbar_title;
    private TabPageIndicator indicator;
    private LinearLayout ll_indicator;
    private ViewPager pager;
    private ArrayList<BasePager> templeBaseFragments;
    private View stu_baby_indicate_line;
    private List<String> titles;
    private int screenWidth;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_staff_work;
    }

    @Override
    public void initView(View view) {
        toolbar_back= (ImageButton) view.findViewById(R.id.toolbar_back);
        toolbar_back.setVisibility(View.GONE);
        toolbar_title= (TextView) view.findViewById(R.id.toolbar_title);
        toolbar_title.setText(mTranslatesString.getSeller_homemanager());
        indicator= (TabPageIndicator) view.findViewById(R.id.indicator);
        ll_indicator= (LinearLayout) view.findViewById(R.id.ll_indicator);
        pager= (ViewPager) view.findViewById(R.id.pager);
        stu_baby_indicate_line = view.findViewById(R.id.viewpager_indicate_line);

    }

    @Override
    public void initListener() {
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /**
                 * 偏移距离：百分比*指示器的宽度
                 * 起始位置：position*指示器的宽度
                 * 最终位置：起始位置+偏移距离
                 */
                float offsetX = positionOffset * (stu_baby_indicate_line.getWidth() * 2);
//                float startX = position * stu_baby_indicate_line.getWidth();
                float startX = position * screenWidth / templeBaseFragments.size() + screenWidth / templeBaseFragments.size() / 4;
                float endX = startX + offsetX;
                ViewHelper.setTranslationX(stu_baby_indicate_line, endX);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {
        templeBaseFragments = new ArrayList<>();
        titles = new ArrayList<>();
        titles.add(mTranslatesString.getCommon_all());
        titles.add(mTranslatesString.getCommon_daiquhuo());
        titles.add(mTranslatesString.getCommon_yunsongzhong());
        titles.add(mTranslatesString.getSeller_homereceived());
        titles.add(mTranslatesString.getCommon_yetreject());
        templeBaseFragments.add(new LogisticsBaseOrderFragment(0,getActivity()));
        templeBaseFragments.add(new LogisticsBaseOrderFragment(1,getActivity()));
        templeBaseFragments.add(new LogisticsBaseOrderFragment(2,getActivity()));
        templeBaseFragments.add(new LogisticsBaseOrderFragment(3,getActivity()));
        templeBaseFragments.add(new LogisticsBaseOrderFragment(4,getActivity()));
        initIndiateWidth();
        MyNewsPager adapter = new MyNewsPager(titles);
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);

    }
    class MyNewsPager extends PagerAdapter {
        private List<String> datas;
        public MyNewsPager(List<String> datas){
            this.datas=datas;
        }
        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String s = datas.get(position);
            return s;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager = templeBaseFragments.get(position);
            View view=basePager.initView();
            basePager.initData();
            container.addView(view);
            return view;
        }
    }
    /**
     * 初始化Indicator的宽度：屏幕的一半
     */

    private void initIndiateWidth() {
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        stu_baby_indicate_line.getLayoutParams().width = screenWidth / templeBaseFragments.size() / 2;
        //刷新：
        stu_baby_indicate_line.invalidate();//onDraw()
    }
}
