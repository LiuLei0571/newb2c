package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.fragment.AllOrderFragment;
import com.example.b2c.fragment.RefuseOrderFragment;
import com.example.b2c.fragment.ReturnOrderFragment;
import com.example.b2c.fragment.SuccessOrderFragment;
import com.example.b2c.fragment.WaitingReceivedOrderFragment;
import com.example.b2c.fragment.WaitingSendOrderFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 买家订单页面
 * 用途：
 * Created by milk on 16/11/21.
 * 邮箱：649444395@qq.com
 */
public class OrderActivityNew extends TempBaseActivity {
    final HomePage[] fragmentPage = new HomePage[]{
            new HomePage("全部", AllOrderFragment.class),
            new HomePage("待发货", WaitingSendOrderFragment.class),
            new HomePage("待收货", WaitingReceivedOrderFragment.class),
            new HomePage("已签收", SuccessOrderFragment.class),
            new HomePage("拒收", RefuseOrderFragment.class),
            new HomePage("退货", ReturnOrderFragment.class)
    };
    @Bind(R.id.rb_all)
    RadioButton mRbAll;
    @Bind(R.id.rb_wait_send)
    RadioButton mRbWaitSend;
    @Bind(R.id.rb_wait_recv)
    RadioButton mRbWaitRecv;
    @Bind(R.id.rb_done)
    RadioButton mRbDone;
    @Bind(R.id.rb_reject)
    RadioButton mRbReject;
    @Bind(R.id.rb_return)
    RadioButton mRbReturn;

    @Override
    public int getRootId() {
        return R.layout.activity_buyer_order;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(OrderActivityNew.this, MainActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            setTitle(mTranslatesString.getCommon_myorder());
            setCurrentTab(0,fragmentPage);
        }
        initText();
    }

    private void initText() {
        mRbAll.setText(mTranslatesString.getCommon_all());
        mRbWaitSend.setText(mTranslatesString.getCommon_waitsend());
        mRbWaitRecv.setText(mTranslatesString.getCommon_waitrecv());
//      mRbDone.setText(mTranslatesString.getSeller_homefinish());
        mRbDone.setText(mTranslatesString.getSeller_homereceived());
        mRbReject.setText(mTranslatesString.getCommon_rejectreceive());
        mRbReturn.setText(mTranslatesString.getCommon_returngoods());
    }

    @OnClick({R.id.rb_all, R.id.rb_wait_send, R.id.rb_wait_recv, R.id.rb_done, R.id.rb_reject, R.id.rb_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_all:
                setCurrentTab(0,fragmentPage);
                break;
            case R.id.rb_wait_send:
                setCurrentTab(1,fragmentPage);
                break;
            case R.id.rb_wait_recv:
                setCurrentTab(2,fragmentPage);
                break;
            case R.id.rb_done:
                setCurrentTab(3,fragmentPage);
                break;
            case R.id.rb_reject:
                setCurrentTab(4,fragmentPage);
                break;
            case R.id.rb_return:
                setCurrentTab(5,fragmentPage);
                break;
        }
    }



}
