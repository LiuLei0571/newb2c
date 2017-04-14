package com.example.b2c.net.zthHttp;

import android.os.Handler;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;

import com.example.b2c.config.Configs;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.net.util.DateUtils;
import com.example.b2c.net.util.Logs;
import com.example.b2c.net.util.Md5Encrypt;
import com.google.gson.Gson;

import org.apache.http.util.TextUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络请求
 */

public class MyHttpUtils {

    private static Request.Builder request;
    private static int code;

    private static class LazyHolder {
        private static final MyHttpUtils INSTANCE = new MyHttpUtils();
    }

    private static Handler okHttpHandler;

    private MyHttpUtils() {
        this.okHttpHandler = new Handler(Looper.getMainLooper());

    }

    public static final MyHttpUtils getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/json;charset=utf-8");

    public static void doPost(final String url, final Map<String, String> map, final boolean isLogin, final int userId, final String token
            , final MyCallback callback) {
        try {
            Gson gson = new Gson();
            String param = gson.toJson(map);
//            OkHttpClient okHttpClient = new OkHttpClient();
            OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(18, TimeUnit.SECONDS).build();
            Request.Builder builder = new Request.Builder().url(url)
                    .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, param.toString()));
            constructHeader(isLogin, builder, userId, token);
            Request build = builder.build();
            Call call = okHttpClient.newCall(build);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.getStackTrace();
                    okHttpHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onFailture();
                            }
                        }
                    });
                }
                String string = null;
                @Override
                public void onResponse(final Call call, final Response response) throws IOException{
                    if (callback != null) {
                        code = response.code();
                        try {
                            string = response.body().string();
                        } catch (MalformedURLException e) {
                            string = "";
                            code = 101;

                        } catch (SocketTimeoutException e) {
                            string = "";
                            code = 102;
                        } catch (IOException e) {
                            string = "";
                            code = 103;
                            e.printStackTrace();
                        } catch (NetworkOnMainThreadException e) {
                            string = "";
//                                    code = 101;
                        }
                    }
                    okHttpHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            callback.onSuccess(string, code);

                        }
                    });
                }

            });
        } catch (Exception e) {
        }
    }

    public void doGet(String url, boolean isLogin, int userId, String token, final MyCallback callback) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(8, TimeUnit.SECONDS).build();
            Request.Builder httpGet = new Request.Builder().url(url);
            constructHeader(isLogin, httpGet, userId, token);
            Request build = httpGet.build();
            Call call = okHttpClient.newCall(build);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    okHttpHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onFailture();
                            }
                        }
                    });
                }
                String string = null;
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (callback != null) {
                        code = response.code();


                        try {
                            string = response.body().string();
                        } catch (MalformedURLException e) {
                            string = "";
                        } catch (SocketTimeoutException e) {
                            string = "";
                        } catch (IOException e) {
                            string = "";
                            e.printStackTrace();
                        }


                    }
                    okHttpHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            callback.onSuccess(string, code);
                        }
                    });
                }

            });
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public interface MyCallback {
        void onSuccess(String result, int code);

        void onFailture();
    }

    /**
     * post方式
     *
     * @param isLogin
     * @param httpPost
     * @param userId
     * @param token
     */
    public static void constructHeader(boolean isLogin, Request.Builder httpPost,
                                       int userId, String token) {
        long ts = DateUtils.getTimeStamp();
        StringBuffer buf = new StringBuffer();
        httpPost.addHeader("X-TimeStamp", "" + ts);
        if (!isLogin) {

            buf.append("token=").append("jinqiao@geH123");
            buf.append("&ts=").append(ts);
            String sign = Md5Encrypt.md5(buf.toString());
            // Log.i("mylog", buf.toString());
            httpPost.addHeader("X-AppName", SPHelper.getString(Configs.APPNAME, "android.jinqiao.com"));
            httpPost.addHeader("X-Sign", sign);
            httpPost.addHeader("X-UserId", String.valueOf(userId));
            httpPost.addHeader("X-UserType", SPHelper.getInt(Configs.USER_TYPE.TYPE) + "");
//            httpPost.addHeader("Content-Type",
//                    "application/x-www-form-urlencoded");
            Logs.d("X-Sign", sign);
            Logs.d("X-UserId", userId + "");
            Logs.d("X-AppName", "android.jinqiao.com");
            Logs.d("X-TimeStamp", ts + "");
            Logs.d("X-UserType", SPHelper.getInt(Configs.USER_TYPE.TYPE) + "");
        } else {
            buf.append("userId=").append(userId);
//            buf.append("&appName=").append("android.jinqiao.com");
            buf.append("&appName=").append(SPHelper.getString(Configs.APPNAME));
            buf.append("&token=").append(token);
            buf.append("&ts=").append(ts);
            String sign = Md5Encrypt.md5(buf.toString());
//            httpPost.addHeader("X-AppName", "android.jinqiao.com");
            httpPost.addHeader("X-AppName", SPHelper.getString(Configs.APPNAME));
            httpPost.addHeader("X-UserId", String.valueOf(userId));
            httpPost.addHeader("X-Sign", sign);
            httpPost.addHeader("X-UserType", SPHelper.getInt(Configs.USER_TYPE.TYPE) + "");
            Logs.d("X-UserId", userId + "");
            Logs.d("X-AppName", SPHelper.getString(Configs.APPNAME));
            Logs.d("X-Sign", sign + "");
            Logs.d("X-TimeStamp", ts + "");
            Logs.d("X-UserType", SPHelper.getInt(Configs.USER_TYPE.TYPE) + "");
        }
        if (TextUtils.isEmpty(SPHelper.getString(Configs.LANGUAGE))) {
            httpPost.addHeader("X-Locale", "default");

        } else {
            httpPost.addHeader("X-Locale", SPHelper.getString(Configs.LANGUAGE));
        }
    }

}
