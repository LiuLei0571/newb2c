package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.seller.LogisticContactInfo;
import com.example.b2c.net.response.seller.LogisticDetail;
import com.example.b2c.net.response.seller.LogisticDetailFarther;
import com.example.b2c.widget.SettingItemView3;
import com.example.b2c.widget.util.Utils;

import org.apache.http.util.TextUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：
 * Created by milk on 16/10/28.
 * 邮箱：649444395@qq.com
 */

public class CourierDetailActivity extends TempBaseActivity {
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_message)
    TextView mTvMessage;
    @Bind(R.id.siv_link_info)
    SettingItemView3 siv_link_info;
    @Bind(R.id.siv_deliver_module)
    SettingItemView3 siv_deliver_module;
    @Bind(R.id.btn_submit)
    Button btn_submit;
    private LogisticDetail mmLogisticDetail;
    private LogisticDetail mLogisticDetail;
    private LogisticContactInfo mLogisticContactInfo;
    @Override
    public int getRootId() {
        return R.layout.activity_seller_courier_detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getExpress_detail());
        mmLogisticDetail = (LogisticDetail) getIntent().getSerializableExtra("logistic");
        initView();
        initText();
        initData();
    }



    private void initData() {
        sellerRdm.getDeliverInfo(mmLogisticDetail.getId(), UserHelper.isSellerLogin(),UserHelper.getSellerID(),UserHelper.getSellerToken());
        sellerRdm.mOneDataListener=new OneDataListener<LogisticDetailFarther>() {
            @Override
            public void onSuccess(LogisticDetailFarther data, String successInfo) {
//                ToastHelper.makeToast(successInfo);
                mLogisticDetail = data.getLogisticDetail();
                mLogisticContactInfo = data.getOgisticContactInfo();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initUI();
                    }
                });

            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(net_error);
            }
        };

    }
    private void initText() {
        siv_link_info.setTv_text(mTranslatesString.getCommon_linkinfo());
        siv_deliver_module.setTv_text(mTranslatesString.getCommon_yunfeimuban());
    }
    private void initUI() {
        setTitle(mLogisticDetail.getDeliveryName());
        mTvTitle.setText(Utils.cutNull(mLogisticDetail.getCompanyName()));
        if(TextUtils.isBlank(mLogisticDetail.getIntroduction())){
            mTvMessage.setText(mTranslatesString.getCommon_nodescribeinfo());
        }else{
            mTvMessage.setText(mLogisticDetail.getIntroduction());
        }
        if (mLogisticDetail.isCorperateStatus()) {
            btn_submit.setText(mTranslatesString.getCancel_express());
        } else {
            btn_submit.setText(mTranslatesString.getCommon_setcorperatedeliver());
        }

    }

    private void initView() {

    }

    @OnClick({R.id.btn_submit,R.id.siv_link_info,R.id.siv_deliver_module})
    protected void getOnClick(View view) {
        switch (view.getId()){
            case R.id.btn_submit:
                if (!mLogisticDetail.isCorperateStatus()) {
                    sellerRdm.getLogisticDetail(ConstantS.BASE_URL + "deliver/company/update.htm", mLogisticDetail.getId(), 0);
                    sellerRdm.responseListener = new ResponseListener() {
                        @Override
                        public void onSuccess(final String errorInfo) {
                            ToastHelper.makeToast(errorInfo);
                            mLogisticDetail.setCorperateStatus(true);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    btn_submit.setText(mTranslatesString.getCancel_express());
                                }
                            });

                        }
                        @Override
                        public void onError(int errorNO, final String errorInfo) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CourierDetailActivity.this, errorInfo, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onFinish() {

                        }

                        @Override
                        public void onLose() {
                            ToastHelper.makeErrorToast(request_failure);
                        }
                    };

                } else {
                    sellerRdm.getBaseRequest(ConstantS.BASE_URL + "deliver/company/delete/" + mLogisticDetail.getId() + ".htm");
                    sellerRdm.mResponseListener = new ResponseListener() {
                        @Override
                        public void onSuccess(String errorInfo) {
                            ToastHelper.makeToast(errorInfo);
                            mLogisticDetail.setCorperateStatus(false);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    btn_submit.setText(mTranslatesString.getCommon_setcorperatedeliver());
                                }
                            });
                        }
                        @Override
                        public void onError(int errorNO, final String errorInfo) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CourierDetailActivity.this, errorInfo, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        @Override
                        public void onFinish() {

                        }
                        @Override
                        public void onLose() {
                            ToastHelper.makeErrorToast(request_failure);
                        }
                    };
                }
                break;
            case R.id.siv_link_info:
                Intent   i_linkinfo = new Intent(CourierDetailActivity.this,CourierContactInfoActivity.class);
                i_linkinfo.putExtra("contactInfo",mLogisticContactInfo);
                startActivity(i_linkinfo);
                break;
            case R.id.siv_deliver_module:
                Intent   i_templet_list = new Intent(CourierDetailActivity.this,CourierTempletListActivity.class);
                i_templet_list.putExtra("id",mLogisticDetail.getId());
                startActivity(i_templet_list);
                break;
            default:
                break;


        }

    }
}
