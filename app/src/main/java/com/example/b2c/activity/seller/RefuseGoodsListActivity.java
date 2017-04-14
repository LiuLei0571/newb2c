package com.example.b2c.activity.seller;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.fragment.seller.RefuseAllFragment;
import com.example.b2c.fragment.seller.RefuseWaitingFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：
 * Created by milk on 16/10/30.
 * 邮箱：649444395@qq.com
 */

public class RefuseGoodsListActivity extends TempBaseActivity implements RefuseAllFragment.OnAllListCount, RefuseWaitingFragment.OnWaitingListCount {
    private final HomePage[] fragmentPage = new HomePage[]{
            new HomePage("待处理", RefuseWaitingFragment.class),
            new HomePage("已处理", RefuseAllFragment.class),
    };
    @Bind(R.id.tv_waiting_process)
    TextView mTvWaitingProcess;
    @Bind(R.id.tv_all)
    TextView mTvAll;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_refuse_goods;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            setTitle(mTranslatesString.getSeller_homerefuse());
            setCurrentTab(0,fragmentPage);
            mTvWaitingProcess.setTextColor(Color.RED);
            mTvAll.setTextColor(Color.BLACK);
        }
        mTvWaitingProcess.setText(mTranslatesString.getSeller_waiteorder());
        mTvAll.setText(mTranslatesString.getSeller_refusing());
    }

    @OnClick({R.id.tv_waiting_process, R.id.tv_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_waiting_process:
                setCurrentTab(0,fragmentPage);
                mTvWaitingProcess.setTextColor(Color.RED);
                mTvAll.setTextColor(Color.BLACK);

                break;
            case R.id.tv_all:
                setCurrentTab(1,fragmentPage);
                mTvAll.setTextColor(Color.RED);
                mTvWaitingProcess.setTextColor(Color.BLACK);

                break;
        }
    }

    @Override
    public void allCount(int watingCount) {
        //已处理
        mTvAll.setText(mTranslatesString.getSeller_refusing() + "(" + watingCount + ")");
    }

    @Override
    public void watingCount(int watingCount) {
        //待处理
        mTvWaitingProcess.setText(mTranslatesString.getSeller_waiteorder() + "(" + watingCount + ")");
    }

}
