package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.CommonEditActivity;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.Configs;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.common.TranslationResult;
import com.example.b2c.net.response.seller.ShopDetail;
import com.example.b2c.net.util.Logs;
import com.example.b2c.net.util.NumberUtil;
import com.example.b2c.widget.CommonListPopupWindow;

import org.apache.http.util.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用途：
 * Created by milk on 16/11/4.
 * 邮箱：649444395@qq.com
 */

public class ChangeShopDetailActivity extends TempBaseActivity {
    private static final  int RQ_PRICE = 10;

    @Bind(R.id.iv_goods_head)
    ImageView mIvGoodsHead;
    @Bind(R.id.tv_goods_name)
    TextView mTvGoodsName;
    @Bind(R.id.tv_goods_money)
    TextView mTvGoodsMoney;
    @Bind(R.id.tv_good_count)
    TextView mTvGoodCount;
    @Bind(R.id.tv_time)
    TextView mTvTime;
    @Bind(R.id.btn_select_courier)
    TextView mBtnSelectCourier;
    @Bind(R.id.tv_brand)
    TextView mTvBrand;
    @Bind(R.id.tv_change_money)
    TextView mTvChangeMoney;
    @Bind(R.id.lyt_money)
    LinearLayout mLytMoney;
    @Bind(R.id.tv_freight)
    TextView mTvFreight;
    @Bind(R.id.lyt_freight)
    LinearLayout mLytFreight;
    @Bind(R.id.tv_goods_message)
    TextView mTvGoodsMessage;
    @Bind(R.id.tv_title_up_time)
    TextView tvTitleUpTime;
    @Bind(R.id.tv_title_banner)
    TextView tvTitleBanner;
    @Bind(R.id.tv_title_price)
    TextView tvTitlePrice;
    @Bind(R.id.tv_title_fee)
    TextView tvTitleFee;
    @Bind(R.id.tv_title_message)
    TextView tvTitleMessage;
    private int type;
    private ShopDetail mShopDetail;

