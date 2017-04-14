package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.OrderCellListAdapter;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.ResponseHanderListener;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.response.OrderCell;
import com.example.b2c.net.util.UIUtils;

import java.util.List;

public class PayActivity extends TempBaseActivity implements OnClickListener {
    private TextView tv_pay_makesureorder;//确认订单
    private ImageView iv_pay_back;//返回
    private ListView list;

    private TextView tv_pay_choosepaytype;//请选择支付方式
    private RadioGroup rg_pay;
    private RadioButton rb_pay_predeposit;

    private Button btn_pay_order;//确认

    private String orderIds;

    private OrderCellListAdapter celladapter;

    @Override
    public int getRootId() {
        return R.layout.pay_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        orderIds = bundle.getString("orderIds");

        initView();
        initData();

        btn_pay_order.setOnClickListener(this);
        iv_pay_back.setOnClickListener(this);

    }

    private void initView() {
        // TODO Auto-generated method stub
        tv_pay_makesureorder = (TextView) findViewById(R.id.tv_pay_makesureorder);
        iv_pay_back = (ImageView) findViewById(R.id.iv_pay_back);
        list = (ListView) findViewById(R.id.list);
        rg_pay = (RadioGroup) findViewById(R.id.rg_pay);
        rb_pay_predeposit = (RadioButton) findViewById(R.id.rb_pay_predeposit);
        tv_pay_choosepaytype = (TextView) findViewById(R.id.tv_pay_choosepaytype);
        btn_pay_order = (Button) findViewById(R.id.btn_pay_order);

        initText();

    }

    private void initText() {
        tv_pay_makesureorder.setText(mTranslatesString.getCommon_payorder());
        rb_pay_predeposit.setText(mTranslatesString.getCommon_predeposits());
        tv_pay_choosepaytype.setText(mTranslatesString.getNotice_chooseordertype());
        btn_pay_order.setText(mTranslatesString.getCommon_makesure());
    }

    public void initData() {
        showProgressDialog(Loading);
        rdm.getOrderCellList(isLogin, userId, token, orderIds);
        rdm.orderCellListListener = new ResponseHanderListener<OrderCell>() {
            @Override
            public void onSuccess(final List<OrderCell> orderCellList) {
                dismissProgressDialog();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        celladapter = new OrderCellListAdapter(PayActivity.this, orderCellList);
                        list.setAdapter(celladapter);
                    }
                });

            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                dismissProgressDialog();
                showAlertDialog(errorInfo);

            }

            @Override
            public void lose() {
                dismissProgressDialog();
                showAlertDialog(request_failure);
            }
        };
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_pay_order:
                updateOrderStatus();
                break;
            case R.id.iv_pay_back:
                setResult(-1);
                finish();
                break;
            default:
                break;
        }


    }

    private void updateOrderStatus() {
        showProgressDialog(Loading);
        //1：已付款 2：确认收货 3：取消订单
        rdm.updateOrderStatus(isLogin, userId, token, orderIds, "1");
        rdm.responseListener = new ResponseListener() {

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);

            }

            @Override
            public void onSuccess(String errorInfo) {
                UIUtils.showToast(PayActivity.this, mTranslatesString.getCommon_paysuccess());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent i_order = new Intent(PayActivity.this, OrderActivityNew.class);
                        startActivity(i_order);
                    }
                });
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                showAlertDialog(errorInfo);
            }

        };
    }


}
