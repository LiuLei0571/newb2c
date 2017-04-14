package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.activity.common.WebViewActivity;
import com.example.b2c.adapter.GoodsListAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.GetShopHomeListener;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.response.GetShopHomeResult;
import com.example.b2c.net.util.HttpClientUtil;

/**
 * 店铺首页
 */
public class ShopHomesActivity extends TempBaseActivity implements OnClickListener {
    private ImageView iv_shop_logo;
    private RelativeLayout rl_shop_home_goodslist, rl_shop_home_introduce;
    private TextView tv_shop_home_name,tv_shop_home_introduce, tv_shop_home_goodslist;
    private Button btn_collect;
    private ListView lv_shop_home;
    private Intent intent;
    private Bundle bundle;
    private GetShopHomeResult home_result;
    private GoodsListAdapter adapter;
    private int shopId, search_type = SearchActivity.SAMPLE;
    private static final int TYPE_SHOP = 2;// 2代表店铺

    @Override
    public int getRootId() {
        return R.layout.shop_home_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        bundle = intent.getExtras();
        shopId = bundle.getInt("shopId");
        initView();
        initText();
        initData();
        addImage(R.drawable.ic_message, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //http://115.29.167.40:81/chat/chatWithSeller/1?userId=156&token=d0ca2afc21076409a20134216cb5c721&userType=0&loginName=maijia&plat=android
                ///chat/chatWithSeller/{sellerId}?userId=&token=&userType&appName=&loginName=
                if (UserHelper.isBuyerLogin()) {
                    //买家登录
                    Intent intent = new Intent(ShopHomesActivity.this, WebViewActivity.class);
                    intent.putExtra("url", ConstantS.BASE_CHAT+"chat/chatWithSeller/" + home_result.getSellerId() + "?userId=" + UserHelper.getBuyerId() + "&token=" + UserHelper.getBuyertoken() + "&userType=" + "0" + "&appName=" + SPHelper.getString(Configs.APPNAME) + "&loginName=" + UserHelper.getBuyerName()+ "&lang=" + SPHelper.getString(Configs.LANGUAGE));
                    startActivity(intent);
                } else {
                    //买家未登录
                    ToastHelper.makeToast(mTranslatesString.getCommon_tologin());
                }
                return;
            }
        });
    }

    public void initView() {
        rl_shop_home_goodslist = (RelativeLayout) findViewById(R.id.rl_shop_home_goodslist);
        rl_shop_home_introduce = (RelativeLayout) findViewById(R.id.rl_shop_home_introduce);
        iv_shop_logo = (ImageView) findViewById(R.id.iv_shop_home_logo);
        tv_shop_home_name = (TextView) findViewById(R.id.tv_shopintro_name);
        btn_collect = (Button) findViewById(R.id.btn_collection);
        lv_shop_home = (ListView) findViewById(R.id.lv_shop_home);
        tv_shop_home_introduce = (TextView) findViewById(R.id.tv_shop_home_introduce);
        tv_shop_home_goodslist = (TextView) findViewById(R.id.tv_shop_home_goodslist);

        rl_shop_home_introduce.setOnClickListener(this);
        rl_shop_home_goodslist.setOnClickListener(this);
        btn_collect.setOnClickListener(this);


    }

    public void initText() {
        setTitle(mTranslatesString
                .getCommon_shophome());
        tv_shop_home_introduce.setText(mTranslatesString
                .getCommon_shopprofile());
        tv_shop_home_goodslist.setText(mTranslatesString
                .getCommon_samplelist());
    }

    public void initData() {
        showProgressDialog(Loading);
        rdm.GetShopHome(unLogin, userId, token, shopId);
        rdm.getshophomeListener = new GetShopHomeListener() {

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }

            @Override
            public void onSuccess(final GetShopHomeResult result) {
                home_result = result;
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (home_result.getIsFavorite() == 1) {
                            btn_collect
                                    .setText(mTranslatesString
                                            .getCommon_favoriteyet());
                        } else {
                            btn_collect
                                    .setText(mTranslatesString
                                            .getCommon_favorite());
                        }
                        tv_shop_home_name.setText(home_result.getShopName());
                        loader.displayImage(HttpClientUtil.BASE_PIC_URL
                                + home_result.getLogo(), iv_shop_logo, options);
                        adapter = new GoodsListAdapter(ShopHomesActivity.this,
                                result.getSampleList());
                        lv_shop_home.setAdapter(adapter);

                    }
                });
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }

        };
    }

    public void Collect_Shop() {
        if (isLogin) {
            showProgressDialog(Loading);
            rdm.Collect(isLogin, userId, token, shopId, TYPE_SHOP);
            rdm.responseListener = new ResponseListener() {
                @Override
                public void onSuccess(String errorInfo) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btn_collect.setText(mTranslatesString.getCommon_favoriteyet());
                        }
                    });
                }

                @Override
                public void onError(int errorNO, String errorInfo) {
                    showAlertDialog(errorInfo);
                }

                @Override
                public void onFinish() {
                    dismissProgressDialog();
                }

                @Override
                public void onLose() {
                    ToastHelper.makeErrorToast(request_failure);
                }
            };
        } else {
            showAlertTextDialog(mTranslatesString.getCommon_tologin());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_shop_home_introduce:
                Intent i_2_intro = new Intent(this, ShopIntroActivity.class);
                Bundle bundle_intro = new Bundle();
                bundle_intro.putInt("shopId", shopId);
                i_2_intro.putExtras(bundle_intro);
                startActivity(i_2_intro);
                break;
            case R.id.rl_shop_home_goodslist:
                Intent i_2_goodlist = new Intent(this, GoodsListActivity.class);
                Bundle bundle_goodlist = new Bundle();
                bundle_goodlist.putInt("shopId", shopId);
                bundle_goodlist.putInt("search_type", search_type);
                i_2_goodlist.putExtras(bundle_goodlist);
                startActivity(i_2_goodlist);
                break;
            case R.id.btn_collection:
                if (home_result.getIsFavorite() == 0)
                    Collect_Shop();
                break;
            default:
                break;
        }
    }

}
