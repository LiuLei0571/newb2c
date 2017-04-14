package com.example.b2c.activity.seller;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.widget.EditTextWithDelete;

import org.apache.http.util.TextUtils;

import java.util.HashMap;
import java.util.Map;



/**
 * 卖家提交退货的反馈信息
 */
public class HandleReturnActivity extends TempBaseActivity implements View.OnClickListener {

    private int function,id;
    private EditTextWithDelete et_text;
    private TextView tv_submit;

    @Override
    public int getRootId() {
        return R.layout.activity_handle_return;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
    }
    private void initText() {
        setTitle(mTranslatesString.getCommon_inputfeedback());
        et_text.setHint(mTranslatesString.getCommon_feedback());
        tv_submit.setText(mTranslatesString.getCommon_submit());
    }

    private void initView() {
        id = getIntent().getIntExtra("id",-1);
        function = getIntent().getIntExtra("function",0);
        et_text = (EditTextWithDelete) findViewById(R.id.et_text);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_submit:
                submitInfo();
                break;
        }

    }

    /**
     * 卖家提交反馈信息
     */
    private void submitInfo() {
        String feedback = et_text.getText().toString();
        if(TextUtils.isEmpty(feedback)){
            ToastHelper.makeToast(mTranslatesString.getCommon_feedback());
            return;
        }
        Map map = new HashMap();
        map.put("id", id);
        map.put("function", function);
        map.put("feedback", feedback);
        showLoading();
        sellerRdm.updateRefund(map);
        sellerRdm.responseListener = new ResponseListener() {
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
