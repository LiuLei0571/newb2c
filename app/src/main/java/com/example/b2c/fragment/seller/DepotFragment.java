package com.example.b2c.fragment.seller;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.adapter.seller.ShopAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.net.listener.base.PageListHasNextListener;
import com.example.b2c.net.response.seller.ShopDetail;
import com.example.b2c.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 用途：
 * Created by milk on 16/11/4.
 * 邮箱：649444395@qq.com
 */

public class DepotFragment extends TempleBaseFragment {
    private ShopAdapter mAdapter;

    @Bind(R.id.courier_list)
    ListView mCourierList;
    @Bind(R.id.refresh)
    RefreshLayout mRefresh;
    private boolean hasNext = false;
    private List<ShopDetail> mOrderLists;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_seller_depot;
    }

    @Override
    protected void initView(View rootView) {

        mOrderLists = new ArrayList<>();
        mAdapter = new ShopAdapter(getActivity(), 0);
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
        sellerRdm.getShopList(ConstantS.BASE_URL + "sample/getSellerSampleList.htm", mPage, 0);
        sellerRdm.mPageListHasNextListener = new PageListHasNextListener<ShopDetail>() {
            @Override
            public void onSuccess(List list, final boolean hasNests) {
                final List<ShopDetail> pageList = (List<ShopDetail>) list;
                if (pageList != null) {

                    if (refresh) {
                        if (mOrderLists != null) {
                            mOrderLists.clear();
                            mOrderLists = pageList;
                        }
                    } else {
                        mOrderLists.addAll(pageList);
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
                            } else
                                mEmpty.setVisibility(View.GONE);
                            mAdapter.setData(mOrderLists);
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
                        getActivity().finish();
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
                        Toast.makeText(mContext, requestFailure, Toast.LENGTH_SHORT).show();
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
}