package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.seller.CourierAdapter;
import com.example.b2c.config.ConstantS;
import com.example.b2c.net.listener.base.PageListHasNextListener;
import com.example.b2c.net.response.seller.LogisticDetail;
import com.example.b2c.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 用途：
 * Created by milk on 16/10/27.
 * 邮箱：649444395@qq.com
 */

public class CourierListActivity extends TempBaseActivity implements AdapterView.OnItemClickListener {


    @Bind(R.id.courier_list)
    ListView mCourierList;
    @Bind(R.id.refresh)
    RefreshLayout mRefresh;
    private boolean hasNext = false;
    private List<LogisticDetail> mLogisticDetailList;
    private CourierAdapter mAdapter;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_courier;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLogisticDetailList = new ArrayList<>();
        mAdapter = new CourierAdapter(this);
        mCourierList.setOnItemClickListener(this);
        setTitle(mTranslatesString.getSeller_expresslist());
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

            mPage = mLogisticDetailList.size();
        }
        sellerRdm.getAllLogistictList(ConstantS.BASE_URL + "deliver/company/all/list.htm", mPage, -1);
        sellerRdm.mPageListHasNextListener = new PageListHasNextListener<LogisticDetail>() {
            @Override
            public void onSuccess(List list, final boolean hasNests) {
                final List<LogisticDetail> pageList = (List<LogisticDetail>) list;
                if (refresh) {
                    mLogisticDetailList.clear();
                    mLogisticDetailList = pageList;

                } else {
                    mLogisticDetailList.addAll(pageList);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (refresh) {
                            if (pageList.isEmpty() && refresh) {
                                mEmpty.setVisibility(View.VISIBLE);
                            }
                        } else {
                            mEmpty.setVisibility(View.GONE);

                        }
                        mAdapter.notifyDataSetInvalidated();
                        mCourierList.invalidate();
                        mAdapter.setData(mLogisticDetailList);
                        hasNext = hasNests;
                        if (refresh) {
                            mRefresh.setRefreshing(false);
                        }
                        mRefresh.setLoading(false);
                    }
                });

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
                if (refresh) {
                    mRefresh.setRefreshing(false);
                }
                mRefresh.setLoading(false);
            }

        };

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogisticDetail logisticDetail = mAdapter.getItem(position);
        Intent intent = new Intent(CourierListActivity.this, CourierDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("logistic", logisticDetail);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData(true);
    }
}
