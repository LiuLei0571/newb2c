package com.example.b2c.fragment.seller;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.adapter.seller.RefuseWaitingAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.listener.base.PageListHasNextListener;
import com.example.b2c.net.response.seller.OrderList;
import com.example.b2c.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * 用途：
 * Created by milk on 16/10/30.
 * 邮箱：649444395@qq.com
 */

public class RefuseWaitingFragment extends TempleBaseFragment {

    private RefuseWaitingAdapter mAdapter;


    @Bind(R.id.courier_list)
    ListView mCourierList;
    @Bind(R.id.refresh)
    RefreshLayout mRefresh;
    private boolean hasNext = false;
    private List<OrderList> mOrderLists;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_sller_refuse_waiting;
    }

    @Override
    protected void initView(View rootView) {
        mOrderLists = new ArrayList<>();
        mAdapter = new RefuseWaitingAdapter(getActivity()) {

            @Override
            protected void onSelect(OrderList orderList) {
                showLoading();
                int orderId = orderList.getId();
                Map map = new HashMap();
                map.put("function", 3);
                map.put("orderId", orderId);
                sellerRdm.postBaseMapRequest(ConstantS.BASE_URL + "seller/order/updateOrder.htm", map);
                sellerRdm.mResponseListener = new ResponseListener() {
                    @Override
                    public void onSuccess(final String errorInfo) {
                        if(getActivity()==null){
                            return;
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastHelper.makeToast(errorInfo);
                                initData(true);
                            }
                        });

                    }

                    @Override
                    public void onError(int errorNO, final String errorInfo) {
                        ToastHelper.makeErrorToast(errorInfo);
                    }

                    @Override
                    public void onFinish() {
                        dissLoading();
                    }

                    @Override
                    public void onLose() {
                        ToastHelper.makeErrorToast(requestFailure);
                    }

                };
            }
        };
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
        mCourierList.setAdapter(mAdapter);
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
        sellerRdm.getOrderList(ConstantS.BASE_URL + "seller/order/list.htm", mPage, 130);
        sellerRdm.mPageListHasNextListener = new PageListHasNextListener<OrderList>() {
            @Override
            public void onSuccess(List list, final boolean hasNests) {
                final List<OrderList> pageList = (List<OrderList>) list;
                if (pageList != null) {
                    if (refresh) {
                        if (mOrderLists != null) {
                            mOrderLists.clear();
                            mOrderLists = pageList;
                        }

                    } else {
                        mOrderLists.addAll(pageList);
                    }
                    if(getActivity()==null){
                        return;
                    }
                    getActivity().runOnUiThread(new Runnable() {
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
                            hasNext = hasNests;
                            mListCount.watingCount(mOrderLists.size());
                        }
                    });
                }
            }

            @Override
            public void onError(int errorNO, final String errorInfo) {
                if(getActivity()==null){
                    return;
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (refresh) {
                            mRefresh.setRefreshing(false);
                        }
                        mRefresh.setLoading(false);
                        ToastHelper.makeErrorToast(errorInfo);
                    }
                });

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onLose() {
                if(getActivity()==null){
                    return;
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (refresh) {
                            mRefresh.setRefreshing(false);
                        }
                        mRefresh.setLoading(false);
                        ToastHelper.makeErrorToast(requestFailure);
                    }
                });
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        initData(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnWaitingListCount) {
            mListCount = (OnWaitingListCount) activity;
        }
    }

    public OnWaitingListCount mListCount;

    public interface OnWaitingListCount {
        void watingCount(int watingCount);
    }
}
