package com.example.b2c.widget;

import android.util.Log;

import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.response.BuyerInfo;
import com.example.b2c.net.response.RefundListResult;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.response.ResponseResult;
import com.example.b2c.net.response.base.DataResult;
import com.example.b2c.net.response.base.PageListLogistic;
import com.example.b2c.net.response.common.BaseStringResult;
import com.example.b2c.net.response.logistics.PageListTemplate;
import com.example.b2c.net.response.logistics.PageListWithdraw;
import com.example.b2c.net.response.seller.AssetsDetailResult;
import com.example.b2c.net.response.seller.CategoryDetailResult;
import com.example.b2c.net.response.seller.CategoryListResult;
import com.example.b2c.net.response.seller.DepotListResult;
import com.example.b2c.net.response.seller.FinaceCellList;
import com.example.b2c.net.response.seller.HomeIndexListResult;
import com.example.b2c.net.response.seller.LogisticDetailFarther;
import com.example.b2c.net.response.seller.OrderDetailLogiscticResult;
import com.example.b2c.net.response.seller.PageListOrder;
import com.example.b2c.net.response.seller.PageListSettle;
import com.example.b2c.net.response.seller.PageListSettleYet;
import com.example.b2c.net.response.seller.PageListShop;
import com.example.b2c.net.response.seller.SellerCompanyEntry;
import com.example.b2c.net.response.seller.ShopDetail;
import com.example.b2c.net.response.seller.ShopEntry;
import com.example.b2c.net.response.seller.TodayListResult;
import com.example.b2c.net.util.HttpClientUtil;
import com.example.b2c.net.util.Logs;
import com.google.gson.Gson;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用途：
 * Created by milk on 16/11/1.
 * 邮箱：649444395@qq.com
 */

public class SellerDataConnection extends RemoteDataConnection {
    public static SellerDataConnection mSellerDataConnection = null;

    public static SellerDataConnection getInstance() {
        if (mSellerDataConnection == null) {
            mSellerDataConnection = new SellerDataConnection();
        }

        return mSellerDataConnection;
    }

