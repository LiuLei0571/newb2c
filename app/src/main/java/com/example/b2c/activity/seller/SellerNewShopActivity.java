package com.example.b2c.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.b2c.R;
import com.example.b2c.activity.CommonEditActivity;
import com.example.b2c.activity.CommonItemActivity;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.dialog.PickImageDialog;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UIHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.response.seller.GoodSources;
import com.example.b2c.net.response.translate.CellText;
import com.example.b2c.net.response.translate.OptionList;
import com.example.b2c.net.util.Logs;
import com.example.b2c.widget.CircleImg;
import com.example.b2c.widget.SettingItemView2;

import org.apache.http.util.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;

/**
 * 用户没有开店情况下去开店
 * 页面
 */
public class SellerNewShopActivity extends TempBaseActivity {
    public static final int RQ_SHOP_NAME = 10;
    public static final int RQ_SHOP_INTRODUCTION = 11;
    public static final int RQ_SHOP_GOODSSOURCE = 12;
    public static final int RQ_MAIN_CATOGERY = 13;

    Map map = new HashMap();

    GoodSources goodSources = new GoodSources();
    /**
     * 来源列表
     */
    private List<CellText> cells;

    private PickImageDialog mPickImage;
    private String mLogo;
    private CircleImg ci_logo;
    private RelativeLayout rl_logo;
    private SettingItemView2 siv_shop_name;
    private SettingItemView2 siv_goods_source;
    private SettingItemView2 siv_shop_introduction;
    private SettingItemView2 siv_main_catogery;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_new_shop;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
    }
    private void initView() {
        ci_logo = (CircleImg) findViewById(R.id.ci_logo);
        rl_logo = (RelativeLayout) findViewById(R.id.rl_logo);
        siv_shop_name = (SettingItemView2) findViewById(R.id.siv_shop_name);
        siv_goods_source = (SettingItemView2) findViewById(R.id.siv_goods_source);
        siv_shop_introduction = (SettingItemView2) findViewById(R.id.siv_shop_introduction);
        siv_main_catogery = (SettingItemView2) findViewById(R.id.siv_main_catogery);
        addText(mTranslatesString.getCommon_submit(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateShop(map);
            }
        });
        cells = mTranslatesStringList.getCargoSourceTypeCode();
        goodSources.setCells(cells);
    }

    private void initText() {
        setTitle(mTranslatesString.getCommon_createshop());
        siv_shop_name.setTv_text(mTranslatesString.getSeller_shopname());
        siv_goods_source.setTv_text(mTranslatesString.getCommon_goodsource());
        siv_shop_introduction.setTv_text(mTranslatesString.getCommon_shopprofile());
        siv_main_catogery.setTv_text(mTranslatesString.getCommon_managecategory());
    }


    public void pickImage(int id) {
        if (mPickImage != null) {
            mPickImage.destory();
        }
        mPickImage = new PickImageDialog(this, id, mLogisticsDataConnection) {
            @Override
            protected void onImageUpLoad(final int id, final String url, String url1) {
                Log.d("onImageUpLoad callBack", url);
                mLogo = url1;
                map.put("logo", mLogo);
                UIHelper.displayImage((CircleImg) SellerNewShopActivity.this.findViewById(id), url);
            }
        };
        mPickImage.show();
    }

    @OnClick({R.id.rl_logo, R.id.siv_shop_name, R.id.siv_goods_source, R.id.siv_shop_introduction, R.id.siv_main_catogery})
    protected void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_logo:
                pickImage(R.id.ci_logo);
                break;
            case R.id.siv_shop_name:
                Intent intent_shop_name = new Intent(SellerNewShopActivity.this, CommonEditActivity.class);
                intent_shop_name.putExtra("title", mTranslatesString.getCommon_shophome());
                intent_shop_name.putExtra("text", "");
                startActivityForResult(intent_shop_name, RQ_SHOP_NAME);
                break;
            case R.id.siv_goods_source:
                Intent intent_goods_source = new Intent(SellerNewShopActivity.this, CommonItemActivity.class);
                intent_goods_source.putExtra("title",mTranslatesString.getCommon_goodsource());
                intent_goods_source.putExtra("text", "");
                intent_goods_source.putExtra("items", goodSources);
                startActivityForResult(intent_goods_source, RQ_SHOP_GOODSSOURCE);
                break;
            case R.id.siv_shop_introduction:
                Intent intent_shop_introduction = new Intent(SellerNewShopActivity.this, CommonEditActivity.class);
                intent_shop_introduction.putExtra("title",mTranslatesString.getCommon_shopprofile());
                intent_shop_introduction.putExtra("text", "");
                startActivityForResult(intent_shop_introduction, RQ_SHOP_INTRODUCTION);
                break;
            case R.id.siv_main_catogery:
//                Intent intent_main_catogery = new Intent(SellerShopActivity.this, CommonEditActivity.class);
//                intent_main_catogery.putExtra("title","经营类目");
//                intent_main_catogery.putExtra("text",shopEntry.getIntroduction());
//                startActivityForResult(intent_main_catogery,RQ_MAIN_CATOGERY);
//                ToastHelper.makeToast("未实现");
                break;
            default:
                break;
        }
    }
    /**
     * 修改用户信息
     *
     * @param map
     */
    private void CreateShop(Map map) {
        showLoading();
        sellerRdm.CreateShopInfo(map, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
        sellerRdm.responseListener = new ResponseListener() {
            @Override
            public void onSuccess(String successInfo) {
                ToastHelper.makeToast(successInfo);
                finish();
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }

            @Override
            public void onFinish() {
                dissLoading();
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logs.d("ssssss" + requestCode + resultCode);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (data == null) {
            return;
        }
        final String finalText = (!TextUtils.isBlank(data.getStringExtra("text"))) ? data.getStringExtra("text").toString() : "";
        switch (requestCode) {
            case RQ_SHOP_NAME:
                if (TextUtils.isBlank(finalText)) {
                    return;
                }
                map.put("name", finalText);
                siv_shop_name.setTv_doc(finalText);
                break;
            case RQ_SHOP_GOODSSOURCE:
                goodSources = (GoodSources) data.getSerializableExtra("items");
                if (TextUtils.isBlank(finalText)) {
                    return;
                }
                map.put("goodsSource", finalText);
                siv_goods_source.setTv_doc(getCheckedItems());
                break;
            case RQ_SHOP_INTRODUCTION:
                if (TextUtils.isBlank(finalText)) {
                    return;
                }
                map.put("introduction", finalText);
                siv_shop_introduction.setTv_doc(finalText);
                break;
            default:
                break;
        }


    }

    private String getCheckedItems() {

        StringBuilder builder = new StringBuilder();
        List<CellText> cells = goodSources.getCells();
        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).isChecked()) {
                builder.append(cells.get(i).getText() + ",");
            }
        }
        String builderString = builder.toString();
        if (builderString.endsWith(",")) {
            return builderString.substring(0, builderString.length() - 1);
        }
        return builderString;
    }


}
