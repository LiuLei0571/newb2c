package com.example.b2c.widget;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.b2c.adapter.livesCommunity.HomeListAdapter;
import com.example.b2c.adapter.livesCommunity.LeiMuGridViewAdapter;
import com.example.b2c.config.Configs;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.response.ResponseResult;
import com.example.b2c.net.response.UploadResult;
import com.example.b2c.net.response.Users;
import com.example.b2c.net.response.livesCommunity.HomeLeiMu;
import com.example.b2c.net.response.livesCommunity.HomeList;
import com.example.b2c.net.response.livesCommunity.LivesDetailsBean;
import com.example.b2c.net.response.logistics.AccountItemResult;
import com.example.b2c.net.response.logistics.BankDetail;
import com.example.b2c.net.response.logistics.SettlementDetailResult;
import com.example.b2c.net.response.staff.DeliveryInfoResult;
import com.example.b2c.net.response.staff.ExpressInfo;
import com.example.b2c.net.util.HttpClientUtil;
import com.example.b2c.net.util.Md5Encrypt;
import com.example.b2c.net.zthHttp.MyHttpUtils;
import com.example.b2c.net.zthHttp.OkhttpUtils;
import com.google.gson.Gson;
import org.apache.http.HttpStatus;
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
 * Created by milk on 16/11/9.
 * 邮箱：649444395@qq.com
 * mOneDataListener用来返回的是一个数据实体，请求玩数据放到bean里面返回的实体就用这个玩意返回
 * mPageListHasNextListener用来返回实体里面的集合，意思就是实体.getRows获取的数据用这个返回
 * uploadListener上传完图片返回的url用这个玩意返回
 */

public class LogisticsDataConnection extends SellerDataConnection {
    public static LogisticsDataConnection sMLogisticsDataConnection = null;

    public Gson mGson = new Gson();
    private final OkhttpUtils okInstance;

    public static LogisticsDataConnection getInstance() {
        if (sMLogisticsDataConnection == null) {
            sMLogisticsDataConnection = new LogisticsDataConnection();
        }

        return sMLogisticsDataConnection;
    }
public LogisticsDataConnection(){
    okInstance = OkhttpUtils.getInstance();
}

