package com.example.b2c.fragment.seller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.activity.seller.HandleReturnActivity;
import com.example.b2c.adapter.seller.ReturnNoAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.PageListHasNextListener;
import com.example.b2c.net.response.RefundListDetail;
import com.example.b2c.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 未处理的退货
 * Created by yy on 2017/2/16.
 */

public class ReturnNoDoFragment extends TempleBaseFragment {
    private ReturnNoAdapter mAdapter;
    @Bind(R.id.courier_list)
    ListView mCourierList;
    @Bind(R.id.refresh)
    RefreshLayout mRefresh;
    private boolean hasNext = false;
    private List<RefundListDetail> mOrderLists;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_sller_return_no;
    }

    @Override
    protected void initView(View rootView) {
        mOrderLists = new ArrayList<>();
        mAdapter = new ReturnNoAdapter(getActivity()) {
            @Override
            public void yes(RefundListDetail detail) {
                Intent i_yes = new Intent(getActivity(), HandleReturnActivity.class);
                i_yes.putExtra("id",detail.getId());
                i_yes.putExtra("function",1);
                startActivity(i_yes);
            }

            @Override
            public void no(RefundListDetail detail) {
                Intent i_yes = new Intent(getActivity(), HandleReturnActivity.class);
                i_yes.putExtra("id",detail.getId());
                i_yes.putExtra("function",0);
                startActivity(i_yes);
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
        sellerRdm.getReturnList(ConstantS.BASE_URL + "seller/refund/list.htm", mPage, 0);
        sellerRdm.mPageListHasNextListener = new PageListHasNextListener<RefundListDetail>() {
            @Override
            public void onSuccess(List list, final boolean hasNests) {
                final List<RefundListDetail> pageList = (List<RefundListDetail>) list;
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
                            }else {
                                mEmpty.setVisibility(View.GONE);
                            }

                            mAdapter.setData(mOrderLists);
                            hasNext = hasNests;

                            mListCount.allNoCount(mOrderLists.size());
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
                        ToastHelper.makeToast(errorInfo);
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
        if (activity instanceof ReturnNoDoFragment.OnNoListCount) {
            mListCount = (ReturnNoDoFragment.OnNoListCount) activity;
        }
    }


    public OnNoListCount mListCount;

    public interface OnNoListCount {
        void allNoCount(int count);
    }
}
