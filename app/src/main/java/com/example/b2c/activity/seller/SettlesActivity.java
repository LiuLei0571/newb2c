package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.Configs;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.seller.FinaceCell;
import com.example.b2c.net.response.seller.FinaceCellList;
import com.example.b2c.widget.SettingItemView3;

import java.util.List;

import butterknife.OnClick;

/**
 * 结算中心
 */
public class SettlesActivity extends TempBaseActivity {

    public static  final  int WAIT_SETTLE = 1;//待结算
    public static  final  int DELAY_SETTLE = 2;//逾期结算
    public static  final  int YET_SETTLE = 3;//已结算

    private SettingItemView3 siv_settle_no;
    private SettingItemView3 siv_settle_delay;
    private SettingItemView3 siv_settle_yet;
    private TextView tv_cash_label;
    private TextView tv_cash;

    private FinaceCellList  finaceCellList;

    @Override
    public int getRootId() {
        return R.layout.activity_settles;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
        initData();

    }

    private void initData() {
        showLoading();
        sellerRdm.getFinaceInfo();
        sellerRdm.mOneDataListener = new OneDataListener<FinaceCellList>() {
            @Override
            public void onSuccess(final FinaceCellList data, String successInfo) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        finaceCellList = data;
                        initUI(data);
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
                ToastHelper.makeErrorToast(net_error);
            }
        };
    }

    private void initUI(FinaceCellList data) {
        List<FinaceCell> list = data.getRows();
        for(FinaceCell fc : list){
            if(fc.getType()== WAIT_SETTLE){
                tv_cash.setText(fc.getMoney());
                return;
            }
        }
    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_settlecenter());
        tv_cash_label.setText(mTranslatesString.getCommon_daijiesuanMoney()+ Configs.CURRENCY_UNIT);
        siv_settle_no.setTv_text(mTranslatesString.getCommon_tvdaijiesuan());
        siv_settle_delay.setTv_text(mTranslatesString.getCommon_yuqijiesuan());
        siv_settle_yet.setTv_text(mTranslatesString.getCommon_yijiesuan());
    }

    private void initView() {
        siv_settle_no = (SettingItemView3) findViewById(R.id.siv_settle_no);
        siv_settle_delay = (SettingItemView3) findViewById(R.id.siv_settle_delay);
        siv_settle_yet = (SettingItemView3) findViewById(R.id.siv_settle_yet);
        tv_cash_label = (TextView) findViewById(R.id.tv_cash_label);
        tv_cash = (TextView) findViewById(R.id.tv_cash);
    }

    @OnClick({R.id.siv_settle_no, R.id.siv_settle_delay, R.id.siv_settle_yet})
    protected void OnClick(View view) {
        switch (view.getId()) {
            case R.id.siv_settle_no:
                Intent i = new Intent(SettlesActivity.this,SettleDetailActivity.class);
                i.putExtra("type",WAIT_SETTLE);
                startActivity(i);
                break;
            case R.id.siv_settle_delay:
                Intent ii = new Intent(SettlesActivity.this,SettleDetailActivity.class);
                ii.putExtra("type",DELAY_SETTLE);
                startActivity(ii);
                break;
            case R.id.siv_settle_yet:
                Intent iii = new Intent(SettlesActivity.this,SettleDetailActivity.class);
                iii.putExtra("type",YET_SETTLE);
                startActivity(iii);
                break;
            default:
                break;
        }

    }
}
