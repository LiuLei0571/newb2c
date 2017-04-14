package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.OrderDetailSampleAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.OrderDetailListener;
import com.example.b2c.net.response.OrderDetailResult;
import com.example.b2c.widget.util.Utils;

import org.apache.http.util.TextUtils;

/**
 * 订单详情页面
 */
public class OrderDetailActivity extends TempBaseActivity {
    private ListView lv_order_detail;
    private TextView tv_total_cost, tv_delivery, tv_owner,
            tv_address, tv_mobile, tv_order_code, tv_create_time, tv_pay_time;
    private TextView tv_order_detail_deliveryfee,
            tv_order_detail_realpay, tv_order_detail_shopname,tv_order_detail_seller_id,
            tv_order_detail_receiveman, tv_order_detail_recv_address,
            tv_order_detail_phone, tv_order_detail_ordercode,
            tv_order_detail_createtime, tv_order_detail_paytime;
    private LinearLayout ll_pay;
    private TextView tv_detail_payer_lb,tv_detail_payer,tv_pay_address_lb,tv_pay_address,
            tv_pay_phone_lb,tv_pay_mobile,tv_delivery_no_lb,tv_delivery_no;
    private OrderDetailSampleAdapter adapter;
    private int orderId;

    @Override
    public int getRootId() {
        return R.layout.order_detail_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        orderId = bundle.getInt("orderId");
        setTitle(mTranslatesString.getCommon_orderdetail());
//        addImage(R.drawable.ic_message, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //http://115.29.167.40:81/chat/chatWithSeller/1?userId=156&token=d0ca2afc21076409a20134216cb5c721&userType=0&loginName=maijia&plat=android
//                Intent intent = new Intent(OrderDetailActivity.this, WebViewActivity.class);
//                intent.putExtra("url", "http://115.29.167.40:81/chat/chatWithSeller/" + orderId + "?userId=" + UserHelper.getBuyerId() + "&token=" + UserHelper.getBuyertoken() + "&userType=" + "0" + "&loginName=" + UserHelper.getBuyerName() + "&plat=android&sellerId");
//                startActivity(intent);
//            }
//        });
        initView();
        initData();
    }

    public void initView() {
        lv_order_detail = (ListView) findViewById(R.id.lv_order_detail);
        tv_total_cost = (TextView) findViewById(R.id.tv_order_detail_total_cost);
        tv_delivery = (TextView) findViewById(R.id.tv_order_detail_delivery_fee);

        tv_owner = (TextView) findViewById(R.id.tv_order_detail_owner);
        tv_address = (TextView) findViewById(R.id.tv_order_detail_address);
        tv_mobile = (TextView) findViewById(R.id.tv_order_detail_mobile);

        tv_order_code = (TextView) findViewById(R.id.tv_order_detail_order_id);
        tv_create_time = (TextView) findViewById(R.id.tv_order_detail_create_time);
        tv_pay_time = (TextView) findViewById(R.id.tv_order_detail_pay_time);

        tv_order_detail_deliveryfee = (TextView) findViewById(R.id.tv_order_detail_deliveryfee);
        tv_order_detail_realpay = (TextView) findViewById(R.id.tv_order_detail_realpay);
        tv_order_detail_shopname = (TextView) findViewById(R.id.tv_order_detail_shopname);
        tv_order_detail_seller_id = (TextView) findViewById(R.id.tv_order_detail_seller_id);
        tv_order_detail_receiveman = (TextView) findViewById(R.id.tv_order_detail_receiveman);
        tv_order_detail_recv_address = (TextView) findViewById(R.id.tv_order_detail_recv_address);
        tv_order_detail_phone = (TextView) findViewById(R.id.tv_order_detail_phone);
        tv_order_detail_ordercode = (TextView) findViewById(R.id.tv_order_detail_ordercode);
        tv_order_detail_createtime = (TextView) findViewById(R.id.tv_order_detail_createtime);
        tv_order_detail_paytime = (TextView) findViewById(R.id.tv_order_detail_paytime);

        ll_pay = (LinearLayout) findViewById(R.id.ll_pay);

        tv_detail_payer_lb = (TextView) findViewById(R.id.tv_detail_payer_lb);
        tv_detail_payer = (TextView) findViewById(R.id.tv_detail_payer);
        tv_pay_address_lb = (TextView) findViewById(R.id.tv_pay_address_lb);
        tv_pay_address = (TextView) findViewById(R.id.tv_pay_address);
        tv_pay_phone_lb = (TextView) findViewById(R.id.tv_pay_phone_lb);
        tv_pay_mobile = (TextView) findViewById(R.id.tv_pay_mobile);
        tv_delivery_no_lb = (TextView) findViewById(R.id.tv_delivery_no_lb);
        tv_delivery_no = (TextView) findViewById(R.id.tv_delivery_no);
        initText();
    }

