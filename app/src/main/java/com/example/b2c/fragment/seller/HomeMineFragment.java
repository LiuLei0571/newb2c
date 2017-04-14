package com.example.b2c.fragment.seller;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.LivesCommunity.LivesCommuntiyHomeActivty;
import com.example.b2c.activity.common.TempleBaseFragment;
import com.example.b2c.activity.seller.CourierListActivity;
import com.example.b2c.activity.seller.DepotListActivity;
import com.example.b2c.activity.seller.InfoActivity;
import com.example.b2c.activity.seller.SellerNewShopActivity;
import com.example.b2c.activity.seller.SellerShopActivity;
import com.example.b2c.activity.seller.SettlesActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.seller.ShopEntry;
import com.example.b2c.widget.CircleImg;
import com.example.b2c.widget.SettingItemView;

import org.apache.http.util.TextUtils;

/**
 * 用途：
 * Created by milk on 16/10/27.
 * 邮箱：649444395@qq.com
 */

public class HomeMineFragment extends TempleBaseFragment implements View.OnClickListener{


    public  static final int RQ_INIT = 1999;
    private ShopEntry shopEntry;


    private FrameLayout fl_head;
    /**
     * 店铺logo
     */
    private CircleImg ci_logo;
    /**
     * 已经开店铺的情况下
     * 用户名，店铺名称，服务态度，服务描述，发货速度
     */
    private LinearLayout ll_head_one;
    private TextView tv_username,tv_shop_name;
    private TextView tv_evaluate_service_attitude,tv_evaluate_service_introduce,tv_evaluate_service_speed;
    private TextView tv_evaluate_service_attitude_label,tv_evaluate_service_introduce_label,tv_evaluate_service_speed_label;

    /**
     * 没有开店铺情况下
     * 用户名，店铺名称，去开店按钮
     */
    private LinearLayout ll_head_two;
    private TextView tv_username_two,tv_shop_notice,seller_lives;
    private Button btn_open_shop;

    /**
     * 商家信息，结算信息，安全中心，快递列表，仓库列表
     */
    private SettingItemView siv_company_info,siv_settle_info,siv_deliver_list,siv_depot_list;
private RelativeLayout siv_lives_list;
    @Override
    protected int getContentViewId() {
        return R.layout.fragment_seller_home_mine;
    }

    @Override
    protected void initView(View rootView) {
        fl_head = (FrameLayout) rootView.findViewById(R.id.fl_head);
        ci_logo = (CircleImg) rootView.findViewById(R.id.ci_logo);
        ll_head_one = (LinearLayout) rootView.findViewById(R.id.ll_head_one);
        tv_username = (TextView) rootView.findViewById(R.id.tv_username);
        tv_shop_name = (TextView) rootView.findViewById(R.id.tv_shop_name);
        tv_evaluate_service_attitude = (TextView) rootView.findViewById(R.id.tv_evaluate_service_attitude);
        tv_evaluate_service_attitude_label = (TextView) rootView.findViewById(R.id.tv_evaluate_service_attitude_label);
        tv_evaluate_service_introduce = (TextView) rootView.findViewById(R.id.tv_evaluate_service_introduce);
        tv_evaluate_service_introduce_label = (TextView) rootView.findViewById(R.id.tv_evaluate_service_introduce_label);
        tv_evaluate_service_speed = (TextView) rootView.findViewById(R.id.tv_evaluate_service_speed);
        tv_evaluate_service_speed_label = (TextView) rootView.findViewById(R.id.tv_evaluate_service_speed_label);

        ll_head_two = (LinearLayout) rootView.findViewById(R.id.ll_head_two);
        tv_username_two = (TextView) rootView.findViewById(R.id.tv_username_two);
        tv_shop_notice = (TextView) rootView.findViewById(R.id.tv_shop_notice);
        seller_lives = (TextView) rootView.findViewById(R.id.seller_lives);
        btn_open_shop = (Button) rootView.findViewById(R.id.btn_open_shop);
        seller_lives.setText(mTranslatesString.getCommon_livescommunity());
        siv_company_info = (SettingItemView) rootView.findViewById(R.id.siv_company_info);
        siv_settle_info = (SettingItemView) rootView.findViewById(R.id.siv_settle_info);
        siv_deliver_list = (SettingItemView) rootView.findViewById(R.id.siv_deliver_list);
        siv_depot_list = (SettingItemView) rootView.findViewById(R.id.siv_depot_list);
        siv_lives_list = (RelativeLayout) rootView.findViewById(R.id.siv_lives_list);
        initText();
        initData();
        siv_company_info.setOnClickListener(this);
        siv_settle_info.setOnClickListener(this);
        siv_deliver_list.setOnClickListener(this);
        siv_depot_list.setOnClickListener(this);
        siv_lives_list.setOnClickListener(this);
    }

