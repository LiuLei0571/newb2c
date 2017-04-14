package com.example.b2c.fragment.logistics;

import android.content.SharedPreferences;
import android.mtp.MtpConstants;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.adapter.MyFragmentPagerAdapter;
import com.example.b2c.fragment.ZTHBaseFragment;
import com.example.b2c.widget.LazyViewPager;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * 用途：订单的fragment
 * Created by milk on 16/11/11.
 * 邮箱：649444395@qq.com
 */

public class HomeOrderFragment extends ZTHBaseFragment implements View.OnClickListener {
    private ImageButton toolbar_back;
    private TextView toolbar_title;
    private View stu_baby_indicate_line;
    private LazyViewPager order_viewpager;
    private List<TempleBaseFragment> fragments;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private int screenWidth;
    private TextView quanbu;
    private TextView news_order;
    private TextView yunsong_order;
    private TextView qianshou_order;
    private TextView jushou_order;
    private SharedPreferences sp;

    public  HomeOrderFragment(){}
    @Override
    public int getLayoutResID() {
        return R.layout.fragment_logistics_home_order;
    }

    @Override
    public void initView(View view) {
        toolbar_back = (ImageButton) view.findViewById(R.id.toolbar_back);
        toolbar_title = (TextView) view.findViewById(R.id.toolbar_title);
        toolbar_title.setText(mTranslatesString.getSeller_homemanager());
        stu_baby_indicate_line = view.findViewById(R.id.stu_baby_indicate_line);
        order_viewpager = (LazyViewPager) view.findViewById(R.id.order_viewpager);
        quanbu= (TextView) view.findViewById(R.id.quanbu);
        news_order= (TextView) view.findViewById(R.id.news_order);
        yunsong_order= (TextView) view.findViewById(R.id.yunsong_order);
        qianshou_order= (TextView) view.findViewById(R.id.qianshou_order);
        jushou_order= (TextView) view.findViewById(R.id.jushou_order);
        //初始化 视频标签 变大
        ViewPropertyAnimator.animate(quanbu).scaleX(1.2f).scaleY(1.2f);
        order_viewpager.setOffscreenPageLimit(1);
        toolbar_back.setVisibility(View.GONE);
        quanbu.setText(mTranslatesString.getCommon_all());
        news_order.setText(mTranslatesString.getSeller_homenewtitle());
        yunsong_order.setText(mTranslatesString.getCommon_yunsongzhong());
        qianshou_order.setText(mTranslatesString.getSeller_homereceived());
        jushou_order.setText(mTranslatesString.getCommon_yetreject());
    }

    @Override
    public void initListener() {
        //设置适配器
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
        order_viewpager.setAdapter(myFragmentPagerAdapter);
        quanbu.setOnClickListener(this);
        news_order.setOnClickListener(this);
        yunsong_order.setOnClickListener(this);
        qianshou_order.setOnClickListener(this);
        jushou_order.setOnClickListener(this);
        order_viewpager.setOnPageChangeListener(new LazyViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /**
                 * 偏移距离：百分比*指示器的宽度
                 * 起始位置：position*指示器的宽度
                 * 最终位置：起始位置+偏移距离
                 */
                float offsetX = positionOffset * (stu_baby_indicate_line.getWidth() * 2);
//                float startX = position * stu_baby_indicate_line.getWidth();
                float startX = position * screenWidth / fragments.size() + screenWidth / fragments.size() / 4;
                float endX = startX + offsetX;
                ViewHelper.setTranslationX(stu_baby_indicate_line, endX);
            }

            @Override
            public void onPageSelected(int position) {
                //标签颜色改变
                //获取颜色
                int green = getResources().getColor(R.color.hand);
                int halfwhite = getResources().getColor(R.color.text_dark);
                quanbu.setTextColor(position == 0 ? green : halfwhite);
                news_order.setTextColor(position == 1 ? green : halfwhite);
                yunsong_order.setTextColor(position == 2 ? green : halfwhite);
                qianshou_order.setTextColor(position == 3 ? green : halfwhite);
                jushou_order.setTextColor(position == 4 ? green : halfwhite);

                //标签做缩放动画  (animate()要做动画的控件)
                ViewPropertyAnimator.animate(quanbu).scaleX(position == 0 ? 1.2f : 1.0f).scaleY(position == 0 ? 1.2f : 1.0f);
                ViewPropertyAnimator.animate(news_order).scaleX(position == 1 ? 1.2f : 1.0f).scaleY(position == 1 ? 1.2f : 1.0f);
                ViewPropertyAnimator.animate(yunsong_order).scaleX(position == 2 ? 1.2f : 1.0f).scaleY(position == 2 ? 1.2f : 1.0f);
                ViewPropertyAnimator.animate(qianshou_order).scaleX(position == 3 ? 1.2f : 1.0f).scaleY(position == 3 ? 1.2f : 1.0f);
                ViewPropertyAnimator.animate(jushou_order).scaleX(position == 4 ? 1.2f : 1.0f).scaleY(position == 4 ? 1.2f : 1.0f);
                //将现在的position存到本地
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {
        fragments = new ArrayList<TempleBaseFragment>();
        fragments.add(new QuanBuFragment(0));
       fragments.add(new QuanBuFragment(1));
       fragments.add(new QuanBuFragment(2));
        fragments.add(new QuanBuFragment(3));
        fragments.add(new QuanBuFragment(4));
        initIndiateWidth();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quanbu:
                order_viewpager.setCurrentItem(0);
                break;
            case R.id.news_order:
                order_viewpager.setCurrentItem(1);
                break;
            case R.id.yunsong_order:
                order_viewpager.setCurrentItem(2);
                break;
            case R.id.qianshou_order:
                order_viewpager.setCurrentItem(3);
                break;
            case R.id.jushou_order:
                order_viewpager.setCurrentItem(4);
                break;

            default:
                break;
        }
    }

    /**
     * 初始化Indicator的宽度：屏幕的一半
     */

    private void initIndiateWidth() {
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        stu_baby_indicate_line.getLayoutParams().width = screenWidth / fragments.size() / 2;
        float startX =screenWidth / fragments.size() / 4;
        ViewHelper.setTranslationX(stu_baby_indicate_line, startX);
        //刷新：
        stu_baby_indicate_line.invalidate();//onDraw()
    }
}
