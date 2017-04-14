package com.example.b2c.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.MyMessageAdapter;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.MyMessageListener;
import com.example.b2c.net.response.SystemMessageDetail;

import java.util.List;

/**
 * 买家消息列表 qiyong
 */
public class MyMessageActivity extends TempBaseActivity{
    private ListView lv_message;
    private List<SystemMessageDetail> message_list;
    private MyMessageAdapter adapter;
    private int pageNum = 1, isReaded = 0;
    @Override
    public int getRootId() {
        return R.layout.my_message_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    public void initView() {
        lv_message = (ListView) findViewById(R.id.lv_my_message);
        initText();
    }

    public void initText() {
        setTitle(mTranslatesString.getCommon_mymessage());
    }
    public void initData() {
        showLoading();
        rdm.GetMyMessage(isLogin, userId, token, pageNum, isReaded);
        rdm.mymessageListener = new MyMessageListener() {

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }

            @Override
            public void onSuccess(final List<SystemMessageDetail> systemMessageList) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (systemMessageList.isEmpty()) {
                            mEmpty.setVisibility(View.VISIBLE);
                        }
                        setAdapter(systemMessageList);

                    }
                });
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                showAlertDialog(errorInfo);
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }
        };
    }

    public void setAdapter(List<SystemMessageDetail> systemMessageList) {
        message_list = systemMessageList;
        adapter = new MyMessageAdapter(MyMessageActivity.this, message_list);
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                lv_message.setAdapter(adapter);
            }
        });
    }

}