    private void initData() {
        showLoading();
        sellerRdm.getShopInfo();
        sellerRdm.mOneDataListener = new OneDataListener<ShopEntry>() {
            @Override
            public void onSuccess(ShopEntry shop,String errorInfo) {
                shopEntry = shop;
                initUI(shopEntry);
            }
            @Override
            public void onError(int errorNO, String errorInfo) {
                initUI(null);
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
    /**
     * 初始化UI界面
     * @param shop
     */
    private void initUI(final ShopEntry shop) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(shop==null){
                    SPHelper.putInt("shopId",0);
                    ll_head_one.setVisibility(View.INVISIBLE);
                    ll_head_two.setVisibility(View.VISIBLE);

                    tv_username_two.setText(UserHelper.getSellerLoginName());
                    tv_shop_notice.setText(mTranslatesString.getCommon_unsetshopinfo());
                    btn_open_shop.setText(mTranslatesString.getCommon_goopenshop());
                    fl_head.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent_shop = new Intent(getActivity(), SellerNewShopActivity.class);
                            intent_shop.putExtra("shop",shop);
                            startActivityForResult(intent_shop,RQ_INIT);
                        }
                    });
                }else{
                    SPHelper.putInt("shopId",shop.getShopId());
                    ll_head_one.setVisibility(View.VISIBLE);
                    ll_head_two.setVisibility(View.INVISIBLE);
                    if(TextUtils.isBlank(shop.getLogo())){
                        ci_logo.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.shop_no_logo));
                    }else{
                        loader.displayImage(ConstantS.BASE_PIC_URL+shop.getLogo(),ci_logo,options);
                    }
                    tv_username.setText(shop.getLoginName());
                    tv_shop_name.setText(shop.getShopName());
                    tv_evaluate_service_attitude.setText(shop.getServiceAttitude());
                    tv_evaluate_service_introduce.setText(shop.getConsistentDescription());
                    tv_evaluate_service_speed.setText(shop.getDeliverySpeed());
                    fl_head.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent_shop = new Intent(getActivity(), SellerShopActivity.class);
                            intent_shop.putExtra("shop",shop);
                            startActivityForResult(intent_shop,RQ_INIT);
                        }
                    });
                }
            }
        });

    }

    private void initText() {
        tv_evaluate_service_attitude_label.setText(mTranslatesString.getCommon_serviceatitude());
        tv_evaluate_service_introduce_label.setText(mTranslatesString.getCommon_servicedescripe());
        tv_evaluate_service_speed_label.setText(mTranslatesString.getCommon_deliveryspeed());

        tv_shop_notice.setText(mTranslatesString.getCommon_unsetshopinfo());
        btn_open_shop.setText(mTranslatesString.getCommon_goopenshop());

        siv_company_info.setTv_text(mTranslatesString.getCommon_businessinfo());
        siv_settle_info.setTv_text(mTranslatesString.getCommon_jiesuanzhongxin());
        siv_deliver_list.setTv_text(mTranslatesString.getSeller_expresslist());
        siv_depot_list.setTv_text(mTranslatesString.getCommon_depotlist());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.siv_company_info:
                //商家信息
                getIntent(getActivity(), InfoActivity.class);
                break;
            case R.id.siv_settle_info:
                //结算信息
                if(shopEntry==null){
                    ToastHelper.makeToast(mTranslatesString.getCommon_pleaseopenshopfirst());
                    return;
                }
                getIntent(getActivity(), SettlesActivity.class);
                break;
            case R.id.siv_deliver_list:
                //快递列表
                if(shopEntry==null){
                    ToastHelper.makeToast(mTranslatesString.getCommon_pleaseopenshopfirst());
                    return;
                }
                getIntent(getActivity(), CourierListActivity.class);
                break;
            case R.id.siv_depot_list:
                //仓库列表
                if(shopEntry==null){
                    ToastHelper.makeToast(mTranslatesString.getCommon_pleaseopenshopfirst());
                    return;
                }
                getIntent(getActivity(), DepotListActivity.class);
                break;
            case R.id.siv_lives_list:
                startActivity(new Intent(getActivity(),LivesCommuntiyHomeActivty.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RQ_INIT){
            initData();
        }
    }
}