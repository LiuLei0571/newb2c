package com.example.b2c.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b2c.R;
import com.example.b2c.activity.common.TempBaseActivity;
import com.example.b2c.adapter.CheckOrderAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.dialog.ItemsDialogHelper;
import com.example.b2c.helper.ToastHelper;
import com.example.b2c.net.listener.ConfirmCartListener;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.listener.ShoppingCartSettleListener;
import com.example.b2c.net.response.CartShopDetail;
import com.example.b2c.net.response.ConfirmCartResult;
import com.example.b2c.net.response.Coupon;
import com.example.b2c.net.response.DeliveryFeeDetail;
import com.example.b2c.net.response.ShopWareHouse;
import com.example.b2c.net.response.ShoppingAddressDetail;
import com.example.b2c.net.util.Logs;
import com.example.b2c.net.util.NumberUtil;
import com.example.b2c.net.util.UIUtils;
import com.example.b2c.widget.SettingItemView2;
import com.example.b2c.widget.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 立即购买、购物车购买------确认订单页面
 */
public class CheckOrderActivity extends TempBaseActivity implements OnClickListener {
    private final static  int  RQSELECTADDRESS  =  10;
    public static CheckOrderActivity instance = null;
    /**
     * 由立即购买页面传递过来的参数
     */
    private int sampleId, sampleSKUId, num,shopWarehouseId;

    /**
     * 页面上的组件
     */
    private LinearLayout ll_to_select,ll_receive_info,ll_select_addr,ll_cash_info;

    private TextView tv_to_select,tv_receive_name,tv_receive_mobile,tv_receive_address;
    private TextView tv_cash_name,tv_cash_mobile,tv_cash_address;
    private TextView tv_address_notice;

    /**
     * 确认订单列表集合
     */
    private ListView lv_check_order;


    /**
     * 页面最底下按钮
     */
    private SettingItemView2 siv_coupon;
    private TextView tv_total_price,tv_delivery_fee;
    private Button btn_check_order;


    private CheckOrderAdapter adapter;


    private String confirmStr = "";
    /**
     * 确认订单详情页
     * 返回参数
     */
    private ConfirmCartResult confirmcart_result;
    private List<CartShopDetail> cart_list;
    private List<Coupon> coupon_List;
    private ShoppingAddressDetail receive_address;
    private ShoppingAddressDetail pay_address;

    /**
     * 选择完收款收货id的返回值
     */
    private int receive_address_id = -1 ,pay_address_id = -1;

    private int couponId = 0;
    private Intent i;

    //优惠金额
    private String couponCash ="0";

