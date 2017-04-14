package com.example.b2c.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.Configs;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.ProtocolInfo;
import com.example.b2c.widget.util.Utils;

/**
 * 买家、卖家查看用户协议
 */
public class ProtocolActivity extends TempBaseActivity {
    private  String  userProtocolCode = "UserRegProtolCode";
    private  String  sellerRegProtolCode = "SellerRegProtolCode";
    private  String  protocol;
    private TextView tv_protocol_title;
    private TextView tv_protocol_content;
    private int type;

    @Override
    public int getRootId() {
        return R.layout.activity_protocol;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
        initData();
    }

    private void initView() {
        type = getIntent().getIntExtra("type", Configs.USER_TYPE.BUYER);
        if(type == Configs.USER_TYPE.BUYER){
            protocol = userProtocolCode;
        }else {
            protocol = sellerRegProtolCode;
        }
        tv_protocol_title = (TextView) findViewById(R.id.tv_protocol_title);
        tv_protocol_content = (TextView) findViewById(R.id.tv_protocol_content);

    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_userprotocol());
    }

    private void initData() {
        showLoading();
        //获取注册协议
        rdm.getProtocolInfo(protocol);
        rdm.mOneDataListener = new OneDataListener<ProtocolInfo>() {
            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }

            @Override
            public void onSuccess(final ProtocolInfo protocolInfo, String successInfo) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initUIAfterGetRemoteData(protocolInfo);
                    }
                });

            }

            @Override
            public void onError(int errorNO,String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }
        };

    }

    private void initUIAfterGetRemoteData(ProtocolInfo protocolInfo) {
        tv_protocol_title.setText(Utils.cutNull(protocolInfo.getTitle()));
        tv_protocol_content.setText(Utils.cutNull("     "+protocolInfo.getText()));
    }
}
