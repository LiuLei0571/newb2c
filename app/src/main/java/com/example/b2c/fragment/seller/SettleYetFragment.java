package com.example.b2c.fragment.seller;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.adapter.seller.SettleYetAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.net.listener.base.PageListHasNextListener;
import com.example.b2c.net.response.seller.SettleYetCell;
import com.example.b2c.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 已结算
 * Created by yy on 2017/2/7.
 */

public class SettleYetFragment extends TempleBaseFragment {
    private  int settleType  = 2;
    private SettleYetAdapter mAdapter;
    @Bind(R.id.lv_settle)
    ListView lv_settle;
    @Bind(R.id.refresh)
    RefreshLayout mRefresh;
    private boolean hasNext = false;
    private List<SettleYetCell> mSettleCellLists;
    @Override
    protected int getContentViewId() {
        return R.layout.fragment_settle;
    }

    @Override
    protected void initView(View rootView) {
        mSettleCellLists = new ArrayList<>();
        mAdapter = new SettleYetAdapter(getActivity());
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
        lv_settle.setAdapter(mAdapter);
    }
    private void initData(final Boolean refresh) {
        int mPage = 0;


        if (!refresh) {
            if (!hasNext) {
                mRefresh.setLoading(false);
                return;
            }

            mPage = mSettleCellLists.size();
        }
        sellerRdm.getSettleYetList(ConstantS.BASE_URL + "seller/finance/settledHistory.htm", mPage, settleType);
        sellerRdm.mPageListHasNextListener = new PageListHasNextListener<SettleYetCell>() {
            @Override
            public void onSuccess(List list, final boolean hasNests) {
                final List<SettleYetCell> pageList = list;
                if (pageList != null) {

                    if (refresh) {
                        if (mSettleCellLists != null) {
                            mSettleCellLists.clear();
                            mSettleCellLists = pageList;
                        }


                    } else {
                        mSettleCellLists.addAll(pageList);
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (refresh) {
                                mRefresh.setRefreshing(false);
                            }
                            mRefresh.setLoading(false);

                            if (mSettleCellLists.isEmpty()) {
                                mEmpty.setVisibility(View.VISIBLE);
                            } else{
                                mEmpty.setVisibility(View.GONE);
                            }


                            mAdapter.setData(mSettleCellLists);
                            hasNext = hasNests;

                        }
                    });
                }

            }

            @Override
            public void onError(int errorNO, final String errorInfo) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (refresh) {
                            mRefresh.setRefreshing(false);
                        }
                        mRefresh.setLoading(false);
                        Toast.makeText(mContext, errorInfo, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onLose() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (refresh) {
                            mRefresh.setRefreshing(false);
                        }
                        mRefresh.setLoading(false);
                        Toast.makeText(mContext, mTranslatesString.getCommon_neterror(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

        };

    }

    @Override
    public void onResume() {
        super.onResume();
        if(isFirst){
            isFirst = false;
            initData(true);
        }

    }
}
