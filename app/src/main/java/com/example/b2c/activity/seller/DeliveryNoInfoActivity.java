package com.example.b2c.activity.seller;

import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UIHelper;
import com.example.b2c.net.listener.base.NoDataListener;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.staff.DeliveryInfoResult;
import com.example.b2c.widget.SimpleTextWatcher;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：
 * Created by milk on 16/11/18.
 * 邮箱：649444395@qq.com
 */

public class DeliveryNoInfoActivity extends TempBaseActivity {
    @Bind(R.id.tv_express_id)
    TextView mTvExpressId;
    @Bind(R.id.tv_order_no)
    EditText mTvOrderNo;
    @Bind(R.id.tv_address)
    EditText mTvAddress;
    @Bind(R.id.btn_submit)
    Button mBtnSubmit;
    @Bind(R.id.tv_title_delivery_no)
    TextView tvTitleDeliveryNo;
    @Bind(R.id.tv_title_order_no)
    TextView tvTitleOrderNo;
    @Bind(R.id.tv_title_order_address)
    TextView tvTitleOrderAddress;
    private DeliveryInfoResult mInfoResult;

    @Override
    public int getRootId() {
        return R.layout.activity_staff_delivery_info;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getOrder_message());
        initText();
        String id = getIntent().getStringExtra("deliveryId");
        showLoading();
        UIHelper.setClickable(mBtnSubmit, mTvOrderNo, mTvAddress);
        mTvAddress.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                UIHelper.setClickable(mBtnSubmit, mTvOrderNo);
            }
        });
        mTvExpressId.setText(id);
        mLogisticsDataConnection.getDeliverInfoRequest(ConstantS.BASE_URL + "staff/deliveryNoInfo.htm", id);
        mLogisticsDataConnection.mOneDataListener = new OneDataListener<DeliveryInfoResult>() {

            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }

            @Override
            public void onSuccess(final DeliveryInfoResult data, String successInfo) {
                if (data != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mInfoResult = data;
                            mTvExpressId.setText(data.getDeliveryNo());
                            mTvOrderNo.setText(data.getOrderCode());
                            if (data.getIsFirst() == 1) {
                                mBtnSubmit.setText(mTranslatesString.getCommon_recive());
                            } else if (data.getIsFirst() == 0) {
                                mBtnSubmit.setText(mTranslatesString.getCommon_receipt());
                            }
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

    private void initText() {
        mTvOrderNo.setHint(mTranslatesString.getCommon_pleaseinputorderno());
        mTvAddress.setHint(mTranslatesString.getCourier_inputaddress());
        tvTitleDeliveryNo.setText(mTranslatesString.getExpress_no());
        tvTitleOrderNo.setText(mTranslatesString.getOrder_no());
        tvTitleOrderAddress.setText(mTranslatesString.getCommon_receivelocation());
    }

    @OnClick(R.id.btn_submit)
    public void onClick() {
        showLoading();
        if (mInfoResult.getIsFirst() == 1) {
            mLogisticsDataConnection.submitDeliverInfoRequest(ConstantS.BASE_URL + "express/updateOrder.htm", mTvOrderNo.getText().toString(), mTvExpressId.getText().toString(), mTvAddress.getText().toString(), 1);
        } else if (mInfoResult.getIsFirst() == 0) {
            mLogisticsDataConnection.submitDeliverInfoRequest(ConstantS.BASE_URL + "express/updateOrder.htm", mTvOrderNo.getText().toString(), mTvExpressId.getText().toString(), mTvAddress.getText().toString(), 0);
        }
        mLogisticsDataConnection.mNodataListener = new NoDataListener() {
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
    }
}
