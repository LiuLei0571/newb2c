package com.example.b2c.activity.seller;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.fragment.seller.ReturnNoDoFragment;
import com.example.b2c.fragment.seller.RuturnYesDoFragment;

import butterknife.Bind;
import butterknife.OnClick;
/**
 * 卖家订单页面 退货
 */
public class ReturnGoodsActivity extends TempBaseActivity implements ReturnNoDoFragment.OnNoListCount, RuturnYesDoFragment.OnYesListCount {

    private final HomePage[] fragmentPage = new HomePage[]{
            new HomePage("待处理", ReturnNoDoFragment.class),
            new HomePage("已处理", RuturnYesDoFragment.class),
    };
    @Bind(R.id.tv_wait)
    TextView mTvWait;
    @Bind(R.id.tv_yet)
    TextView mTvYet;
    @Override
    public int getRootId() {
        return R.layout.activity_return_goods;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            setTitle(mTranslatesString.getCommon_returnorder());
            setCurrentTab(0,fragmentPage);
            mTvWait.setTextColor(Color.RED);
            mTvYet.setTextColor(Color.BLACK);
        }
        mTvWait.setText(mTranslatesString.getSeller_waiteorder());
        mTvYet.setText(mTranslatesString.getSeller_refusing());
    }

    @OnClick({R.id.tv_wait, R.id.tv_yet})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_wait:
                setCurrentTab(0,fragmentPage);
                mTvWait.setTextColor(Color.RED);
                mTvYet.setTextColor(Color.BLACK);

                break;
            case R.id.tv_yet:
                setCurrentTab(1,fragmentPage);
                mTvYet.setTextColor(Color.RED);
                mTvWait.setTextColor(Color.BLACK);

                break;
        }
    }


    @Override
    public void allNoCount(int count) {
        mTvWait.setText( mTranslatesString.getSeller_waiteorder()+ "(" + count + ")");
    }

    @Override
    public void allYesCount(int count) {
        mTvYet.setText( mTranslatesString.getSeller_refusing()+ "(" + count + ")");
    }
}
