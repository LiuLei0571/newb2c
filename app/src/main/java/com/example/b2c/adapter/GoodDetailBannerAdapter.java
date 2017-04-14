package com.example.b2c.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.b2c.R;
import com.example.b2c.helper.ImageHelper;

import java.util.ArrayList;

public class GoodDetailBannerAdapter extends PagerAdapter{

	private ArrayList<String> data;
	private Context  context;

	public GoodDetailBannerAdapter( Context context,ArrayList<String> data) {
		super();
		this.context = context;
		this.data = data;

	}
	/**
	 * 返回多少page
	 */
	public int getCount() {
		return data == null ? 0 : data.size();
	}
	/**view滑动到一半时是否创建新的view
	 * true:表示不去创建，使用缓存；false:去重新创建
	 */
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view==object;
	}
	/**
	 * 类似于BaseAdapter的getView方法
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View imageLayout = LayoutInflater.from(context).inflate(
				R.layout.item_good_detail, container, false);
		ImageView imageView = (ImageView) imageLayout.findViewById(R.id.pagers);
		((ViewPager) container).addView(imageLayout, 0);
		ImageHelper.display(data.get(position), imageView);
		return imageLayout;
	}
	/**
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
		 object = null;
	}


}
