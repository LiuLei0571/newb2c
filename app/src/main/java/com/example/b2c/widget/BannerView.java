package com.example.b2c.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.b2c.R;
import com.example.b2c.activity.GoodsDetailActivity;
import com.example.b2c.activity.HomeIntentAdWebviewActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.net.response.BannerAdDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 用途：
 * Created by milk on 16/9/30.
 * 邮箱：649444395@qq.com
 */

public class BannerView extends BaseRelativeLayout {
    @Bind(R.id.viewPage)
    ViewPager mViewPager;
    @Bind(R.id.line)
    LinearLayout mLinearLayout;
    private List<BannerAdDetail> listData;
    private long loopDelayed = 10000;
    private Handler mHandler = new Handler();
    private Context context;
    public BannerView(Context context) {
        super(context);
        this.context=context;

    }

    public BannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;

    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;


    }

    @Override
    public int getRootLayoutId() {
        return R.layout.item_home_banner;
    }

    @Override
    public void afterViewBind(View view) {
        mLinearLayout.removeAllViews();
        listData = new ArrayList<>();

    }

    private void refreshPoint(int position) {
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            if (position == 0) {
                position = listData.size() - 2;
            }
            if (position == listData.size() - 1) {
                position = 1;
            }
            if (position - 1 == i) {
                ((ImageView) mLinearLayout.getChildAt(i)).setImageResource(R.drawable.banner_point_selected);
            } else {
                ((ImageView) mLinearLayout.getChildAt(i)).setImageResource(R.drawable.banner_point_unselected);

            }
        }
    }

    public void addPoint(int size) {
        mLinearLayout.removeAllViews();
        for (int i = 0; i < size; i++) {
            ImageView imageView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.item_temple_image, mLinearLayout, false);
            if (i == 0) {
                imageView.setImageResource(R.drawable.banner_point_selected);
            } else {
                imageView.setImageResource(R.drawable.banner_point_unselected);
            }
            mLinearLayout.addView(imageView);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return listData.size() == 0 ? 0 : listData.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                final BannerAdDetail modele = listData.get(position);
                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                mImageLoader.displayImage(ConstantS.BASE_PIC_URL + modele.getPicPath(), imageView,options);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(getContext(), NewWebViewActivity.class);
//                        intent.putExtra("url", modele.getPicLink());
//                        getContext().startActivity(intent);
                        String picLink=modele.getPicLink();
                        int sampleId = getSampleId(picLink);
                        if (sampleId != 0) {
                            Intent intent = new Intent(context, GoodsDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("sampleId", sampleId);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        } else {
                            Intent intent = new Intent(context, HomeIntentAdWebviewActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("url", picLink);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    }
                });
                container.addView(imageView, layoutParams);

                return imageView;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            //重写pageAdapter必须重写此方案
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                refreshPoint(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 0) {
                    if (mViewPager.getCurrentItem() == 0) {
                        mViewPager.setCurrentItem(listData.size() - 2, false);
                    }
                    if (mViewPager.getCurrentItem() == listData.size() - 1) {
                        mViewPager.setCurrentItem(1, false);
                    }
                }
            }

        });
    }

    public void setData(List<BannerAdDetail> modelList) {
        if (modelList.size() > 0) {
            listData.clear();
            listData.addAll(modelList);
            listData.add(0, listData.get(listData.size() - 1));
            listData.add(listData.get(1));
            addPoint(modelList.size());
            mViewPager.getAdapter().notifyDataSetChanged();
            mViewPager.setCurrentItem(1);
            startImageCycle();
        }

    }


    private Runnable myTask = new Runnable() {
        @Override
        public void run() {
            if (listData.size() > 0) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                mHandler.postDelayed(this, loopDelayed);
            }
        }
    };

    public void startImageCycle() {
        if (myTask != null) {
            mHandler.removeCallbacks(myTask);
        }
        mHandler.postDelayed(myTask, loopDelayed);
    }
    private int getSampleId(String picLink) {
        // TODO Auto-generated method stub
        String picLinkP = picLink;
        String id = null;
        String idStr = null;
        if (picLinkP != null) {
            if (picLinkP.lastIndexOf("/") != picLinkP.length() - 1) {
                idStr = picLinkP.substring(picLinkP.lastIndexOf("/") + 1);
                if (idStr.indexOf(".") == -1) {
                    id = idStr;
                }
            }
        }
        if (id == null) {
            return 0;
        } else {
            return Integer.parseInt(id);
        }

    }

}