    /**
     * 没有回传数据
     *
     * @param url
     */
    public void getBaseRequest(String url) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getBaseWorker(url));
        executor.shutdown();
    }

    public class getBaseWorker implements Runnable {
        String url;

        public getBaseWorker(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            doBaseRequest(url);
        }

    }

    private void doBaseRequest(String url) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(url, null, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    mResponseListener.onSuccess(result.getErrorInfo());
                } else {
                    mResponseListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mResponseListener.onLose();
            }

        } catch (NetException e) {
            Log.e("mPageListListener", e.getMessage());
            mResponseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mPageListListener", e.getMessage());
        } finally {
            mResponseListener.onFinish();
        }
    }

    /**
     * post请求
     * 没有回传数据
     *
     * @param url
     * @param map
     */
    public void postBaseMapRequest(String url, Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new PostBaseMapWorker(url, map));
        executor.shutdown();
    }

    public class PostBaseMapWorker implements Runnable {
        String url;
        Map map;

        public PostBaseMapWorker(String url, Map map) {
            this.url = url;
            this.map = map;
        }

        @Override
        public void run() {
            doBaseMapRequest(url, map);
        }

    }

    private void doBaseMapRequest(String url, Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(url, map, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    mResponseListener.onSuccess(result.getErrorInfo());
                } else {
                    mResponseListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mResponseListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doBaseMapRequest", e.getMessage());
            mResponseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doBaseMapRequest", e.getMessage());
        } finally {
            mResponseListener.onFinish();
        }
    }

    /**
     * 订单概况
     *
     * @param url
     */
    public void getOrderSituation(String url) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getOrderSituationWorker(url));
        executor.shutdown();
    }


    public class getOrderSituationWorker implements Runnable {
        String url;

        public getOrderSituationWorker(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            doOrderSituation(url);
        }
    }

    private void doOrderSituation(String url) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(url, null, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    HomeIndexListResult mHomeIndexListResult = gson.fromJson(data_json.toString(), HomeIndexListResult.class);
                    mHomeIndexListListener.onSuccess(mHomeIndexListResult.getRows(), mHomeIndexListResult.getShop());
                } else {
                    mHomeIndexListListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mHomeIndexListListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doOrderSituation", e.getMessage());
            mHomeIndexListListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doOrderSituation", e.getMessage());
        } finally {
            mHomeIndexListListener.onFinish();
        }
    }

    /**
     * 今日订单
     *
     * @param url
     */
    public void getTodayOrderList(String url) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getTodayOrderListWorker(url));
        executor.shutdown();
    }

    public class getTodayOrderListWorker implements Runnable {
        public String url;

        public getTodayOrderListWorker(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            doTodayOrderList(url);
        }
    }

    public void doTodayOrderList(String url) {

        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(url, null, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    TodayListResult mPageList = gson.fromJson(data_json.toString(), TodayListResult.class);
                    mTodayOrderListener.onSuccess(mPageList.getRows(), mPageList.getOrderTotalMoney(), mPageList.getOrderTotalCount(), mPageList.getBuyerTotalCount(), mPageList.getSampleTotalCount());
                } else {
                    mTodayOrderListener.onError(result.getErrorNO(),
                            result.getErrorInfo());

                }
            } else {
                mHomeIndexListListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doOrderSituation", e.getMessage());
            mHomeIndexListListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doOrderSituation", e.getMessage());
        } finally {
            mHomeIndexListListener.onFinish();
        }
    }

    /**
     * 获取所有快递列表
     *
     * @param url
     * @param mPage
     * @param type
     */
    public void getAllLogistictList(String url, int mPage, int type) {

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getHasNextListWorker(url, mPage, type));
        executor.shutdown();
    }

    public class getHasNextListWorker implements Runnable {
        String url;
        int page;
        int type;

        public getHasNextListWorker(String url, int mPage, int type) {
            this.url = url;
            this.page = mPage;
            this.type = type;
        }


        @Override
        public void run() {
            doHasNextList(url, page, type);
        }
    }

    public void doHasNextList(String url, int mPage, int type) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("startRow", mPage + "");
        if (type != -1) {
            params.put("orderStatus", type + "");

        }
        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    PageListLogistic mPageList = gson.fromJson(data_json.toString(), PageListLogistic.class);
                    mPageListHasNextListener.onSuccess(mPageList.getRows(), mPageList.isHasNext());
                } else {
                    mPageListHasNextListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mPageListHasNextListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doOrderSituation", e.getMessage());
            mPageListHasNextListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doOrderSituation", e.getMessage());
        } finally {
            mPageListHasNextListener.onFinish();
        }
    }

    /**
     * 获取产品详情
     *
     * @param url
     */
    public void getGoodsDetail(String url) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getListWorker(url));
        executor.shutdown();
    }

    public class getListWorker implements Runnable {
        String url;

        public getListWorker(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            dogetPageList(url);
        }
    }


    private void dogetPageList(String url) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(url, null, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    ShopDetail mPageList = gson.fromJson(data_json.toString(), ShopDetail.class);
                    mOneDataListener.onSuccess(mPageList, result.getErrorInfo());
                } else {
                    mOneDataListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mOneDataListener.onLose();
            }
        } catch (NetException e) {
            Log.e("mPageListListener", e.getMessage());
            mOneDataListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mPageListListener", e.getMessage());
        } finally {
            mOneDataListener.onFinish();
        }
    }

    /**
     * 设置合作快递
     *
     * @param url
     * @param id
     * @param type
     */
    public void getLogisticDetail(String url, int id, int type) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getLogisticWorker(url, id, type));
        executor.shutdown();
    }

    public class getLogisticWorker implements Runnable {
        String url;
        int type;
        int id;

        public getLogisticWorker(String url, int id, int type) {
            this.type = type;
            this.url = url;
            this.id = id;
        }

        @Override
        public void run() {
            doLogisticDetail(url, id, type);
        }
    }

    private void doLogisticDetail(String url, int id, int type) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("deliveryCompanyId", id + "");
        params.put("isDefault", type + "");
        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
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
            Log.e("mPageListListener", e.getMessage());
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mPageListListener", e.getMessage());
        } finally {
            responseListener.onFinish();
        }
    }


    /**
     * 获取订单列表
     *
     * @param url
     * @param mPage
     * @param type
     */
    public void getOrderList(String url, int mPage, int type) {

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getOrderListWorker(url, mPage, type));
        executor.shutdown();
    }

    public class getOrderListWorker implements Runnable {
        String url;
        int page;
        int type;

        public getOrderListWorker(String url, int mPage, int type) {
            this.url = url;
            this.page = mPage;
            this.type = type;
        }


        @Override
        public void run() {
            doOrderList(url, page, type);
        }
    }

    public void doOrderList(String url, int mPage, int type) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("startRow", mPage + "");
        if (type != -1) {
            params.put("orderStatus", type + "");

        }
        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    PageListOrder mPageList = gson.fromJson(data_json.toString(), PageListOrder.class);
                    mPageListHasNextListener.onSuccess(mPageList.getRows(), mPageList.isHasNext());
                } else {
                    mPageListHasNextListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mPageListHasNextListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doOrderSituation", e.getMessage());
            mPageListHasNextListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doOrderSituation", e.getMessage());
        } finally {
            mPageListHasNextListener.onFinish();
        }
    }


    /**
     * 卖家获取退货列表
     *
     * @param url
     * @param mPage
     * @param type
     */
    public void getReturnList(String url, int mPage, int type) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetReturnListWorker(url, mPage, type));
        executor.shutdown();
    }

    public class GetReturnListWorker implements Runnable {
        String url;
        int page;
        int type;

        public GetReturnListWorker(String url, int mPage, int type) {
            this.url = url;
            this.page = mPage;
            this.type = type;
        }

        @Override
        public void run() {
            doGetReturnList(url, page, type);
        }
    }

    public void doGetReturnList(String url, int mPage, int type) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("startRow", mPage + "");
        if (type != -1) {
            params.put("refundStatus", type + "");

        }
        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    RefundListResult mPageList = gson.fromJson(data_json.toString(), RefundListResult.class);
                    mPageListHasNextListener.onSuccess(mPageList.getRefundList(), mPageList.isHasNext());
                } else {
                    mPageListHasNextListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mPageListHasNextListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doGetReturnList", e.getMessage());
            mPageListHasNextListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetReturnList", e.getMessage());
        } finally {
            mPageListHasNextListener.onFinish();
        }
    }

    /*
      修改手机号
       */
    public void getChangePhoneRequest(String url, String phone) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getChangePhoneWorker(url, phone));
        executor.shutdown();
    }

    public class getChangePhoneWorker implements Runnable {
        String url;
        String phone;

        public getChangePhoneWorker(String url, String phone) {
            this.url = url;
            this.phone = phone;
        }

        @Override
        public void run() {
            doChangePhoneRequest(url, phone);
        }

    }

    private void doChangePhoneRequest(String url, String phone) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    mResponseListener.onSuccess(result.getErrorInfo());
                } else {
                    mResponseListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mResponseListener.onLose();
            }

        } catch (NetException e) {
            Log.e("mPageListListener", e.getMessage());
            mResponseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mPageListListener", e.getMessage());
        } finally {
            mResponseListener.onFinish();
        }
    }


    /*
        产品快递详情
   */
    public void getLogisticDetailRequest(String url) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getLogisticDetailWorker(url));
        executor.shutdown();
    }

    public class getLogisticDetailWorker implements Runnable {
        String url;

        public getLogisticDetailWorker(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            doLogisticDetailWorkerRequest(url);
        }

    }

    private void doLogisticDetailWorkerRequest(String url) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(url, null, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    OrderDetailLogiscticResult mPageList = gson.fromJson(data_json.toString(), OrderDetailLogiscticResult.class);
                    mPageListListener.onSuccess(mPageList.getRows());
                } else {
                    mPageListListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mPageListListener.onLose();
            }

        } catch (NetException e) {
            Log.e("mPageListListener", e.getMessage());
            mPageListListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mPageListListener", e.getMessage());
        } finally {
            mPageListListener.onFinish();
        }
    }

    /*
    资产
*/
    public void getAssetsRequest(String url) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getAssetsWorker(url));
        executor.shutdown();
    }

    public class getAssetsWorker implements Runnable {
        String url;

        public getAssetsWorker(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            doAssetsRequest(url);
        }

    }

    private void doAssetsRequest(String url) {
        builder.registerTypeAdapter(Double.class, new DoubleTypeAdapter());

        Gson gson = builder.create();
        try {
            Response response = HttpClientUtil.doPost(url, null, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
//                    AssetsDetailResult mPageList=  JSON.parseObject(data_json.toString(),AssetsDetailResult .class);
                    AssetsDetailResult mPageList = gson.fromJson(data_json.toString(), AssetsDetailResult.class);
                    mTwoStringListener.onSuccess(mPageList.getCanWithdraw() + "", mPageList.getBankCard());
                } else {
                    mTwoStringListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mTwoStringListener.onLose();
            }

        } catch (NetException e) {
            Log.e("mPageListListener", e.getMessage());
            mTwoStringListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mPageListListener", e.getMessage());
        } finally {
            mTwoStringListener.onFinish();
        }
    }

    /**
     * 申请提现
     *
     * @param url
     * @param money
     */
    public void getAssetsDeailRequest(String url, String money) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getAssetsDetailWorker(url, money));
        executor.shutdown();
    }

    public class getAssetsDetailWorker implements Runnable {
        String url;
        String money;

        public getAssetsDetailWorker(String url, String money) {
            this.url = url;
            this.money = money;
        }

        @Override
        public void run() {
            doAssetsDetailMoney(url, money);
        }

    }

    private void doAssetsDetailMoney(String url, String money) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("money", money);
        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    mResponseListener.onSuccess(result.getErrorInfo());
                } else {
                    mResponseListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mResponseListener.onLose();
            }

        } catch (NetException e) {
            Log.e("mPageListListener", e.getMessage());
            mResponseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mPageListListener", e.getMessage());
        } finally {
            mResponseListener.onFinish();
        }
    }

    /**
     * 获取提现列表
     *
     * @param url
     * @param mPage
     * @param type
     */
    public void getWithdrawList(String url, int mPage, int type) {

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getWithdrawListWorker(url, mPage, type));
        executor.shutdown();
    }

    public class getWithdrawListWorker implements Runnable {
        String url;
        int page;
        int type;

        public getWithdrawListWorker(String url, int mPage, int type) {
            this.url = url;
            this.page = mPage;
            this.type = type;
        }


        @Override
        public void run() {
            doWithdrawList(url, page, type);
        }
    }

    public void doWithdrawList(String url, int mPage, int type) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("startRow", mPage + "");
        if (type != -1) {
            params.put("status", type + "");

        }
        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");

                    PageListWithdraw mPageList = gson.fromJson(data_json.toString(), PageListWithdraw.class);
                    mPageListHasNextListener.onSuccess(mPageList.getRows(), mPageList.isHasNext());
                } else {
                    mPageListHasNextListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mPageListHasNextListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doOrderSituation", e.getMessage());
            mPageListHasNextListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doOrderSituation", e.getMessage());
        } finally {
            mPageListHasNextListener.onFinish();
        }
    }

    /**
     * 获取商品列表
     *
     * @param url
     * @param mPage
     * @param type
     */
    public void getShopList(String url, int mPage, int type) {

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getShopListWorker(url, mPage, type));
        executor.shutdown();
    }

    public class getShopListWorker implements Runnable {
        String url;
        int page;
        int type;

        public getShopListWorker(String url, int mPage, int type) {
            this.url = url;
            this.page = mPage;
            this.type = type;
        }


        @Override
        public void run() {
            doShopList(url, page, type);
        }
    }

    public void doShopList(String url, int mPage, int type) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("startRow", mPage + "");
        if (type != -1) {
            params.put("isSelling", type + "");

        }
        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");

                    PageListShop mPageList = gson.fromJson(data_json.toString(), PageListShop.class);
                    mPageListHasNextListener.onSuccess(mPageList.getRows(), mPageList.isHasNext());
                } else {
                    mPageListHasNextListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mPageListHasNextListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doOrderSituation", e.getMessage());
            mPageListHasNextListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doOrderSituation", e.getMessage());
        } finally {
            mPageListHasNextListener.onFinish();
        }
    }

    /**
     * 修改商品
     *
     * @param url
     * @param map
     */
    public void updateSimpleSample(String url, Map map) {

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new MSimpleSampleWorker(url, map));
        executor.shutdown();
    }

    public class MSimpleSampleWorker implements Runnable {
        String url;
        Map map;

        public MSimpleSampleWorker(String url, Map map) {
            this.url = url;
            this.map = map;
        }

        @Override
        public void run() {
            doUpDateSample(url, map);
        }
    }

    public void doUpDateSample(String url, Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(url, map, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    mResponseListener.onSuccess(result.getErrorInfo());
                } else {
                    mResponseListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mResponseListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doUpDateSample", e.getMessage());
            mResponseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doUpDateSample", e.getMessage());
        } finally {
            mResponseListener.onFinish();
        }
    }

    /**
     * 回传一个data_list的
     *
     * @param url
     */
    public void getOwnLogisticResult(String url) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getOwnLogisticWorker(url));
        executor.shutdown();
    }

    public class getOwnLogisticWorker implements Runnable {
        String url;

        public getOwnLogisticWorker(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            doSelectLogisticRequest(url);
        }
    }

    private void doSelectLogisticRequest(String url) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(url, null, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    DataResult mPageList = gson.fromJson(data_json.toString(), DataResult.class);
                    mOwnLogisticResultListener.onSuccess(mPageList.getRows(), result.getErrorInfo());
                } else {
                    mOwnLogisticResultListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mOwnLogisticResultListener.onLose();
            }
        } catch (NetException e) {
            Log.e("mPageListListener", e.getMessage());
            mOwnLogisticResultListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mPageListListener", e.getMessage());
        } finally {
            mOwnLogisticResultListener.onFinish();
        }
    }

    /**
     * 选择快递公司
     *
     * @param url
     */
    public void getSureLogistic(String url, int id, int deliverId) {

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getSureLogisticsWorker(url, id, deliverId));
        executor.shutdown();
    }

    public class getSureLogisticsWorker implements Runnable {
        String url;
        int page;
        int type;

        public getSureLogisticsWorker(String url, int mPage, int type) {
            this.url = url;
            this.page = mPage;
            this.type = type;
        }


        @Override
        public void run() {
            doSureLogistics(url, page, type);
        }
    }

    public void doSureLogistics(String url, int id, int deliverId) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("orderId", id + "");
        params.put("deliveryCompanyId", deliverId + "");

        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    mResponseListener.onSuccess(result.getErrorInfo());
                } else {
                    mResponseListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mResponseListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doOrderSituation", e.getMessage());
            mResponseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doOrderSituation", e.getMessage());
        } finally {
            mResponseListener.onFinish();
        }
    }

    /*
    * 上架 下架
    */
    public void getShelvesRequest(String url, int id) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getShelvesWorker(url, id));
        executor.shutdown();
    }

    public class getShelvesWorker implements Runnable {
        String url;
        int id;

        public getShelvesWorker(String url, int id) {
            this.id = id;
            this.url = url;
        }

        @Override
        public void run() {
            doShelvesRequest(url, id);
        }

    }

    private void doShelvesRequest(String url, int id) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("ids", id + "");
        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    mResponseListener.onSuccess(result.getErrorInfo());
                } else {
                    mResponseListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mResponseListener.onLose();
            }
        } catch (NetException e) {
            Log.e("mPageListListener", e.getMessage());
            mResponseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mPageListListener", e.getMessage());
        } finally {
            mResponseListener.onFinish();
        }
    }

    // 快递退出
    public void userLogout(String url) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new UserLogoutWorker(url));
        executor.shutdown();
    }

    public class UserLogoutWorker implements Runnable {
        String url;

        public UserLogoutWorker(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            doUserLogout(url);
        }

    }

    public void doUserLogout(String url) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "express/logout.htm", map, false, -1, "");
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    logoutListener.onSuccess(result.isSuccess());
                } else {
                    logoutListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                logoutListener.onLose();
            }
        } catch (NetException e) {
            logoutListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doUserLogout", e.getMessage());
        } finally {
            logoutListener.onFinish();
        }
    }

    // 3.10.4获取卖家公司注册信息
    public void getCompanyRegInfo() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetCompanyRegInfoWorker());
        executor.shutdown();
    }

    public class GetCompanyRegInfoWorker implements Runnable {
        public GetCompanyRegInfoWorker() {
        }

        @Override
        public void run() {
            doGetCompanyRegInfo();
        }

    }

    public void doGetCompanyRegInfo() {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL
                    + "company/getCompanyRegInfo.htm", UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    SellerCompanyEntry sellerCompanyEntry = gson.fromJson(data_json.toString(), SellerCompanyEntry.class);
                    mOneDataListener.onSuccess(sellerCompanyEntry, null);
                } else {
                    mOneDataListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mOneDataListener.onLose();
            }
        } catch (NetException e) {
            mOneDataListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doUserLogout", e.getMessage());
        } finally {
            mOneDataListener.onFinish();
        }
    }

    // 3.10.1商家注册第一步
    public void postCompanyRegStepOne(Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new PostCompanyRegStepOneWorker(map));
        executor.shutdown();
    }

    public class PostCompanyRegStepOneWorker implements Runnable {
        Map map;

        public PostCompanyRegStepOneWorker(Map map) {
            this.map = map;
        }

        @Override
        public void run() {
            doPostCompanyRegStepOne(map);
        }

    }

    public void doPostCompanyRegStepOne(Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "company/register-step1.htm", map, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    responseListener.onSuccess(null);
                } else {
                    responseListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                responseListener.onLose();
            }
        } catch (NetException e) {
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doPostCompanyRegStepOne", e.getMessage());
        } finally {
            responseListener.onFinish();
        }
    }

    // 3.10.1商家注册第二步
    public void postCompanyRegStepTwo(Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new PostCompanyRegStepTwoWorker(map));
        executor.shutdown();
    }

    public class PostCompanyRegStepTwoWorker implements Runnable {
        Map map;

        public PostCompanyRegStepTwoWorker(Map map) {
            this.map = map;
        }

        @Override
        public void run() {
            doPostCompanyRegStepTwo(map);
        }

    }

    public void doPostCompanyRegStepTwo(Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "company/register-step2.htm", map, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    responseListener.onSuccess(null);
                } else {
                    responseListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                responseListener.onLose();
            }
        } catch (NetException e) {
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Logs.d("doPostCompanyRegStepTwo", e.getMessage());
        } finally {
            responseListener.onFinish();
        }
    }

    // 3.10.1商家注册第三步
    public void postCompanyRegStepThree(Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new PostCompanyRegStepThreeWorker(map));
        executor.shutdown();
    }

    public class PostCompanyRegStepThreeWorker implements Runnable {
        Map map;

        public PostCompanyRegStepThreeWorker(Map map) {
            this.map = map;
        }

        @Override
        public void run() {
            doPostCompanyRegStepThree(map);
        }

    }

    public void doPostCompanyRegStepThree(Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "company/register-step3.htm", map, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    responseListener.onSuccess(null);
                } else {
                    responseListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                responseListener.onLose();
            }
        } catch (NetException e) {
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Logs.d("doPostCompanyRegStepThree", e.getMessage());
        } finally {
            responseListener.onFinish();
        }
    }

    // 3.10.6商家注册信息修改
    public void postUpdateCompany(Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new PostUpdateCompanyWorker(map));
        executor.shutdown();
    }

    public class PostUpdateCompanyWorker implements Runnable {
        Map map;

        public PostUpdateCompanyWorker(Map map) {
            this.map = map;
        }

        @Override
        public void run() {
            doPostUpdateCompany(map);
        }

    }

    public void doPostUpdateCompany(Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "company/updateCompany.htm", map, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    responseListener.onSuccess(null);
                } else {
                    responseListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                responseListener.onLose();
            }
        } catch (NetException e) {
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Logs.d("doPostCompanyRegStepTwo", e.getMessage());
        } finally {
            responseListener.onFinish();
        }
    }

    // 3.10.6获取店铺信息
    public void getShopInfo() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetShopInfoWorker());
        executor.shutdown();
    }

    public class GetShopInfoWorker implements Runnable {
        public GetShopInfoWorker() {
        }

        @Override
        public void run() {
            doGetShopInfoWorker();
        }

    }

    public void doGetShopInfoWorker() {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL
                    + "seller/shop/getShopInfo.htm", UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    ShopEntry shopEntry = gson.fromJson(data_json.toString(), ShopEntry.class);
                    mOneDataListener.onSuccess(shopEntry, null);
                } else {
                    mOneDataListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mOneDataListener.onLose();
            }
        } catch (NetException e) {
            mOneDataListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Logs.d("doGetShopInfoWorker", e.getMessage());
        } finally {
            mOneDataListener.onFinish();
        }
    }

    /**
     * 修改店铺信息
     */
    public void updateShopInfo(Map map, boolean isLogin, int userId, String token) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new SellerDataConnection.UpdateShopInfoWorker(map, isLogin, userId, token));
        executor.shutdown();
    }

    public class UpdateShopInfoWorker implements Runnable {
        Map map;
        boolean isLogin;
        int userId;
        String token;

        public UpdateShopInfoWorker(Map map, boolean isLogin, int userId, String token) {
            super();
            this.map = map;
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
        }

        @Override
        public void run() {
            doUpdateShopInfo(map, isLogin, userId, token);
        }
    }

    private void doUpdateShopInfo(Map map, boolean isLogin, int userId, String token) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL + "seller/shop/updateShop.htm",
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
            Log.e("doUpdateShopInfo", e.getMessage());
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doUpdateShopInfo", e.getMessage());
        } finally {
            responseListener.onFinish();
        }

    }

    /**
     * 创建店铺信息
     */
    public void CreateShopInfo(Map map, boolean isLogin, int userId, String token) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new SellerDataConnection.CreateShopInfoWorker(map, isLogin, userId, token));
        executor.shutdown();
    }

    public class CreateShopInfoWorker implements Runnable {
        Map map;
        boolean isLogin;
        int userId;
        String token;

        public CreateShopInfoWorker(Map map, boolean isLogin, int userId, String token) {
            super();
            this.map = map;
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
        }

        @Override
        public void run() {
            doCreateShopInfo(map, isLogin, userId, token);
        }
    }

    private void doCreateShopInfo(Map map, boolean isLogin, int userId, String token) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL + "seller/shop/saveShop.htm",
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
            Log.e("doCreateShopInfo", e.getMessage());
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doCreateShopInfo", e.getMessage());
        } finally {
            responseListener.onFinish();
        }

    }

    /**
     * 获取卖家信息
     */
    public void getSellerInfo(boolean isLogin, int userId, String token) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new SellerDataConnection.GetSellerInfoWorker(isLogin, userId, token));
        executor.shutdown();
    }

    public class GetSellerInfoWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;

        public GetSellerInfoWorker(boolean isLogin, int userId, String token) {
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
        }

        @Override
        public void run() {
            doGetSellerInfo(isLogin, userId, token);
        }
    }

    private void doGetSellerInfo(boolean isLogin, int userId, String token) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL + "seller/getAccountInfo.htm",
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
            Log.e("doGetSellerInfo", e.getMessage());
            mOneDataListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetSellerInfo", e.getMessage());
        } finally {
            mOneDataListener.onFinish();
        }


    }

    /**
     * 修改卖家信息
     */
    public void updateSellerInfo(Map map, boolean isLogin, int userId, String token) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new SellerDataConnection.UpdateSellerInfoWorker(map, isLogin, userId, token));
        executor.shutdown();
    }

    public class UpdateSellerInfoWorker implements Runnable {
        Map map;
        boolean isLogin;
        int userId;
        String token;

        public UpdateSellerInfoWorker(Map map, boolean isLogin, int userId, String token) {
            super();
            this.map = map;
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
        }

        @Override
        public void run() {
            doUpdateSellerInfo(map, isLogin, userId, token);
        }
    }

    private void doUpdateSellerInfo(Map map, boolean isLogin, int userId, String token) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL + "seller/updateAccountInfo.htm",
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

    /**
     * 3.10.11卖家查看快递公司信息
     */
    public void getDeliverInfo(int id, boolean isLogin, int userId, String token) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new SellerDataConnection.GetDeliverInfoWorker(id, isLogin, userId, token));
        executor.shutdown();
    }

    public class GetDeliverInfoWorker implements Runnable {
        int id;
        boolean isLogin;
        int userId;
        String token;

        public GetDeliverInfoWorker(int id, boolean isLogin, int userId, String token) {
            this.id = id;
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
        }

        @Override
        public void run() {
            doGetDeliverInfo(id, isLogin, userId, token);
        }
    }

    private void doGetDeliverInfo(int id, boolean isLogin, int userId, String token) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL + "deliver/company/select/" + id + ".htm",
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
                    LogisticDetailFarther info = gson.fromJson(data_json.toString(), LogisticDetailFarther.class);
                    mOneDataListener.onSuccess(info, result.getErrorInfo());
                } else {
                    mOneDataListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mOneDataListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doGetDeliverInfo", e.getMessage());
            mOneDataListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetDeliverInfo", e.getMessage());
        } finally {
            mOneDataListener.onFinish();
        }


    }

    /**
     * 获取物流公司所有的模板列表
     *
     * @param url
     * @param mPage
     * @param id
     */
    public void getAllFreightTemplateListById(String url, int mPage, int id) {

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getHasNextTemplateListWorker(url, mPage, id));
        executor.shutdown();
    }

    public class getHasNextTemplateListWorker implements Runnable {
        String url;
        int page;
        int id;

        public getHasNextTemplateListWorker(String url, int mPage, int id) {
            this.url = url;
            this.page = mPage;
            this.id = id;
        }


        @Override
        public void run() {
            doHasTemplateListList(url, page, id);
        }
    }

    public void doHasTemplateListList(String url, int mPage, int id) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("startRow", mPage + "");
        params.put("deliveryCompanyId", id + "");
        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    PageListTemplate mPageList = gson.fromJson(data_json.toString(), PageListTemplate.class);
                    mPageListHasNextListener.onSuccess(mPageList.getRows(), mPageList.isHasNext());
                } else {
                    mPageListHasNextListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mPageListHasNextListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doHasTemplateListList", e.getMessage());
            mPageListHasNextListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doHasTemplateListList", e.getMessage());
        } finally {
            mPageListHasNextListener.onFinish();
        }
    }

    /**
     * 3.12.3卖家反馈退货申请
     */
    public void updateRefund(Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new DoRefundWorker(map));
        executor.shutdown();
    }

    public class DoRefundWorker implements Runnable {
        Map map;

        public DoRefundWorker(Map map) {
            super();
            this.map = map;
        }

        @Override
        public void run() {
            doRefund(map);
        }
    }

    private void doRefund(Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL + "seller/refund/update.htm",
                    map,
                    UserHelper.isSellerLogin(),
                    UserHelper.getSellerID(),
                    UserHelper.getSellerToken());
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
            Log.e("doRefund", e.getMessage());
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doRefund", e.getMessage());
        } finally {
            responseListener.onFinish();
        }

    }

    /**
     * 获取卖家所有的仓库列表
     */
    public void getAllDepotList() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getAllDepotListWorker());
        executor.shutdown();
    }

    public class getAllDepotListWorker implements Runnable {
        public getAllDepotListWorker() {
        }

        @Override
        public void run() {
            doGetAllDepotList();
        }
    }

    public void doGetAllDepotList() {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL + "seller/shopWarehouse/list.htm", UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    DepotListResult mPageList = gson.fromJson(data_json.toString(), DepotListResult.class);
                    mPageListListener.onSuccess(mPageList.getRows());
                } else {
                    mPageListListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mPageListListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doGetAllDepotList", e.getMessage());
            mPageListListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetAllDepotList", e.getMessage());
        } finally {
            mPageListListener.onFinish();
        }
    }

    /**
     * 通过id删除仓库信息
     */
    public void deleteDepotById(int depotId) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new DeleteDepotByIdWorker(depotId));
        executor.shutdown();
    }

    public class DeleteDepotByIdWorker implements Runnable {
        int depotId;

        public DeleteDepotByIdWorker(int depotId) {
            this.depotId = depotId;
        }

        @Override
        public void run() {
            doDeleteDepotById(depotId);
        }
    }

    public void doDeleteDepotById(int depotId) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL + "seller/shopWarehouse/delete/" + depotId + ".htm", UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    mResponseListener.onSuccess(result.getErrorInfo());
                } else {
                    mResponseListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mResponseListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doDeleteDepotById", e.getMessage());
            mResponseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doDeleteDepotById", e.getMessage());
        } finally {
            mResponseListener.onFinish();
        }
    }

    /**
     * 新建、修改仓库信息
     */
    public void modifyDepot(Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new ModifyDepotWorker(map));
        executor.shutdown();
    }

    public class ModifyDepotWorker implements Runnable {
        Map map;

        public ModifyDepotWorker(Map map) {
            this.map = map;
        }

        @Override
        public void run() {
            doModifyDepot(map);
        }
    }

    public void doModifyDepot(Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL + "seller/shopWarehouse/save.htm", map, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    mResponseListener.onSuccess(result.getErrorInfo());
                } else {
                    mResponseListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mResponseListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doModifyDepot", e.getMessage());
            mResponseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doModifyDepot", e.getMessage());
        } finally {
            mResponseListener.onFinish();
        }
    }

    //3.11.1获取卖家财务信息(总体)
    public void getFinaceInfo() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetFinaceInfoWorker());
        executor.shutdown();
    }

    public class GetFinaceInfoWorker implements Runnable {
        public GetFinaceInfoWorker() {
        }

        @Override
        public void run() {
            doGetFinaceInfo();
        }

    }

    public void doGetFinaceInfo() {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL
                    + "seller/finance/finaceList.htm", UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    FinaceCellList finaceCellList = gson.fromJson(data_json.toString(), FinaceCellList.class);
                    mOneDataListener.onSuccess(finaceCellList, result.getErrorInfo());
                } else {
                    mOneDataListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mOneDataListener.onLose();
            }
        } catch (NetException e) {
            mOneDataListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetFinaceInfo", e.getMessage());
        } finally {
            mOneDataListener.onFinish();
        }
    }


    /**
     * 3.11.2获取财务详情 1:待结算  3：逾期
     *
     * @param url
     * @param mPage
     * @param type
     */
    public void getSettleListByType(String url, int mPage, int type) {

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetSettleListByTypeWorker(url, mPage, type));
        executor.shutdown();
    }

    public class GetSettleListByTypeWorker implements Runnable {
        String url;
        int page;
        int type;

        public GetSettleListByTypeWorker(String url, int mPage, int type) {
            this.url = url;
            this.page = mPage;
            this.type = type;
        }


        @Override
        public void run() {
            doGetSettleListByType(url, page, type);
        }
    }

    public void doGetSettleListByType(String url, int mPage, int type) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("startRow", mPage + "");
        params.put("type", type + "");
        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    PageListSettle mPageList = gson.fromJson(data_json.toString(), PageListSettle.class);
                    mPageListHasNextListener.onSuccess(mPageList.getRows(), mPageList.isHasNext());
                } else {
                    mPageListHasNextListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mPageListHasNextListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doGetSettleListByType", e.getMessage());
            mPageListHasNextListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetSettleListByType", e.getMessage());
        } finally {
            mPageListHasNextListener.onFinish();
        }
    }

    /**
     * 3.11.3获取已结算历史
     *
     * @param url
     * @param mPage
     */
    public void getSettleYetList(String url, int mPage, int type) {

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetSettleYetListWorker(url, mPage, type));
        executor.shutdown();
    }

    public class GetSettleYetListWorker implements Runnable {
        String url;
        int page;
        int type;

        public GetSettleYetListWorker(String url, int mPage, int type) {
            this.url = url;
            this.page = mPage;
            this.type = type;
        }


        @Override
        public void run() {
            doGetSettleYetList(url, page, type);
        }
    }

    public void doGetSettleYetList(String url, int mPage, int type) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("startRow", mPage + "");
        params.put("type", type + "");
        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    PageListSettleYet mPageList = gson.fromJson(data_json.toString(), PageListSettleYet.class);
                    mPageListHasNextListener.onSuccess(mPageList.getRows(), mPageList.isHasNext());
                } else {
                    mPageListHasNextListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mPageListHasNextListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doGetSettleYetList", e.getMessage());
            mPageListHasNextListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetSettleYetList", e.getMessage());
        } finally {
            mPageListHasNextListener.onFinish();
        }
    }

    // 上传卖家图片
    public void SellerUploadFile(String url,boolean isLogin, int userId, String token,
                                 List<String> list, String fileName) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new SellerUploadFileWorker(url,isLogin, userId, token, list, fileName));
        executor.shutdown();
    }

    public class SellerUploadFileWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        String filepath;
        String fileName;
        List<String> list;
        String url;
        public SellerUploadFileWorker(String url,boolean isLogin, int userId, String token,
                                      List<String> list, String fileName) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.list = list;
            this.fileName = fileName;
            this.url=url;
        }

        @Override
        public void run() {
            doSellerUploadFile(url,isLogin, userId, token, list, fileName);
        }
    }

    public void doSellerUploadFile(String url,boolean isLogin, int userId, String token,
                                   List<String> list, String fileName) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.PostImage(url, isLogin,
                    userId, token, list, fileName);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject js = new JSONObject(strResult);
                JSONArray jsonArray = js.getJSONArray("data");
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                List<String> mList = new ArrayList<>();
                if (result.isSuccess()) {
//
                    JSONObject data_json = new JSONObject(response.getContent());
                    BaseStringResult result1 = gson.fromJson(data_json.toString().trim(), BaseStringResult.class);
                    uploadListener.onSuccess(result1.getData());
                } else {
                    uploadListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                uploadListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doUploadFile", e.getMessage());
            uploadListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doUploadFile", e.getMessage());
        } finally {
            uploadListener.onFinish();
        }
    }

    // 发布产品
    public void releaseProduct(Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new releaseProductWorker(map));
        executor.shutdown();
    }

    public class releaseProductWorker implements Runnable {
        public Map map;

        public releaseProductWorker(Map map) {
            this.map = map;
        }

        @Override
        public void run() {
            doReleaseProduct(map);
        }
    }

    public void doReleaseProduct(Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL + "seller/publishSample.htm", map, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    mNodataListener.onSuccess(result.getErrorInfo());

                } else {
                    mNodataListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mNodataListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doUploadFile", e.getMessage());
            uploadListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doUploadFile", e.getMessage());
        } finally {
            mNodataListener.onFinish();
        }
    }

    /**
     * 获取类目列表
     */
    public void getCategoryById(int depotId) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getCategoryByIdWorker(depotId));
        executor.shutdown();
    }

    public class getCategoryByIdWorker implements Runnable {
        int depotId;

        public getCategoryByIdWorker(int depotId) {
            this.depotId = depotId;
        }

        @Override
        public void run() {
            dogetCategoryById(depotId);
        }
    }

    public void dogetCategoryById(int depotId) {
        Map<String, String> params = new HashMap<>();
        params.put("categoryId", depotId + "");
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL + "category/getNextCategoryList.htm", params, false, -1, "");
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    CategoryListResult data = gson.fromJson(new JSONObject(strResult).getJSONObject("data").toString(), CategoryListResult.class);
                    mPageListListener.onSuccess(data.getRows());
                } else {
                    mPageListListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mPageListListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doDeleteDepotById", e.getMessage());
            mPageListListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doDeleteDepotById", e.getMessage());
        } finally {
            mPageListListener.onFinish();
        }
    }

    /**
     * 获取类目x详情
     */
    public void getCategoryDetail(int depotId) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getCategoryDetailWorker(depotId));
        executor.shutdown();
    }

    public class getCategoryDetailWorker implements Runnable {
        int depotId;

        public getCategoryDetailWorker(int depotId) {
            this.depotId = depotId;
        }

        @Override
        public void run() {
            dogetCategoryDetail(depotId);
        }
    }

    public void dogetCategoryDetail(int depotId) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL + "category/getPropertiesInfoByCategory/" + depotId + ".htm", false, -1, "");
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    CategoryDetailResult data = gson.fromJson(new JSONObject(strResult).getJSONObject("data").toString(), CategoryDetailResult.class);
                    mPageListListener.onSuccess(data.getRows());
                } else {
                    mPageListListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mPageListListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doDeleteDepotById", e.getMessage());
            mPageListListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doDeleteDepotById", e.getMessage());
        } finally {
            mPageListListener.onFinish();
        }
    }

}