    public void initText() {
        setTitle(mTranslatesString
                .getCommon_orderdetail());
        tv_order_detail_deliveryfee.setText(mTranslatesString
                .getCommon_freight());
//        tv_order_detail_realpay.setText(mTranslatesString.getCommon_realpay());
        tv_order_detail_realpay.setText(mTranslatesString
                .getCommon_sampletotalprice());
        tv_order_detail_shopname.setText(mTranslatesString
                .getCommon_shop());
        tv_order_detail_receiveman.setText(mTranslatesString
                .getCommon_receiveman());
        tv_order_detail_recv_address.setText(mTranslatesString
                .getCommon_receivelocation());
        tv_order_detail_phone.setText(mTranslatesString.getCommon_tellphone());


        tv_order_detail_ordercode.setText(mTranslatesString
                .getCommon_ordernumber());
        tv_order_detail_createtime.setText(mTranslatesString
                .getCommon_createtime());
        tv_order_detail_paytime.setText(mTranslatesString
                .getCommon_paytime());

        tv_detail_payer_lb.setText(mTranslatesString.getCommon_cashman());
        tv_pay_address_lb.setText(mTranslatesString.getConnom_shoukuandizhi());
        tv_pay_phone_lb.setText(mTranslatesString.getCommon_tellphone());
        tv_delivery_no_lb.setText(mTranslatesString.getExpress_no());
    }

    public void initData() {
        showLoading();
        rdm.GetOrderDetail(isLogin, userId, token, orderId);
        rdm.orderdetailListener = new OrderDetailListener() {
            @Override
            public void onSuccess(OrderDetailResult result) {
                initSurface(result);
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

    public void initSurface(final OrderDetailResult result) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                tv_total_cost.setText(Configs.CURRENCY_UNIT + Utils.cutNullWithZero(result.getTotalPrice()));
                tv_delivery.setText(Configs.CURRENCY_UNIT + Utils.cutNullWithZero(result.getDeliveryFee()));
                tv_order_detail_seller_id.setText(result.getShopName());

                tv_owner.setText(Utils.cutNull(result.getReceiveFirstName())+" "+Utils.cutNull(result.getReceiveLastName()));
                tv_address.setText(result.getReceiveAddress());
                tv_mobile.setText(result.getReceiveMobile());
                tv_order_code.setText(result.getOrderCode());
                tv_delivery_no.setText(Utils.cutNull(result.getDeliveryNo()));
                tv_create_time.setText(result.getCreateTime());
                tv_pay_time.setText(result.getPayTime() + "");
                adapter = new OrderDetailSampleAdapter(OrderDetailActivity.this, result.getOrderDetailList());
                lv_order_detail.setAdapter(adapter);
                setListViewHeightBasedOnChildren(lv_order_detail);
                if(!TextUtils.isBlank(result.getPayFirstName())&&!TextUtils.isBlank(result.getPayLastName())){
                    ll_pay.setVisibility(View.VISIBLE);
                    tv_detail_payer.setText(Utils.cutNull(result.getPayFirstName()+" "+Utils.cutNull(result.getPayLastName())));
                    tv_pay_address.setText(result.getPayAddress());
                    tv_pay_mobile.setText(result.getPayMobile());
                }else {
                    ll_pay.setVisibility(View.GONE);
                }
            }
        });

    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}
