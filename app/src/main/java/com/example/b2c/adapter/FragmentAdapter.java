package com.example.b2c.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.b2c.activity.MainActivity;
import com.example.b2c.fragment.HomeCartFragment;
import com.example.b2c.fragment.HomeClassifyFragment;
import com.example.b2c.fragment.HomeCollectionFragment;
import com.example.b2c.fragment.HomeIndexFragmentNew;
import com.example.b2c.fragment.HomeMineFragment;

/**
 * 自定义fragment适配器
 * @author YY
 *
 */
public class FragmentAdapter  extends  FragmentStatePagerAdapter  {
	
	public final static int TAB_COUNT = 5;

	public FragmentAdapter(FragmentManager fm){
		super(fm);
	}

	@Override
	public Fragment getItem(int id) {
		switch (id) {
		case MainActivity.HOME_PAGE:
			HomeIndexFragmentNew home_fragment = new HomeIndexFragmentNew();
			return home_fragment;
		case MainActivity.CLASSIFY:
			HomeClassifyFragment homeClassify_fragment = new HomeClassifyFragment();
			return homeClassify_fragment;
		case MainActivity.SHOPPING_CART:
			HomeCartFragment shop_fragment = new HomeCartFragment();
			return shop_fragment;
		case MainActivity.COLLECTION:
			HomeCollectionFragment homeCollection_fragment = new HomeCollectionFragment();
			return homeCollection_fragment;
		case MainActivity.MINE:
			HomeMineFragment homeMine_fragment = new HomeMineFragment();
			return homeMine_fragment;
		default:
			return null;
		}
		
	}

	@Override
	public int getCount() {
		return TAB_COUNT;
	}

}
