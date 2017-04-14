package com.example.b2c.activity.seller;

import android.os.Bundle;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.Configs;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.seller.TodayOrderListener;

import java.util.List;

import butterknife.Bind;

/**
 * 用途：
 * Created by milk on 16/10/28.
 * 邮箱：649444395@qq.com
 */

public class TodayOrderActivity extends TempBaseActivity {
    @Bind(R.id.tv_amount)
    TextView mTvAmount;
    @Bind(R.id.tv_today_pay_title)
    TextView mTvTodayPayTitle;
    @Bind(R.id.tv_today_pay_num)
    TextView mTvTodayPayNum;
    @Bind(R.id.tv_people_title)
    TextView mTvPeopleTitle;
    @Bind(R.id.tv_people_num)
    TextView mTvPeopleNum;
    @Bind(R.id.tv_store_title)
    TextView mTvStoreTitle;
    @Bind(R.id.tv_store_num)
    TextView mTvStoreNum;
    @Bind(R.id.tv_title_merchandise)
    TextView tvTitleMerchandise;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_today_order;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getToday_title());
        initText();
        initData();
    }

    private void initText() {
        tvTitleMerchandise.setText(mTranslatesString.getGoods_merchandise());
        mTvTodayPayTitle.setText(mTranslatesString.getToday_paynum());
        mTvPeopleTitle.setText(mTranslatesString.getToday_buyernum());
        mTvStoreTitle.setText(mTranslatesString.getToday_goodsnum());
    }

    private void initData() {
        showLoading();
        sellerRdm.getTodayOrderList(ConstantS.BASE_URL + "seller/order/today/list.htm");
        sellerRdm.mTodayOrderListener = new TodayOrderListener() {
            @Override
            public void onSuccess(List mList, final String order, final int buyer, final int sample, final int money) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvAmount.setText( Configs.CURRENCY_UNIT + order);
                        mTvPeopleNum.setText(sample + "");
                        mTvStoreNum.setText(money + "");
                        mTvTodayPayNum.setText(buyer + "");
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
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }
        };
    }
}
