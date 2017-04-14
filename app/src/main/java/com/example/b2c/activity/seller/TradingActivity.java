package com.example.b2c.activity.seller;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.seller.WithDrawAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.net.listener.base.PageListHasNextListener;
import com.example.b2c.net.response.seller.WithdrawDetail;
import com.example.b2c.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 用途：
 * Created by milk on 16/10/27.
 * 邮箱：649444395@qq.com
 */

public class TradingActivity extends TempBaseActivity {
    WithDrawAdapter mDrawAdapter;
    @Bind(R.id.courier_list)
    ListView mCourierList;
    @Bind(R.id.refresh)
    RefreshLayout mRefresh;
    private boolean hasNext = false;
    private List<WithdrawDetail> mOrderLists;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_trading;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getAssets_trading());
        mOrderLists=new ArrayList<>();
        mDrawAdapter = new WithDrawAdapter(this);
        mRefresh.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoadMore() {
                if (hasNext) {
                    initData(false);
                }

            }

            @Override
            public void onRefresh() {
                initData(true);
            }
        });
        mRefresh.setColorSchemeResources(R.color.red, R.color.orange, R.color.blue);
        mCourierList.setAdapter(mDrawAdapter);
        initData(true);
    }

    private void initData(final Boolean refresh) {
        int mPage = 0;


        if (!refresh) {
            if (!hasNext) {
                mRefresh.setLoading(false);
                return;
            }
            mPage = mOrderLists.size();
        }
        sellerRdm.getWithdrawList(ConstantS.BASE_URL + "withdraw/list.htm", mPage, 0);
        sellerRdm.mPageListHasNextListener = new PageListHasNextListener<WithdrawDetail>() {
            @Override
            public void onSuccess(List list, final boolean hasNests) {
                final List<WithdrawDetail> pageList = (List<WithdrawDetail>) list;
                if (pageList != null) {
                    if (refresh) {
                        if (mOrderLists != null) {
                            mOrderLists.clear();
                            mOrderLists = pageList;
                        }
                    } else {
                        mOrderLists.addAll(pageList);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(pageList.isEmpty()){
                                mEmpty.setVisibility(View.VISIBLE);
                            }
                            mDrawAdapter.setData(mOrderLists);
                            hasNext = hasNests;
                            if (refresh) {
                                mRefresh.setRefreshing(false);
                            }
                            mRefresh.setLoading(false);
                        }
                    });
                }
            }
            @Override
            public void onError(int errorNO, String errorInfo) {

                if (refresh) {
                    mRefresh.setRefreshing(false);
                }
                mRefresh.setLoading(false);
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onLose() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (refresh) {
                            mRefresh.setRefreshing(false);
                        }
                        mRefresh.setLoading(false);
                    }
                });

            }

        };

    }

}
