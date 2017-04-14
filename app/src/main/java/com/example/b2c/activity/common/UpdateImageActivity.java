package com.example.b2c.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.b2c.R;
import com.example.b2c.helper.ImageHelper;
import com.example.b2c.net.response.common.ImageItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 用途：
 * 作者：Created by john on 2017/2/8.
 * 邮箱：liulei2@aixuedai.com
 */


public class UpdateImageActivity extends TempBaseActivity {
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private int currentPosition;//当前的位置
    private List<ImageItem> mListData = new ArrayList<>();
    private MyPageAdapter mAdapter;

    @Override
    public int getRootId() {
        return R.layout.activity_update_image;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();

    }

    private void initView() {
        addImage(R.drawable.close, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListData.size() == 1) {
                    removeAll();
                    onBackClick();
                } else {
                    removeImg(currentPosition);
                    Log.d("mList1", "onClick: ");
                    viewpager.removeAllViews();
                    mAdapter.removeView(currentPosition);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mAdapter = new MyPageAdapter(mListData);
        viewpager.setAdapter(mAdapter);
        viewpager.setCurrentItem(currentPosition);
    }

    public void removeAll() {
        mListData.clear();
        sendActivityMessage();
    }

    private void sendActivityMessage() {
        Intent intent = new Intent();
        intent.putExtra("mListData", (Serializable) mListData);
        this.setResult(0, intent);
        finish();
    }

    private void removeImg(int location) {
        if (location + 1 <= mListData.size()) {
            mListData.remove(location);
        }
    }

    private void initData() {
        currentPosition = getIntent().getIntExtra("imageMark", 0);
        mListData = (ArrayList<ImageItem>) getIntent().getSerializableExtra("mListData");
    }

    class MyPageAdapter extends PagerAdapter {
        private List<ImageItem> dataList = new ArrayList<ImageItem>();
        private ArrayList<ImageView> mViews = new ArrayList<ImageView>();

        public MyPageAdapter(List<ImageItem> dataList) {
            this.dataList = dataList;
            int size = dataList.size();
            for (int i = 0; i != size; i++) {
                ImageView iv = new ImageView(UpdateImageActivity.this);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                ImageHelper.display("file:/" + mListData.get(i).getUrl(), iv);
                iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                mViews.add(iv);
            }
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public Object instantiateItem(View arg0, int arg1) {
            ImageView iv = mViews.get(arg1);
            ((ViewPager) arg0).addView(iv);
            return iv;
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {
            if (mViews.size() >= arg1 + 1) {
                ((ViewPager) arg0).removeView(mViews.get(arg1));
            }
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        public void removeView(int position) {
            if (position + 1 <= mViews.size()) {
                mViews.remove(position);
            }
        }
    }

    @Override
    public void onBackClick() {
        sendActivityMessage();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            sendActivityMessage();
        }
        return super.onKeyDown(keyCode, event);
    }
}
