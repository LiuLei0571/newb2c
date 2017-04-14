package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.GetShopDetailListener;
import com.example.b2c.net.response.GetShopDetailResult;

/**
 * 店铺首页--店铺简介 页面
 */
public class ShopIntroActivity extends TempBaseActivity{
	private ImageView iv_logo;
	private TextView tv_shopName, tv_serviceAttitude, tv_consistentDescription,
			tv_contactTel, tv_deliverySpeed, tv_rateOfPraise,tv_rateOfPraise_front,
			tv_consistentDescription_front,tv_serviceAttitude_front, tv_deliverySpeed_front,
			tv_contactTel_front;
	private int shopId;

	@Override
	public int getRootId() {
		return R.layout.shop_introduce_layout;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		Bundle bundle = i.getExtras();
		shopId = bundle.getInt("shopId");
		initView();
		initData();
	}

	public void initView() {
		tv_shopName = (TextView) findViewById(R.id.tv_shopintro_name);
		tv_serviceAttitude = (TextView) findViewById(R.id.tv_serviceAttitude);
		tv_consistentDescription = (TextView) findViewById(R.id.tv_consistentDescription);
		tv_contactTel = (TextView) findViewById(R.id.tv_contactTel);
		tv_deliverySpeed = (TextView) findViewById(R.id.tv_deliverySpeed);
		tv_rateOfPraise = (TextView) findViewById(R.id.tv_rateOfPraise);
		iv_logo = (ImageView) findViewById(R.id.iv_shop_logo);
		tv_rateOfPraise_front = (TextView) findViewById(R.id.tv_rateOfPraise_front);
		tv_consistentDescription_front = (TextView) findViewById(R.id.tv_consistentDescription_front);
		tv_serviceAttitude_front = (TextView) findViewById(R.id.tv_serviceAttitude_front);
		tv_deliverySpeed_front = (TextView) findViewById(R.id.tv_deliverySpeed_front);
		tv_contactTel_front = (TextView) findViewById(R.id.tv_contactTel_front);
		initText();
	}

	public void initText() {
		setTitle(mTranslatesString
				.getCommon_shopprofile());
		tv_rateOfPraise_front.setText(mTranslatesString
				.getCommon_goodcommentrate());
		tv_consistentDescription_front.setText(mTranslatesString
				.getCommon_consitancerate());
		tv_serviceAttitude_front.setText(mTranslatesString
				.getCommon_serviceatitude());
		tv_deliverySpeed_front.setText(mTranslatesString
				.getCommon_deliveryspeed());
		tv_contactTel_front.setText(mTranslatesString
				.getCommon_linkway());
	}
	/**
	 * 初始化数据
	 */
	public void initData() {
		showLoading();
		rdm.GetShopDetail(unLogin, userId, token, shopId);
		rdm.getshopdetailListener = new GetShopDetailListener() {

			@Override
			public void onSuccess(final GetShopDetailResult result) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						tv_shopName.setText(result.getShopName());
						tv_serviceAttitude.setText(result.getServiceAttitude()+ "");
						tv_consistentDescription.setText(result.getConsistentDescription() + "");
						tv_contactTel.setText(result.getContactTel());
						tv_deliverySpeed.setText(result.getDeliverySpeed() + "");
						tv_rateOfPraise.setText(result.getRateOfPraise() + "");
						loader.displayImage(ConstantS.BASE_PIC_URL+ result.getShopLogo(),iv_logo,options);
					}
				});
			}
			@Override
			public void onError(int errorNO, String errorInfo) {
				ToastHelper.makeErrorToast(errorInfo);
			}
			@Override
			public void onFinish() {
				dissLoading();
			}
			@Override
			public void onLose(){
				ToastHelper.makeErrorToast(request_failure);
			}
		};
	}

}
