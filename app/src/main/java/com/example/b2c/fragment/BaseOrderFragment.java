package com.example.b2c.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.b2c.R;
import com.example.b2c.activity.OrderDetailActivity;
import com.example.b2c.activity.OrderEvaluateNewActivity;
import com.example.b2c.activity.OrderLogisticActivity;
import com.example.b2c.activity.ReturnGoodActivity;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.adapter.AllOrderAdapter;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.GetMyOrderListener;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.listener.base.NoDataListener;
import com.example.b2c.net.response.BuyerOrderList;
import com.example.b2c.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 用途：
 * Created by milk on 16/11/21.
 * 邮箱：649444395@qq.com
 */
public abstract class BaseOrderFragment extends TempleBaseFragment implements AdapterView.OnItemClickListener {
    private BaseAdapter mAdapter;
    ListView mCourierList;
    RefreshLayout mRefresh;
    protected boolean hasNext = false;
    protected List<BuyerOrderList> mOrderLists;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_base_order;
    }


    @Override
    protected void initView(View rootView) {
        mCourierList = (ListView) rootView.findViewById(R.id.courier_list);
        mRefresh = (RefreshLayout) rootView.findViewById(R.id.refresh);
        mOrderLists = new ArrayList<>();
        mAdapter = new AllOrderAdapter(getBaseActivity()) {
            /**
             * 提醒买家发货
             * @param orderList
             */
            @Override
            public void remind(BuyerOrderList orderList) {
                showLoading();
                rdm.getBuyerRemindRequest(ConstantS.BASE_URL + "trade/buyer/remindSellerToShip.htm", orderList.getOrderId(), orderList.getShopId());
                rdm.mNodataListener = new NoDataListener() {
                    @Override
                    public void onSuccess(String success) {
                        ToastHelper.makeToast(success);
                    }

                    @Override
                    public void onError(int errorNO,String errorInfo) {
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

            @Override
            public void cancel(BuyerOrderList order) {
                showLoading();
                rdm.cancelOrder(order.getOrderId());
                rdm.mResponseListener = new ResponseListener() {
                    @Override
                    public void onSuccess(String errorInfo) {
                        ToastHelper.makeToast(errorInfo);
                        initData(true);
                    }

                    @Override
                    public void onError(int errorNO, String errorInfo) {
                        ToastHelper.makeErrorToast(errorInfo);
                    }

                    @Override
                    public void onFinish() {
                        dissLoading();
                    }

                    @Override
                    public void onLose() {
                        ToastHelper.makeToast(requestFailure);
                    }
                };


            }

            @Override
            public void seeExp(BuyerOrderList order) {
                Intent i_return = new Intent(getContext(), OrderLogisticActivity.class);
                i_return.putExtra("orderId",order.getOrderId());
                startActivity(i_return);
            }

//            @Override
//            public void reject(BuyerOrderList order) {
//                Intent i_reject= new Intent(getContext(), RejectGoodActivity.class);
//                i_reject.putExtra("order",order);
//                startActivity(i_reject);
//            }

            @Override
            public void returnGood(BuyerOrderList order) {
                Intent i_return = new Intent(getContext(), ReturnGoodActivity.class);
                i_return.putExtra("order",order);
                startActivity(i_return);
            }

            @Override
            public void evaluate(BuyerOrderList order) {
                Intent i_evaluate = new Intent(getContext(), OrderEvaluateNewActivity.class);
                i_evaluate.putExtra("object",order);
                startActivity(i_evaluate);
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
        mCourierList.setOnItemClickListener(this);
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
        rdm.GetMyOrder(isLogin, userId, token, mPage, getType());
        rdm.getmyorderListener = new GetMyOrderListener() {
            @Override
            public void onSuccess(List<BuyerOrderList> orderList, final boolean hasNexts) {
                final List<BuyerOrderList> pageList = orderList;
                if (pageList != null) {

                    if (refresh) {
                        if (mOrderLists != null) {
                            mOrderLists.clear();
                            mOrderLists = pageList;
                        }
                    } else {
                        mOrderLists.addAll(pageList);
                    }
                    if(getBaseActivity()==null){
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BuyerOrderList orderList = (BuyerOrderList) (parent.getItemAtPosition(position));
        Intent intent = new Intent(getBaseActivity(), OrderDetailActivity.class);
        intent.putExtra("orderId", orderList.getOrderId());
        startActivity(intent);
    }

    protected abstract int getType();
}
