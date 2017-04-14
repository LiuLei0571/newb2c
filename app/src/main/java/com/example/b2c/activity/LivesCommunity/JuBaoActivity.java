package com.example.b2c.activity.LivesCommunity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.NoDataListener;

import java.util.HashMap;
import java.util.Map;

public class JuBaoActivity extends TempBaseActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private EditText et_content_message;
    private TextView tv_jubao_zishu;
    private Button btn_lives_tijiao;
    private String id;
    private String tag;

    @Override
    public int getRootId() {
        return R.layout.activity_ju_bao;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        et_content_message = (EditText) findViewById(R.id.et_content_message);
        tv_jubao_zishu = (TextView) findViewById(R.id.tv_jubao_zishu);
        btn_lives_tijiao = (Button) findViewById(R.id.btn_lives_tijiao);
        et_content_message.setHint(mTranslatesString.getCommon_liyou());
        btn_lives_tijiao.setOnClickListener(this);
    }
    int zishu;
    private void initData(){
        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");
        if (tag.equals("jubao")){
            toolbar_title.setText(mTranslatesString.getCommon_jubao());
            id = intent.getStringExtra("id");
        }else{
            toolbar_title.setText(mTranslatesString.getCommon_fankui());
        }

        et_content_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                zishu=et_content_message.getText().length();

            }
            @Override
            public void afterTextChanged(Editable editable) {
                tv_jubao_zishu.setText(zishu+"/100");
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lives_tijiao:
                submit();
                break;
        }
    }
    private void submit() {
        showLoading();
        String message = et_content_message.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            Toast.makeText(this, mTranslatesString.getCommon_reasonnotnull(), Toast.LENGTH_SHORT).show();
            return;
        }
            Map<String, String> map = new HashMap<>();
            String url;
        if (tag.equals("jubao")) {
            map.put("reportReason",message);
            map.put("informationId",id);
            url= ConstantS.BASE_URL+"life/addLifeReport.htm";
        }else{
            map.put("content",message);
            url= ConstantS.BASE_URL+"life/addLifeFeedback.htm";
        }
        mLogisticsDataConnection.jubao(getApplication(),url,map);
        mLogisticsDataConnection.mNodataListener=new NoDataListener() {
            @Override
            public void onSuccess(String success) {
                ToastHelper.makeToast(mTranslatesString.getCommon_infosubmityet());
                finish();
                dissLoading();
            }
            @Override
            public void onError(int errorNo, String errorInfo) {
                ToastHelper.makeToast(errorInfo);
                dissLoading();
            }
            @Override
            public void onFinish() {}
            @Override
            public void onLose() {
                dissLoading();
            }
        };
    }
}