    private List<HashMap<String, String>> ls_d = new ArrayList<>();
    private HashMap<String, String> selected = new HashMap<>();
    private List<TranslationResult> deliveryType;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_shop_detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTranslatesString.getCommon_sampledetail());
        type = getIntent().getIntExtra("type", 0);
        mShopDetail = (ShopDetail) getIntent().getSerializableExtra("shopDetail");
        initText();
    }

    private void initText() {
        tvTitleUpTime.setText(mTranslatesString.getShop_uptime());
        tvTitleBanner.setText(mTranslatesString.getGoods_banner());
        tvTitlePrice.setText(mTranslatesString.getCommon_sampleprice());
        tvTitleFee.setText(mTranslatesString.getCommon_freight());
        tvTitleMessage.setText(mTranslatesString.getGoods_message());
    }

    private void initData(int id) {
        showLoading();
        sellerRdm.getGoodsDetail(ConstantS.BASE_URL + "sample/getSampleInfo/" + id + ".htm");
        sellerRdm.mOneDataListener = new OneDataListener<ShopDetail>() {

            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }

            @Override
            public void onSuccess(ShopDetail data, String successInfo) {
                if (data != null) {
                    mShopDetail = data;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initView();
                        }
                    });
                }
            }
            @Override
            public void onError(int errorNO,String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }
        };
    }

    private void initView() {
        if (type == 0) {
            mBtnSelectCourier.setText(mTranslatesString.getShop_up());
        } else if (type == 1) {
            mBtnSelectCourier.setText(mTranslatesString.getShop_down());
        }
        loader.displayImage(ConstantS.BASE_PIC_URL + mShopDetail.getDefaultPic(), mIvGoodsHead, options);
        mTvBrand.setText(mShopDetail.getBrand());
        mTvChangeMoney.setText(Configs.CURRENCY_UNIT+mShopDetail.getPrice() + "");
        mTvGoodCount.setText(mShopDetail.getStock() + mTranslatesString.getCommon_num());
        mTvGoodsMessage.setText(mShopDetail.getInfo());
        mTvGoodsName.setText(mShopDetail.getName());
        mTvGoodsMoney.setText(Configs.CURRENCY_UNIT+":" + mShopDetail.getPrice());
        mTvTime.setText(mShopDetail.getCreateTime());

        deliveryType = mTranslatesStringList.getDeliveryTypeNew();
        Logs.d("deliveryType"+deliveryType.size());
        if(ls_d.isEmpty()){
            ls_d.clear();
        }
        if(selected.size() == 0){
            selected.clear();
        }
        for (TranslationResult result : deliveryType) {
            HashMap map = new HashMap();
            map.put(result.getValue(), result.getText());
            ls_d.add(map);
            if(result.getValue().equals(mShopDetail.getDeliveryType())){
                selected.put(result.getValue(), result.getText());
                mTvFreight.setText(result.getText());
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isFirstLoad){
            isFirstLoad = false;
            initData(mShopDetail.getId());
        }

    }
    private String selectDName = null;

    @OnClick({R.id.btn_select_courier, R.id.lyt_money, R.id.lyt_freight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_select_courier:
                if (type == 0) {
                    getNetData(ConstantS.BASE_URL + "sample/batchOnSample.htm", mShopDetail.getId(), 0);
                } else if (type == 1) {
                    getNetData(ConstantS.BASE_URL + "sample/batchOffSample.htm", mShopDetail.getId(), 1);

                }
                break;
            case R.id.lyt_money:
                Intent  i_m =new Intent(ChangeShopDetailActivity.this, CommonEditActivity.class);
                Bundle  bundle_m =new Bundle();
                bundle_m.putString("title",mTranslatesString.getCommon_sampleprice());
                bundle_m.putString("text", mShopDetail.getPrice().replace(",",""));
                i_m.putExtras(bundle_m);
                startActivityForResult (i_m,RQ_PRICE);
                break;
            case R.id.lyt_freight://配送方式
                CommonListPopupWindow pop = new CommonListPopupWindow(this, ls_d, selected, new CommonListPopupWindow.CommonCallBack() {
                    @Override
                    public void onClick(int selectItem, String value) {
                        Logs.d(""+value+"value"+selectItem);
                        if(TextUtils.isBlank(value) && selected.containsKey(value)){
                            return;
                        }else {
                            selected = ls_d.get(selectItem);
                        }
                        Map map =new HashMap();
                        map.put("sampleId",mShopDetail.getId());
                        selectDName = value;
                        for (TranslationResult dType : deliveryType) {
                            if(value.equals(dType.getText())){
                                map.put("deliveryType",dType.getValue());
                            }
                        }
                        Logs.d("selected"+selected);
                        updateSample(map);
                    }
                });
                pop.show(view);
                break;
        }
    }

    /**
     * 商品上架下架
     * @param url
     * @param id
     * @param mType
     */
    private void getNetData(String url, int id, final int mType) {
        showLoading();
        sellerRdm.getShelvesRequest(url, id);
        sellerRdm.mResponseListener = new ResponseListener() {
            @Override
            public void onSuccess(String errorInfo) {
                ToastHelper.makeToast(errorInfo);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mType == 0) {
                            type = 1;
                            mBtnSelectCourier.setText(mTranslatesString.getShop_down());

                        } else if (mType == 1) {
                            type = 0;
                            mBtnSelectCourier.setText(mTranslatesString.getShop_up());

                        }
                    }
                });
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
                ToastHelper.makeErrorToast(request_failure);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        Logs.d("" + requestCode + resultCode);
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        switch (requestCode) {
            case RQ_PRICE:
                final String text1 = data.getStringExtra("text");
                if (TextUtils.isBlank(text1)) {
                    return;
                }
                if(text1.matches("[0-9]+")){
                    Map map = new HashMap();
                    map.put("sampleId", mShopDetail.getId());
                    map.put("price", text1);
                    updateSample(map);
                }else {
                    ToastHelper.makeToast(mTranslatesString.getCommon_styleerror());
                }
                break;
        }
    }

    /**
     * 修改商品信息
     * @param map
     */
    private void  updateSample(final Map map){
        if (type == 0) {
            sellerRdm.updateSimpleSample(ConstantS.BASE_URL + "sample/updateSampleInfo.htm",map);
        } else if (type == 1) {
            sellerRdm.updateSimpleSample(ConstantS.BASE_URL + "sample/updateSampleInfo.htm",map);

        }
        sellerRdm.mResponseListener = new ResponseListener() {
            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onSuccess(String errorInfo) {
                ToastHelper.makeToast(errorInfo);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(map.containsKey("deliveryType")){
                            mTvFreight.setText(selectDName.toString());
                        }
                        if(map.containsKey("price")){
                            mTvChangeMoney.setText(Configs.CURRENCY_UNIT+ NumberUtil.GetStringValue(Double.valueOf(map.get("price").toString()),null));
                        }
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
}
