package com.example.b2c.activity.seller;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.seller.WaitingSendGoodsAdapter;
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
 * Created by milk on 16/10/29.
 * 邮箱：649444395@qq.com
 */

public class WaitingGoodsListActivity extends TempBaseActivity {

    private WaitingSendGoodsAdapter mAdapter;


    @Bind(R.id.courier_list)
    ListView mCourierList;
    @Bind(R.id.refresh)
    RefreshLayout mRefresh;
    private boolean hasNext = false;
    private List<OrderList> mOrderLists;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_new_order;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderLists = new ArrayList<>();
        mAdapter = new WaitingSendGoodsAdapter(this) {
            @Override
            protected void onSelect(OrderList orderDetail) {
                sellerRdm.getBaseRequest(ConstantS.BASE_URL + "seller/sendOrder/sure/" + orderDetail.getId() + ".htm");
                sellerRdm.mResponseListener = new ResponseListener() {

                    @Override
                    public void onSuccess(final String errorInfo) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(WaitingGoodsListActivity.this, errorInfo, Toast.LENGTH_SHORT).show();
                                initData(true);
                            }
                        });
                    }

                    @Override
                    public void onError(int errorNO, String errorInfo) {
                        ToastHelper.makeErrorToast(errorInfo);
                    }

                    @Override
                    public void onFinish() {
                    }

                    @Override
                    public void onLose() {
                        ToastHelper.makeErrorToast(request_failure);
                    }
                };
            }

            @Override
            protected void onCancel(OrderList order) {

                showLoading();
                int orderId = order.getId();
                Map map = new HashMap();
                map.put("function",2);
                map.put("orderId",orderId);
                sellerRdm.postBaseMapRequest(ConstantS.BASE_URL + "seller/order/updateOrder.htm",map);
                sellerRdm.mResponseListener = new ResponseListener() {
                    @Override
                    public void onSuccess(final String errorInfo) {
                        runOnUiThread(new Runnable() {
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
                        ToastHelper.makeErrorToast(request_failure);
                    }
                };


            }
        };
        setTitle(mTranslatesString.getSeller_homewaitingtitle());
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
        sellerRdm.getOrderList(ConstantS.BASE_URL + "seller/order/list.htm", mPage, 20);
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

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (refresh) {
                                mRefresh.setRefreshing(false);
                                mEmpty.setVisibility(View.GONE);
                            }
                            mRefresh.setLoading(false);
                            if (mOrderLists.isEmpty()) {
                                mEmpty.setVisibility(View.VISIBLE);
                            } else {
                                mEmpty.setVisibility(View.GONE);
                            }
                            mAdapter.setData(mOrderLists);
                            hasNext = hasNests;
                        }
                    });
                }

            }

            @Override
            public void onError(int errorNO, String errorInfo) {

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

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstLoad) {
            isFirstLoad = false;
            initData(true);
        }
    }
}
