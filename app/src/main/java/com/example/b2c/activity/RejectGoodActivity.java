package com.example.b2c.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.dialog.DialogHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.response.BuyerOrderList;
import com.example.b2c.widget.EditTextWithDelete;

import org.apache.http.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 买家订单列表
 * 申请拒收    qiyong
 */
public class RejectGoodActivity extends TempBaseActivity implements View.OnClickListener{

    private BuyerOrderList order;
    private EditTextWithDelete et_text;
    private TextView tv_submit;

    @Override
    public int getRootId() {
        return R.layout.activity_reject_good;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_applyreject());
        et_text.setHint(mTranslatesString.getCommon_inputrejectreason());
        tv_submit.setText(mTranslatesString.getCommon_submitapply());
    }

    private void initView() {
        order = (BuyerOrderList) getIntent().getSerializableExtra("order");
        et_text = (EditTextWithDelete) findViewById(R.id.et_text);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_submit:
                DialogHelper.BtnTv deleteBtn =  new DialogHelper.BtnTv(mTranslatesString.getCommon_sure(),new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        rejectGood();

                    }
                });
                DialogHelper.BtnTv cancelBtn =  new DialogHelper.BtnTv(mTranslatesString.getNotice_cancel(),new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                    }
                });
                DialogHelper.showDialog(this,mTranslatesString.getNotice_noticename(),mTranslatesString.getCommon_surerejectorderm(), Gravity.CENTER,deleteBtn,cancelBtn);
                break;
        }
    }
    /**
     * 买家拒收货物
     */
    private void rejectGood() {
        String rejectReason = et_text.getText().toString();
        if(TextUtils.isEmpty(rejectReason)){
            ToastHelper.makeToast(mTranslatesString.getCommon_inputrejectreason());
            return;
        }
        Map map = new HashMap();
        map.put("orderId", order.getOrderId());
        map.put("rejectReason", rejectReason);
        showLoading();
        rdm.rejectGoods(map);
        rdm.mResponseListener = new ResponseListener() {
            @Override
            public void onSuccess(String errorInfo) {
                ToastHelper.makeToast(errorInfo);
                finish();
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
