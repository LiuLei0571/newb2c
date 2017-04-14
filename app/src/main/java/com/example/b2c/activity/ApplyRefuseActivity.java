package com.example.b2c.activity;

import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UIHelper;
import com.example.b2c.net.listener.base.NoDataListener;
import com.example.b2c.widget.SimpleTextWatcher;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：
 * Created by milk on 16/11/22.
 * 邮箱：649444395@qq.com
 */

public class ApplyRefuseActivity extends TempBaseActivity {
    @Bind(R.id.from_advise)
    EditText mFromAdvise;
    @Bind(R.id.submit_advise)
    Button mSubmitAdvise;
    private int orderId;

    @Override
    public int getRootId() {
        return R.layout.activity_apply_refuse;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getCommon_rejectreceivegoods());
        orderId = getIntent().getIntExtra("orderId", -1);
        UIHelper.setClickable(mSubmitAdvise, mFromAdvise);
        mFromAdvise.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                UIHelper.setClickable(mSubmitAdvise, mFromAdvise);
            }
        });
    }

    @OnClick(R.id.submit_advise)
    public void onClick() {
        showLoading();
        rdm.getBuyerRefuseRequest(ConstantS.BASE_URL + "trade/buyer/rejectOrder.htm", orderId, mFromAdvise.getText().toString().trim());
        rdm.mNodataListener = new NoDataListener() {
            @Override
            public void onSuccess(String success) {
                ToastHelper.makeToast(success);
            }

            @Override
            public void onError(int errorNo,String errorInfo) {
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
