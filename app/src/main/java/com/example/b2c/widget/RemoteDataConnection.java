package com.example.b2c.widget;

import android.util.Log;

import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.response.BankListResult;
import com.example.b2c.net.response.BuyerInfo;
import com.example.b2c.net.response.CouponListResult;
import com.example.b2c.net.response.OrderCellListResult;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.response.ResponseResult;
import com.example.b2c.net.response.common.LanguageListResult;
import com.example.b2c.net.util.HttpClientUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RemoteDataConnection extends RemoteDataManager {
    private static RemoteDataConnection sharedConnection = null;

    public String GetShoppingAddressListUrl = "user/shoppingAddressList.htm";
    protected final GsonBuilder builder = new GsonBuilder();

    public static RemoteDataConnection getInstance() {
        if (sharedConnection == null) {
            sharedConnection = new RemoteDataConnection();

        }

        return sharedConnection;
    }

    // 获取银行列表功能
    public void GetBankList(boolean isLogin, int userId, String token) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetBankCardWorker(isLogin, userId, token));
        executor.shutdown();
    }

    public class GetBankCardWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;

        public GetBankCardWorker(boolean isLogin, int userId, String token) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
        }

        @Override
        public void run() {
            doGetBankList(isLogin, userId, token);
        }
    }

    public void doGetBankList(boolean isLogin, int userId, String token) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL
                    + "user/bankList.htm", isLogin, userId, token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    BankListResult banklist_result = gson.fromJson(
                            data_json.toString(), BankListResult.class);
                    getbanklistListener
                            .onSuccess(banklist_result.getBankList());
                } else {
                    getbanklistListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                getbanklistListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doGetBankList", e.getMessage());
            getbanklistListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetBankList", e.getMessage());
        } finally {
            getbanklistListener.onFinish();
        }
    }

    // 版定用户银行卡功能
    public void BindBankCard(boolean isLogin, int userId, String token,
                             int bankId, String holderName, String mobile, String bankCard,
                             int isDefault) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new BindBankCardWorker(isLogin, userId, token, bankId,
                holderName, mobile, bankCard, isDefault));
        executor.shutdown();
    }

    public class BindBankCardWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        int bankId;
        String holderName;
        String mobile;
        String bankCard;
        int isDefault;

        public BindBankCardWorker(boolean isLogin, int userId, String token,
                                  int bankId, String holderName, String mobile, String bankCard,
                                  int isDefault) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.bankId = bankId;
            this.holderName = holderName;
            this.mobile = mobile;
            this.bankCard = bankCard;
            this.isDefault = isDefault;
        }

        @Override
        public void run() {
            doBindBankList(isLogin, userId, token, bankId, holderName, mobile,
                    bankCard, isDefault);
        }
    }

    public void doBindBankList(boolean isLogin, int userId, String token,
                               int bankId, String holderName, String mobile, String bankCard,
                               int isDefault) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("bankId", bankId + "");
        map.put("holderName", holderName);
        map.put("mobile", mobile);
        map.put("bankCard", bankCard);
        map.put("isDefault", isDefault + "");
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "user/insertBankCard.htm", map, isLogin, userId, token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    responseListener.onSuccess(result.getErrorInfo());
                } else {
                    responseListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                responseListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doBindBankList", e.getMessage());
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doBindBankList", e.getMessage());
        } finally {
            responseListener.onFinish();
        }
    }

    // 修改用户银行卡功能
    public void ChangeBankCard(boolean isLogin, int userId, String token,
                               int id, int bankId, String holderName, String mobile,
                               String bankCard, int isDefault) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new ChangeBankCardWorker(isLogin, userId, token, id,
                bankId, holderName, mobile, bankCard, isDefault));
        executor.shutdown();
    }

    public class ChangeBankCardWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        int bankId;
        String holderName;
        String mobile;
        String bankCard;
        int isDefault;
        int id;

        public ChangeBankCardWorker(boolean isLogin, int userId, String token,
                                    int id, int bankId, String holderName, String mobile,
                                    String bankCard, int isDefault) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.bankId = bankId;
            this.holderName = holderName;
            this.mobile = mobile;
            this.bankCard = bankCard;
            this.isDefault = isDefault;
            this.id = id;
        }

        @Override
        public void run() {
            doChangeBankList(isLogin, userId, token, id, bankId, holderName,
                    mobile, bankCard, isDefault);
        }
    }

    public void doChangeBankList(boolean isLogin, int userId, String token,
                                 int id, int bankId, String holderName, String mobile,
                                 String bankCard, int isDefault) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id + "");
        map.put("bankId", bankId + "");
        map.put("holderName", holderName);
        map.put("mobile", mobile);
        map.put("bankCard", bankCard);
        map.put("isDefault", isDefault + "");
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "user/updateBankCard.htm", map, isLogin, userId, token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    responseListener.onSuccess(result.getErrorInfo());
                } else {
                    responseListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                responseListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doChangeBankList", e.getMessage());
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doChangeBankList", e.getMessage());
        } finally {
            responseListener.onFinish();
        }
    }

    // 修改用户银行卡功能
    public void DeleteBankCard(boolean isLogin, int userId, String token,
                               int bankId) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new DeleteBankCardWorker(isLogin, userId, token,
                bankId));
        executor.shutdown();
    }

    public class DeleteBankCardWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        int bankId;

        public DeleteBankCardWorker(boolean isLogin, int userId, String token,
                                    int bankId) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.bankId = bankId;
        }

        @Override
        public void run() {
            doDeleteBankList(isLogin, userId, token, bankId);
        }
    }

    public void doDeleteBankList(boolean isLogin, int userId, String token,
                                 int bankId) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL
                            + "user/deleteBankCard/" + bankId + ".htm", isLogin,
                    userId, token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    responseListener.onSuccess(result.getErrorInfo());
                } else {
                    responseListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                responseListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doDeleteBankList", e.getMessage());
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doDeleteBankList", e.getMessage());
        } finally {
            responseListener.onFinish();
        }
    }

    //支付订单时获取订单号列表
    public void getOrderCellList(boolean isLogin, int userId, String token,
                                 String ids) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetOrderCellListWorker(isLogin, userId, token,
                ids));
        executor.shutdown();
    }

    public class GetOrderCellListWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        String ids;

        public GetOrderCellListWorker(boolean isLogin, int userId, String token,
                                      String ids) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.ids = ids;
        }

        @Override
        public void run() {
            doGetOrderCellList(isLogin, userId, token, ids);
        }

    }

    private void doGetOrderCellList(boolean isLogin, int userId, String token, String ids) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();
        map.put("ids", ids);
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                            + "trade/orderInfo/orderList.htm", map, isLogin, userId,
                    token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    OrderCellListResult bankcardlist_result = gson.fromJson(
                            data_json.toString(), OrderCellListResult.class);
                    orderCellListListener.onSuccess(bankcardlist_result.getOrderList());
                } else {
                    orderCellListListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                orderCellListListener.lose();
            }
        } catch (NetException e) {
            Log.e("doDeleteShoppingCart", e.getMessage());
            orderCellListListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doDeleteShoppingCart", e.getMessage());
        }


    }

    //更改订单状态
    public void updateOrderStatus(boolean isLogin, int userId, String token, String ids, String orderStatus) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new UpdateOrderStatusWorker(isLogin, userId, token, ids, orderStatus));
        executor.shutdown();
    }

    public class UpdateOrderStatusWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        String ids;
        String orderStatus;

        public UpdateOrderStatusWorker(boolean isLogin, int userId, String token,
                                       String ids, String orderStatus) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.ids = ids;
            this.orderStatus = orderStatus;
        }

        @Override
        public void run() {
            doUpdateOrderStatus(isLogin, userId, token, ids, orderStatus);

        }

    }

    public void doUpdateOrderStatus(boolean isLogin, int userId, String token, String ids, String orderStatus) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("ids", ids);
        map.put("orderStatus", orderStatus);
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                            + "trade/updateOrderStatus.htm", map, isLogin, userId,
                    token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    responseListener.onSuccess(result.getErrorInfo());
                } else {
                    responseListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                responseListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doDeleteShoppingCart", e.getMessage());
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doDeleteShoppingCart", e.getMessage());
        } finally {
            responseListener.onFinish();
        }


    }

    class DoubleTypeAdapter implements JsonSerializer<Double> {
        @Override
        public JsonElement serialize(Double d, Type type,
                                     JsonSerializationContext context) {
            DecimalFormat format = new DecimalFormat("##0.00");
            String temp = format.format(d);
            System.out.println(temp);
            JsonPrimitive pri = new JsonPrimitive(temp);
            return pri;
        }

    }

    /**
     * 获取买家信息
     */
    public void getBuyerInfo(boolean isLogin, int userId, String token) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetBuyerInfoWorker(isLogin, userId, token));
        executor.shutdown();
    }

    public class GetBuyerInfoWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;

        public GetBuyerInfoWorker(boolean isLogin, int userId, String token) {
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
        }

        @Override
        public void run() {
            doGetBuyerInfo(isLogin, userId, token);
        }
    }

    private void doGetBuyerInfo(boolean isLogin, int userId, String token) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL + "user/getUserInfo.htm",
                    null,
                    isLogin,
                    userId,
                    token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    BuyerInfo buyerInfo = gson.fromJson(data_json.toString(), BuyerInfo.class);
                    mOneDataListener.onSuccess(buyerInfo, result.getErrorInfo());
                } else {
                    mOneDataListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mOneDataListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doGetBuyerInfo", e.getMessage());
            mOneDataListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetBuyerInfo", e.getMessage());
        } finally {
            mOneDataListener.onFinish();
        }


    }

    /**
     * 修改买家信息
     */
    public void updateBuyerInfo(Map map, boolean isLogin, int userId, String token) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new UpdateBuyerInfoWorker(map, isLogin, userId, token));
        executor.shutdown();
    }

    public class UpdateBuyerInfoWorker implements Runnable {
        Map map;
        boolean isLogin;
        int userId;
        String token;

        public UpdateBuyerInfoWorker(Map map, boolean isLogin, int userId, String token) {
            super();
            this.map = map;
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
        }

        @Override
        public void run() {
            doUpdateBuyerInfo(map, isLogin, userId, token);
        }
    }

    private void doUpdateBuyerInfo(Map map, boolean isLogin, int userId, String token) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL + "user/updateUserInfo.htm",
                    map,
                    isLogin,
                    userId,
                    token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    responseListener.onSuccess(result.getErrorInfo());
                } else {
                    responseListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                responseListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doGetBuyerInfo", e.getMessage());
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetBuyerInfo", e.getMessage());
        } finally {
            responseListener.onFinish();
        }

    }

    /*
     *语言列表
     */
    public void getLanguageRequest() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getLanguageWorker());
        executor.shutdown();
    }

    public class getLanguageWorker implements Runnable {

        @Override
        public void run() {
            doLanguageRequest();
        }

    }