    //创建Handler对象
   private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    System.out.println("handleMessage thread id " + Thread.currentThread().getId());
                    double totalPrice = NumberUtil.GetDoubleValue(confirmcart_result.getTotalOrdersPrice()) ;
                    double totalDeliveryFee = 0 ;
                    //遍历所有的店铺
                    for(CartShopDetail shop : instance.cart_list){
                        for(ShopWareHouse depot : shop.getShopWareHouseList()){
                            for(DeliveryFeeDetail dfd : depot.getDeliveryFeeList()){
                                if(dfd.isChecked()){
                                    totalDeliveryFee +=  NumberUtil.GetDoubleValue(dfd.getDeliveryFee());
                                    continue;
                                }
                            }
                        }
                    }
                    totalPrice += totalDeliveryFee+NumberUtil.GetDoubleValue(couponCash);
                    totalPrice =totalPrice - NumberUtil.GetDoubleValue(couponCash);
//                  ToastHelper.makeToast(""+totalPrice+totalDeliveryFee);
                    tv_total_price.setText(Configs.CURRENCY_UNIT+NumberUtil.GetStringValue(totalPrice,null));
                    tv_delivery_fee.setText("("+mTranslatesString.getCommon_hanyunfei()+Configs.CURRENCY_UNIT+NumberUtil.GetStringValue(totalDeliveryFee,null)+")");
                    break;
            }
        }
    };


    @Override
    public int getRootId() {
        return R.layout.check_order_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        i = getIntent();
        Bundle bundle = i.getExtras();
        confirmStr = bundle.getString("confirmStr");
        initView();
        if (confirmStr == null) {
            /**
             * 立即购买下单
             */
            sampleId = bundle.getInt("sampleId");
            sampleSKUId = bundle.getInt("sampleSKUId");
            num = bundle.getInt("num");
            shopWarehouseId = bundle.getInt("shopWarehouseId");
            initDataByBuy();
        } else {
            /**
             * 购物车购买下单
             */
            initDataByCart();
        }
    }

    public void initView() {
        instance = this;

        ll_select_addr = (LinearLayout) findViewById(R.id.ll_select_addr);
        ll_receive_info = (LinearLayout) findViewById(R.id.ll_receive_info);
        ll_cash_info = (LinearLayout) findViewById(R.id.ll_cash_info);

        tv_to_select = (TextView) findViewById(R.id.tv_to_select);
        tv_receive_name = (TextView) findViewById(R.id.tv_receive_name);
        tv_receive_mobile = (TextView) findViewById(R.id.tv_receive_mobile);
        tv_receive_address = (TextView) findViewById(R.id.tv_receive_address);
        tv_cash_name = (TextView) findViewById(R.id.tv_cash_name);
        tv_cash_mobile = (TextView) findViewById(R.id.tv_cash_mobile);
        tv_cash_address = (TextView) findViewById(R.id.tv_cash_address);
        tv_address_notice = (TextView) findViewById(R.id.tv_address_notice);

        lv_check_order = (ListView) findViewById(R.id.lv_check_order);

        siv_coupon = (SettingItemView2) findViewById(R.id.siv_coupon);
        tv_total_price = (TextView) findViewById(R.id.tv_total_price);
        tv_delivery_fee = (TextView) findViewById(R.id.tv_delivery_fee);

        btn_check_order = (Button) findViewById(R.id.btn_check_order);

        ll_select_addr.setOnClickListener(this);
        siv_coupon.setOnClickListener(this);
        btn_check_order.setOnClickListener(this);

        initText();
    }

    public void initText() {
        setTitle(mTranslatesString.getCommon_makesureorder());
        siv_coupon.setTv_text(mTranslatesString.getCommon_coupons());
        tv_delivery_fee.setText(mTranslatesString.getCommon_unincludedelivery());
        btn_check_order.setText(mTranslatesString.getCommon_makesureorder());
    }


    /**
     * 购物车下单到确认页面初始化
     */
    public void initDataByCart() {
        showLoading();
        Map map = new HashMap();
        map.put("confirmStr", confirmStr);
        if(receive_address_id != -1){
            map.put("shoppingAddressId",receive_address_id);
        }
        if(pay_address_id != -1){
            map.put("payAddressId",pay_address_id);
        }
        rdm.ConfirmCart(isLogin, userId, token, map);
        rdm.confirmcartListener = new ConfirmCartListener() {
            @Override
            public void onSuccess(ConfirmCartResult confirmcart_result) {
                instance.confirmcart_result = confirmcart_result;
                cart_list = confirmcart_result.getCartShopList();
                receive_address = confirmcart_result.getShoppingAddress();
                pay_address = confirmcart_result.getPayAddress();
                coupon_List = confirmcart_result.getCouponList();
                initCartIds();
                initSurface();
            }
            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
                finish();
            }
            @Override
            public void onFinish() {
                dissLoading();
            }

            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
                finish();
            }
        };
    }

    /**
     * 初始化仓库层的cartIds串
     */
    private void initCartIds() {
        for (int i = 0; i < cart_list.size(); i++) {
            for (int j = 0; j < cart_list.get(i).getShopWareHouseList().size(); j++) {
                String scIds="";
                for(int k = 0; k < cart_list.get(i).getShopWareHouseList().get(j).getShoppingCartList().size(); k++){
                    scIds += cart_list.get(i).getShopWareHouseList().get(j).getShoppingCartList().get(k).getShoppingCartId()+",";
                }
                if(scIds.endsWith(",")){
                    scIds = scIds.substring(0,scIds.length()-1);
                }
                cart_list.get(i).getShopWareHouseList().get(j).setCartIds(scIds);
            }
        }
    }
    /**
     * 立即购买到确认订单页 初始化方法
     */
    public void initDataByBuy() {
        showLoading();
        Map map = new HashMap();
        map.put("sampleId",sampleId);
        if (sampleSKUId != -1){
            map.put("sampleSKUId", "" + sampleSKUId);
        }
        map.put("num",num);
        map.put("shopWarehouseId",shopWarehouseId);
        if(receive_address_id != -1){
            map.put("shoppingAddressId",receive_address_id);
        }
        if(pay_address_id != -1){
            map.put("payAddressId",pay_address_id);
        }
        rdm.BuyNow(isLogin, userId, token, map);
        rdm.confirmcartListener = new ConfirmCartListener() {

            @Override
            public void onFinish() {
                dissLoading();
            }
            @Override
            public void onSuccess(ConfirmCartResult confirmcart_result) {
                instance.confirmcart_result = confirmcart_result;
                cart_list = confirmcart_result.getCartShopList();
                receive_address = confirmcart_result.getShoppingAddress();
                pay_address = confirmcart_result.getPayAddress();
                coupon_List = confirmcart_result.getCouponList();
                initCartIds();
                initSurface();
            }
            @Override
            public void onError(int errorNO, String errorInfo) {
//                ToastHelper.makeErrorToast(errorInfo);
                //没有收货地址
                if(errorNO == 1){
                    Intent intent =getIntent().putExtra("error",errorInfo);
                    setResult(1099,intent);
                }else {
                    ToastHelper.makeErrorToast(errorInfo);
                }
                finish();
            }
            @Override
            public void onLose() {
                ToastHelper.makeErrorToast(request_failure);
                finish();
            }

        };
    }
    public void  initAddressLyt(){
        tv_receive_name.setText(mTranslatesString.getCommon_receiveman()+": "+Utils.cutNull(receive_address.getFirstName())+
                " "+Utils.cutNull(receive_address.getLastName()));
        tv_receive_mobile.setText(Utils.cutNull(receive_address.getMobile()));
        tv_receive_address.setText(mTranslatesString.getCommon_receivelocation()+": "+Utils.cutNull(receive_address.getProvinceName()+
                " "+receive_address.getCityName()+
                " "+receive_address.getAddress()));
        tv_address_notice.setText("("+mTranslatesString.getCommon_addresssuggest()+")");
        if(pay_address != null) {
            ll_cash_info.setVisibility(View.VISIBLE);
            tv_cash_name.setText(mTranslatesString.getCommon_cashman()+": " + Utils.cutNull(pay_address.getFirstName()) +
                    " " + Utils.cutNull(pay_address.getLastName()));
            tv_cash_mobile.setText(Utils.cutNull(pay_address.getMobile()));
            tv_cash_address.setText(mTranslatesString.getConnom_shoukuandizhi()+": " + Utils.cutNull(pay_address.getProvinceName() +
                    " " + pay_address.getCityName() +
                    " " + pay_address.getAddress()));
        }else {
            ll_cash_info.setVisibility(View.GONE);
        }
    }
    /**
     * 初始化界面
     */
    public void initSurface() {
        if (receive_address != null) {
            adapter = new CheckOrderAdapter(instance, cart_list,uiHandler);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    initAddressLyt();
                    lv_check_order.setAdapter(adapter);
                    siv_coupon.setTv_doc(mTranslatesString.getCommon_notuse());
                    tv_total_price.setText(Configs.CURRENCY_UNIT+confirmcart_result.getTotalOrdersPrice());
                }
            });
        }
    }

    /**
     * 购物车确认下单接口
     * @throws JSONException
     */
    public void postOrder() throws JSONException {
        int receiveAddressId= receive_address.getId();
        int payAddressId = 0;
        if(pay_address != null && pay_address.getId()!=receive_address.getId()){
            payAddressId = pay_address.getId();
        }
        JSONArray orderInfo = new JSONArray();
        if (cart_list != null) {
            for (int i = 0; i < cart_list.size(); i++) {
                LinearLayout check_item_layout = (LinearLayout) lv_check_order.getChildAt(i);
                EditText et_remark = (EditText) check_item_layout.findViewById(R.id.et_remark);
                String remark = et_remark.getText().toString();
                for (int j = 0; j < cart_list.get(i).getShopWareHouseList().size(); j++) {
                    JSONObject shop_bean = new JSONObject();
                    shop_bean.put("remark", remark);
                    shop_bean.put("scIds", cart_list.get(i).getShopWareHouseList().get(j).getCartIds());
                    int deliveryCompanyId = -1;
                    for(int k = 0; k < cart_list.get(i).getShopWareHouseList().get(j).getDeliveryFeeList().size(); k++){
                        if(cart_list.get(i).getShopWareHouseList().get(j).getDeliveryFeeList().get(k).isChecked()){
                             deliveryCompanyId = cart_list.get(i).getShopWareHouseList().get(j).getDeliveryFeeList().get(k).getDeliveryCompanyId();
                             continue;
                         }
                    }
                    if(deliveryCompanyId == -1){
                        ToastHelper.makeToast(mTranslatesString.getCommon_pleaseselectdeliver());
                        return;
                    }else {
                        shop_bean.put("deliveryCompanyId", deliveryCompanyId);
                    }
                    orderInfo.put(i, shop_bean);
                }
            }
            Map map = new HashMap();
            map.put("cartShopList",orderInfo.toString());
            map.put("shoppingAddressId",receiveAddressId);
            if(payAddressId>0){
                map.put("payAddressId",payAddressId);
            }
            if(couponId > 0){
                map.put("couponId",couponId);
            }
            showProgressDialog(Waiting);
            rdm.PostOrder(isLogin, userId, token, map);
            rdm.shoppingcartsettleListener = new ShoppingCartSettleListener() {
                @Override
                public void onSuccess(int errorNO, String errorInfo) {
                    ToastHelper.makeToast(errorInfo);
                    Intent intent = new Intent(CheckOrderActivity.this, OrderActivityNew.class);
                    startActivity(intent);
                    finish();
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
                    UIUtils.showToast(CheckOrderActivity.this,request_failure);
                }

            };
        }
    }

    /**
     * 立即购买确认下单接口
     */
    public void postBuyOrder() {
        int receiveAddressId= receive_address.getId();
        int payAddressId = 0;
        if(pay_address != null && pay_address.getId()!=receive_address.getId()){
            payAddressId = pay_address.getId();
        }
        int deliveryCompanyId = 0;
        for(DeliveryFeeDetail dfd : cart_list.get(0).getShopWareHouseList().get(0).getDeliveryFeeList()){
            if(dfd.isChecked()){
                deliveryCompanyId =dfd.getDeliveryCompanyId();
                break;
            }
        }
        if(deliveryCompanyId <= 0){
            ToastHelper.makeToast(mTranslatesString.getCommon_pleaseselectdeliver());
            return;
        }
        LinearLayout check_item_layout = (LinearLayout) lv_check_order.getChildAt(0);
        EditText et_remark = (EditText) check_item_layout.findViewById(R.id.et_remark);
        String remark = et_remark.getText().toString();
        Map map = new HashMap();
        map.put("sampleId",sampleId);
        if(sampleSKUId>0){
            map.put("sampleSKUId",sampleSKUId);
        }
        map.put("num",num);
        map.put("shopWarehouseId",shopWarehouseId);
        map.put("shoppingAddressId",receiveAddressId);
        map.put("deliveryCompanyId",deliveryCompanyId);
        map.put("remark",remark);
        if(payAddressId>0){
            map.put("payAddressId",payAddressId);
        }
        if(couponId > 0){
            map.put("couponId",couponId);
        }
        showProgressDialog(Waiting);
        rdm.PostBuyOrder(isLogin, userId, token, map);
        rdm.mResponseListener = new ResponseListener() {
            @Override
            public void onFinish() {
                dismissProgressDialog();
            }

            @Override
            public void onLose() {
                UIUtils.showToast(CheckOrderActivity.this,request_failure);
            }

            @Override
            public void onSuccess(String errorInfo) {
                ToastHelper.makeToast(errorInfo);
                Intent intent = new Intent(CheckOrderActivity.this, OrderActivityNew.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(int errorNO, String errorInfo) {
                ToastHelper.makeErrorToast(errorInfo);
            }

        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {// 选择地址返回的onactivityresult
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }
        switch (requestCode) {
            case RQSELECTADDRESS:
                receive_address = (ShoppingAddressDetail) data.getSerializableExtra("receiveAddress");
                receive_address_id = receive_address.getId();
                Serializable paySerializable = data.getSerializableExtra("payAddress");
                if(paySerializable != null){
                    pay_address = (ShoppingAddressDetail) paySerializable;
                    pay_address_id = pay_address.getId();
                }else {
                    pay_address = null;
                    pay_address_id = -1;
                }
//              initAddressLyt();
                if (confirmStr == null) {
                    initDataByBuy();
                }else {
                    initDataByCart();
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_select_addr:
                Intent i_saa = new Intent(this,SelectAddressActivity.class);
                i_saa.putExtra("rId",receive_address.getId());
                if(pay_address!=null){
                    i_saa.putExtra("pId",pay_address.getId());
                }
                startActivityForResult(i_saa, RQSELECTADDRESS);
                break;
            case R.id.siv_coupon:
                if(coupon_List==null || coupon_List.size()==0){
                    ToastHelper.makeToast(mTranslatesString.getCommon_nousefulcoupons());
                    return;
                }
                HashMap mapCk =new HashMap();
                final List<HashMap<String,String>> couponList = new ArrayList<>();
                for(Coupon coupon :coupon_List){
                    if(coupon.isChecked()){
                        mapCk.put(coupon.getId()+"",coupon.getName()+"("+Configs.CURRENCY_UNIT + coupon.getDiscount()+")");
                    }
                    HashMap<String,String>  map =new HashMap();
                    map.put(coupon.getId()+"",coupon.getName()+"("+Configs.CURRENCY_UNIT + coupon.getDiscount()+")");
                    couponList.add(map);
                }
                HashMap<String,String>  map =new HashMap();
                map.put(coupon_List.size()+"",mTranslatesString.getCommon_notuse());
                couponList.add(map);
                new ItemsDialogHelper(this,mTranslatesString.getCommon_coupons(),couponList,mapCk,new ItemsDialogHelper.AbstractOnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position) {
                        if(position == couponList.size()) {
                            couponId = 0;
                            couponCash = "0";
                        }
                        initCouponPop(position);
                        //发送消息更新价格
                        Message message = uiHandler.obtainMessage();
                        message.what =1;
                        uiHandler.sendMessage(message);
                    }
                }).show();
                break;
            case R.id.btn_check_order:
                if (confirmStr == null) {
                    Logs.d("立即购买");
                    postBuyOrder();
                } else {
                    try {
                        Logs.d("购物车购买");
                        postOrder();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 初始画优惠券选项
     * @param position
     */
    private void initCouponPop(int position){
        boolean flag = false;
        for (int i1 = 0; i1 < coupon_List.size(); i1++) {
            if(i1==position){
                flag = true;
                coupon_List.get(i1).setChecked(true);
                siv_coupon.setTv_doc(coupon_List.get(i1).getName()+"("+Configs.CURRENCY_UNIT + coupon_List.get(i1).getDiscount()+")");
                couponId = coupon_List.get(i1).getId();
                couponCash = coupon_List.get(i1).getDiscount();
            }else {
                coupon_List.get(i1).setChecked(false);
            }
        }
        if(!flag){
            siv_coupon.setTv_doc(mTranslatesString.getCommon_notuse());
        }

    }


}
