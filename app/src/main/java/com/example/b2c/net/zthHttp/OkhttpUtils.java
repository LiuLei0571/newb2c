package com.example.b2c.net.zthHttp;

import android.content.Context;

import android.os.Handler;
import android.os.Looper;

import com.example.b2c.config.Configs;

import com.example.b2c.helper.SPHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.util.DateUtils;
import com.example.b2c.net.util.Logs;
import com.example.b2c.net.util.Md5Encrypt;
import com.example.b2c.widget.util.FileUtils;
import com.google.gson.Gson;


import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.TextUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 请求网络框架
 */

public class OkhttpUtils {
    private static OkhttpUtils mInstance;
    private static OkHttpClient okHttpClient;
    private static Handler okHttpHandler;
    private OkhttpUtils() {
        this.okHttpHandler = new Handler(Looper.getMainLooper());

    }
    public static OkhttpUtils getInstance()
    {
        if (mInstance == null)
        {
            synchronized (OkhttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkhttpUtils();

                }
            }
        }
        return mInstance;
    }
    private Exception e;
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/json;charset=utf-8");
    public void doPost(final Context context, final String url, final Map<String, String> map, final boolean isLogin, final int userId, final String token
            , final MyCallback callback)throws NetException {
        try {
            Gson gson = new Gson();
            String param = gson.toJson(map);

            okHttpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .cache(new Cache(new File(context.getCacheDir(),"okhttpcache"),5*10240*1024)).build();
            Request.Builder builder =  new Request.Builder().url(url)
                    .cacheControl(new CacheControl.Builder().maxAge(5, TimeUnit.SECONDS)
                    .maxStale(5,TimeUnit.SECONDS).build())
                    .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, param.toString()));
            builder.header("Cache-Control","max-age=36000");
            constructHeader(isLogin, builder, userId, token);
            final Request build = builder.build();
           Call call = okHttpClient.newCall(build);
            if (callback!=null){
                callback.onStart();
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {
                        okHttpHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailture(e);
                            }
                        });
                    }
                    @Override
                    public void onResponse(final Call call, final Response response) throws IOException {
                        final String jieguo = response.body().string();
                        if (response.code()==200){
                            okHttpHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //发送消息更新UI
                                    callback.onSuccess(jieguo);
                                    callback.onFinish();
                                }
                            });
                        }else{
                            okHttpHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onFailture(e);
                                }
                            });

                            }
                    }
                });
            }
        } catch (Exception e) {
        }
    }
    public static void doGet(final Context context, final String url, final boolean isLogin, final int userId, final String token
            , final MyCallback callback)throws NetException{
        okHttpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .cache(new Cache(new File(context.getCacheDir(),"okhttpcache"),5*10240*1024)).build();
            Request.Builder builder =  new Request.Builder().url(url);
        builder.header("Cache-Control","max-age=36000");
        constructHeader(isLogin, builder, userId, token);
        final Request build = builder.build();
        Call call = okHttpClient.newCall(build);
        if (callback!=null){
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    okHttpHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailture(e);
                        }
                    });
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String jieguo = response.body().string();
                    if (response.code()==200){
                        okHttpHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                //发送消息更新UI
                                callback.onSuccess(jieguo);
                                callback.onFinish();
                            }
                        });
                    }else{
                        okHttpHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFinish();
                            }
                        });

                    }
                }
            });
        }
    }
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    /**
     * 上传图片
     */
    @SuppressWarnings("deprecation")
    public static void postImage(Context context,String url, boolean isLogin, int userId,
                  String token, List<String> files, String dirPathName,final MyCallback callback) throws NetException {
                    okHttpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .cache(new Cache(new File(context.getCacheDir(),"okhttpcache"),5*10240*1024)).build();

        try {
            StringBody comment = new StringBody("pic");
            MultipartEntityBuilder multipartEntityBuilder=MultipartEntityBuilder.create();

            for (int i = 0; i < files.size(); i++) {
                File file = FileUtils.compressFile(files.get(i), files.get(i));
                FileBody bin = new FileBody(file);
                multipartEntityBuilder.addPart("file" + i, bin);// file1为请求后台的File upload;属性      
            }
            multipartEntityBuilder.addPart("filename", comment);// filename1为请求后台的普通参数;属性 
            multipartEntityBuilder.addPart("dirPath", new StringBody(dirPathName));
            multipartEntityBuilder.addPart("enctype", new StringBody("multipart/form-data"));
            Request.Builder builder =  new Request.Builder().url(url)
                    .post(RequestBody.create(MEDIA_TYPE_PNG, multipartEntityBuilder.toString()));
            constructHeader(isLogin, builder, userId, token);
            final Request build = builder.build();
            Call call = okHttpClient.newCall(build);
            //TODO 加上回调然后进行测试
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    okHttpHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailture(e);
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String jieguo = response.body().string();
                    if (response.code()==200){
                        okHttpHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                //发送消息更新UI
                                callback.onSuccess(jieguo);
                                callback.onFinish();
                            }
                        });
                    }else{
                        okHttpHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFinish();
                            }
                        });
                    }
                }
            });
//            HashMap<String,String> map = new HashMap<>();
//            map.put("Content-Disposition", "form-data; name=\"mFile\";filename=\""+dirPathName+"\"");
//            RequestBody requestBody = new MultipartBuilder().addPart(Headers.of(map), RequestBody.create(MEDIA_TYPE_PHONE, file)).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



    }
    public interface MyCallback {
    void onSuccess(String result);
    void onFailture(Exception e);
    void onStart();
    void onFinish();
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
