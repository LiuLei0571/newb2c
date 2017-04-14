package com.example.b2c.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.BuyerLoginActivity;
import com.example.b2c.activity.CheckOrderActivity;
import com.example.b2c.activity.GoodsDetailActivity;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.adapter.ShopAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UIHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.listener.ShoppingCartListener;
import com.example.b2c.net.response.CartShopCell;
import com.example.b2c.net.util.Logs;
import com.example.b2c.net.util.NumberUtil;
import com.example.b2c.widget.RemoteDataConnection;

import java.util.List;

/**
 * 购物车
 */
public class HomeCartFragment extends TempleBaseFragment implements OnClickListener {

    private static final int RQ_SAMPLE = 11;
    private static final int RQ_CHECK = 12;

    private static final int pageSize = 1000;
    //未登录view
    private View mRootView;

    //购物车列表view
    private ListView lv_shop;

    private ShopAdapter shop_adapter;

    private Button btn_2login, btn_settlement;

    private TextView tv_total_price, tv_delete, tv_shopping_cart_title,
            tv_select_all, tv_shopping_cart_sum,
            tv_shopping_cart_unincludedelivery;

    private CheckBox cb_all_shop;

    public static double total_price = 0;
    private List<CartShopCell> cart_list;
    private int pageNum = 1;
    private Button mBtnloadMore;
    private View LoadMore;



    @Override
    protected int getContentViewId() {
        return R.layout.cart_layout;
    }

    @Override
    protected void initView(View rootView) {
        lv_shop = (ListView) rootView.findViewById(R.id.lv_shop);
        cb_all_shop = (CheckBox) rootView.findViewById(R.id.cb_select_all);
        tv_total_price = (TextView) rootView.findViewById(R.id.tv_cost);
        tv_delete = (TextView) rootView.findViewById(R.id.tv_cart_delete);
        btn_settlement = (Button) rootView.findViewById(R.id.btn_settlement);
        tv_shopping_cart_title = (TextView) rootView.findViewById(R.id.tv_shopping_cart_title);
        tv_select_all = (TextView) rootView.findViewById(R.id.tv_selet_all);
        tv_shopping_cart_sum = (TextView) rootView.findViewById(R.id.tv_shopping_cart_sum);
        tv_shopping_cart_unincludedelivery = (TextView) rootView.findViewById(R.id.tv_shopping_cart_uninclude);
        LoadMore = View.inflate(getActivity(), R.layout.loadmorelayout, null);
        mBtnloadMore = (Button) LoadMore.findViewById(R.id.btn_loadmore);
        tv_delete.setOnClickListener(this);
        btn_settlement.setOnClickListener(this);
        mBtnloadMore.setOnClickListener(this);
        UIHelper.setClickable(btn_settlement, false);
        initText();
        this.isPrepared = true;
        lazyLoad();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(getContentViewId(), container, false);
        mEmpty = (RelativeLayout) mRootView.findViewById(R.id.empty);
        tv_nodata = (TextView) mRootView.findViewById(R.id.tv_nodata);
        if (tv_nodata != null) {
            tv_nodata.setText(mTranslatesString.getCommon_nodata());
        }
        if (!isLogin) {// 根据是否登录判断选择哪个界面进行显示
            mRootView = inflater.inflate(R.layout.unlogin_layout, container, false);
            btn_2login = (Button) mRootView.findViewById(R.id.btn_to_login);
            btn_2login.setText(mTranslatesString.getCommon_tologin());

            btn_2login.setText(mTranslatesString.getCommon_tologin());
            btn_2login.setOnClickListener(this);

        } else {
            initView(mRootView);
        }
        return mRootView;
    }

    public void initText() {
        tv_shopping_cart_title.setText(mTranslatesString.getCommon_shoppingcart());
        tv_select_all.setText(mTranslatesString.getCommon_selectall());
        tv_delete.setText(mTranslatesString.getCommon_delete());
        tv_shopping_cart_sum.setText(mTranslatesString.getCommon_sum());
        tv_shopping_cart_unincludedelivery.setText(mTranslatesString.getCommon_unincludedelivery());
        btn_settlement.setText(mTranslatesString.getCommon_settle());
        mBtnloadMore.setText(mTranslatesString.getCommon_loadmore());
    }

