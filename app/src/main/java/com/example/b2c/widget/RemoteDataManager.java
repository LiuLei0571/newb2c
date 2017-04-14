package com.example.b2c.widget;

import android.util.Log;

import com.example.b2c.config.Configs;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.helper.UserHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.listener.ConfirmCartListener;
import com.example.b2c.net.listener.FavoriteSampleListener;
import com.example.b2c.net.listener.FavoriteShopListener;
import com.example.b2c.net.listener.GetBankCardListListener;
import com.example.b2c.net.listener.GetBankListListener;
import com.example.b2c.net.listener.GetMyOrderListener;
import com.example.b2c.net.listener.GetShopDetailListener;
import com.example.b2c.net.listener.GetShopHomeListener;
import com.example.b2c.net.listener.GetStaffOrderListener;
import com.example.b2c.net.listener.LanguageListener;
import com.example.b2c.net.listener.LoginListener;
import com.example.b2c.net.listener.LogoutListener;
import com.example.b2c.net.listener.MakeOrderResultListener;
import com.example.b2c.net.listener.ModelTemplateListener;
import com.example.b2c.net.listener.MyMessageListener;
import com.example.b2c.net.listener.OrderDetailListener;
import com.example.b2c.net.listener.RefundListListener;
import com.example.b2c.net.listener.RegisterListener;
import com.example.b2c.net.listener.ResponseHanderListener;
import com.example.b2c.net.listener.ResponseListener;
import com.example.b2c.net.listener.ResultListener;
import com.example.b2c.net.listener.ReturnFromServerListener;
import com.example.b2c.net.listener.SampleCommentListener;
import com.example.b2c.net.listener.SampleDetailListener;
import com.example.b2c.net.listener.ShoppingAddressDetailListener;
import com.example.b2c.net.listener.ShoppingCartListener;
import com.example.b2c.net.listener.ShoppingCartSettleListener;
import com.example.b2c.net.listener.TranslatesListener;
import com.example.b2c.net.listener.UploadListener;
import com.example.b2c.net.listener.base.BaseUserListener;
import com.example.b2c.net.listener.base.HomeIndexListListener;
import com.example.b2c.net.listener.base.NoDataListener;
import com.example.b2c.net.listener.base.OneDataListener;
import com.example.b2c.net.listener.base.OneIntListener;
import com.example.b2c.net.listener.base.OneStringListener;
import com.example.b2c.net.listener.base.OwnLogisticResultListener;
import com.example.b2c.net.listener.base.PageListHasNextListener;
import com.example.b2c.net.listener.base.PageListListener;
import com.example.b2c.net.listener.base.TwoStringListener;
import com.example.b2c.net.listener.seller.ShopInfoListener;
import com.example.b2c.net.listener.seller.TodayOrderListener;
import com.example.b2c.net.response.BuyerOrderList;
import com.example.b2c.net.response.CategoryFirstListResult;
import com.example.b2c.net.response.CodeResult;
import com.example.b2c.net.response.ConfirmCartResult;
import com.example.b2c.net.response.FavoriteSampleListResult;
import com.example.b2c.net.response.FavoriteShopListResult;
import com.example.b2c.net.response.GetBankCardListResult;
import com.example.b2c.net.response.GetMyOrderResult;
import com.example.b2c.net.response.GetShopDetailResult;
import com.example.b2c.net.response.GetShopHomeResult;
import com.example.b2c.net.response.GetShoppingAddressResult;
import com.example.b2c.net.response.HomePageDataListener;
import com.example.b2c.net.response.HomePageDataObject;
import com.example.b2c.net.response.MyMessageResult;
import com.example.b2c.net.response.OrderCell;
import com.example.b2c.net.response.OrderDeliverResult;
import com.example.b2c.net.response.OrderDetailResult;
import com.example.b2c.net.response.ProtocolInfo;
import com.example.b2c.net.response.RPAddressResult;
import com.example.b2c.net.response.RefundListResult;
import com.example.b2c.net.response.RegisterResult;
import com.example.b2c.net.response.Response;
import com.example.b2c.net.response.ResponseResult;
import com.example.b2c.net.response.SampleCommentResult;
import com.example.b2c.net.response.SampleDetailResult;
import com.example.b2c.net.response.SampleListResult;
import com.example.b2c.net.response.ShopListResult;
import com.example.b2c.net.response.ShoppingAddressDetail;
import com.example.b2c.net.response.ShoppingCartResult;
import com.example.b2c.net.response.UploadResult;
import com.example.b2c.net.response.Users;
import com.example.b2c.net.response.base.BaseResult;
import com.example.b2c.net.response.seller.ShopInfoDetail;
import com.example.b2c.net.response.translate.MobileStaticTextCode;
import com.example.b2c.net.response.translate.TranslatesResult;
import com.example.b2c.net.util.HttpClientUtil;
import com.example.b2c.net.util.Logs;
import com.example.b2c.net.util.Md5Encrypt;
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

public class RemoteDataManager {

//    public static final String UPLOAD_URL = "http://115.29.172.200/remoting/rest/mobile/security/uploadFile.htm";
//    public static final String LOGISTICS_UPLOAD_URL = "http://115.29.172.200/remoting/rest/mobile/security/unLoginUploadFile.htm";
//    public static final String SELLER_UPLOAD_URL = "http://115.29.172.200//remoting/rest/mobile/security/mobileUploadSamplePic.htm";

    private static RemoteDataManager sharedManager = null;
    public static int NETERROR_CODE = 500;


    public TodayOrderListener mTodayOrderListener;
    public PageListHasNextListener mPageListHasNextListener;
    public PageListListener mPageListListener;
    public ResponseListener mResponseListener;
    public OwnLogisticResultListener mOwnLogisticResultListener;
    public TwoStringListener mTwoStringListener;


    public TranslatesListener translatesListener;
    public ResponseListener responseListener;
    public LoginListener loginListener;
    public LogoutListener logoutListener;

    //2.0
    public HomePageDataListener mHomeIndexListener;
    public ShopInfoListener mShopInfoListener;
    public ResultListener resultListener;

    public BaseUserListener useListener;
    public NoDataListener mNodataListener;
    public OneIntListener mOneIntListener;
    public OneStringListener mOneStringListener;
    public OneDataListener mOneDataListener;

    public ResponseHanderListener responseHanderListener;


    protected MobileStaticTextCode mTranslatesString = SPHelper.getBean("translate", MobileStaticTextCode.class);

    //商品收藏监听器
    public FavoriteSampleListener favoriteSampleListener;
    //商店收藏监听器
    public FavoriteShopListener favoriteShopListener;

    public ResponseListener getEmailVerifyCodeListener;

    public ReturnFromServerListener getmobilecodeListener;
    public RegisterListener registerListener;
    public SampleDetailListener sampledetailListener;
    public ShoppingCartListener shoppingcartListener;
    public ConfirmCartListener confirmcartListener;
    public GetMyOrderListener getmyorderListener;
    public GetShopHomeListener getshophomeListener;


    public ShoppingAddressDetailListener shoppingaddressdetailListener;
    public GetShopDetailListener getshopdetailListener;
    public GetBankCardListListener getbankcardlistListener;
    public GetBankListListener getbanklistListener;
    public OrderDetailListener orderdetailListener;
    public ShoppingCartSettleListener shoppingcartsettleListener;
    public UploadListener uploadListener;
    public RefundListListener refundlistListener;
    public SampleCommentListener samplecommentListener;
    public MyMessageListener mymessageListener;

    //	public OrderCellListListener  orderCellListListener;//支付订单，获取订单号列表
    public ResponseHanderListener<OrderCell> orderCellListListener;//支付订单，获取订单号列表
    public MakeOrderResultListener makeOrderResultListener;//确认下单
    public HomeIndexListListener mHomeIndexListListener;


    //3.0
    public ModelTemplateListener mTemplateListener;
    public GetStaffOrderListener mStaffOrderListener;
    public LanguageListener mLanguageListener;

    public static RemoteDataManager getInstance() {
        if (sharedManager == null) {
            sharedManager = new RemoteDataManager();
        }

        return sharedManager;
    }


    /**
     * 卖家 买家登录
     * 登录功能
     */
    public void Login(String loginName, String password, boolean isLogin, int userId, String token,int type) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new LoginWorker(loginName, password, isLogin, userId, token,type));
        executor.shutdown();
    }


    public class LoginWorker implements Runnable {
        String loginName;
        String password;
        boolean isLogin;
        int userId;
        String token;
        int type;

        public LoginWorker(String loginName, String password, boolean isLogin,
                           int userId, String token,int type) {
            super();
            this.loginName = loginName;
            this.password = password;
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.type = type;
        }

        @Override
        public void run() {
            doLogin(loginName, password, isLogin, userId, token ,type);
        }

    }

    public void doLogin(String loginName, String password, boolean isLogin,
                        int userId, String token ,int type) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();
        map.put("loginName", loginName);
        map.put("password", Md5Encrypt.md5(password));
        map.put("type", type+"");
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "user/login.htm", map, isLogin, userId, token);
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
                    if (Configs.USER_TYPE.COURIER == usersInfo.getUserType()) {
                        SPHelper.putBean(Configs.COURIER.INFO, usersInfo);
                    }
                    if (Configs.USER_TYPE.EXPRESS == usersInfo.getUserType()) {
                        SPHelper.putBean(Configs.EXPRESS.INFO, usersInfo);
                    }
                    SPHelper.putBean(Configs.LIVES.INFO, usersInfo);
                    loginListener.onSuccess(usersInfo.getUserId(), usersInfo.getToken(), usersInfo.getUserType() + "", usersInfo.getStep());
                } else {
                    loginListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                loginListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doLogin", e.getMessage());
            loginListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (Exception e) {
            Log.e("doLogin", e.getMessage());
        } finally {
            loginListener.onFinish();
        }
    }

    // 登出功能
    public void Logout(boolean isLogin, int userId, String token) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new LogoutWorker(isLogin, userId, token));
        executor.shutdown();
    }

    public class LogoutWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;

        public LogoutWorker(boolean isLogin, int userId, String token) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
        }

        @Override
        public void run() {
            doLogout(isLogin, userId, token);
        }

    }

    public void doLogout(boolean isLogin, int userId, String token) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL + "user/logout.htm", map, isLogin, userId, token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    logoutListener.onSuccess(result.isSuccess());
                } else {
                    logoutListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                logoutListener.onLose();
            }


        } catch (NetException e) {
            Log.e(" doLogout", e.getMessage());
            logoutListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e(" doLogout", e.getMessage());
        } finally {
            logoutListener.onFinish();
        }
    }

