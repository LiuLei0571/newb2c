package com.example.b2c.activity.seller;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.b2c.R;
import com.example.b2c.activity.CommonEditActivity;
import com.example.b2c.activity.CommonItemActivity;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.config.ConstantS;
import com.example.b2c.dialog.PickImageDialog;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.helper.UIHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.response.seller.GoodSources;
import com.example.b2c.net.response.seller.ShopEntry;
import com.example.b2c.net.response.translate.CellText;
import com.example.b2c.net.response.translate.OptionList;
import com.example.b2c.net.util.Logs;
import com.example.b2c.widget.CircleImg;
import com.example.b2c.widget.SettingItemView2;

import org.apache.http.util.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用户存在店铺的页面
 * 店铺信息页面
 */
public class SellerShopActivity extends TempBaseActivity{

    public static final int RQ_SHOP_NAME = 10;
    public static final int RQ_SHOP_INTRODUCTION= 11;
    public static final int RQ_SHOP_GOODSSOURCE = 12;
    public static final int RQ_MAIN_CATOGERY = 13;

    private PickImageDialog mPickImage;
    private String mLogo;

    @Bind(R.id.rl_logo)
    RelativeLayout rl_logo;
    @Bind(R.id.ci_logo)
    CircleImg ci_logo;
    @Bind(R.id.siv_shop_name)
    SettingItemView2 siv_shop_name;
    @Bind(R.id.siv_goods_source)
    SettingItemView2 siv_goods_source;
    @Bind(R.id.siv_shop_introduction)
    SettingItemView2 siv_shop_introduction;
    @Bind(R.id.siv_main_catogery)
    SettingItemView2 siv_main_catogery;

    ShopEntry shopEntry;

    GoodSources goodSources =new GoodSources();
    /**
     * 来源列表
     */
    private List<CellText> cells;

    @Override
    public int getRootId() {
        return R.layout.activity_seller_shop;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initText();
        initData();
    }