    public void initData() {
        Logs.d("initData isLogin=" + isLogin);
        if (!isLogin) {
            return;
        }
        showProgressDialog(loading, waiting);
        rdm = RemoteDataConnection.getInstance();
        rdm.GetShoppingCart(isLogin, userId, token, pageNum);
        rdm.shoppingcartListener = new ShoppingCartListener() {
            @Override
            public void onSuccess(final List<CartShopCell> cartShopCellList) {
                cart_list = cartShopCellList;
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (cart_list.size() == pageSize) {
                            lv_shop.addFooterView(LoadMore);
                        }
                        cb_all_shop.setChecked(false);// 每次获取数据前对勾选进行清零
                        cb_all_shop.setOnCheckedChangeListener(new CheckAllListener());
                        shop_adapter = new ShopAdapter(mContext, cart_list, handler);
                        lv_shop.setAdapter(shop_adapter);
                        total_price = 0;
                        tv_total_price.setText(Configs.CURRENCY_UNIT + NumberUtil.GetStringValue(total_price,null));
                    }
                });
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(mTranslatesString.getCommon_neterror());
            }
        };
    }

    //懒加载
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        Log.d("lazyLoad","initData");
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
        Logs.d("onStart  isLogin=" + isLogin);
    }

    /**
     * 从购物车里删除商品
     */
    public void DeleteCart() {
        String ids = "";
        if (cart_list != null)
            for (int i = 0; i < cart_list.size(); i++) {
                for (int j = 0; j < cart_list.get(i).getSamples().size(); j++) {
                    if (cart_list.get(i).getSamples().get(j).isChecked()) {
                        ids += cart_list.get(i).getSamples().get(j).getId() + ",";
                    }
                }
            }
        if (ids.length() > 0) {
            showLoading();
            ids = ids.substring(0, ids.length() - 1);
            rdm.DeleteShoppingCart(isLogin, userId, token, ids);
            rdm.responseListener = new ResponseListener() {
                @Override
                public void onSuccess(String errorInfo) {
                    initData();
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
        } else {
            ToastHelper.makeErrorToast(mTranslatesString.getCommon_toastcar());
        }
    }

    public void Jump2Settlement() {
        String confirmStr = "[";
        Intent i2settlement = new Intent(getActivity(), CheckOrderActivity.class);
        Bundle bundle = new Bundle();
        if (cart_list != null)
            for (int i = 0; i < cart_list.size(); i++) {
                for (int j = 0; j < cart_list.get(i).getSamples().size(); j++) {
                    if (cart_list.get(i).getSamples().get(j).isChecked()) {
                        confirmStr = confirmStr
                                + "{\"shoppingCartId\":"
                                + cart_list.get(i).getSamples().get(j)
                                .getId()
                                + ",\"num\":"
                                + cart_list.get(i).getSamples().get(j)
                                .getSampleNum() + "},";
                    }
                }
            }
        if (confirmStr.length() > 1) {// 如果有确认提交订单的信息则跳转
            confirmStr = confirmStr.substring(0, confirmStr.length() - 1) + ']';
            bundle.putString("confirmStr", confirmStr);
            i2settlement.putExtras(bundle);
            System.out.println(confirmStr);
            startActivityForResult(i2settlement, RQ_CHECK);
        }
    }

    public void LoadMore() {
        lv_shop.removeFooterView(LoadMore);
        pageNum++;
        rdm.GetShoppingCart(isLogin, userId, token, pageNum);
        rdm.shoppingcartListener = new ShoppingCartListener() {

            @Override
            public void onSuccess(final List<CartShopCell> cartShopList) {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (cartShopList.size() == pageSize) {
                            lv_shop.addFooterView(LoadMore);
                        }
                        cart_list.addAll(cartShopList);
                        shop_adapter.notifyDataSetChanged();
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
                ToastHelper.makeErrorToast(requestFailure);
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_to_login:
                Intent i_to_login = new Intent(mContext, BuyerLoginActivity.class);
                startActivity(i_to_login);
                break;
            case R.id.tv_cart_delete:
                DeleteCart();
                break;
            case R.id.btn_settlement:
                Jump2Settlement();
                break;
            case R.id.btn_loadmore:
                LoadMore();
                break;
            default:
                break;
        }
    }

    class CheckAllListener implements OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton arg0, boolean checked) {
            for (int i = 0; i < cart_list.size(); i++) {
                shop_adapter.getIsSelected().put(i, checked);
            }
            shop_adapter.notifyDataSetChanged();
            shop_adapter.setAllShopChecked(checked);
            UIHelper.setClickable(btn_settlement, checked);
            mRootView.invalidate();
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10) { // 更改选中商品的总价格
                double price = (Double) msg.obj;
                total_price += price;
                tv_total_price.setText(Configs.CURRENCY_UNIT + NumberUtil.GetStringValue(total_price,null));
                UIHelper.setClickable(btn_settlement, true);
            } else if (msg.what == 11) {
            } else if (msg.what == 60) {
                int sampleId = (int) msg.obj;
                Intent i_to_detial = new Intent(getActivity(), GoodsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("sampleId", sampleId);
                i_to_detial.putExtras(bundle);
                startActivityForResult(i_to_detial, RQ_SAMPLE);
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQ_CHECK || requestCode == RQ_SAMPLE) {
            initData();
        }
    }
}
