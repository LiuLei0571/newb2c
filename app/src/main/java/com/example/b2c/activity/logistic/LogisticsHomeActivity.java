package com.example.b2c.activity.logistic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.fragment.ZTHBaseFragment;
import com.example.b2c.fragment.logistics.HomeMinFragment;
import com.example.b2c.fragment.logistics.HomeOrderFragment;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.response.translate.MobileStaticTextCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 用途：物流首页 用户管理
 * Created by milk on 16/11/10.
 * 邮箱：649444395@qq.com
 */

public class LogisticsHomeActivity extends TempBaseActivity {

    private FrameLayout tabcontent;
    private RadioGroup main_tab_group;
    private LinearLayout linearLayout;

    private List<ZTHBaseFragment> listFragment;
    private RadioButton rbt_order;
    private RadioButton rbt_mine;
    private FinshReceiver myIntentFilter;


    @Override
    public int getRootId() {
        return R.layout.activity_logistics_home;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    public void initView() {
        tabcontent = (FrameLayout) findViewById(R.id.tabcontent);
        main_tab_group = (RadioGroup) findViewById(R.id.main_tab_group);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        rbt_order = (RadioButton) findViewById(R.id.rbt_order);
        rbt_mine = (RadioButton) findViewById(R.id.rbt_mine);
        MobileStaticTextCode mTranslatesString = SPHelper.getBean("translate", MobileStaticTextCode.class);
        registerBoradcastReceiver();
        rbt_order.setText(mTranslatesString.getSeller_homemanager());
        rbt_mine.setText(mTranslatesString.getLogistic_accountmanage());
    }

    public void initListener() {
        main_tab_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rbt_order:
                        loagFragment(currentFragment, listFragment.get(0));
                        break;
                    case R.id.rbt_mine:
                        loagFragment(currentFragment, listFragment.get(1));
                        break;
                }
            }
        });
    }

    public void initData() {
        listFragment = new ArrayList<ZTHBaseFragment>();
        listFragment.add(new HomeOrderFragment());
        listFragment.add(new HomeMinFragment());
        loagFragment(null, listFragment.get(0));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myIntentFilter);//注销动态注册的广播

    }

    //定义存储当前位置的变量
    private ZTHBaseFragment currentFragment;

    //点击按钮切换fragment
    private void loagFragment(ZTHBaseFragment from, ZTHBaseFragment to) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (from == null) {
            //第一次进入时添加默认的fragment
            ft.add(R.id.tabcontent, to).commit();
            currentFragment = to;
        } else {

            if (from != to) {
                //如果不相等的时候说明是切换fragment
                if (to.isAdded()) {
                    //没有添加过了就将点击处的fragment替换之前的
                    ft.hide(from).show(to).commit();
                } else {
                    ft.hide(from).add(R.id.tabcontent, to).commit();
                }
                currentFragment = to;
            }
        }
    }

    public void registerBoradcastReceiver() {
        myIntentFilter = new FinshReceiver();
        registerReceiver(myIntentFilter, new IntentFilter("FinishActivity"));
    }

    //广播接收事件
    private class FinshReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        MobileStaticTextCode mTranslatesString = SPHelper.getBean("translate", MobileStaticTextCode.class);

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                ToastHelper.makeToast(mTranslatesString.getCommon_againlogput());
                mExitTime = System.currentTimeMillis();
            } else {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