//    // 注册功能
//    public void Register(String loginName, String password, String code,
//                         boolean isLogin, int userId, String token) {
//        ExecutorService executor = Executors.newFixedThreadPool(5);
//        executor.execute(new RegisterWorker(loginName, password, code, isLogin,
//                userId, token));
//        executor.shutdown();
//    }
//
//    public class RegisterWorker implements Runnable {
//        String loginName;
//        String password;
//        String code;
//        boolean isLogin;
//        int userId;
//        String token;
//
//        public RegisterWorker(String loginName, String password, String code,
//                              boolean isLogin, int userId, String token) {
//            super();
//            this.loginName = loginName;
//            this.password = password;
//            this.code = code;
//            this.isLogin = isLogin;
//            this.userId = userId;
//            this.token = token;
//        }
//
//        @Override
//        public void run() {
//            doRegister(loginName, password, code, isLogin, userId, token);
//        }
//
//    }
//
//    public void doRegister(String loginName, String password, String code,
//                           boolean isLogin, int userId, String token) {
//        Gson gson = new Gson();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("loginName", loginName);
//        map.put("password", Md5Encrypt.md5(password));
//        map.put("code", code);
//        try {
//            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
//                    + "user/register.htm", map, isLogin, userId, token);
//            if (response.getStatusCode() == HttpStatus.SC_OK) {
//                String strResult = response.getContent();
//                JSONObject meta_json = new JSONObject(strResult)
//                        .getJSONObject("meta");
//                ResponseResult result = gson.fromJson(meta_json.toString(),
//                        ResponseResult.class);
//                if (result.isSuccess()) {
//                    JSONObject data_json = new JSONObject(strResult)
//                            .getJSONObject("data");
//                    RegisterResult register_result = gson.fromJson(
//                            data_json.toString(), RegisterResult.class);
//                    register_result.setResult(result);
//                    registerListener.onSuccess(register_result.getUserId(),
//                            register_result.getToken(),
//                            register_result.getNickName());
//                } else {
//                    registerListener.onError(result.getErrorNO(),
//                            result.getErrorInfo());
//                }
//            } else {
//                registerListener.onError("",
//                        mTranslatesString.getCommon_neterror());
//            }
//            registerListener.lose();
//        } catch (NetException e) {
//            Log.e("doRegister", e.getMessage());
//            registerListener.onError(mTranslatesString.getCommon_neterror(), "");
//        } catch (JSONException e) {
//            Log.e("doRegister", e.getMessage());
//        }
//    }


    // 注册功能---买家，卖家注册
    public void Register(Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new RegisterWorker(map));
        executor.shutdown();
    }

    public class RegisterWorker implements Runnable {
        Map map;

        public RegisterWorker(Map map) {
            super();
            this.map = map;
        }

        @Override
        public void run() {
            doRegister(map);
        }

    }

    public void doRegister(Map map) {
        Gson gson = new Gson();
        //密码本地加密
        String encodePassword = Md5Encrypt.md5((String) map.get("password"));
        map.put("password", encodePassword);
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "user/register.htm", map, false, -1, null);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    RegisterResult register_result = gson.fromJson(
                            data_json.toString(), RegisterResult.class);
                    registerListener.onSuccess(register_result.getUserId(),
                            register_result.getToken(),
                            register_result.getUserType());
                } else {
                    registerListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                registerListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doRegister", e.getMessage());
            registerListener.onError(500, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doRegister", e.getMessage());
        } finally {
            registerListener.onFinish();
        }
    }


    // 获取手机验证码功能
    public void GetMobileCode(String loginName, boolean isLogin, int userId,
                              String token) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetMobileCodeWorker(loginName, isLogin, userId,
                token));
        executor.shutdown();
    }

    public class GetMobileCodeWorker implements Runnable {
        String loginName;
        boolean isLogin;
        int userId;
        String token;

        public GetMobileCodeWorker(String loginName, boolean isLogin,
                                   int userId, String token) {
            super();
            this.loginName = loginName;
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
        }

        @Override
        public void run() {
            doGetMobileCode(loginName, isLogin, userId, token);
        }

    }

    public void doGetMobileCode(String loginName, boolean isLogin, int userId,
                                String token) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("loginName", loginName);
        Log.e("loginName", loginName);
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "user/sendMobileCode.htm", map, isLogin, userId, token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    getmobilecodeListener.onSuccess(result.getErrorNO(),
                            result.getErrorInfo());
                } else {
                    getmobilecodeListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                getmobilecodeListener.onLose();
            }

        } catch (NetException e) {
            getmobilecodeListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
            Log.e("doGetMobileCode", e.getMessage());
        } catch (JSONException e) {
            Log.e("doGetMobileCode", e.getMessage());
        } finally {
            getmobilecodeListener.onFinish();
        }
    }

    /**
     * 获取邮箱验证码功能
     */
    public void GetEmailCode(String email, int type) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetEmailCodeWorker(email, type));
        executor.shutdown();
    }

    public class GetEmailCodeWorker implements Runnable {
        String email;
        int type;

        public GetEmailCodeWorker(String email, int type) {
            super();
            this.email = email;
            this.type = type;
        }

        @Override
        public void run() {
            doGetEmailCode(email, type);
        }

    }

    public void doGetEmailCode(String email, int type) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("type", type + "");
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "user/send-email-code.htm", map, false, UserHelper.getBuyerId(), UserHelper.getBuyertoken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    getEmailVerifyCodeListener.onSuccess(result.getErrorInfo());
                } else {
                    getEmailVerifyCodeListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                getEmailVerifyCodeListener.onLose();
            }

        } catch (NetException e) {
            getEmailVerifyCodeListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
            Log.e("doGetMobileCode", e.getMessage());
        } catch (JSONException e) {
            Log.e("doGetEmailCode", e.getMessage());
        } finally {
            getEmailVerifyCodeListener.onFinish();
        }
    }

    //老的初始化首页数据
    public void initHomePage(boolean isLogin, int userId, String token) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new InitHomePageWorker(isLogin, userId, token));
        executor.shutdown();
    }

    public class InitHomePageWorker implements Runnable {

        private boolean isLogin;
        private int userId;
        private String token;

        public InitHomePageWorker(boolean isLogin, int userId, String token) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
        }

        @Override
        public void run() {
            doGetInitHomePage(isLogin, userId, token);
        }

    }

    public void doGetInitHomePage(boolean isLogin, int userId, String token) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL
                            + "banner/homePage.htm", isLogin, userId,
                    token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                JSONObject data_json = new JSONObject(response.getContent());

                ResponseResult result = gson.fromJson(data_json.getJSONObject("meta").toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    HomePageDataObject data = gson.fromJson(data_json.getJSONObject("data").toString(), HomePageDataObject.class);
                    data.setResult(result);
                    mHomeIndexListener.onSuccess(data, result.getErrorInfo());
//                    banneradlistListener.onSuccess(data.getBannerAdvertisingList());
//                    hotcategoryListener.onSuccess(data.getHotCategoryList());
//                    bigbrandlistListener.onSuccess(data.getBigBrandList());
//                    famousshoplistListener.onSuccess(data.getFamousShopList());

                } else {
                    mHomeIndexListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mHomeIndexListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doGetInitHomePage", e.getMessage());
            mHomeIndexListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetInitHomePage", e.getMessage());
        } finally {
            mHomeIndexListener.onFinish();
        }
    }


    //新的初始化首页数据
    public void initHomePageNew(boolean isLogin, int userId, String token) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new InitHomePageWorkerNew(isLogin, userId, token));
        executor.shutdown();
    }

    public class InitHomePageWorkerNew implements Runnable {

        private boolean isLogin;
        private int userId;
        private String token;

        public InitHomePageWorkerNew(boolean isLogin, int userId, String token) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
        }

        @Override
        public void run() {
            doGetInitHomePageNew(isLogin, userId, token);
        }

    }


    public void doGetInitHomePageNew(boolean isLogin, int userId, String token) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                            + "banner/homePage.htm", null, isLogin, userId,
                    token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {

                JSONObject data_json = new JSONObject(response.getContent());
                BaseResult result = gson.fromJson(data_json.toString().trim(), BaseResult.class);
                if (result.getMeta().isSuccess()) {
                    mTemplateListener.onSuccess(result.getData(), result.getMeta().getErrorInfo());

                } else {
                    mTemplateListener.onError(result.getMeta().getErrorNO(), result.getMeta().getErrorInfo());
                }
            } else {
                mTemplateListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doGetInitHomePage", e.getMessage());
            mTemplateListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetInitHomePage", e.getMessage());
        } finally {
            mTemplateListener.onFinish();
        }
    }

    //我的收藏-获取收藏商品功能
    public void GetFavoriteSample(int pageNum) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetFavoriteSampleWorker(pageNum));
        executor.shutdown();
    }

    public class GetFavoriteSampleWorker implements Runnable {
        int pageNum;

        public GetFavoriteSampleWorker(int pageNum) {
            super();
            this.pageNum = pageNum;
        }

        @Override
        public void run() {
            doGetFavoriteSample(pageNum);
        }

    }

    public void doGetFavoriteSample(int pageNum) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageNum", "" + pageNum);
        try {
            Response response = HttpClientUtil
                    .doPost(ConstantS.BASE_URL + "user/favoriteSampleList.htm", map,
                            UserHelper.isBuyerLogin(), UserHelper.getBuyerId(), UserHelper.getBuyertoken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    FavoriteSampleListResult favorite_result = gson.fromJson(data_json.toString(), FavoriteSampleListResult.class);
                    favoriteSampleListener.onSuccess(favorite_result
                            .getFavoritesampleList());
                } else {
                    favoriteSampleListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                favoriteSampleListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doGetFavoriteSample", e.getMessage());
            favoriteSampleListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetFavoriteSample", e.getMessage());
        } finally {
            favoriteSampleListener.onFinish();
        }
    }

    //我的收藏-获取收藏店铺功能
    public void GetFavoriteShop(int pageNum) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetFavoriteShopWorker(pageNum));
        executor.shutdown();
    }

    public class GetFavoriteShopWorker implements Runnable {
        int pageNum;

        public GetFavoriteShopWorker(int pageNum) {
            super();
            this.pageNum = pageNum;
        }

        @Override
        public void run() {
            doGetFavoriteShop(pageNum);
        }

    }

    public void doGetFavoriteShop(int pageNum) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();
        map.put("pageNum", "" + pageNum);
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "user/favoriteShopList.htm", map, UserHelper.isBuyerLogin(), UserHelper.getBuyerId(), UserHelper.getBuyertoken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    FavoriteShopListResult favorite_result = gson.fromJson(data_json.toString(), FavoriteShopListResult.class);
                    favorite_result.setResult(result);
                    favoriteShopListener.onSuccess(favorite_result.getFavoriteshopList());
                } else {
                    favoriteShopListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                favoriteShopListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doGetFavoriteShop", e.getMessage());
            favoriteShopListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetFavoriteShop", e.getMessage());
        } finally {
            favoriteShopListener.onFinish();
        }
    }

    // 获取类目列表功能
    public void GetCategoryList(boolean isLogin, int userId, String token) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetCategoryListWorker(isLogin, userId, token));
        executor.shutdown();
    }

    public class GetCategoryListWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;

        public GetCategoryListWorker(boolean isLogin, int userId, String token) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
        }

        @Override
        public void run() {
            doGetCategoryList(isLogin, userId, token);
        }

    }

    public void doGetCategoryList(boolean isLogin, int userId, String token) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL
                    + "category/getCategoryList.htm", isLogin, userId, token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    CategoryFirstListResult categorylist_result = gson
                            .fromJson(data_json.toString(),
                                    CategoryFirstListResult.class);
                    categorylist_result.setResult(result);
                    mPageListListener.onSuccess(categorylist_result
                            .getFirstCategoryList());
                } else {
                    mPageListListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mPageListListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doGetCategoryList", e.getMessage());
            mPageListListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetCategoryList", e.getMessage());
        } finally {
            mPageListListener.onFinish();
        }
    }

    // 获取商品列表，并有多种方式请求，参数不固定了，使用map参数
    public void GetCategorySample(boolean isLogin, Map<String, String> map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetCategorySampleWorker_(isLogin, map));
        executor.shutdown();
    }

    public class GetCategorySampleWorker_ implements Runnable {
        Map<String, String> map;
        boolean isLogin;

        public GetCategorySampleWorker_(boolean isLogin, Map<String, String> map) {
            super();
            this.map = map;
            this.isLogin = isLogin;
        }

        @Override
        public void run() {
            doGetCategorySample(isLogin, map);
        }
    }

    public void doGetCategorySample(boolean isLogin, Map<String, String> map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "sample/sampleList.htm", map, isLogin, UserHelper.getBuyerId(), UserHelper.getBuyertoken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    SampleListResult samplelist_result = gson.fromJson(data_json.toString(), SampleListResult.class);
                    mPageListHasNextListener.onSuccess(samplelist_result.getSampleList(), samplelist_result.isHasNext());
                } else {
                    mPageListHasNextListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mPageListHasNextListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doGetCategorySample", e.getMessage());
            mPageListHasNextListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetCategorySample", e.getMessage());
        } finally {
            mPageListHasNextListener.onFinish();
        }
    }

    //获取产品详细
    public void GetSampleDetail(boolean isLogin, String sampleId) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetSampleDetailWorker(isLogin, sampleId));
        executor.shutdown();
    }

    public class GetSampleDetailWorker implements Runnable {
        boolean isLogin;
        String sampleId;

        public GetSampleDetailWorker(boolean isLogin, String sampleId) {
            super();
            this.isLogin = isLogin;
            this.sampleId = sampleId;
        }

        @Override
        public void run() {
            doGetSampleDetail(isLogin, sampleId);
        }

    }

    public void doGetSampleDetail(boolean isLogin, String sampleId) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL
                    + "sample/sampleDetail/" + sampleId + ".htm", isLogin, UserHelper.getBuyerId(), UserHelper.getBuyertoken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    SampleDetailResult sampledetail_result = gson.fromJson(data_json.toString(), SampleDetailResult.class);
                    sampledetail_result.setResult(result);
                    sampledetailListener.onSuccess(sampledetail_result);
                } else {
                    sampledetailListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                sampledetailListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doGetSampleDetail", e.getMessage());
            sampledetailListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetSampleDetail", e.getMessage());
        } finally {
            sampledetailListener.onFinish();
        }
    }

    //获取购物车列表
    public void GetShoppingCart(boolean isLogin, int userId, String token,
                                int pageNum) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetShoppingCartWorker(isLogin, userId, token,
                pageNum));
        executor.shutdown();
    }

    public class GetShoppingCartWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        int pageNum;

        public GetShoppingCartWorker(boolean isLogin, int userId, String token,
                                     int pageNum) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.pageNum = pageNum;
        }

        @Override
        public void run() {
            doGetShoppingCart(isLogin, userId, token, pageNum);
        }

    }

    public void doGetShoppingCart(boolean isLogin, int userId, String token,
                                  int pageNum) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageNum", pageNum + "");
        try {
            Response response = HttpClientUtil
                    .doPost(ConstantS.BASE_URL + "trade/shoppingCartList.htm", map,
                            isLogin, userId, token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    ShoppingCartResult shoppingcart_result = gson.fromJson(
                            data_json.toString(), ShoppingCartResult.class);
                    shoppingcart_result.setResult(result);
                    shoppingcartListener.onSuccess(shoppingcart_result.getCartShopList());
                } else {
                    shoppingcartListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                shoppingcartListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doGetShoppingCart", e.getMessage());
            shoppingcartListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetShoppingCart", e.getMessage());
        } finally {
            shoppingcartListener.onFinish();
        }
    }

    //搜索店铺
    public void GetShopBySearch(boolean isLogin, String name, int pageNum) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetShopBySearchWorker(isLogin, name, pageNum));
        executor.shutdown();
    }

    public class GetShopBySearchWorker implements Runnable {
        boolean isLogin;
        String name;
        int pageNum;

        public GetShopBySearchWorker(boolean isLogin, String name, int pageNum) {
            super();
            this.isLogin = isLogin;
            this.name = name;
            this.pageNum = pageNum;

        }

        @Override
        public void run() {
            doGetShopBySearch(isLogin, name, pageNum);
        }
    }

    public void doGetShopBySearch(boolean isLogin, String name, int pageNum) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("pageNum", pageNum + "");
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "shop/shopList.htm", map, isLogin, UserHelper.getBuyerId(), UserHelper.getBuyertoken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    ShopListResult shoplist_result = gson.fromJson(data_json.toString(), ShopListResult.class);
                    mPageListHasNextListener.onSuccess(shoplist_result.getShopList(), shoplist_result.isHasNext());
                } else {
                    mPageListHasNextListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mPageListHasNextListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doGetShopBySearch", e.getMessage());
            mPageListHasNextListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetShopBySearch", e.getMessage());
        } finally {
            mPageListHasNextListener.onFinish();
        }
    }

    //商品详情-添加产品到购物车
    public void PostShoppingCart(Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new PostShoppingCartWorker(map));
        executor.shutdown();
    }

    public class PostShoppingCartWorker implements Runnable {
        private Map map;

        public PostShoppingCartWorker(Map map) {
            super();
            this.map = map;
        }

        @Override
        public void run() {
            doPostShoppingCart(map);
        }
    }

    public void doPostShoppingCart(Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "trade/addShoppingCart.htm", map, UserHelper.isBuyerLogin(), UserHelper.getBuyerId(), UserHelper.getBuyertoken());
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
            Log.e("doPostShoppingCart", e.getMessage());
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doPostShoppingCart", e.getMessage());
        } finally {
            responseListener.onFinish();
        }
    }

    //从购物车中删除商品
    public void DeleteShoppingCart(boolean isLogin, int userId, String token,
                                   String ids) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new DeleteShoppingCartWorker(isLogin, userId, token,
                ids));
        executor.shutdown();
    }

    public class DeleteShoppingCartWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        String ids;

        public DeleteShoppingCartWorker(boolean isLogin, int userId,
                                        String token, String ids) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.ids = ids;
        }

        @Override
        public void run() {
            doDeleteShoppingCart(isLogin, userId, token, ids);
        }
    }

    public void doDeleteShoppingCart(boolean isLogin, int userId, String token,
                                     String ids) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("ids", ids);
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                            + "trade/batchDeleteCartSample.htm", map, isLogin, userId,
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
                    responseListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
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

    /**
     * 买家修改密码
     * @param isLogin
     * @param userId
     * @param token
     * @param map
     */
    public void ChangePassword(boolean isLogin, int userId, String token,Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new ChangePasswordWorker(isLogin, userId, token,map));
        executor.shutdown();
    }

    public class ChangePasswordWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        Map  map;

        public ChangePasswordWorker(boolean isLogin, int userId, String token,Map map) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.map = map;
        }

        @Override
        public void run() {
            doChangePassword(isLogin, userId, token, map);
        }
    }

    public void doChangePassword(boolean isLogin, int userId, String token,Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL+ "user/changePassword.htm", map, isLogin, userId, token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),ResponseResult.class);
                if (result.isSuccess()) {
                    responseListener.onSuccess(result.getErrorInfo());
                } else {
                    responseListener.onError(result.getErrorNO(),result.getErrorInfo());
                }
            } else {
                responseListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doChangePassword", e.getMessage());
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doChangePassword", e.getMessage());
        } finally {
            responseListener.onFinish();
        }
    }

    /**
     * 卖家 买家 忘记密码功能
     */
    public void ForgetPassword(boolean isLogin, int userId, String token,Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new ForgetPasswordWorker(isLogin, userId, token, map));
        executor.shutdown();
    }

    public class ForgetPasswordWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        Map  map;

        public ForgetPasswordWorker(boolean isLogin, int userId,String token,Map  map) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.map = map;
        }

        @Override
        public void run() {
            doForgetPassword(isLogin, userId, token, map);
        }
    }

    public void doForgetPassword(boolean isLogin, int userId, String token,Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL + "user/forgetPassword.htm", map, isLogin, userId, token);
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
            Log.e("doForgetPassword", e.getMessage());
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doForgetPassword", e.getMessage());
        } finally {
            responseListener.onFinish();
        }
    }

    /**
     * @param type 0:未收藏 1：已收藏
     */
    public void Collect(boolean isLogin, int userId, String token,
                        int targetId, int type) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new CollectWorker(isLogin, userId, token, targetId,
                type));
        executor.shutdown();
    }

    public class CollectWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        int targetId;
        int type;

        public CollectWorker(boolean isLogin, int userId, String token,
                             int targetId, int type) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.targetId = targetId;
            this.type = type;
        }

        @Override
        public void run() {
            doCollect(isLogin, userId, token, targetId, type);
        }
    }

    public void doCollect(boolean isLogin, int userId, String token,
                          int targetId, int type) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("targetId", targetId + "");
        map.put("type", type + "");
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "user/saveFavorite.htm", map, isLogin, userId, token);
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
            Log.e("doCollect", e.getMessage());
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doCollect", e.getMessage());
        } finally {
            responseListener.onFinish();
        }
    }

    //取消收藏
    public void UnCollect(boolean isLogin, int userId, String token, int type,
                          String targetId) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new UnCollectWorker(isLogin, userId, token, type,
                targetId));
        executor.shutdown();
    }

    public class UnCollectWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        String targetId;
        int type;

        public UnCollectWorker(boolean isLogin, int userId, String token,
                               int type, String targetId) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.targetId = targetId;
            this.type = type;
        }

        @Override
        public void run() {
            doUnCollect(isLogin, userId, token, type, targetId);
        }
    }

    public void doUnCollect(boolean isLogin, int userId, String token,
                            int type, String targetId) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", type + "");
        map.put("targetId", targetId);
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "user/deleteFavorite.htm", map, isLogin, userId, token);
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
            Log.e("doUnCollect", e.getMessage());
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doUnCollect", e.getMessage());
        } finally {
            responseListener.onFinish();
        }
    }

    // 购物车确认下单功能，使用默认服务器传递的地址
    public void ConfirmCart(boolean isLogin, int userId, String token,Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new ConfirmCartWorker(isLogin, userId, token,map));
        executor.shutdown();
    }

    public class ConfirmCartWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        Map map;

        public ConfirmCartWorker(boolean isLogin, int userId, String token,Map map) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.map = map;
        }

        @Override
        public void run() {
            doConfirmCart(isLogin, userId, token, map);

        }
    }

    public void doConfirmCart(boolean isLogin, int userId, String token,Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                            + "trade/confirmShoppingCart.htm", map, isLogin, userId,token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    ConfirmCartResult confirmcart_result = gson.fromJson(
                            data_json.toString(), ConfirmCartResult.class);
                    confirmcart_result.setResult(result);
                    confirmcartListener.onSuccess(confirmcart_result);
                } else {
                    confirmcartListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                confirmcartListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doConfirmCart", e.getMessage());
            confirmcartListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doConfirmCart", e.getMessage());
        } finally {
            confirmcartListener.onFinish();
        }
    }

    // 购物车下订单，自己选择购物地址功能
    public void ConfirmCartWithAddressId(boolean isLogin, int userId,
                                         String token, String confirmStr, int addressId) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new ConfirmCartWithAddressIdWorker(isLogin, userId,
                token, confirmStr, addressId));
        executor.shutdown();
    }

    public class ConfirmCartWithAddressIdWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        String confirmStr;
        int addressId;

        public ConfirmCartWithAddressIdWorker(boolean isLogin, int userId,
                                              String token, String confirmStr, int addressId) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.confirmStr = confirmStr;
            this.addressId = addressId;
        }

        @Override
        public void run() {
            doConfirmCartWithAddressId(isLogin, userId, token, confirmStr,
                    addressId);

        }
    }

    public void doConfirmCartWithAddressId(boolean isLogin, int userId,
                                           String token, String confirmStr, int addressId) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();
        map.put("confirmStr", confirmStr);
        map.put("shoppingAddressId", addressId + "");
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                            + "trade/confirmShoppingCart.htm", map, isLogin, userId,
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
                    ConfirmCartResult confirmcart_result = gson.fromJson(
                            data_json.toString(), ConfirmCartResult.class);
                    confirmcart_result.setResult(result);
                    confirmcartListener.onSuccess(confirmcart_result);
                } else {
                    confirmcartListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                confirmcartListener.onLose();
            }

        } catch (NetException e) {
            Log.e("CartWithAddressId", e.getMessage());
            confirmcartListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("CartWithAddressId", e.getMessage());
        } finally {
            confirmcartListener.onFinish();
        }
    }

    // 立即购买下单功能，不带地址参数
    public void BuyNow(boolean isLogin, int userId, String token, Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new BuyNowWorker(isLogin, userId, token, map));
        executor.shutdown();
    }

    public class BuyNowWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        Map map;

        public BuyNowWorker(boolean isLogin, int userId, String token, Map map) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.map = map;
        }

        @Override
        public void run() {
            doBuyNow(isLogin, userId, token, map);
        }
    }

    public void doBuyNow(boolean isLogin, int userId, String token, Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "trade/sampleBuyNow.htm", map, isLogin, userId, token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    ConfirmCartResult confirmcart_result = gson.fromJson(data_json.toString(), ConfirmCartResult.class);
                    confirmcart_result.setResult(result);
                    confirmcartListener.onSuccess(confirmcart_result);
                } else {
                    confirmcartListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                confirmcartListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doBuyNow", e.getMessage());
            confirmcartListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doBuyNow", e.getMessage());
        } finally {
            confirmcartListener.onFinish();
        }
    }

    // 立即购买下单功能,带地址参数
    public void BuyNowWithAddressId(boolean isLogin, int userId, String token,
                                    int sampleId, int sampleSKUId, int num, int addressId) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new BuyNowWithAddressIdWorker(isLogin, userId, token,
                sampleId, sampleSKUId, num, addressId));
        executor.shutdown();
    }

    public class BuyNowWithAddressIdWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        int sampleId;
        int sampleSKUId;
        int num;
        int addressId;

        public BuyNowWithAddressIdWorker(boolean isLogin, int userId,
                                         String token, int sampleId, int sampleSKUId, int num,
                                         int addressId) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.sampleId = sampleId;
            this.sampleSKUId = sampleSKUId;
            this.num = num;
            this.addressId = addressId;
        }

        @Override
        public void run() {
            doBuyNowWithAddressId(isLogin, userId, token, sampleId,
                    sampleSKUId, num, addressId);
        }
    }

    public void doBuyNowWithAddressId(boolean isLogin, int userId,
                                      String token, int sampleId, int sampleSKUId, int num, int addressId) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("sampleId", sampleId + "");
        map.put("shoppingAddressId", addressId + "");
        if (sampleSKUId != -1)
            map.put("sampleSKUId", "" + sampleSKUId);
        map.put("num", num + "");
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "trade/sampleBuyNow.htm", map, isLogin, userId, token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    ConfirmCartResult confirmcart_result = gson.fromJson(
                            data_json.toString(), ConfirmCartResult.class);
                    confirmcart_result.setResult(result);
                    confirmcartListener.onSuccess(confirmcart_result);
                } else {
                    confirmcartListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                confirmcartListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doBuyNowWithAddressId", e.getMessage());
            confirmcartListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doBuyNowWithAddressId", e.getMessage());
        } finally {
            confirmcartListener.onFinish();
        }
    }

    /**
     *  购物车 确认下单功能
      */
    public void PostOrder(boolean isLogin, int userId, String token,Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new PostOrderWorker(isLogin, userId, token, map));
        executor.shutdown();
    }

    public class PostOrderWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        Map map;

        public PostOrderWorker(boolean isLogin, int userId, String token,Map map) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.map = map;
        }

        @Override
        public void run() {
            doPostOrder(isLogin, userId, token, map);
        }
    }

    public void doPostOrder(boolean isLogin, int userId, String token,Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL + "trade/cartMakeOrder.htm", map, isLogin, userId, token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
//                JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
//                MakeOrderResult settle_result = gson.fromJson(data_json.toString(),MakeOrderResult.class);
//                settle_result.setResult(result);
                if (result.isSuccess()) {
                    shoppingcartsettleListener.onSuccess( result.getErrorNO() , result.getErrorInfo());
                } else {
                    shoppingcartsettleListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                shoppingcartsettleListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doPostOrder", e.getMessage());
            shoppingcartsettleListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doPostOrder", e.getMessage());
        } finally {
            shoppingcartsettleListener.onFinish();
        }
    }
    /**
     * 买家通过立即购买确认下单功能
     * @param isLogin
     * @param userId
     * @param token
     * @param map
     */
    public void PostBuyOrder(boolean isLogin, int userId, String token, Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new PostBuyOrderWorker(isLogin, userId, token, map));
        executor.shutdown();
    }

    public class PostBuyOrderWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        Map map;

        public PostBuyOrderWorker(boolean isLogin, int userId, String token, Map map) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.map = map;
        }

        @Override
        public void run() {
            doPostBuyOrder(isLogin, userId, token, map);
        }
    }

    public void doPostBuyOrder(boolean isLogin, int userId, String token, Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "trade/sampleMakeOrder.htm", map, isLogin, userId, token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    mResponseListener.onSuccess(result.getErrorInfo());

                } else {
                    mResponseListener.onError(result.getErrorNO(),result.getErrorInfo());
                }
            } else {
                mResponseListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doPostBuyOrder", e.getMessage());
            mResponseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doPostBuyOrder", e.getMessage());
        } finally {
            mResponseListener.onFinish();
        }
    }
    /**
     * 买家下单获取收货收款地址
     */
    public void getReceivePayAddr(Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetReceivePayAddrWorker(map));
        executor.shutdown();
    }

    public class GetReceivePayAddrWorker implements Runnable {
        Map map;

        public GetReceivePayAddrWorker(Map map) {
            super();
            this.map = map;
        }

        @Override
        public void run() {
            doGetReceivePayAddr(map);
        }
    }

    public void doGetReceivePayAddr(Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "user/getAddressInfo.htm", map, UserHelper.isBuyerLogin(), UserHelper.getBuyerId(), UserHelper.getBuyertoken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    RPAddressResult rPAddressResult = gson.fromJson(data_json.toString(), RPAddressResult.class);
                    mOneDataListener.onSuccess(rPAddressResult,result.getErrorInfo());
                } else {
                    mOneDataListener.onError(result.getErrorNO(),result.getErrorInfo());
                }
            } else {
                mOneDataListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doGetReceivePayAddr", e.getMessage());
            mOneDataListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetReceivePayAddr", e.getMessage());
        } finally {
            mOneDataListener.onFinish();
        }
    }

    // 获取我的订单功能
    public void GetMyOrder(boolean isLogin, int userId, String token,
                           int pageNum, int orderStatus) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetMyOrderWorker(isLogin, userId, token, pageNum,
                orderStatus));
        executor.shutdown();
    }

    public class GetMyOrderWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        int pageNum;
        int orderStatus;

        public GetMyOrderWorker(boolean isLogin, int userId, String token,
                                int pageNum, int orderStatus) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.pageNum = pageNum;
            this.orderStatus = orderStatus;
        }

        @Override
        public void run() {
            doGetMyOrder(isLogin, userId, token, pageNum, orderStatus);
        }
    }

    public void doGetMyOrder(boolean isLogin, int userId, String token,
                             int pageNum, int orderStatus) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("startRow", pageNum + "");
        map.put("orderStatus", orderStatus + "");
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "trade/buyer/orderList.htm", map, isLogin, userId, token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    GetMyOrderResult getmyorder_result = gson.fromJson(
                            data_json.toString(), GetMyOrderResult.class);
                    getmyorder_result.setResult(result);
                    getmyorderListener.onSuccess(getmyorder_result
                            .getRows(), getmyorder_result.isHasNext());
                } else {
                    getmyorderListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                getmyorderListener.onLose();
            }

        } catch (NetException e) {
            getmyorderListener.onError(NETERROR_CODE, mTranslatesString.getNotice_requesterror());
            Log.e("doGetMyOrder", e.getMessage());
        } catch (JSONException e) {
            Log.e("doGetMyOrder", e.getMessage());
        } finally {
            getmyorderListener.onFinish();
        }
    }
    /**
     * 买家获取我的订单退货列表
     */
    public void getRefundList(boolean isLogin, int userId, String token,int pageNum) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetRefundListWorker(isLogin, userId, token, pageNum));
        executor.shutdown();
    }
    public class GetRefundListWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        int pageNum;

        public GetRefundListWorker(boolean isLogin, int userId, String token,int pageNum) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.pageNum = pageNum;
        }
        @Override
        public void run() {
            doGetRefundList(isLogin, userId, token, pageNum);
        }
    }

    public void doGetRefundList(boolean isLogin, int userId, String token,int pageNum) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();
        map.put("startRow", pageNum + "");
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL+ "trade/refundList.htm", map, isLogin, userId, token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    RefundListResult getmyorder_result = gson.fromJson(data_json.toString(), RefundListResult.class);
                    mPageListHasNextListener.onSuccess(getmyorder_result.getRefundList(), getmyorder_result.isHasNext());
                } else {
                    mPageListHasNextListener.onError(result.getErrorNO(),result.getErrorInfo());
                }
            } else {
                mPageListHasNextListener.onLose();
            }

        } catch (NetException e) {
            mPageListHasNextListener.onError(NETERROR_CODE, mTranslatesString.getNotice_requesterror());
            Log.e("doGetRefundList", e.getMessage());
        } catch (JSONException e) {
            Log.e("doGetRefundList", e.getMessage());
        } finally {
            mPageListHasNextListener.onFinish();
        }
    }

    // 获取店铺详情功能
    public void GetShopHome(boolean isLogin, int userId, String token,
                            int shopId) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetShopHomeWorker(isLogin, userId, token, shopId));
        executor.shutdown();
    }

    public class GetShopHomeWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        int shopId;

        public GetShopHomeWorker(boolean isLogin, int userId, String token,
                                 int shopId) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.shopId = shopId;
        }

        @Override
        public void run() {
            doGetShopHome(isLogin, userId, token, shopId);
        }
    }

    public void doGetShopHome(boolean isLogin, int userId, String token,
                              int shopId) {
        Log.d("sssss", String.valueOf(isLogin));
        Log.d("sssss", String.valueOf(userId));
        Log.d("sssss", token);
        Log.d("sssss", String.valueOf(shopId));
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL
                            + "shop/shopHomePage/" + shopId + ".htm", isLogin, userId,
                    token);
            Log.d("sssss", String.valueOf(response.getStatusCode()));
            Log.d("sssss", String.valueOf(HttpStatus.SC_OK));

            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    GetShopHomeResult getshophome_result = gson.fromJson(
                            data_json.toString(), GetShopHomeResult.class);
                    getshophome_result.setResult(result);
                    getshophomeListener.onSuccess(getshophome_result);
                } else {
                    getshophomeListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                getshophomeListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doGetShopHome", e.getMessage());
            getshophomeListener.onError(NETERROR_CODE, mTranslatesString.getNotice_requesterror());
        } catch (JSONException e) {
            Log.e("doGetShopHome", e.getMessage());
        } finally {
            getshophomeListener.onFinish();
        }
    }

    // 通过个人中心查看个人收货地址列表功能
    public void GetShoppingAddressList(int type) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetShoppingAddressListWorker(type));
        executor.shutdown();
    }

    public class GetShoppingAddressListWorker implements Runnable {
        int type;

        public GetShoppingAddressListWorker(int type) {
            super();
            this.type = type;
        }

        @Override
        public void run() {
            doPostShoppingAddressList(type);
        }
    }

    public void doPostShoppingAddressList(int type) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", type + "");
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "user/shoppingAddressList.htm", map, UserHelper.isBuyerLogin(), UserHelper.getBuyerId(), UserHelper.getBuyertoken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    GetShoppingAddressResult ls = gson
                            .fromJson(data_json.toString(),
                                    GetShoppingAddressResult.class);
                    mPageListListener.onSuccess(ls.getShoppingAddressList());
                } else {
                    mPageListListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mPageListListener.onLose();
            }

        } catch (NetException e) {
            Log.e("ShoppingAddressList", e.getMessage());
            mPageListListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("GetShoppingAddressList", e.getMessage());
        } finally {
            mPageListListener.onFinish();
        }
    }

    // 获取收货地址详情功能
    public void GetShoppingAddressDetail(boolean isLogin, int userId,
                                         String token, int addrId) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetShoppingAddressDetailWorker(isLogin, userId,
                token, addrId));
        executor.shutdown();
    }

    public class GetShoppingAddressDetailWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        int addrId;

        public GetShoppingAddressDetailWorker(boolean isLogin, int userId,
                                              String token, int addrId) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.addrId = addrId;
        }

        @Override
        public void run() {
            doGetShoppingAddressDetail(isLogin, userId, token, addrId);
        }
    }

    public void doGetShoppingAddressDetail(boolean isLogin, int userId,
                                           String token, int addrId) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL
                            + "user/shoppingAddressDetail/" + addrId + ".htm", isLogin,
                    userId, token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    ShoppingAddressDetail address_detail_result = gson
                            .fromJson(data_json.toString(),
                                    ShoppingAddressDetail.class);
                    shoppingaddressdetailListener
                            .onSuccess(address_detail_result);
                } else {
                    shoppingaddressdetailListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                shoppingaddressdetailListener.onLose();
            }

        } catch (NetException e) {
            Log.e("ShopAddressDetail", e.getMessage());
            shoppingaddressdetailListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("ShoppAddressDetail", e.getMessage());
        } finally {
            shoppingaddressdetailListener.onFinish();
        }
    }

    // 获取所有的省份
    public void GetAllProvinceCode(boolean isLogin, int userId, String token) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetAllProvinceCode(isLogin, userId, token));
        executor.shutdown();
    }

    public class GetAllProvinceCode implements Runnable {
        boolean isLogin;
        int userId;
        String token;

        public GetAllProvinceCode(boolean isLogin, int userId, String token) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
        }

        @Override
        public void run() {
            doGetAllProvinceCode(isLogin, userId, token);
        }
    }

    public void doGetAllProvinceCode(boolean isLogin, int userId, String token) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "common/listAllProvince.htm", null, false, userId, token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    CodeResult code_result = gson.fromJson(data_json.toString(), CodeResult.class);
                    responseHanderListener.onSuccess(code_result.getCodeList());
                } else {
                    responseHanderListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                responseHanderListener.lose();
            }
        } catch (NetException e) {
            Log.e("GetAllProvinceCode", e.getMessage());
            responseHanderListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("GetAllProvinceCode", e.getMessage());
        }
    }

    // 获取下一级地区功能
    public void GetNextCode(boolean isLogin, int userId, String token, String code) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetNextCodeWorker(isLogin, userId, token, code));
        executor.shutdown();
    }

    public class GetNextCodeWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        String code;

        public GetNextCodeWorker(boolean isLogin, int userId, String token, String code) {
            super();
            this.code = code;
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
        }

        @Override
        public void run() {
            doGetNextCode(isLogin, userId, token, code);
        }
    }

    public void doGetNextCode(boolean isLogin, int userId, String token, String code) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "common/nextLevelList.htm", map, false, userId, code);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(), ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    CodeResult code_result = gson.fromJson(data_json.toString(), CodeResult.class);
                    responseHanderListener.onSuccess(code_result.getCodeList());
                } else {
                    responseHanderListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                responseHanderListener.lose();
            }
        } catch (NetException e) {
            Log.e("doGetNextCode", e.getMessage());
            responseHanderListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetNextCode", e.getMessage());
        }
    }

    // 增加收货地址功能
    public void AddShoppingAddress(Map<String, String> map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new AddShoppingAddressWorker(map));
        executor.shutdown();
    }

    public class AddShoppingAddressWorker implements Runnable {
        Map<String, String> map;

        public AddShoppingAddressWorker(Map<String, String> map) {
            super();
            this.map = map;
        }

        @Override
        public void run() {
            doAddShoppingAddress(map);
        }
    }

    public void doAddShoppingAddress(Map<String, String> map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                            + "user/addShoppingAddress.htm", map, UserHelper.isBuyerLogin(), UserHelper.getBuyerId(),
                    UserHelper.getBuyertoken());
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
            Log.e("doAddShoppingAddress", e.getMessage());
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doAddShoppingAddress", e.getMessage());
        } finally {
            responseListener.onFinish();
        }
    }

    // 修改收货地址功能
    public void ChangeShoppingAddress(Map<String, String> map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new ChangeShoppingAddressWorker(map));
        executor.shutdown();
    }

    public class ChangeShoppingAddressWorker implements Runnable {
        Map<String, String> map;

        public ChangeShoppingAddressWorker(Map<String, String> map) {
            super();
            this.map = map;
        }

        @Override
        public void run() {
            doChangeShoppingAddress(map);
        }
    }

    public void doChangeShoppingAddress(Map<String, String> map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                            + "user/updateShoppingAddress.htm", map, UserHelper.isBuyerLogin(), UserHelper.getBuyerId(),
                    UserHelper.getBuyertoken());
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
            Log.e("doChangeShoppingAddress", e.getMessage());
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doChangeShoppingAddress", e.getMessage());
        } finally {
            responseListener.onFinish();
        }
    }

    // 删除收货地址功能
    public void DeleteShoppingAddress(int addressId) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new DeleteShoppingAddressWorker(addressId));
        executor.shutdown();
    }

    public class DeleteShoppingAddressWorker implements Runnable {
        int addressId;

        public DeleteShoppingAddressWorker(int addressId) {
            super();
            this.addressId = addressId;
        }

        @Override
        public void run() {
            doDeleteShoppingAddress(addressId);
        }
    }

    public void doDeleteShoppingAddress(int addressId) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL
                            + "user/deleteShoppingAddress/" + addressId + ".htm",
                    UserHelper.isBuyerLogin(), UserHelper.getBuyerId(), UserHelper.getBuyertoken());
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
            Log.e("doDeleteShoppingAddress", e.getMessage());
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doDeleteShoppingAddress", e.getMessage());
        } finally {
            responseListener.onFinish();
        }
    }

    // 获取店铺简介功能
    public void GetShopDetail(boolean isLogin, int userId, String token,
                              int shopId) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetShopDetailWorker(isLogin, userId, token, shopId));
        executor.shutdown();
    }

    public class GetShopDetailWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        int shopId;

        public GetShopDetailWorker(boolean isLogin, int userId, String token,
                                   int shopId) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.shopId = shopId;
        }

        @Override
        public void run() {
            doGetShopDetail(isLogin, userId, token, shopId);
        }
    }

    public void doGetShopDetail(boolean isLogin, int userId, String token,
                                int shopId) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL
                            + "shop/shopDetail/" + shopId + ".htm", isLogin, userId,
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
                    GetShopDetailResult shopdetail_result = gson.fromJson(
                            data_json.toString(), GetShopDetailResult.class);
                    getshopdetailListener.onSuccess(shopdetail_result);
                } else {
                    getshopdetailListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                getshopdetailListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doGetShopDetail", e.getMessage());
            getshopdetailListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetShopDetail", e.getMessage());
        } finally {
            getshopdetailListener.onFinish();
        }
    }

    // 获取用户银行卡列表功能
    public void GetBankCardList(boolean isLogin, int userId, String token) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetBankCardListWorker(isLogin, userId, token));
        executor.shutdown();
    }

    public class GetBankCardListWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;

        public GetBankCardListWorker(boolean isLogin, int userId, String token) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
        }

        @Override
        public void run() {
            doGetBankCardList(isLogin, userId, token);
        }
    }

    public void doGetBankCardList(boolean isLogin, int userId, String token) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL
                    + "user/memberBankCardList.htm", isLogin, userId, token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    GetBankCardListResult bankcardlist_result = gson.fromJson(
                            data_json.toString(), GetBankCardListResult.class);
                    getbankcardlistListener.onSuccess(bankcardlist_result
                            .getMemberBankCardList());
                } else {
                    getbankcardlistListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                getbankcardlistListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doGetBankCardList", e.getMessage());
            getbankcardlistListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetBankCardList", e.getMessage());
        } finally {
            getbankcardlistListener.onFinish();
        }
    }

    // 获取订单详情功能
    public void GetOrderDetail(boolean isLogin, int userId, String token,
                               int orderId) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetOrderDetailWorker(isLogin, userId, token,
                orderId));
        executor.shutdown();
    }

    public class GetOrderDetailWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        int orderId;

        public GetOrderDetailWorker(boolean isLogin, int userId, String token,
                                    int orderId) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.orderId = orderId;
        }

        @Override
        public void run() {
            doGetOrderDetail(isLogin, userId, token, orderId);
        }
    }

    public void doGetOrderDetail(boolean isLogin, int userId, String token,
                                 int orderId) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL
                            + "trade/orderDetail/" + orderId + ".htm", isLogin, userId,
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
                    OrderDetailResult orderdetail_result = gson.fromJson(
                            data_json.toString(), OrderDetailResult.class);
                    orderdetailListener.onSuccess(orderdetail_result);
                } else {
                    orderdetailListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                orderdetailListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doGetOrderDetail", e.getMessage());
            orderdetailListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetOrderDetail", e.getMessage());
        } finally {
            orderdetailListener.onFinish();
        }
    }

    // 更改订单状态功能
    public void UpdateOrderStatus(boolean isLogin, int userId, String token,
                                  int orderStatus, String ids) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new UpdateOrderStatusWorker(isLogin, userId, token,
                orderStatus, ids));
        executor.shutdown();
    }

    public class UpdateOrderStatusWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        int orderStatus;
        String ids;

        public UpdateOrderStatusWorker(boolean isLogin, int userId,
                                       String token, int orderStatus, String ids) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.orderStatus = orderStatus;
            this.ids = ids;
        }

        @Override
        public void run() {
            doUpdateOrderStatus(isLogin, userId, token, orderStatus, ids);
        }
    }

    public void doUpdateOrderStatus(boolean isLogin, int userId, String token,
                                    int orderStatus, String ids) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("orderStatus", orderStatus + "");
        map.put("ids", ids);
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
                    responseListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                responseListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doGetOrderDetail", e.getMessage());
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetOrderDetail", e.getMessage());
        } finally {
            responseListener.onFinish();
        }
    }

    // 添加订单评论功能
    public void SaveSampleEvaluation(Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new SaveSampleEvaluationWorker(map));
        executor.shutdown();
    }

    public class SaveSampleEvaluationWorker implements Runnable {

        Map map;

        public SaveSampleEvaluationWorker(Map map) {
            super();
            this.map = map;
        }

        @Override
        public void run() {
            doSaveSampleEvaluation(map);
        }
    }

    public void doSaveSampleEvaluation(Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL+ "sample/saveSampleEvaluation.htm", map, UserHelper.isBuyerLogin(), UserHelper.getBuyerId(),
                    UserHelper.getBuyertoken());
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
            Log.e("doSaveSampleEvaluation", e.getMessage());
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doSaveSampleEvaluation", e.getMessage());
        } finally {
            responseListener.onFinish();
        }
    }

    // 申请退款功能
    public void SaveRefund(boolean isLogin, int userId, String token,
                           int shopId, int orderDetailId, int isReceving, double refundPrice,
                           int refundReason, String picEvidence, String refundExplain) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new SaveRefundWorker(isLogin, userId, token, shopId,
                orderDetailId, isReceving, refundPrice, refundReason,
                picEvidence, refundExplain));
        executor.shutdown();
    }

    public class SaveRefundWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        int shopId;
        int orderDetailId;
        int isReceving;
        double refundPrice;
        int refundReason;
        String picEvidence;
        String refundExplain;

        public SaveRefundWorker(boolean isLogin, int userId, String token,
                                int shopId, int orderDetailId, int isReceving,
                                double refundPrice, int refundReason, String picEvidence,
                                String refundExplain) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.shopId = shopId;
            this.orderDetailId = orderDetailId;
            this.isReceving = isReceving;
            this.refundPrice = refundPrice;
            this.refundReason = refundReason;
            this.picEvidence = picEvidence;
            this.refundExplain = refundExplain;
        }

        @Override
        public void run() {
            doSaveRefund(isLogin, userId, token, shopId, orderDetailId,
                    isReceving, refundPrice, refundReason, picEvidence,
                    refundExplain);
        }
    }

    public void doSaveRefund(boolean isLogin, int userId, String token,
                             int shopId, int orderDetailId, int isReceving, double refundPrice,
                             int refundReason, String picEvidence, String refundExplain) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("shopId", shopId + "");
        map.put("orderDetailId", orderDetailId + "");
        map.put("isReceiving", isReceving + "");
        map.put("refundPrice", refundPrice + "");
        map.put("refundReason", refundReason + "");
        map.put("picEvidence", picEvidence + "");
        map.put("refundExplain", refundExplain + "");
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "trade/saveRefund.htm", map, isLogin, userId, token);
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
            Log.e("doSaveRefund", e.getMessage());
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doSaveRefund", e.getMessage());
        } finally {
            responseListener.onFinish();
        }
    }

    // 上传图片功能
    public void UploadFile(boolean isLogin, int userId, String token,
                           List<String> list, String fileName) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new UploadFileWorker(isLogin, userId, token, list, fileName));
        executor.shutdown();
    }

    public class UploadFileWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        String filepath;
        String fileName;
        List<String> list;

        public UploadFileWorker(boolean isLogin, int userId, String token,
                                List<String> list, String fileName) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.list = list;
            this.fileName = fileName;
        }

        @Override
        public void run() {
            doUploadFile(isLogin, userId, token, list, fileName);
        }
    }

    public void doUploadFile(boolean isLogin, int userId, String token,
                             List<String> list, String fileName) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.PostImage(ConstantS.BASE_PIC_URL+"remoting/rest/mobile/security/uploadFile.htm", isLogin,
                    userId, token, list, fileName);
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

    // 获取产品评论功能
    public void GetSampleComment(boolean isLogin, int sampleId) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetSampleCommentWorker(isLogin, sampleId));
        executor.shutdown();
    }

    public class GetSampleCommentWorker implements Runnable {
        boolean isLogin;
        int sampleId;

        public GetSampleCommentWorker(boolean isLogin, int sampleId) {
            super();
            this.sampleId = sampleId;
            this.isLogin = isLogin;
        }

        @Override
        public void run() {
            doGetSampleComment(isLogin, sampleId);
        }
    }

    public void doGetSampleComment(boolean isLogin, int sampleId) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("sampleId", sampleId + "");
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                            + "sample/sampleEvaluationList.htm", map, isLogin, UserHelper.getBuyerId(),
                    UserHelper.getBuyertoken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    SampleCommentResult samplecomment_result = gson.fromJson(data_json.toString(),
                            SampleCommentResult.class);
                    samplecommentListener.onSuccess(samplecomment_result
                            .getSampleEvaluationList());
                } else {
                    samplecommentListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                samplecommentListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doGetSampleComment", e.getMessage());
            samplecommentListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetSampleComment", e.getMessage());
        } finally {
            samplecommentListener.onFinish();
        }
    }

    // 获取我的消息功能
    public void GetMyMessage(boolean isLogin, int userId, String token,
                             int pageNum, int isReaded) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetMyMessageWorker(isLogin, userId, token,
                pageNum, isReaded));
        executor.shutdown();
    }

    public class GetMyMessageWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        int pageNum;
        int isReaded;

        public GetMyMessageWorker(boolean isLogin, int userId, String token,
                                  int pageNum, int isReaded) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.pageNum = pageNum;
            this.isReaded = isReaded;
        }

        @Override
        public void run() {
            doGetMyMessage(isLogin, userId, token, pageNum, isReaded);
        }
    }

    public void doGetMyMessage(boolean isLogin, int userId, String token,
                               int pageNum, int isReaded) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageNum", pageNum + "");
        map.put("isReaded", isReaded + "");
        try {
            Response response = HttpClientUtil
                    .doPost(ConstantS.BASE_URL + "user/systemMessageList.htm", map,
                            isLogin, userId, token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    MyMessageResult mymessage_result = gson.fromJson(
                            data_json.toString(), MyMessageResult.class);
                    mymessageListener.onSuccess(mymessage_result
                            .getSystemMessageList());
                } else {
                    mymessageListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mymessageListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doGetMyMessage", e.getMessage());
            mymessageListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetMyMessage", e.getMessage());
        } finally {
            mymessageListener.onFinish();
        }
    }

    // 获取国际化接口功能
    public void GetTranslate(boolean isLogin, int userId, String token, String code) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetTranslateWorker(isLogin, userId, token, code));
        executor.shutdown();
    }

    public class GetTranslateWorker implements Runnable {
        boolean isLogin;
        int userId;
        String token;
        String code;

        public GetTranslateWorker(boolean isLogin, int userId, String token, String code) {
            super();
            this.isLogin = isLogin;
            this.userId = userId;
            this.token = token;
            this.code = code;
        }

        @Override
        public void run() {
            doGetTranslate(isLogin, userId, token, code);
        }
    }

    public void doGetTranslate(boolean isLogin, int userId, String token, String code) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "common/listTranslates.htm", map, isLogin, userId, token);
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    TranslatesResult translates_result = gson.fromJson(
                            data_json.toString(), TranslatesResult.class);
                    Logs.d(data_json.toString());
                    translatesListener.onSuccess(translates_result.getTranslates());
                } else {
                    translatesListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                translatesListener.onLose();
            }
        } catch (NetException e) {
            Log.e("doGetTranslate", e.getMessage());
            translatesListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetTranslate", e.getMessage());
        } finally {
            translatesListener.onFinish();
        }
    }
    /*2.0*/
    /**
     * 卖家接口
     */
    public void sellerShopInfo() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new SellerShopInfoWorker());
        executor.shutdown();
    }

    public class SellerShopInfoWorker implements Runnable {

        @Override
        public void run() {
            dosellerShopInfo();
        }
    }

    public void dosellerShopInfo() {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "shop/getShopInfo.htm", null, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    ShopInfoDetail mResult = gson.fromJson(
                            data_json.toString(), ShopInfoDetail.class);
                    mShopInfoListener.onSuccess(mResult);
                } else {
                    mShopInfoListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                mShopInfoListener.onLose();
            }
        } catch (NetException e) {
            Log.d("doGetTranslate", e.getMessage());
            mShopInfoListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doGetTranslate", e.getMessage());
        } finally {
            mShopInfoListener.onFinish();
        }
    }

    // 获取手机验证码功能
    public void GetUserMobileCode(String loginName, String phone) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new GetUserMobileCodeWorker(loginName, phone));
        executor.shutdown();
    }

    public class GetUserMobileCodeWorker implements Runnable {
        String loginName;
        String phone;

        public GetUserMobileCodeWorker(String loginName, String phone) {
            super();
            this.loginName = loginName;
            this.phone = phone;
        }


        @Override
        public void run() {
            doGetUserMobileCode(loginName, phone);
        }

    }

    public void doGetUserMobileCode(String loginName, String phone) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();
        map.put("loginName", loginName);
        map.put("mobile", phone);
        Log.e("loginName", loginName);
        Log.e("phone", phone);
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "seller/send-mobile-code.htm", map, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    getmobilecodeListener.onSuccess(result.getErrorNO(),
                            result.getErrorInfo());
                } else {
                    getmobilecodeListener.onError(result.getErrorNO(),
                            result.getErrorInfo());
                }
            } else {
                getmobilecodeListener.onLose();
            }

        } catch (NetException e) {
            getmobilecodeListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
            Log.e("doGetMobileCode", e.getMessage());
        } catch (JSONException e) {
            Log.e("doGetMobileCode", e.getMessage());
        } finally {
            getmobilecodeListener.onFinish();
        }
    }

    // 修改卖家登录密码
    public void ChangeSellerPassword(String old, String news, String againNews) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new ChangeSellerPasswordWorker(old, news, againNews));
        executor.shutdown();
    }

    public class ChangeSellerPasswordWorker implements Runnable {
        String old;
        String news;
        String again;

        public ChangeSellerPasswordWorker(String old, String news, String againNews) {
            this.again = againNews;
            this.old = old;
            this.news = news;
        }

        @Override
        public void run() {
            doChangeSellerPassword(old, news, again);
        }

    }

    public void doChangeSellerPassword(String old, String news, String againNews) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("oldPassWord", Md5Encrypt.md5(old));
        map.put("newPassWord", Md5Encrypt.md5(news));
        map.put("newPassWord2", Md5Encrypt.md5(againNews));
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "user/password/updatePassWord.htm", map, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    resultListener.onSuccess(result.getErrorInfo());
                } else {
                    resultListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                resultListener.onLose();
            }

        } catch (NetException e) {
            resultListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
            Log.e("doGetMobileCode", e.getMessage());
        } catch (JSONException e) {
            Log.e("doGetMobileCode", e.getMessage());
        } finally {
            resultListener.onFinish();
        }
    }

    // 忘记卖家登录密码
    public void ForgetSellerPassword(String loginName, String phone, String news, String againNews, String code) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new ForgetSellerPasswordWorker(loginName, phone, news, againNews, code));
        executor.shutdown();
    }

    public class ForgetSellerPasswordWorker implements Runnable {
        String loginName;
        String phone;
        String news;
        String again;
        String code;

        public ForgetSellerPasswordWorker(String loginName, String phone, String news, String again, String code) {
            this.loginName = loginName;
            this.phone = phone;
            this.news = news;
            this.again = again;
            this.code = code;
        }


        @Override
        public void run() {
            doForgetSellerPassword(loginName, phone, news, again, code);
        }

    }

    public void doForgetSellerPassword(String loginName, String phone, String news, String again, String code) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("loginName", loginName);
        map.put("mobile", phone);
        map.put("newPassword", Md5Encrypt.md5(news));
        map.put("newRePassword", Md5Encrypt.md5(again));
        map.put("code", code);
