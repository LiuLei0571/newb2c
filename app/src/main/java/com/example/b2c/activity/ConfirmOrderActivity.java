package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.BuyerOrderDetailAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.NoDataListener;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.BuyerOrderList;
import com.example.b2c.widget.ListViewForScrollView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：
 * Created by milk on 16/11/21.
 * 邮箱：649444395@qq.com
 */
public class ConfirmOrderActivity extends TempBaseActivity {

    @Bind(R.id.tv_delivery_no)
    TextView mTvDeliveryNo;
    @Bind(R.id.tv_shop_name)
    TextView mTvShopName;
    @Bind(R.id.list)
    ListViewForScrollView mList;
    @Bind(R.id.tv_order_money)
    TextView mTvOrderMoney;
    @Bind(R.id.tv_express_money)
    TextView mTvExpressMoney;
    @Bind(R.id.btn_contact_seller)
    TextView mBtnContactSeller;
    @Bind(R.id.btn_remind_seller)
    TextView mBtnRemindSeller;
    @Bind(R.id.lyt_tab)
    LinearLayout mLytTab;
    @Bind(R.id.tv_express_no)
    TextView tv_express_no;
    @Bind(R.id.tv_sum)
    TextView tv_sum;


    private String dNo;
    private BuyerOrderDetailAdapter mAdapter;
    private int orderId;

    @Override
    public int getRootId() {
        return R.layout.activity_buyer_confirm_order;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getCommon_received());
        dNo = getIntent().getStringExtra("deliveryId");
        orderId = getIntent().getIntExtra("orderId", -1);
        mAdapter = new BuyerOrderDetailAdapter(this);
        initData();
        initView();
    }

    private  void initView(){
        tv_express_no.setText(mTranslatesString.getExpress_no());
        tv_sum.setText(mTranslatesString.getCommon_sum());
        mBtnContactSeller.setText(mTranslatesString.getCommon_rejectgoods());
        mBtnRemindSeller.setText(mTranslatesString.getCommon_received());
    }

    private void initData() {
        showLoading();
        rdm.getBuyerSaoYiSaoRequest(ConstantS.BASE_URL + "trade/buyer/orderInfoByNo.htm", orderId, dNo);
        rdm.mOneDataListener = new OneDataListener<BuyerOrderList>() {

            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }

            @Override
            public void onSuccess(final BuyerOrderList data, String successInfo) {
                if (data != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLytTab.setVisibility(View.VISIBLE);
                            mTvShopName.setText(data.getShopName());
                            mTvDeliveryNo.setText(data.getDeliveryNo());
                            mTvOrderMoney.setText(Configs.CURRENCY_UNIT + data.getTotalPrice());
                            mTvExpressMoney.setText("("+ mTranslatesString.getCommon_freight()+ Configs.CURRENCY_UNIT+ data.getDeliveryFee() + ")");
                            mAdapter.setData(data.getOrderDetailList());
                            mList.setAdapter(mAdapter);
                        }
                    });

                }
            }

            @Override
            public void onError(int errorNO,String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }
        };
    }

    @OnClick({R.id.btn_contact_seller, R.id.btn_remind_seller})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_contact_seller:
                Intent intent = new Intent(ConfirmOrderActivity.this, ApplyRefuseActivity.class);
                intent.putExtra("orderId", orderId);
                startActivity(intent);
                break;
            case R.id.btn_remind_seller:
                showLoading();
                rdm.getBuyerConfirmRequest(ConstantS.BASE_URL + "trade/buyer/confirmReceiptOrder.htm", orderId);
                rdm.mNodataListener = new NoDataListener() {
                    @Override
                    public void onSuccess(String success) {
                        ToastHelper.makeToast(success);
                        finish();
                    }

                    @Override
                    public void onError(int errorNO,String errorInfo) {
                        ToastHelper.makeErrorToast(errorInfo);
                    }

                    @Override
                    public void onFinish() {
                        dissLoading();
                    }

                    @Override
                    public void onLose() {
                        ToastHelper.makeErrorToast(request_failure);
                    }
                };
                break;
        }
    }
}