    /*
   没有回传数据
    */
    public void getExpressBaseRequest(String url) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getExpressBaseWorker(url));
        executor.shutdown();
    }

    public class getExpressBaseWorker implements Runnable {
        String url;

        public getExpressBaseWorker(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            doExpressBaseRequest(url);
        }

    }

    private void doExpressBaseRequest(String url) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(url, null, UserHelper.isExpressLogin(), UserHelper.getExpressID(), UserHelper.getSExpressToken());
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
            Log.e("mPageListListener", e.getMessage());
            mPageListHasNextListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mPageListListener", e.getMessage());
        } finally {
            mNodataListener.onFinish();
        }
    }


    // 上传图片功能
    public void logisitcisUploadFile(boolean isLogin, int userId, String token,
                                     List<String> list) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new LogisticsUploadFileWorker(isLogin, userId, token, list));
        executor.shutdown();
    }

    public class LogisticsUploadFileWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        List<String> list;

        public LogisticsUploadFileWorker(boolean isLogin, int userId, String token,
                                         List<String> list) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.list = list;
        }

        @Override
        public void run() {
            doLogisticsUploadFile(isLogin, userId, token, list);
        }
    }

    public void doLogisticsUploadFile(boolean isLogin, int userId, String token,
                                      List<String> list) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.PostImage(ConstantS.BASE_PIC_URL+"remoting/rest/mobile/security/unLoginUploadFile.htm", isLogin,
                    userId, token, list, "headPhoto");
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    UploadResult upload_result = gson.fromJson(
                            data_json.toString(), UploadResult.class);
                    List<String> mList = new ArrayList<>();
                    mList.add(upload_result.getFilePath());
                    uploadListener.onSuccess(mList);
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

    /*
    * 快递登录
     */
    public void getLogisticsLoginRequest(String url, String name, String password) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getLogisticsLoginWorker(url, name, password));
        executor.shutdown();
    }

    public class getLogisticsLoginWorker implements Runnable {
        String url;
        String name;
        String password;

        public getLogisticsLoginWorker(String url, String name, String password) {
            this.name = name;
            this.password = password;
            this.url = url;
        }

        @Override
        public void run() {
            doLogisticsLoginRequest(url, name, password);
        }

    }

    private void doLogisticsLoginRequest(String url, String name, String password) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("loginName", name);
        params.put("password", Md5Encrypt.md5(password));
        try {
            Response response = HttpClientUtil.doPost(url, params, false, -1, "");
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    Users usersInfo = gson.fromJson(
                            data_json.toString(), Users.class);

                    usersInfo.setResult(result);
                    SPHelper.putInt(Configs.USER_TYPE.TYPE, usersInfo.getUserType());
                    if (Configs.USER_TYPE.BUYER == usersInfo.getUserType()) {
                        SPHelper.putBean(Configs.BUYER.INFO, usersInfo);
                    }
                    if (Configs.USER_TYPE.SELLER == usersInfo.getUserType()) {
                        SPHelper.putBean(Configs.SELLER.INFO, usersInfo);
                    }
//                    if (Configs.USER_TYPE.COURIER == usersInfo.getUserType()) {
//                        SPHelper.putBean(Configs.COURIER.INFO, usersInfo);
//                    }
                    else {
                        SPHelper.putBean(Configs.EXPRESS.INFO, usersInfo);
                    }
                    useListener.onSuccess(usersInfo, result.getErrorInfo());
                } else {
                    useListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                useListener.onLose();
            }

        } catch (NetException e) {
            Log.e("mPageListListener", e.getMessage());
            useListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mPageListListener", e.getMessage());
        } finally {
            useListener.onFinish();
        }
    }

    /**
     * 获取所有物流子账号
     *
     * @param url
     * @param mPage
     */
    public void getAccountListList(String url, int mPage, int pageSize) {

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getAccountListWorker(url, mPage, pageSize));
        executor.shutdown();
    }

    public class getAccountListWorker implements Runnable {
        String url;
        int page;
        int pageSize;


        public getAccountListWorker(String url, int mPage, int pageSize) {
            this.url = url;
            this.page = mPage;
            this.pageSize = pageSize;
        }


        @Override
        public void run() {
            doHasNextList(url, page, pageSize);
        }
    }

    public void doHasNextList(String url, int mPage, int pageSize) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("startRow", mPage + "");
        params.put("pageSize", pageSize + "");
        params.put("deliveryCompanyId", UserHelper.getExpressLoginId() + "");

        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isExpressLogin(), UserHelper.getExpressID(), UserHelper.getSExpressToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    String json = data_json.toString();
                    AccountItemResult mPageList = gson.fromJson(json, AccountItemResult.class);
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
    // 修改物流登录密码
    public void ChangeLogisticsPassword(String old, String news, String url) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new ChangeLogisticsPasswordWorker(old, news, url));
        executor.shutdown();
    }

    public class ChangeLogisticsPasswordWorker implements Runnable {
        String old;
        String news;
        String again;

        public ChangeLogisticsPasswordWorker(String old, String news, String againNews) {
            this.again = againNews;
            this.old = old;
            this.news = news;
        }

        @Override
        public void run() {
            doChangeSellerPassword(old, news, again);
        }

    }

    public void doChangeSellerPassword(String old, String news, String url) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<String, String>();
        params.put("oldPassword", Md5Encrypt.md5(old));
        params.put("password", Md5Encrypt.md5(news));
        params.put("deliveryCompanyId", UserHelper.getExpressLoginId() + "");
        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isExpressLogin(), UserHelper.getExpressID(), UserHelper.getSExpressToken());
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
            resultListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
            Log.e("doGetMobileCode", e.getMessage());
        } catch (JSONException e) {
            Log.e("doGetMobileCode", e.getMessage());
        } finally {
            mNodataListener.onFinish();
        }
    }

    /*
     * 新增子账号信息
      */
    public void getAddCourierRequest(String url,Map<String, String> params ) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getAddCourierWorker(url,params));
        executor.shutdown();
    }

    public class getAddCourierWorker implements Runnable {
        private String mUrl;
        private Map<String, String> mParams;
        public getAddCourierWorker(String url,Map<String, String> params) {
           this.mParams=params;
            this.mUrl=url;
        }
        @Override
        public void run() {
            doAddCourierRequest(mUrl,mParams);
        }
    }

    private void doAddCourierRequest(String url,Map<String, String> params) {
        try {

        Gson gson = new Gson();
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isExpressLogin(), UserHelper.getExpressID(), UserHelper.getSExpressToken());
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
            Log.e("mPageListListener", e.getMessage());
            mNodataListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mPageListListener", e.getMessage());
        } finally {
            mNodataListener.onFinish();
        }
    }

    /*
     * 修改子账号信息
      */
    public void getUpdateCourierRequest(String url, int id, String name, String mobile, String address, String email) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getUpdateCourierWorker(url, id, name, mobile, address, email));
        executor.shutdown();
    }

    public class getUpdateCourierWorker implements Runnable {


        private String mUrl;
        private int mId;
        private String mName;
        private String mMobile;
        private String mAddress;
        private String mEmail;

        public getUpdateCourierWorker(String url, int id, String name, String mobile, String address, String email) {
            mUrl = url;
            mId = id;
            mName = name;
            mMobile = mobile;
            mAddress = address;
            mEmail = email;
        }


        @Override
        public void run() {
            doUpdateCourierRequest(mUrl, mId, mName, mMobile, mAddress, mEmail);
        }
    }

    private void doUpdateCourierRequest(String url, int id, String name, String mobile, String address, String email) {

        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("deliveryCompanyId", UserHelper.getExpressLoginId() + "");
        params.put("accountId", id + "");
        params.put("name", name);
        params.put("address", address);
        params.put("mobile", mobile);
        params.put("email", email);

        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isExpressLogin(), UserHelper.getExpressID(), UserHelper.getSExpressToken());
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
            Log.e("mPageListListener", e.getMessage());
            mOneIntListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mPageListListener", e.getMessage());
        } finally {
            mNodataListener.onFinish();
        }
    }

    /**
     * 结算历史
     *
     * @param url
     */
    public void getSettleHistoryListRequest(String url, int page) {

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getSettleHistoryListWorker(url, page));
        executor.shutdown();
    }

    public class getSettleHistoryListWorker implements Runnable {
        String url;
        int page;

        public getSettleHistoryListWorker(String url, int page) {
            this.url = url;
            this.page = page;
        }


        @Override
        public void run() {
            doSettleHistoryList(url, page);
        }
    }

    public void doSettleHistoryList(String url, int page) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("deliveryCompanyId", UserHelper.getExpressLoginId() + "");
        params.put("startRow", page + "");
        params.put("type","0");
        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isExpressLogin(), UserHelper.getExpressID(), UserHelper.getSExpressToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    String jieguo = data_json.toString();
                    SettlementDetailResult mPageList = gson.fromJson(jieguo, SettlementDetailResult.class);

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
    /*
* 获取快递订单信息
*/
    public void getDeliverInfoRequest(String url, String phone) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getDeliveryInfoWorker(url, phone));
        executor.shutdown();
    }

    public class getDeliveryInfoWorker implements Runnable {


        private String mUrl;
        private String mPhone;

        public getDeliveryInfoWorker(String url, String phone) {
            mUrl = url;
            mPhone = phone;
        }

        @Override
        public void run() {
            doDeliveryInfoRequest(mUrl, mPhone);
        }
    }

    public void doDeliveryInfoRequest(String url, String phone) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("deliveryNo", phone);
        params.put("deliveryCompanyId", UserHelper.getExpressLoginId() + "");

        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isExpressLogin(), UserHelper.getExpressID(), UserHelper.getSExpressToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    DeliveryInfoResult mResult = gson.fromJson(data_json.toString(), DeliveryInfoResult.class);
                    mOneDataListener.onSuccess(mResult, result.getErrorInfo());
                } else {
                    mOneDataListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mOneDataListener.onLose();
            }

        } catch (NetException e) {
            Log.e("mOneStringListener", e.getMessage());
            mOneDataListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mOneStringListener", e.getMessage());
        } finally {
            mOneDataListener.onFinish();
        }
    }

    /**
     * 提交订单信息
     *
     * @param url
     * @param orderCode
     * @param deliveryNo
     * @param place
     * @param type
     */
    public void submitDeliverInfoRequest(String url, String orderCode, String deliveryNo, String place, int type) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new submitDeliveryInfoWorker(url, orderCode, deliveryNo, place, type));
        executor.shutdown();
    }

    public class submitDeliveryInfoWorker implements Runnable {


        private String mUrl;
        private String mOrder;
        private String mDeliveryNo;
        private String mPlace;
        private int mType;

        public submitDeliveryInfoWorker(String url, String orderCode, String deliveryNo, String place, int type) {
            mUrl = url;
            mOrder = orderCode;
            mDeliveryNo = deliveryNo;
            mPlace = place;
            mType = type;
        }

        @Override
        public void run() {
            doSubmitDeliveryInfoRequest(mUrl, mOrder, mDeliveryNo, mPlace, mType);
        }
    }

    public void doSubmitDeliveryInfoRequest(String url, String orderCode, String deliveryNo, String place, int type) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("orderCode", orderCode);
        params.put("place", place);
        params.put("deliveryNo", deliveryNo);
        params.put("isFirst", type + "");
        params.put("deliveryCompanyId", UserHelper.getExpressLoginId() + "");
        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isExpressLogin(), UserHelper.getExpressID(), UserHelper.getSExpressToken());
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
            Log.e("mOneStringListener", e.getMessage());
            mNodataListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mOneStringListener", e.getMessage());
        } finally {
            mNodataListener.onFinish();
        }
    }

    /*
    * 子账号详细信息
     */
    public void getBankDetailRequest(String url) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getCBankDetailWorker(url));
        executor.shutdown();
    }

    public class getCBankDetailWorker implements Runnable {

        private String mUrl;

        public getCBankDetailWorker(String url) {
            mUrl = url;
        }

        @Override
        public void run() {
            doBankDetail(mUrl);
        }
    }

    private void doBankDetail(String url) {

        Gson gson = new Gson();

        try {
            Response response = HttpClientUtil.doPost(url, null, false, -1, "");
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    BankDetail accountDetail = gson.fromJson(data_json.toString(), BankDetail.class);
                    mOneDataListener.onSuccess(accountDetail, result.getErrorInfo());
                } else {
                    mOneDataListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mOneDataListener.onLose();
            }
        } catch (NetException e) {
            Log.e("mOneDataListener", e.getMessage());
            mOneDataListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mOneDataListener", e.getMessage());
        } finally {
            mOneDataListener.onFinish();
        }
    }
    /*
     获取快递用户详情
   */
    public void getExpressUserInfo(String url) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getExpressUserInfo(url));
        executor.shutdown();
    }

    public class getExpressUserInfo implements Runnable {
        String url;

        public getExpressUserInfo(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            dogetExpressUserInfo(url);
        }

    }

    private void dogetExpressUserInfo(String url) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doGet(url, UserHelper.isExpressLogin(), UserHelper.getExpressID(), UserHelper.getSExpressToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    ExpressInfo expressInfo = gson.fromJson((new JSONObject(strResult)
                            .getJSONObject("data").toString()), ExpressInfo.class);
                    mOneDataListener.onSuccess(expressInfo, result.getErrorInfo());
                } else {
                    mOneDataListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mOneDataListener.onLose();
            }

        } catch (NetException e) {
            Log.e("mPageListListener", e.getMessage());
            mNodataListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mPageListListener", e.getMessage());
        } finally {
            mOneDataListener.onFinish();
        }
    }
    /**
     * 关联订单，拒收理由
     */
    public void guanlian(Map<String,String>map){
        MyHttpUtils instance = MyHttpUtils.getInstance();
        instance.doPost(ConstantS.BASE_URL + "staff/confirmOrder.htm", map,
                true, UserHelper.getExpressID(), UserHelper.getSExpressToken(), new MyHttpUtils.MyCallback() {
                    @Override
                    public void onSuccess(String result,int code) {
                        JSONObject json= null;
                        try {
                            json = new JSONObject(result);
                           JSONObject meta = json.getJSONObject("meta");
                            ResponseResult metaData = mGson.fromJson(meta.toString(),
                                    ResponseResult.class);
                            if (metaData.isSuccess()){
                                mNodataListener.onSuccess(metaData.getErrorInfo());
                            }else {
                                mNodataListener.onError(metaData.getErrorNO(), metaData.getErrorInfo());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onFailture() {

                    }
                });
    }

    /**
     * 获取生活专区首页的类目方法
     * @param context
     */
    public void getLivesLeimu(Context context){
        try {
            okInstance.doPost(context, ConstantS.BASE_URL+ "life/index/getCategories.htm",
                    null, false, -1, null,  new OkhttpUtils.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            //TODO
                            try {
                                JSONObject meta = new JSONObject(result).getJSONObject("meta");

                                ResponseResult metaData = mGson.fromJson(meta.toString(),
                                        ResponseResult.class);
                                if (metaData.isSuccess()){
                                    JSONObject data = new JSONObject(result).getJSONObject("data");
                                    HomeLeiMu homeLeiMu = mGson.fromJson(data.toString(), HomeLeiMu.class);
                                    mOneDataListener.onSuccess(homeLeiMu, metaData.getErrorInfo());
                                }else{
                                    mOneDataListener.onError(metaData.getErrorNO(), metaData.getErrorInfo());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                mOneDataListener.onLose();
                            }
                        }

                        @Override
                        public void onFailture(Exception e) {
                            mOneDataListener.onFinish();
                        }

                        @Override
                        public void onStart(){
                        }

                        @Override
                        public void onFinish() {
                        }
                    }
            );
        } catch (NetException e) {
            mOneDataListener.onLose();
            e.printStackTrace();
        }
    }
    /**
     * 生活专区的发布接口
     */
    public void livesFabu(Context context,Map<String,String>map){
        try {
            okInstance.doPost(context, ConstantS.BASE_URL + "life/saveMyInfo.htm",
                    map, true, UserHelper.getLivesID(), UserHelper.getLivesToken(), new OkhttpUtils.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            JSONObject json= null;
                            try {
                                json = new JSONObject(result);
                                JSONObject meta = json.getJSONObject("meta");
                                ResponseResult metaData = mGson.fromJson(meta.toString(),
                                        ResponseResult.class);
                                if (metaData.isSuccess()){
                                    mNodataListener.onSuccess(metaData.getErrorInfo());
                                }else {
                                    mNodataListener.onError(metaData.getErrorNO(), metaData.getErrorInfo());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailture(Exception e) {
                            mNodataListener.onLose();
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFinish() {

                        }
                    }
            );
        }catch (NetException e){
            mNodataListener.onLose();
        }
    }
    /**
     * 请求生活专区类目对应的列表
     */
    public void getLeimuMessageList(Context context,Map<String,String>map){
        try {
            okInstance.doPost(context, ConstantS.BASE_URL + "life/getInfomationListByCategoryId.htm",
                    map, false, -1, null, new OkhttpUtils.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject meta = new JSONObject(result).getJSONObject("meta");

                                ResponseResult metaData = mGson.fromJson(meta.toString(),
                                        ResponseResult.class);
                                if (metaData.isSuccess()){
                                    JSONObject data = new JSONObject(result).getJSONObject("data");
                                    HomeList homelIst = mGson.fromJson(data.toString(), HomeList.class);
                                    mOneDataListener.onSuccess(homelIst, metaData.getErrorInfo());
                                }else{
                                    mOneDataListener.onError(metaData.getErrorNO(), metaData.getErrorInfo());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                mOneDataListener.onLose();
                            }
                        }
                        @Override
                        public void onFailture(Exception e) {
                            mOneDataListener.onLose();
                        }
                        @Override
                        public void onStart() {}
                        @Override
                        public void onFinish() {}
                    });
        } catch (NetException e) {
            e.printStackTrace();
            mOneDataListener.onLose();
        }
    }
    /**
     * 生活专区信息详情
     */

    public void getLivesDetails(Context context,Map<String,String>map,
                                int id){
        try {
            okInstance.doPost(context, ConstantS.BASE_URL + "life/info/detail.htm", map,
                    false, id, null, new OkhttpUtils.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject meta = new JSONObject(result).getJSONObject("meta");

                                ResponseResult metaData = mGson.fromJson(meta.toString(),
                                        ResponseResult.class);
                                if (metaData.isSuccess()){
                                    JSONObject data = new JSONObject(result).getJSONObject("data");
                                    LivesDetailsBean livesDetailsBean = mGson.fromJson(data.toString(), LivesDetailsBean.class);
                                    mOneDataListener.onSuccess(livesDetailsBean, metaData.getErrorInfo());
                                }else{
                                    mOneDataListener.onError(metaData.getErrorNO(), metaData.getErrorInfo());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                mOneDataListener.onLose();
                            }
                        }
                        @Override
                        public void onFailture(Exception e) {mOneDataListener.onLose();}
                        @Override
                        public void onStart() {}

                        @Override
                        public void onFinish() {}
                    });
        } catch (NetException e) {
            mOneDataListener.onLose();
            e.printStackTrace();
        }
    }
    /**
     * 反馈，举报接口
     */
    public void jubao(Context context,String url,Map<String,String>map){
        String livesToken = UserHelper.getLivesToken();
        try {
            okInstance.doPost(context, url, map, true, UserHelper.getLivesID(),livesToken , new OkhttpUtils.MyCallback() {
                @Override
                public void onSuccess(String result) {
                    JSONObject json= null;
                    try {
                        json = new JSONObject(result);
                        JSONObject meta = json.getJSONObject("meta");
                        ResponseResult metaData = mGson.fromJson(meta.toString(),
                                ResponseResult.class);
                        if (metaData.isSuccess()){
                            mNodataListener.onSuccess(metaData.getErrorInfo());
                        }else {
                            mNodataListener.onError(metaData.getErrorNO(), metaData.getErrorInfo());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailture(Exception e) {
                    mNodataListener.onLose();
                }
                @Override
                public void onStart() {}
                @Override
                public void onFinish() {}
            });
        } catch (NetException e) {
            e.printStackTrace();
            mNodataListener.onLose();
        }
    }
    /**
     * 获取我的发布信息列表
     */
    public void getMyFabuList(Context context,Map<String,String>map){
        try {
            okInstance.doPost(context, ConstantS.BASE_URL + "life/myInfoList.htm",
                    map, true, UserHelper.getLivesID(), UserHelper.getLivesToken(), new OkhttpUtils.MyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject meta = new JSONObject(result).getJSONObject("meta");
                                ResponseResult metaData = mGson.fromJson(meta.toString(),
                                        ResponseResult.class);
                                if (metaData.isSuccess()){
                                    JSONObject data = new JSONObject(result).getJSONObject("data");
                                    HomeList homelIst = mGson.fromJson(data.toString(), HomeList.class);
                                    mOneDataListener.onSuccess(homelIst, metaData.getErrorInfo());
                                }else{
                                    mOneDataListener.onError(metaData.getErrorNO(), metaData.getErrorInfo());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                mOneDataListener.onLose();
                            }
                        }
                        @Override
                        public void onFailture(Exception e) {
                            mOneDataListener.onLose();
                        }
                        @Override
                        public void onStart() {}
                        @Override
                        public void onFinish() {}
                    });
        } catch (NetException e) {
            e.printStackTrace();
            mOneDataListener.onLose();
        }
    }
}