//        Log.e("loginName",loginName);
//        Log.e("mobile",phone);
//        Log.e("newPassWord",news);
//        Log.e("newRePassWord",again);
//        Log.e("code",code);
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "seller/valid-modify-password.htm", map, UserHelper.isSellerLogin(), UserHelper.getSellerID(), UserHelper.getSellerToken());
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
            responseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
            Log.e("doGetMobileCode", e.getMessage());
        } catch (JSONException e) {
            Log.e("doGetMobileCode", e.getMessage());
        } finally {
            responseListener.onFinish();
        }
    }


    /*
     * 买家扫一扫获取订单信息
      */
    public void getBuyerSaoYiSaoRequest(String url, int orderId, String id) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getBuyerSaoYiSaoWorker(url, orderId, id));
        executor.shutdown();
    }

    public class getBuyerSaoYiSaoWorker implements Runnable {

        private String mUrl;
        private String mId;
        private int mOrderId;

        public getBuyerSaoYiSaoWorker(String url, int orderId, String id) {
            mUrl = url;
            mId = id;
            mOrderId = orderId;
        }

        @Override
        public void run() {
            doBuyerSaoYiSao(mUrl, mOrderId, mId);
        }
    }

    private void doBuyerSaoYiSao(String url, int orderId, String id) {

        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId + "");
        params.put("deliveryNo", id);

        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isBuyerLogin(), UserHelper.getBuyerId(), UserHelper.getBuyertoken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult)
                            .getJSONObject("data");
                    BuyerOrderList accountDetail = gson.fromJson(data_json.toString(), BuyerOrderList.class);
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

    /**
     * 买家提醒发货
      */
    public void getBuyerRemindRequest(String url, int orderId, int id) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getBuyerRemindWorker(url, orderId, id));
        executor.shutdown();
    }

    public class getBuyerRemindWorker implements Runnable {

        private String mUrl;
        private int mId;
        private int mOrderId;

        public getBuyerRemindWorker(String url, int orderId, int id) {
            mUrl = url;
            mOrderId = orderId;
            mId = id;
        }

        @Override
        public void run() {
            doBuyerRemind(mUrl, mOrderId, mId);
        }
    }

    private void doBuyerRemind(String url, int orderId, int id) {

        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId + "");
        params.put("shopId", id + "");

        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isBuyerLogin(), UserHelper.getBuyerId(), UserHelper.getBuyertoken());
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
            Log.e("mOneDataListener", e.getMessage());
            mNodataListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mOneDataListener", e.getMessage());
        } finally {
            mNodataListener.onFinish();
        }
    }

    /**
    * 买家取消订单
    */
    public void cancelOrder(int orderId) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new CancelOrderWorker(orderId));
        executor.shutdown();
    }

    public class CancelOrderWorker implements Runnable {

        private int  orderId;

        public CancelOrderWorker(int orderId) {
            this.orderId = orderId;
        }

        @Override
        public void run() {
            doCancelOrder(orderId);
        }
    }

    private void doCancelOrder(int  orderId) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doGet(ConstantS.BASE_URL +"trade/cancelOrder/"+orderId+".htm", UserHelper.isBuyerLogin(), UserHelper.getBuyerId(), UserHelper.getBuyertoken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),ResponseResult.class);
                if (result.isSuccess()) {
                    mResponseListener.onSuccess(result.getErrorInfo());
                } else {
                    mResponseListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mResponseListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doCancelOrder", e.getMessage());
            mResponseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doCancelOrder", e.getMessage());
        } finally {
            mResponseListener.onFinish();
        }
    }

    /**
     * 买家申请退货
     * @param map
     */
    public void returnGoods(Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new ReturnGoodsWorker(map));
        executor.shutdown();
    }
    public class ReturnGoodsWorker implements Runnable {
        private Map map;
        public ReturnGoodsWorker(Map map) {
           this.map = map;
        }
        @Override
        public void run() {
            doReturnGoods(map);
        }
    }

    private void doReturnGoods(Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL+"trade/saveRefund.htm", map, UserHelper.isBuyerLogin(), UserHelper.getBuyerId(), UserHelper.getBuyertoken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),ResponseResult.class);
                if (result.isSuccess()) {
                    mResponseListener.onSuccess(result.getErrorInfo());
                } else {
                    mResponseListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mResponseListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doReturnGoods", e.getMessage());
            mResponseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doReturnGoods", e.getMessage());
        } finally {
            mResponseListener.onFinish();
        }
    }
    /**
     * 买家拒收货物
     * @param map
     */
    public void rejectGoods(Map map) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new RejectGoodsWorker(map));
        executor.shutdown();
    }
    public class RejectGoodsWorker implements Runnable {
        private Map map;
        public RejectGoodsWorker(Map map) {
            this.map = map;
        }
        @Override
        public void run() {
            doRejectGoods(map);
        }
    }

    private void doRejectGoods(Map map) {
        Gson gson = new Gson();
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL+"trade/buyer/rejectOrder.htm", map, UserHelper.isBuyerLogin(), UserHelper.getBuyerId(), UserHelper.getBuyertoken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),ResponseResult.class);
                if (result.isSuccess()) {
                    mResponseListener.onSuccess(result.getErrorInfo());
                } else {
                    mResponseListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mResponseListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doRejectGoods", e.getMessage());
            mResponseListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doRejectGoods", e.getMessage());
        } finally {
            mResponseListener.onFinish();
        }
    }
    /**
     * 买家查看物流详情
     * @param orderId
     */
    public void lookOrderLogistic(int orderId) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new LookOrderLogisticWorker(orderId));
        executor.shutdown();
    }
    public class LookOrderLogisticWorker implements Runnable {
        private int orderId;
        public LookOrderLogisticWorker(int orderId) {
            this.orderId = orderId;
        }
        @Override
        public void run() {
            doLookOrderLogistic(orderId);
        }
    }

    private void doLookOrderLogistic(int orderId) {
        Gson gson = new Gson();
        Map map = new HashMap();
        map.put("orderId",orderId);
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL+"trade/buyer/orderDeliverDetail.htm", map, UserHelper.isBuyerLogin(), UserHelper.getBuyerId(), UserHelper.getBuyertoken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult).getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    OrderDeliverResult orderDeliverResult = gson.fromJson(data_json.toString(), OrderDeliverResult.class);
                    mOneDataListener.onSuccess(orderDeliverResult,result.getErrorInfo());
                } else {
                    mOneDataListener.onError(result.getErrorNO(), result.getErrorInfo());
                }
            } else {
                mOneDataListener.onLose();
            }

        } catch (NetException e) {
            Log.e("doLookOrderLogistic", e.getMessage());
            mOneDataListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("doLookOrderLogistic", e.getMessage());
        } finally {
            mOneDataListener.onFinish();
        }
    }

    /*
   * 买家拒收并提交理由
    */
    public void getBuyerRefuseRequest(String url, int orderId, String remark) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getBuyerRefuseWorker(url, orderId, remark));
        executor.shutdown();
    }

    public class getBuyerRefuseWorker implements Runnable {

        private String mUrl;
        private String mId;
        private int mOrderId;

        public getBuyerRefuseWorker(String url, int orderId, String id) {
            mUrl = url;
            mId = id;
            mOrderId = orderId;
        }

        @Override
        public void run() {
            doBuyerRefuse(mUrl, mOrderId, mId);
        }
    }

    private void doBuyerRefuse(String url, int orderId, String remark) {

        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId + "");
        params.put("rejectReason", remark);

        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isBuyerLogin(), UserHelper.getBuyerId(), UserHelper.getBuyertoken());
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
            Log.e("mOneDataListener", e.getMessage());
            mNodataListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mOneDataListener", e.getMessage());
        } finally {
            mNodataListener.onFinish();
        }
    }
    /*
   * 买家确认收货
    */
    public void getBuyerConfirmRequest(String url, int orderId) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getBuyerConfirmWorker(url, orderId));
        executor.shutdown();
    }

    public class getBuyerConfirmWorker implements Runnable {

        private String mUrl;
        private int mOrderId;

        public getBuyerConfirmWorker(String url, int orderId) {
            mUrl = url;
            mOrderId = orderId;
        }

        @Override
        public void run() {
            doBuyerConfirm(mUrl, mOrderId);
        }
    }

    private void doBuyerConfirm(String url, int orderId) {

        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId + "");
        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isBuyerLogin(), UserHelper.getBuyerId(), UserHelper.getBuyertoken());
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
            Log.e("mOneDataListener", e.getMessage());
            mNodataListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mOneDataListener", e.getMessage());
        } finally {
            mNodataListener.onFinish();
        }
    }


    /*
* 买家拒收并提交理由
*/
    public void getBindPhoneRequest(String url, String orderId, String remark) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getBindPhoneWorker(url, orderId, remark));
        executor.shutdown();
    }

    public class getBindPhoneWorker implements Runnable {


        private String mUrl;
        private String mCode;
        private String mPhone;

        public getBindPhoneWorker(String url, String orderId, String remark) {
            this.mUrl = url;
            this.mCode = orderId;
            this.mPhone = remark;
        }


        @Override
        public void run() {
            doBindPhone(mUrl, mCode, mPhone);
        }
    }

    private void doBindPhone(String url, String code, String mPhone) {

        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("mobile", mPhone);

        try {
            Response response = HttpClientUtil.doPost(url, params, UserHelper.isBuyerLogin(), UserHelper.getBuyerId(), UserHelper.getBuyertoken());
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
            Log.e("mOneDataListener", e.getMessage());
            mNodataListener.onError(NETERROR_CODE, mTranslatesString.getCommon_neterror());
        } catch (JSONException e) {
            Log.e("mOneDataListener", e.getMessage());
        } finally {
            mNodataListener.onFinish();
        }
    }

    /**
     * @param code 协议编号
     *             获取用户协议信息
     */
    public void getProtocolInfo(String code) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new getProtocolInfoWorker(code));
        executor.shutdown();
    }

    public class getProtocolInfoWorker implements Runnable {
        private String code;

        public getProtocolInfoWorker(String code) {
            this.code = code;
        }

        @Override
        public void run() {
            doGetProtocolInfo(code);
        }
    }

    private void doGetProtocolInfo(String code) {
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        try {
            Response response = HttpClientUtil.doPost(ConstantS.BASE_URL
                    + "common/getProtocolInfo.htm", params, false, UserHelper.getBuyerId(), UserHelper.getBuyertoken());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                String strResult = response.getContent();
                JSONObject meta_json = new JSONObject(strResult)
                        .getJSONObject("meta");
                ResponseResult result = gson.fromJson(meta_json.toString(),
                        ResponseResult.class);
                if (result.isSuccess()) {
                    JSONObject data_json = new JSONObject(strResult).getJSONObject("data");
                    ProtocolInfo protocolInfo = gson.fromJson(data_json.toString(), ProtocolInfo.class);
                    mOneDataListener.onSuccess(protocolInfo, null);
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

}