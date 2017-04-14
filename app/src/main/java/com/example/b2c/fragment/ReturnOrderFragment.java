package com.example.b2c.fragment;

import android.view.View;
import android.widget.ListView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.adapter.RefundListAdapter;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.PageListHasNextListener;
import com.example.b2c.net.response.RefundListDetail;
import com.example.b2c.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 买家查看退货订单
 * 用途：
 * Created by yy on 16/11/21.
 * 邮箱：649444395@qq.com
 */

public class ReturnOrderFragment extends TempleBaseFragment{
    private RefundListAdapter mAdapter;
    ListView mCourierList;
    RefreshLayout mRefresh;
    protected boolean hasNext = false;
    protected List<RefundListDetail> mOrderLists;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_base_order;
    }

    @Override
    protected void initView(View rootView) {
        mCourierList = (ListView) rootView.findViewById(R.id.courier_list);
        mRefresh = (RefreshLayout) rootView.findViewById(R.id.refresh);
        mOrderLists = new ArrayList<>();
        mAdapter = new RefundListAdapter(getBaseActivity());
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
        mCourierList.setAdapter(mAdapter);
        mRefresh.setColorSchemeResources(R.color.red, R.color.orange, R.color.blue);
    }

    @Override
    public void onResume() {
        super.onResume();
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
        showLoading();
        rdm.getRefundList(isLogin, userId, token, mPage);
        rdm.mPageListHasNextListener = new PageListHasNextListener<RefundListDetail>() {
            @Override
            public void onSuccess(List<RefundListDetail> orderList, final boolean hasNexts) {
                final List<RefundListDetail> pageList = orderList;
                if (pageList != null) {
                    if (refresh) {
                        if (mOrderLists != null) {
                            mOrderLists.clear();
                            mOrderLists = pageList;
                        }
                    } else {
                        mOrderLists.addAll(pageList);
                    }
                    if(getBaseActivity() == null){
                        return;
                    }
                    getBaseActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (refresh) {
                                mRefresh.setRefreshing(false);
                            }
                            mRefresh.setLoading(false);
                            if (mOrderLists.isEmpty()) {
                                mEmpty.setVisibility(View.VISIBLE);
                            } else {
                                mEmpty.setVisibility(View.GONE);
                            }
                            mAdapter.setData(mOrderLists);
                            hasNext = hasNexts;
                        }
                    });
                }
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
                if(getBaseActivity() == null){
                    return;
                }
                getBaseActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (refresh) {
                            mRefresh.setRefreshing(false);
                        }
                        mRefresh.setLoading(false);
                    }
                });
            }

            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onLose() {
                if(getBaseActivity() == null){
                    return;
                }
                getBaseActivity().runOnUiThread(new Runnable() {
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
