package com.example.b2c.activity.logistic;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.logistics.BankDetail;
import com.example.b2c.widget.util.Utils;

import butterknife.Bind;

/**
 * 用途：银行详情 * Created by milk on 16/11/29.
 * 邮箱：649444395@qq.com
 */
public class ShowBankDetailActivity extends TempBaseActivity {
    @Bind(R.id.iv_bankcard_detail)
    ImageView mIvBankcardDetail;
    @Bind(R.id.tv_bank_card)
    TextView mTvBankCard;
    @Bind(R.id.tv_bank_account)
    TextView mTvBankAccount;
    @Bind(R.id.tv_bank_name)
    TextView mTvBankName;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_title_message)
    TextView tvTitleMessage;

    @Override
    public int getRootId() {
        return R.layout.activity_logistis_bank;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getLogistic_remitmoneyinfo());
        tvTitleMessage.setText(mTranslatesString.getRemark_message());
        initData();
    }

    private void initData() {
        showLoading();
        mLogisticsDataConnection.getBankDetailRequest(ConstantS.BASE_URL_EXPRESS + "platAccountInfo.htm");
        mLogisticsDataConnection.mOneDataListener = new OneDataListener<BankDetail>() {
            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }

            @Override
            public void onSuccess(final BankDetail data, String successInfo) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvBankCard.setText(mTranslatesString.getLogistic_cardnumber() + " : " + Utils.cutNull(data.getBankCard()));
                        mTvBankAccount.setText(mTranslatesString.getLogistic_accounthead() + ": " + Utils.cutNull(data.getAccountName()));
                        mTvBankName.setText(mTranslatesString.getLogistic_depositbank() + ": " + Utils.cutNull(data.getBankName()));
                        mTvTitle.setText(data.getMessagePhone());
                    }
                });
            }

            @Override
            public void onError(int errorNO,String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }
        };
    }
}