    private void initUI(ShopEntry data) {
        if(TextUtils.isBlank(data.getLogo())){
            ci_logo.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.shop_no_logo));
        }else{
            loader.displayImage(ConstantS.BASE_PIC_URL+data.getLogo(),ci_logo,options);
        }
        OptionList optionList = SPHelper.getBean("options", OptionList.class);
        cells = optionList.getCargoSourceTypeCode();
        goodSources.setCells(cells);
        siv_shop_name.setTv_doc(data.getShopName());
        String sources = data.getGoodsSource();
        if(!TextUtils.isBlank(sources)){
            String[] sces = sources.split(",");
            String value;
            StringBuilder sces_builder=new StringBuilder();
            for(CellText cell : cells){
                value = cell.getValue();
                for(String es : sces){
                    if(value.equals(es)){
                        cell.setChecked(true);
                        sces_builder.append(cell.getText()+",");
                        break;
                    }
                }
            }
            String builderString = sces_builder.toString();
            if(builderString.endsWith(",")){
                siv_goods_source.setTv_doc(builderString.substring(0,builderString.length()-1));
            }
        }
        siv_shop_introduction.setTv_doc(data.getIntroduction());
        siv_main_catogery.setTv_doc("******");

    }

    private void initData() {
        showLoading();
        sellerRdm.getShopInfo();
        sellerRdm.mOneDataListener = new OneDataListener<ShopEntry>(){

            @Override
            public void onFinish() {
                dissLoading();
            }
            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
            }
            @Override
            public void onSuccess(final ShopEntry data, String successInfo) {
                shopEntry = data;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initUI(data);
                    }
                });

            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }
        };

    }

    private void initText() {
        setTitle(mTranslatesString.getSeller_shopinfo());
        siv_shop_name.setTv_text(mTranslatesString.getSeller_shopname());
        siv_goods_source.setTv_text(mTranslatesString.getCommon_goodsource());
        siv_shop_introduction.setTv_text(mTranslatesString.getCommon_shopprofile());
        siv_main_catogery.setTv_text(mTranslatesString.getCommon_managecategory());
    }

    private void initView() {

    }

    public void pickImage(int id) {
        if (mPickImage != null) {
            mPickImage.destory();
        }
        mPickImage = new PickImageDialog(this, id, mLogisticsDataConnection) {
            @Override
            protected void onImageUpLoad(final  int id, final String url, String url1) {
                Log.d("onImageUpLoad callBack", url);
                mLogo = url1;
                Map map =new HashMap();
                map.put("logo",mLogo);
                modifyUserInfo(map, new SellerShopActivity.CallBack() {
                    @Override
                    public void doCallBack() {
                        UIHelper.displayImage((CircleImg) SellerShopActivity.this.findViewById(id), url);
                    }
                });
            }
        };
        mPickImage.show();
    }

    @OnClick({R.id.rl_logo,R.id.siv_shop_name,R.id.siv_goods_source,R.id.siv_shop_introduction,R.id.siv_main_catogery})
    protected void OnClick(View view){
        switch (view.getId()){
            case R.id.rl_logo:
                pickImage(R.id.ci_logo);
                break;
            case R.id.siv_shop_name:
                Intent intent_shop_name = new Intent(SellerShopActivity.this, CommonEditActivity.class);
                intent_shop_name.putExtra("title",mTranslatesString.getCommon_shophome());
                if(shopEntry==null){
                    intent_shop_name.putExtra("text","");
                }else{
                    intent_shop_name.putExtra("text",shopEntry.getShopName());
                }
                startActivityForResult(intent_shop_name,RQ_SHOP_NAME);
                break;
            case R.id.siv_goods_source:
                Intent intent_goods_source = new Intent(SellerShopActivity.this, CommonItemActivity.class);
                intent_goods_source.putExtra("title",mTranslatesString.getCommon_goodsource());
                if(shopEntry==null){
                    intent_goods_source.putExtra("text","");
                }else{
                    intent_goods_source.putExtra("text",shopEntry.getGoodsSource());
                }
                intent_goods_source.putExtra("items",goodSources);
                startActivityForResult(intent_goods_source,RQ_SHOP_GOODSSOURCE);
                break;
            case R.id.siv_shop_introduction:
                Intent intent_shop_introduction = new Intent(SellerShopActivity.this, CommonEditActivity.class);
                intent_shop_introduction.putExtra("title",mTranslatesString.getCommon_shopprofile());
                if(shopEntry==null){
                    intent_shop_introduction.putExtra("text","");
                }else{
                    intent_shop_introduction.putExtra("text",shopEntry.getIntroduction());
                }
                startActivityForResult(intent_shop_introduction,RQ_SHOP_INTRODUCTION);
                break;
//            case R.id.siv_main_catogery:
//                Intent intent_main_catogery = new Intent(SellerShopActivity.this, CommonEditActivity.class);
//                intent_main_catogery.putExtra("title","经营类目");
//                intent_main_catogery.putExtra("text",shopEntry.getIntroduction());
//                startActivityForResult(intent_main_catogery,RQ_MAIN_CATOGERY);
//                ToastHelper.makeToast("未实现");
//                break;
            default:
                break;
        }
    }
    /**
     * 修改信息成功回调
     */
    public interface  CallBack{
        void doCallBack();
    }
    /**
     * 修改用户信息
     * @param map
     */
    private void modifyUserInfo(Map map,final SellerShopActivity.CallBack callBack) {
        showLoading();
        sellerRdm.updateShopInfo(map, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
        sellerRdm.responseListener = new ResponseListener() {
            @Override
            public void onSuccess(String successInfo) {
                ToastHelper.makeToast(successInfo);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBack.doCallBack();
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

            @Override
            public void onFinish() {
                dissLoading();
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            return;
        }
        if( data == null){
            return;
        }
        final String finalText = (!TextUtils.isBlank(data.getStringExtra("text")))?data.getStringExtra("text").toString():"";
        Map map =new HashMap();
        switch (requestCode){
            case RQ_SHOP_NAME:
                map.clear();
                if(TextUtils.isBlank(finalText)){
                    return;
                }
                map.put("name",finalText);
                modifyUserInfo(map, new SellerShopActivity.CallBack() {
                    @Override
                    public void doCallBack() {
                        siv_shop_name.setTv_doc(finalText);
                    }
                });
                break;
            case RQ_SHOP_GOODSSOURCE:
                map.clear();
                goodSources = (GoodSources) data.getSerializableExtra("items");
                if(TextUtils.isBlank(finalText)){
                    return;
                }
                shopEntry.setGoodsSource(finalText);
                map.put("goodsSource",finalText);
                modifyUserInfo(map, new SellerShopActivity.CallBack() {
                    @Override
                    public void doCallBack() {

                        siv_goods_source.setTv_doc(getCheckedItems());
                    }
                });
                break;
            case RQ_SHOP_INTRODUCTION:
                map.clear();
                if(TextUtils.isBlank(finalText)){
                    return;
                }
                map.put("introduction",finalText);
                modifyUserInfo(map, new SellerShopActivity.CallBack() {
                    @Override
                    public void doCallBack() {
                        siv_shop_introduction.setTv_doc(finalText);
                    }
                });
                break;
            default:
                break;
        }





    }
    private String getCheckedItems() {

        StringBuilder builder = new StringBuilder();
        List<CellText> cells = goodSources.getCells();
        for (int i = 0; i < cells.size(); i++) {
            if(cells.get(i).isChecked()){
                builder.append(cells.get(i).getText()+",");
            }
        }
        String builderString = builder.toString();
        if(builderString.endsWith(",")){
            return  builderString.substring(0,builderString.length()-1);
        }
        return builderString;
    }
}