//{"meta":{"errorNO":0,"errorInfo":"成功","success":true},"data":{"rows":[{"name":"English","code":"en"},{"name":"中文","code":"default"}]}}

    private void doLanguageRequest() {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL + "common/listLanguageChoice.htm", false, -1, "");
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                JSONObject languageListResult = new JSONObject(strResult)
                        .getJSONObject("data");
                if (result.isSuccess()) {
                    LanguageListResult listResult = gson.fromJson(languageListResult.toString(), LanguageListResult.class);
                    mLanguageListener.onSuccess(listResult.getRows(), result.getErrorInfo());
                } else {
                    mLanguageListener.onFailure(result.getErrorInfo());
                }
            } else {
                mLanguageListener.onLose();
            }
        } catch (NetException e) {
            Log.e("mPageListListener", e.getMessage());
            mPageListHasNextListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mPageListListener", e.getMessage());
        } finally {
            mLanguageListener.onFinish();
        }
    }


    /**
     * 买家获取优惠券列表
     */
    public void getCouponList( boolean isNeedLogin,Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetCouponListWorker(isNeedLogin,map));
        executor.shutdown();
    }

    public class GetCouponListWorker implements Runnable {

        boolean isNeedLogin;
        Map map;

        public GetCouponListWorker(boolean isNeedLogin,Map map) {

            this.isNeedLogin =isNeedLogin;
            this.map =map;
        }

        @Override
        public void run() {
            doGetCouponList(isNeedLogin,map);
        }

    }
    private void doGetCouponList(boolean isNeedLogin,Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL + "user/couponList.htm", map,isNeedLogin, UserHelper.getBuyerId(), UserHelper.getBuyertoken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),ResponseResult.class);
                JSONObject data = new JSONObject(strResult).getJSONObject("data");
                if (result.isSuccess()) {
                    CouponListResult couponListResult = gson.fromJson(data.toString(), CouponListResult.class);
                    mPageListHasNextListener.onSuccess(couponListResult.getCoupons(), couponListResult.isHasNext());
                } else {
                    mPageListHasNextListener.onError(result.getErrorNO(),result.getErrorInfo());
                }
            } else {
                mPageListHasNextListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doGetCouponList", e.getMessage());
            mPageListHasNextListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetCouponList", e.getMessage());
        } finally {
            mPageListHasNextListener.onFinish();
        }
    }

}
