package com.example.b2c.activity.logistic;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.logistics.LanguageAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.helper.DialogUtils;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.SysmHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.LanguageListener;
import com.example.b2c.net.listener.TranslatesListener;
import com.example.b2c.net.response.common.LanguageResult;
import com.example.b2c.net.response.translate.Translates;
import com.example.b2c.widget.util.DialogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class LanguageSettingActivity extends TempBaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.lt_language)
    ListView mLvLanguage;
    private LanguageAdapter mAdapter;
    private List<LanguageResult> mList = new ArrayList<>();
    private String mLanguage;

    @Override
    public int getRootId() {
        return R.layout.activity_language_setting;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    public void initData() {
        showLoading();
        rdm.getLanguageRequest();
        rdm.mLanguageListener = new LanguageListener() {
            @Override
            public void onSuccess(final List<LanguageResult> mLists, String errorInfo) {
                runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                                      if (mList != null) {
                                          mList = mLists;
                                          mAdapter.setData(mList);
                                          int position = 0;
                                          for (LanguageResult mData : mLists) {
                                              position++;
                                              if (SPHelper.getString(Configs.LANGUAGE).equals(mData.getCode())) {
                                                  mAdapter.setSellextItemPosition(position - 1);
                                                  break;
                                              }
                                          }
                                      }
                                  }
                              }
                );

            }

            @Override
            public void onFailure(String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }

            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onLose() {

            }
        };
    }

    public void initView() {
        final DialogUtils dialog=new DialogUtils(LanguageSettingActivity.this);
        setTitle(mTranslatesString.getCommon_tongyongsetting());
        mAdapter = new LanguageAdapter(this);
        mLvLanguage.setAdapter(mAdapter);
        addText(mTranslatesString.getCommon_sure(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.putString(Configs.LANGUAGE, mLanguage);
                dialog.myshowDialog(mTranslatesString.getCommon_chongqi(),mTranslatesString.getCommon_makesure(),mTranslatesString.getNotice_cancel());
                dialog.setMyOnClickListener(new DialogUtils.MyOnClickListener() {
                    @Override
                    public void onZuoClickListener() {
                        SysmHelper.restartApp(LanguageSettingActivity.this,0);
                    }

                    @Override
                    public void onYouClickListener() {
                            dialog.aa.dismiss();
                    }
                });
//                SysmHelper.restartApp(LanguageSettingActivity.this, 1000);
            }
        });
        mLvLanguage.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        mAdapter.setSellextItemPosition(position);
        mLanguage = mAdapter.getItem(position).getCode();
        mAdapter.notifyDataSetChanged();
    }
}
    /**
     * 请求语言接口
     */
//public void alterLanguage(){
//    rdm.GetTranslate(unLogin, userId, token, null);
//    rdm.translatesListener = new TranslatesListener() {
//
//        @Override
//        public void onFinish() {
////            initView();
////            initData();
//        }
//
//        @Override
//        public void onLose() {
//
//        }
//
//        @Override
//        public void onSuccess(Translates translates) {
//            SPHelper.putBean("translate", translates.getMobileStaticTextCode());
//            SPHelper.putBean("options", translates.getOptionList());
//        }
//
//        @Override
//        public void onError(int errorNO, String errorInfo) {
//
//        }
//    };

//    Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case 100:
//                 //获取是从哪个activity跳转过来的
////                    Intent intent = getIntent();
////                    String className = intent.getComponent().getClassName();
////                    Toast.makeText(getApplication(),className,Toast.LENGTH_LONG).show();
//                    finish();
//                    break;
//            }
//        }
//    };


