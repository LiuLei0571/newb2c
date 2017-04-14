package com.example.b2c.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.base.BaseAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UIHelper;
import com.example.b2c.net.listener.base.PageListHasNextListener;
import com.example.b2c.net.response.Coupon;
import com.example.b2c.net.response.translate.CellText;
import com.example.b2c.net.util.DateUtils;
import com.example.b2c.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * 买家 优惠券列表
 */
public class MyCouponsActivity extends TempBaseActivity {
    private static final int ZERO_COUPON = 0;
    private static final int ONE_COUPON = 1;

    private CouponAdapter mAdapter;

    @Bind(R.id.lv_items)
    ListView lv_items;
    @Bind(R.id.refresh)
    RefreshLayout mRefresh;
    private List<Coupon> mCoupons;

    private boolean hasNext = false;
    private Map map = null;


    @Override
    public int getRootId() {
        return R.layout.activity_my_coupons;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCoupons = new ArrayList<>();
        map = new HashMap();
        initText();
        mAdapter = new CouponAdapter(this);
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
        lv_items.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstLoad) {
            isFirstLoad = false;
            initData(true);
        }
    }

    private void initData(final boolean refresh) {
        int mStartRow = 0;
        if (!refresh) {
            if (!hasNext) {
                mRefresh.setLoading(false);
                return;
            }
            mStartRow = mCoupons.size();
        }
        map.put("startRow", mStartRow);
        rdm.getCouponList(isLogin, map);
        rdm.mPageListHasNextListener = new PageListHasNextListener<Coupon>() {
            @Override
            public void onSuccess(List list, final boolean hasNests) {
                final List<Coupon> pageList = (List<Coupon>) list;
                if (pageList != null) {
                    if (refresh) {
                        if (mCoupons != null) {
                            mCoupons.clear();
                            mCoupons = pageList;
                        }
                    } else {
                        mCoupons.addAll(pageList);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (refresh) {
                                mRefresh.setRefreshing(false);
                            }
                            mRefresh.setLoading(false);
                            if (mCoupons.isEmpty()) {
                                mEmpty.setVisibility(View.VISIBLE);
                            } else {
                                mEmpty.setVisibility(View.GONE);
                            }
                            mAdapter.setData(mCoupons);
                            hasNext = hasNests;
                        }
                    });
                }

            }

            @Override
            public void onError(int errorNO, final String errorInfo) {
                runOnUiThread(new Runnable() {
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (refresh) {
                            mRefresh.setRefreshing(false);
                        }
                        mRefresh.setLoading(false);
                        ToastHelper.makeErrorToast(request_failure);
                    }
                });
            }
        };
    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_mycoupon());
    }


    class CouponAdapter extends BaseAdapter<Coupon> {

        private Map<String ,String > couponTypeMap;
        public CouponAdapter(Context context) {
            super(context, R.layout.item_coupon);
            couponTypeMap = new HashMap<>();
            initCouponMap(optionList.getCouponType());
        }
        private void initCouponMap(List<CellText>  cells) {
            if(cells != null){
                for (CellText cell : cells) {
                    couponTypeMap.put(cell.getValue(),cell.getText());
                }
            }
        }

        @Override
        public void renderView(ViewHolderFactory viewHolderFactory, int position) {
             final Coupon coupon = getItem(position);
             TextView tv_discount = viewHolderFactory.findViewById(R.id.tv_discount);
             TextView tv_type = viewHolderFactory.findViewById(R.id.tv_type);
             TextView tv_isHasUsed = viewHolderFactory.findViewById(R.id.tv_isHasUsed);
             TextView tv_date_between = viewHolderFactory.findViewById(R.id.tv_date_between);
            if (coupon != null) {
                tv_discount.setText(Configs.CURRENCY_UNIT+coupon.getDiscount());
                tv_type.setText(couponTypeMap.get(coupon.getType()+""));
                if(coupon.getType() == ZERO_COUPON){
                    tv_type.setBackgroundColor(UIHelper.getColor(R.color.main_green));
                }else if(coupon.getType() == ONE_COUPON){
                    tv_type.setBackgroundColor(UIHelper.getColor(R.color.main_purple));
                }
                if(coupon.getIsHasUsed() == ZERO_COUPON){
                    tv_isHasUsed.setText(mTranslatesString.getCoupon_notuse());
                }else if(coupon.getType() == ONE_COUPON){
                    tv_isHasUsed.setText(mTranslatesString.getCoupon_yetuse());
                }
                StringBuilder sb = new StringBuilder();
                if(coupon.getCreateTime()>0){
                    sb.append(DateUtils.getDateStr(DateUtils.DATE_SMALL_STR_DOT,coupon.getCreateTime()));
                    sb.append("-");
                }
                if(coupon.getExpiredDate()>0){
                    sb.append(DateUtils.getDateStr(DateUtils.DATE_SMALL_STR_DOT,coupon.getExpiredDate()));
                }else if(coupon.getExpiredDate()==0){
                    sb.append(mTranslatesString.getCommon_alwaysusefull());
                }
                tv_date_between.setText(sb.toString());
            }
        }
    }




}
