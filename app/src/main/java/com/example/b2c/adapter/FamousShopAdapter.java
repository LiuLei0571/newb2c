package com.example.b2c.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.b2c.R;
import com.example.b2c.activity.ShopHomesActivity;
import com.example.b2c.net.response.FamousShopDetail;
import com.example.b2c.net.util.HttpClientUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

public class FamousShopAdapter extends BaseAdapter {
	
	private String[] mImageList;
	private Context context;
	private List<FamousShopDetail> shop_List;
	private ImageLoader loader;
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = null;
	

	public FamousShopAdapter(Context context, List<FamousShopDetail> shop_List, DisplayImageOptions options, ImageLoadingListener animateFirstListener2) {
		super();
		this.context = context;
		this.shop_List = shop_List;
		this.loader = ImageLoader.getInstance();
		this.options=options;
		this.mImageList=new String[shop_List.size()];
		this.animateFirstListener=animateFirstListener2;
		initMImageList(shop_List);
	}
	private void  initMImageList(List<FamousShopDetail> shop_List){
		for(int i=0;i<shop_List.size();i++){
			String  picPath=HttpClientUtil.BASE_PIC_URL+shop_List.get(i).getPicPath();
			mImageList[i]=picPath;
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return shop_List.size() / 2;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;
		if (convertView == null) {
			viewHolder = new ViewHolder();  
			convertView = View.inflate(context, R.layout.famous_shop_item, null);
			//可以理解为从vlist获取view  之后把view返回给ListView  
            
			viewHolder.iv_famous_shop_pic1 = (ImageView) convertView
					.findViewById(R.id.iv_famous_shop_item_pic1);
			
			
			viewHolder.iv_famous_shop_pic2 = (ImageView) convertView
					.findViewById(R.id.iv_famous_shop_item_pic2);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		loader.displayImage(mImageList[position * 2],
							viewHolder.iv_famous_shop_pic1,
							options,
							animateFirstListener);
		loader.displayImage(
				mImageList[position * 2 + 1],
				viewHolder.iv_famous_shop_pic2,
				options,
				animateFirstListener);
		viewHolder.iv_famous_shop_pic1.setTag(position*2);  
		viewHolder.iv_famous_shop_pic2.setTag(position*2+1); 
		
		viewHolder.iv_famous_shop_pic1.setOnClickListener(new ToShopListener(position,true));
		viewHolder.iv_famous_shop_pic2.setOnClickListener(new ToShopListener(position,false));
	
		return convertView;
	}

	class ViewHolder {
		private ImageView iv_famous_shop_pic1, iv_famous_shop_pic2;
	}
	
	class ToShopListener implements View.OnClickListener {
		int shopId;
		int mPosition;  
		boolean flag;
        public ToShopListener(int position,boolean flag){  
            this.mPosition= position;  
            this.shopId=GetShopIdByPosition(position,flag);
            this.flag=flag;
        }  
		@Override
		public void onClick(View arg0) {
			if(shopId==0){
				return;
			}
			Intent i_to_shop = new Intent(context, ShopHomesActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("shopId", shopId);
			i_to_shop.putExtras(bundle);
			context.startActivity(i_to_shop);
		}

		public int GetShopIdByPosition(int position,boolean flag0) {
			FamousShopDetail shop=null;
			if(flag0){
				shop=shop_List.get(position*2);
			}else{
				shop=shop_List.get(position*2+1);
			}
//			String url =shop.getUrl();
//			int shopId=0;
//			if(url.lastIndexOf("/")!=url.length()-1){
//				shopId=Integer.valueOf(url.lastIndexOf("/")+1);
//			}
			return shopId=shop.getShopId();
		}

	}
	
	

	
}
