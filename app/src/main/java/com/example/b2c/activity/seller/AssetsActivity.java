package com.example.b2c.activity.seller;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.listener.base.TwoStringListener;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：
 * Created by milk on 16/10/27.
 * 邮箱：649444395@qq.com
 */


public class AssetsActivity extends TempBaseActivity {
    @Bind(R.id.tv_amount)
    TextView mTvAmount;
    @Bind(R.id.lyt_withdrawal)
    LinearLayout mLytWithdrawal;
    @Bind(R.id.tv_trading)
    TextView mTvTrading;
    @Bind(R.id.tv_withdraw)
    TextView mTvWithdraw;
    @Bind(R.id.tv_bank_card)
    TextView mTvBankCard;
    @Bind(R.id.tv_card_num)
    TextView mTvCardNum;
    @Bind(R.id.btn_withdraw)
    TextView mBtnWithdraw;
    @Bind(R.id.tv_asses_can)
    TextView tvAssesCan;


    @Override
    public int getRootId() {
        return R.layout.activity_seller_assets;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getMin_assets());
        initText();
        initView();
    }

    private void initText() {
        tvAssesCan.setText(mTranslatesString.getAssets_can());
        mBtnWithdraw.setText(mTranslatesString.getAssets_apply());
        mTvTrading.setText(mTranslatesString.getAssets_trading());
        mTvWithdraw.setText(mTranslatesString.getAssets_withdraw());
        mTvCardNum.setText(mTranslatesString.getAssets_notbind());
        mTvBankCard.setText(mTranslatesString.getAssets_bindcar());
    }

    private void initView() {
        showLoading();
        sellerRdm.getAssetsRequest(ConstantS.BASE_URL + "user/getMemberFinanceInfo.htm");
        sellerRdm.mTwoStringListener = new TwoStringListener() {
            @Override
            public void onSuccess(final String errorInfo, final String error2) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvAmount.setText(errorInfo);
                        mTvCardNum.setText(error2);
                        if (errorInfo.equals("0") || errorInfo.equals("0.0") || errorInfo.equals("0.00")) {
                            mBtnWithdraw.setTextColor(getResources().getColor(R.color.text_gray));
                            mBtnWithdraw.setClickable(false);
                        }
                    }
                });
            }

            @Override
            public void onError(int errorNO, final String errorInfo) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AssetsActivity.this, errorInfo, Toast.LENGTH_SHORT).show();
                    }
                });
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

    @OnClick({R.id.lyt_withdrawal, R.id.tv_bank_card, R.id.tv_withdraw, R.id.tv_trading, R.id.btn_withdraw})
    protected void getOnClick(View view) {
        switch (view.getId()) {

            case R.id.tv_bank_card:

                break;
            case R.id.tv_withdraw:
                getIntent(AssetsActivity.this, WithdrawalDetailActivity.class);
                break;
            case R.id.tv_trading:
                getIntent(this, TradingActivity.class);
                break;
            case R.id.btn_withdraw:
                showLoading();
                sellerRdm.getAssetsDeailRequest(ConstantS.BASE_URL + "withdraw/insert/doWithdraw.htm", mTvAmount.getText().toString());
                sellerRdm.mResponseListener = new ResponseListener() {
                    @Override
                    public void onSuccess(String errorInfo) {
                        ToastHelper.makeErrorToast(errorInfo);
                        finish();
                    }
                    @Override
                    public void onError(int errorNO, final String errorInfo) {
                        ToastHelper.makeErrorToast(errorInfo);
                    }

                    @Override
                    public void onFinish() {
                        dissLoading();
                    }

                    @Override
                    public void onLose() {
                        dissLoading();
                    }

                };
                break;
            default:
                break;
        }
    }
}
