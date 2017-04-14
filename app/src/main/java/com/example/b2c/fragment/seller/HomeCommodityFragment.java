package com.example.b2c.fragment.seller;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.widget.util.Utils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：
 * Created by milk on 16/10/27.
 * 邮箱：649444395@qq.com
 */

public class HomeCommodityFragment extends TempleBaseFragment {
    private final HomePage[] fragmentPage = new HomePage[]{
            new HomePage("在售商品", SellingFragment.class),
            new HomePage("仓库中", DepotFragment.class),
    };
    @Bind(R.id.tv_waiting_process)
    TextView mTvSelling;
    @Bind(R.id.tv_all)
    TextView mTvDepot;


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_seller_home_commodity;
    }

    @Override
    protected void initView(View rootView) {
        setChildCurrentTab(0, fragmentPage);
        mTvDepot.setTextColor(Color.BLACK);
        mTvSelling.setTextColor(Color.RED);
        mTvDepot.setText(Utils.cutNull(mTranslatesString.getGoods_depot()));
        mTvSelling.setText(Utils.cutNull(mTranslatesString.getGoods_selling()));
    }


    @OnClick({R.id.tv_waiting_process, R.id.tv_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_waiting_process:
                setChildCurrentTab(0, fragmentPage);
                mTvDepot.setTextColor(Color.BLACK);
                mTvSelling.setTextColor(Color.RED);
                break;
            case R.id.tv_all:
                setChildCurrentTab(1, fragmentPage);
                mTvDepot.setTextColor(Color.RED);
                mTvSelling.setTextColor(Color.BLACK);

                break;
        }
    }

}
