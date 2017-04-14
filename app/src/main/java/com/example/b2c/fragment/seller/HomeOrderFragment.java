package com.example.b2c.fragment.seller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.activity.seller.CancelListActivity;
import com.example.b2c.activity.seller.FinishOrderActivity;
import com.example.b2c.activity.seller.NewOrderListActivity;
import com.example.b2c.activity.seller.ReceivedOrderActivity;
import com.example.b2c.activity.seller.RefuseGoodsListActivity;
import com.example.b2c.activity.seller.ReleaseProductActivity;
import com.example.b2c.activity.seller.ReturnGoodsActivity;
import com.example.b2c.activity.seller.SampleSDActivity;
import com.example.b2c.activity.seller.SendGoodsListActivity;
import com.example.b2c.activity.seller.TodayOrderActivity;
import com.example.b2c.activity.seller.WaitingGoodsListActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.base.HomeIndexListListener;
import com.example.b2c.net.response.OrderSituation;
import com.example.b2c.net.response.seller.ShopInfoDetail;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：卖家订单管理
 * Created by milk on 16/10/27.
 * 邮箱：649444395@qq.com
 */

public class HomeOrderFragment extends TempleBaseFragment {
    private String[] names = null;
    private int[] icons = {R.drawable.on_depot, R.drawable.on_sale, R.drawable.publish_sample};
    @Bind(R.id.tv_today_num)
    TextView mTvTodayNum;
    @Bind(R.id.tv_today_title)
    TextView mTvTodayTitle;
    @Bind(R.id.lyt_today)
    LinearLayout mLytToday;
    @Bind(R.id.tv_refused_num)
    TextView mTvRefusedNum;
    @Bind(R.id.tv_refused_title)
    TextView mTvRefusedTitle;
    @Bind(R.id.lyt_refused)
    LinearLayout mLytRefused;
    @Bind(R.id.tv_new_order_num)
    TextView mTvNewOrderNum;
    @Bind(R.id.tv_new_order_title)
    TextView mTvNewOrderTitle;
    @Bind(R.id.lyt_lyt_new_order)
    LinearLayout mLytLytNewOrder;
    @Bind(R.id.tv_waiting_send_num)
    TextView mTvWaitingSendNum;
    @Bind(R.id.tv_waiting_send_title)
    TextView mTvWaitingSendTitle;
    @Bind(R.id.lyt_waiting_send)
    LinearLayout mLytWaitingSend;
    @Bind(R.id.tv_send_num)
    TextView mTvSendNum;
    @Bind(R.id.tv_send_title)
    TextView mTvSendTitle;
    @Bind(R.id.lyt_send)
    LinearLayout mLytSend;
    @Bind(R.id.tv_received_num)
    TextView tvReceivedNum;
    @Bind(R.id.tv_received_title)
    TextView tvReceivedTitle;
    @Bind(R.id.lyt_received)
    LinearLayout lytReceived;
    @Bind(R.id.tv_finish_num)
    TextView tvFinishNum;
    @Bind(R.id.tv_finish_title)
    TextView tvFinishTitle;
    @Bind(R.id.lyt_finish)
    LinearLayout lytFinish;
    @Bind(R.id.tv_back_num)
    TextView tvBackNum;
    @Bind(R.id.tv_back_title)
    TextView tvBackTitle;
    @Bind(R.id.lyt_back)
    LinearLayout lytBack;
    @Bind(R.id.tv_cancel_num)
    TextView tv_cancel_num;
    @Bind(R.id.tv_cancel_title)
    TextView tv_cancel_title;
    @Bind(R.id.lyt_cancel)
    LinearLayout lyt_cancel;
    @Bind(R.id.gv_home)
    GridView gv_home;
    ShopInfoDetail shopInfoDetails;

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_seller_order;
    }

    @Override
    protected void initView(View rootView) {
        names = new String[]{mTranslatesString.getGoods_depot(), mTranslatesString.getCommon_onselling(), mTranslatesString.getCommon_publishgoods()};
        initText();
        initGridView();
    }

    private void initGridView() {
        gv_home.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0://仓库中
                        Intent i = new Intent(getActivity(), SampleSDActivity.class);
                        i.putExtra("type", 1);
                        startActivity(i);
                        break;
                    case 1://在售中
                        Intent ii = new Intent(getActivity(), SampleSDActivity.class);
                        ii.putExtra("type", 2);
                        startActivity(ii);
                        break;
                    case 2://发布商品
                        if (shopInfoDetails.isHasWareHouse()) {
                            getIntent(getActivity(), ReleaseProductActivity.class);
                        } else {
                            ToastHelper.makeErrorToast(mTranslatesString.getCommon_pleaseselect() + mTranslatesString.getCommon_newinsertdepot());
                        }
                        break;
                }
            }
        });
    }

    private void initText() {
        mTvNewOrderTitle.setText(mTranslatesString.getSeller_homenewtitle());
        mTvTodayTitle.setText(mTranslatesString.getSeller_hometodaytitle());
        mTvRefusedTitle.setText(mTranslatesString.getSeller_homerefuse());
        mTvWaitingSendTitle.setText(mTranslatesString.getSeller_homewaitingtitle());
        mTvSendTitle.setText(mTranslatesString.getSeller_homesendtitle());
        tvReceivedTitle.setText(mTranslatesString.getSeller_homereceived());
        tvFinishTitle.setText(mTranslatesString.getSeller_homefinish());
        tvBackTitle.setText(mTranslatesString.getCommon_returngoods());
        tv_cancel_title.setText(mTranslatesString.getCommon_yetcancel());
        gv_home.setAdapter(new HomeAdapter());
    }

    private void initData() {
        showLoading();
        sellerRdm.getOrderSituation(ConstantS.BASE_URL + "seller/order/overview.htm");
        sellerRdm.mHomeIndexListListener = new HomeIndexListListener<OrderSituation>() {
            @Override
            public void onSuccess(List list, final ShopInfoDetail shopInfoDetail) {
                final List<OrderSituation> mList = (List<OrderSituation>) list;
                shopInfoDetails = shopInfoDetail;
                dissLoading();
                if (shopInfoDetail != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mOnTitleListener.onTitle(shopInfoDetails.getName());
                            for (OrderSituation orderSituation : mList) {
                                switch (orderSituation.getStatus()) {
                                    case 0:
                                        mTvTodayNum.setText(orderSituation.getCount() + "");
                                        break;
                                    case 10:
                                        mTvNewOrderNum.setText(orderSituation.getCount() + "");
                                        break;
                                    case 20:
                                        mTvWaitingSendNum.setText(orderSituation.getCount() + "");
                                        break;
                                    case 30:
                                        mTvSendNum.setText(orderSituation.getCount() + "");
                                        break;
                                    case 100:
                                        tv_cancel_num.setText(orderSituation.getCount() + "");
                                        break;
                                    case 130:
                                        mTvRefusedNum.setText(orderSituation.getCount() + "");
                                        break;
                                    case 141:
                                        tvReceivedNum.setText(orderSituation.getCount() + "");
                                        break;
                                    case 142:
                                        tvFinishNum.setText(orderSituation.getCount() + "");
                                        break;
                                    case 143:
                                        tvBackNum.setText(orderSituation.getCount() + "");
                                        break;
                                }
                            }
                        }
                    });
                }
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
                ToastHelper.makeErrorToast(requestFailure);
            }
        };
    }

    @Override
    public void onAttach(Activity activity) {
        if (activity instanceof OnTitleListener) {
            mOnTitleListener = (OnTitleListener) activity;
        }
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnTitleListener = null;
    }

    private OnTitleListener mOnTitleListener;

    @OnClick({R.id.lyt_today, R.id.lyt_lyt_new_order, R.id.lyt_cancel, R.id.lyt_waiting_send, R.id.lyt_send, R.id.lyt_received, R.id.lyt_finish, R.id.lyt_back, R.id.lyt_refused})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lyt_today:
                //今日订单
                getIntent(getActivity(), TodayOrderActivity.class);
                break;
            case R.id.lyt_lyt_new_order:
                //新订单
                getIntent(getActivity(), NewOrderListActivity.class);
                break;
            case R.id.lyt_cancel:
                //取消订单
                getIntent(getActivity(), CancelListActivity.class);
                break;
            case R.id.lyt_refused:
                //拒收订单
                getIntent(getActivity(), RefuseGoodsListActivity.class);
                break;
            case R.id.lyt_waiting_send:
                //待发货订单
                getIntent(getActivity(), WaitingGoodsListActivity.class);
                break;
            case R.id.lyt_send:
                //已发货订单
                getIntent(getActivity(), SendGoodsListActivity.class);
                break;
            case R.id.lyt_received:
                //已签收订单
                getIntent(getActivity(), ReceivedOrderActivity.class);
                break;
            case R.id.lyt_finish:
                //已完成订单
                getIntent(getActivity(), FinishOrderActivity.class);
                break;
            case R.id.lyt_back:
                //已退货订单
                getIntent(getActivity(), ReturnGoodsActivity.class);
                break;
            default:
                break;

        }
    }

    public interface OnTitleListener {
        void onTitle(String title);
    }

    /**
     * 卖家仓库中、在售中、发布商品
     */
    private class HomeAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(getActivity().getApplicationContext(),
                        R.layout.item_home_grid, null);
            } else {
                view = convertView;
            }
            ImageView iv = (ImageView) view.findViewById(R.id.iv_homeitem_icon);
            TextView tv = (TextView) view.findViewById(R.id.tv_homeitem_name);
            tv.setText(names[position]);
            iv.setImageResource(icons[position]);
            return view;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    }
}
