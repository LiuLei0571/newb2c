package com.example.b2c.activity.seller;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.seller.OrderDetailAdapter;
import com.example.b2c.adapter.seller.OrderDetailLogisticAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.PageListListener;
import com.example.b2c.net.response.seller.OrderDetail;
import com.example.b2c.net.response.seller.OrderDetailLogistic;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 卖家查看已发货订单物流详情
 * 用途：
 * Created by milk on 16/11/4.
 * 邮箱：649444395@qq.com
 */

public class OrderDetailLogisticActivity extends TempBaseActivity {
    @Bind(R.id.tv_logistics_id)
    TextView mTvLogisticsId;
    @Bind(R.id.list_shop)
    ListView mListShopView;
    @Bind(R.id.list_logistic)
    ListView mListLogistic;
    @Bind(R.id.tv_express_title)
    TextView mTvTitle;
    @Bind(R.id.tv_title_delivery)
    TextView tvTitleDelivery;
    private List<OrderDetail> mListShop = new ArrayList<>();
    private OrderDetailAdapter mOrderDetailAdapter;
    private String id;
    private String code;
    private OrderDetailLogisticAdapter mLogisticAdapter;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_order_logistic;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getExpress_detail1());
        mOrderDetailAdapter = new OrderDetailAdapter(this);
        tvTitleDelivery.setText(mTranslatesString.getDelivery_no());
        mLogisticAdapter = new OrderDetailLogisticAdapter(this);
        mListShop = (ArrayList<OrderDetail>) getIntent().getSerializableExtra("orderLogistic");
        id = getIntent().getStringExtra("id");
        code = getIntent().getStringExtra("code");
        mTvLogisticsId.setText(code);
        mOrderDetailAdapter.setData(mListShop);
        mListShopView.setAdapter(mOrderDetailAdapter);
        showLoading();
        sellerRdm.getLogisticDetailRequest(ConstantS.BASE_URL + "seller/order/deliver/detail/" + id + ".htm");
        sellerRdm.mPageListListener = new PageListListener() {
            @Override
            public void onSuccess(List list) {
                final List<OrderDetailLogistic> mLogisticDetail = (List<OrderDetailLogistic>) list;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mLogisticDetail.isEmpty()) {
                            mTvTitle.setText(mTranslatesString.getToast_noexpress());
                        }
                        mLogisticAdapter.setData(mLogisticDetail);
                        mListLogistic.setAdapter(mLogisticAdapter);
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
