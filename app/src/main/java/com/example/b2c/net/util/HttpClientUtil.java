package com.example.b2c.net.util;

import com.example.b2c.config.Configs;
import com.example.b2c.config.ConstantS;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.net.exception.NetException;
import com.example.b2c.net.exception.RequestFailureException;
import com.example.b2c.net.response.Response;
import com.example.b2c.widget.util.FileUtils;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author shark
 */
public class HttpClientUtil {
    private final static String TAG = "HttpClientUtil.class";

    private final static int TIME_OUT = 30000;
    private final static Integer CONNECTION_TIMEOUT = 3 * 10000; //设置请求超时2秒钟 根据业务调整
    private final static Integer SO_TIMEOUT = 3 * 10000; //设置等待数据超时时间2秒钟 根据业务调整
    private final static int PORT = 80;
    public static String serverURL = ConstantS.BASE_SERVCER;
    public static String BASE_PIC_URL = ConstantS.BASE_PIC_URL;

    private static HttpClient httpClient;

    public static synchronized HttpClient getHttpClient() {
        if (null == httpClient) {
            HttpParams params = new BasicHttpParams();
            ConnManagerParams.setMaxTotalConnections(params, 5000);
            ConnPerRoute perRoute = new ConnPerRouteBean(10);
            ConnManagerParams.setMaxConnectionsPerRoute(params, perRoute);
            ConnManagerParams.setTimeout(params, 1000);
            //设置最大请求时间
            HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
            //设置最大响应时间
            HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
            SchemeRegistry schReg = new SchemeRegistry();
            schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), PORT));
            schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), PORT));
            ClientConnectionManager conManager = new ThreadSafeClientConnManager(params, schReg);

            httpClient = new DefaultHttpClient(conManager, params);
        }
        return httpClient;
    }

    /**
     * Use http get method to visit server, obtain the response.
     *
     * @param url web address
     * @return status code and entity the server returns
     * @throws RequestFailureException
     * @throws ParseException
     */
    public static Response doGet(String url, boolean isLogin, int userId, String token) throws NetException {
        Response response = new Response();//return

        HttpGet httpGet = new HttpGet(url);
        constructHeader(isLogin, httpGet, userId, token);
        Logs.i("url", url);
        Logs.i("isLogin", isLogin + "");
        Logs.i("userId", userId + "");
        Logs.i("token", token + "");

        try {
            HttpResponse httpResponse = getHttpClient().execute(httpGet);
            Logs.i(TAG + "_responseStatusLine", httpResponse.getStatusLine().toString());
            int status = httpResponse.getStatusLine().getStatusCode();

            response.setStatusCode(status);//set
            if (status == HttpStatus.SC_OK) {
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    response.setContent(convertStreamToString(instream));//set
                    Logs.i(TAG + "_responseEntity", response.getContent());
                    instream.close();
                }
            } else {
                Logs.e(TAG, "requestFailure");
            }
        } catch (SocketTimeoutException e) {
            Logs.e(TAG, "SocketTimeoutException", e);
            throw new NetException(e);
        } catch (IOException e) {
            Logs.e(TAG, "IOException", e);
            throw new NetException(e);
        }

        return response;
    }

    /**
     * @param url web address
     * @return status code and entity the server returns
     * @throws NetException
     * @throws RequestFailureException
     * @Description: Use http post method to visit server, obtain the response.
     */
    public static Response doPost(String url, Map<String, String> map, boolean isLogin, int userId, String token) throws NetException {
        Response response = new Response();//return
        Gson gson = new Gson();
        String param = gson.toJson(map);
        Logs.d("url", url);
        Logs.d("isLogin", isLogin + "");
        Logs.d("userId", userId + "");
        Logs.d("token", token + "");
        Logs.d("map", param);
        try {
            HttpClient client = getHttpClient();
            HttpPost httpPost = new HttpPost(url);
            constructHeader(isLogin, httpPost, userId, token);

            httpPost.addHeader("Content-type", "application/json;charset=utf-8");
            httpPost.setHeader("Accept", "application/json");

            StringEntity httpEntity = new StringEntity(param, HTTP.UTF_8);
            httpPost.setEntity(httpEntity);

            HttpResponse httpResponse = client.execute(httpPost);
            Logs.i(TAG + "_responseStatusLine", httpResponse.getStatusLine().toString());
            int status = httpResponse.getStatusLine().getStatusCode();
            response.setStatusCode(status);//set
            if (status == HttpStatus.SC_OK) {
                HttpEntity entityResponse = httpResponse.getEntity();
                if (entityResponse != null) {
                    InputStream instream = entityResponse.getContent();
                    response.setContent(convertStreamToString(instream));//set
                    Logs.i(TAG + "_responseEntity", response.getContent());
                    instream.close();

                }
            } else {
                Logs.e(TAG, "requestFailure");

            }
        } catch (SocketTimeoutException e) {
            Logs.e(TAG, "SocketTimeoutException", e);

            throw new NetException(e);
        } catch (IOException e) {
            Logs.e(TAG, "IOException", e);
            throw new NetException(e);
        }

        return response;
    }

    @SuppressWarnings("deprecation")
    public static Response PostImage(String url, boolean isLogin, int userId,
                                     String token, List<String> list, String dirPathName) throws NetException {
        Response response = new Response();
        HttpClient client = getHttpClient();
        HttpPost httppost = new HttpPost(url);
        constructHeader(isLogin, httppost, userId, token);

        List<String> mImageList = new ArrayList<>();
        mImageList = list;
        try {
            StringBody comment = new StringBody("pic");
            MultipartEntity reqEntity = new MultipartEntity();
            for (int i = 0; i < mImageList.size(); i++) {
                File file = FileUtils.compressFile(mImageList.get(i), mImageList.get(i));
                FileBody bin = new FileBody(file);
                reqEntity.addPart("file" + i, bin);// file1为请求后台的File upload;属性      
            }
            //evidences
            reqEntity.addPart("filename", comment);// filename1为请求后台的普通参数;属性 
            reqEntity.addPart("dirPath", new StringBody(dirPathName));
            reqEntity.addPart("enctype", new StringBody("multipart/form-data"));
            httppost.setEntity(reqEntity);
            HttpResponse httpresponse = client.execute(httppost);
            response.setStatusCode(httpresponse.getStatusLine().getStatusCode());
            Logs.i(TAG + "_responseStatusLine", httpresponse.getStatusLine().toString());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entityResponse = httpresponse.getEntity();
                if (entityResponse != null) {
                    InputStream instream = entityResponse.getContent();
                    response.setContent(convertStreamToString(instream));
                    Logs.i(TAG + "_responseEntity", response.getContent());
                    instream.close();

                }
            } else {
                throw new NetException("Negative response from server. "
                        + httpresponse.getStatusLine().toString());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @SuppressWarnings({"deprecation"})
    public static Response doImagePost(String server, String path, List<NameValuePair> params, byte[] imageData, String imageName, String authInfo) throws NetException {
        serverURL = server;
        String url = generateUrl(path, params);
        Response response = new Response();
        /*
         * int timeOut = Integer.valueOf(VsFootballProPerties.loadProperties(
		 * mContext).getProperty("time_out"));
		 */
        int timeOut = TIME_OUT;
        HttpParams httpParams = new BasicHttpParams();
        // set time out parameters
        ConnManagerParams.setMaxTotalConnections(httpParams, 5000);
        ConnPerRoute perRoute = new ConnPerRouteBean(10);
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, perRoute);
        HttpConnectionParams.setConnectionTimeout(httpParams, timeOut);
        HttpConnectionParams.setSoTimeout(httpParams, timeOut);

        try {
            HttpPost httpPost = new HttpPost(url);
            ArrayList<NameValuePair> paramNews = addParam(authInfo, params);

            HttpEntity reqEntity;
            if (imageData != null) {
                reqEntity = MultipartEntityBuilder
                        .create()
                        .addPart("headImg",
                                new ByteArrayBody(imageData, imageName))
                        // .addPart("name", new
                        // StringBody(name)).addPart("fileName", new
                        // StringBody(imageName))
                        // .addPart("mimeType", new StringBody("image/jpg"))
                        .addPart(
                                "param",
                                new StringBody(paramNews.get(0).getValue(),
                                        Charset.forName("UTF-8"))).build();
            } else {
                reqEntity = MultipartEntityBuilder.create()
                        // .addPart("name", new
                        // StringBody(name)).addPart("fileName", new
                        // StringBody(imageName))
                        // .addPart("mimeType", new StringBody("image/jpg"))
                        .addPart(
                                "param",
                                new StringBody(paramNews.get(0).getValue(),
                                        Charset.forName("UTF-8"))).build();
            }
            httpPost.setEntity(reqEntity);
            // httpPost.addHeader("Authentication-Token", authInfo);
            // StringEntity stringEntity = new
            // StringEntity(params.get(0).getValue(), HTTP.UTF_8);
            // stringEntity.setContentType("application/json");
            // httpPost.setEntity(stringEntity);

            HttpClient client = getHttpClient();
            HttpResponse httpResponse = client.execute(httpPost);
            Logs.i(TAG + "_responseStatusLine", httpResponse.getStatusLine()
                    .toString());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    response.setContent(convertStreamToString(instream));
                    Logs.i(TAG + "_responseEntity", response.getContent());
                    instream.close();

                }
            } else {
                throw new NetException("Negative response from server. "
                        + httpResponse.getStatusLine().toString());
            }
        } catch (IOException e) {
            Logs.e(TAG, "IOException", e);
            throw new NetException(e);
        }
        return response;
    }

    @SuppressWarnings({"deprecation"})
    public static Response doReportPost(String server, String path,
                                        List<NameValuePair> params, ArrayList<byte[]> byteList,
                                        String imageName, String authInfo) throws NetException {
        serverURL = server;
        String url = generateUrl(path, params);
        Response response = new Response();
        int timeOut = TIME_OUT;
        HttpParams httpParams = new BasicHttpParams();
        ConnManagerParams.setMaxTotalConnections(httpParams, 10000);
        ConnPerRoute perRoute = new ConnPerRouteBean(10);
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, perRoute);
        HttpConnectionParams.setConnectionTimeout(httpParams, timeOut);
        HttpConnectionParams.setSoTimeout(httpParams, timeOut);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        try {
            HttpPost httpPost = new HttpPost(url);
            ArrayList<NameValuePair> paramNews = addParam(authInfo, params);
            HttpEntity reqEntity;
            if (byteList != null && byteList.size() > 0) {
                builder.addPart(
                        "param",
                        new StringBody(paramNews.get(0).getValue(), Charset
                                .forName("UTF-8"))).build();
                for (byte[] imageData : byteList) {
                    builder.addPart("attachments", new ByteArrayBody(imageData,
                            imageName));
                }
                reqEntity = builder.build();
            } else {
                reqEntity = MultipartEntityBuilder
                        .create()
                        .addPart(
                                "param",
                                new StringBody(paramNews.get(0).getValue(),
                                        Charset.forName("UTF-8"))).build();
            }
            httpPost.setEntity(reqEntity);
            HttpClient client = getHttpClient();
            HttpResponse httpResponse = client.execute(httpPost);
            Logs.i(TAG + "_responseStatusLine", httpResponse.getStatusLine()
                    .toString());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
            if (response.getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    response.setContent(convertStreamToString(instream));
                    Logs.i(TAG + "_responseEntity", response.getContent());
                    instream.close();
                }
            } else {
                throw new NetException("Negative response from server. "
                        + httpResponse.getStatusLine().toString());
            }
        } catch (IOException e) {

            Logs.e(TAG, "IOException", e);
            throw new NetException(e);
        }
        return response;
    }

    /**
     * @param is inputStream
     * @return string convert from the inputStream
     * @Description: Convert the InputStream to String.
     */
    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static String generateUrl(String path, List<NameValuePair> params) {
        /*
         * String url =
		 * VsFootballProPerties.loadProperties(mContext).getProperty(
		 * "server_url")
		 */
        String url = serverURL + path;
        for (NameValuePair nvp : params) {
            String value = "";
            if (nvp.getName() != null && nvp.getValue() == null) {
                value = "";
            } else {
                value = nvp.getValue();
            }
            url = url.replace("{" + nvp.getName() + "}", value);
        }
        Logs.d(TAG, "URL:" + url);
        return url;
    }

    private static ArrayList<NameValuePair> addParam(String authInfo,
                                                     List<NameValuePair> params) {
        ArrayList<NameValuePair> paramNews = new ArrayList<NameValuePair>();
        if (authInfo != null && !authInfo.equals("")) {
            if (params.size() != 0) {
                paramNews.add(new BasicNameValuePair("param", "{"
                        + authInfo
                        + params.get(0).getValue().toString()
                        .replaceFirst("\\{", ",")));
            } else {
                paramNews.add(new BasicNameValuePair("param", "{" + authInfo
                        + "}"));
            }
        } else {
            if (params.size() != 0) {
                paramNews.add(new BasicNameValuePair("param", params.get(0)
                        .getValue()));
            }
        }
        return paramNews;
    }

    public static void constructHeader(boolean isLogin, HttpGet httpGet,
                                       int userId, String token) {
        long ts = DateUtils.getTimeStamp();
        StringBuffer buf = new StringBuffer();
        if (!isLogin) {
            buf.append("token=").append("jinqiao@geH123");
            buf.append("&ts=").append(ts);
            String sign = Md5Encrypt.md5(buf.toString());
            // Log.i("mylog", buf.toString());

            httpGet.addHeader("X-Sign", sign);
            httpGet.addHeader("X-AppName",  SPHelper.getString(Configs.APPNAME,"android.jinqiao.com"));
            httpGet.addHeader("X-UserId", String.valueOf(userId));
            httpGet.addHeader("X-TimeStamp", "" + ts);
            httpGet.addHeader("X-UserType", SPHelper.getInt(Configs.USER_TYPE.TYPE) + "");
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
            // Log.i("mylog", buf.toString());
            httpGet.addHeader("X-UserId", String.valueOf(userId));
            httpGet.addHeader("X-Sign", sign);
//            httpGet.addHeader("X-AppName", "android.jinqiao.com");
            httpGet.addHeader("X-AppName", SPHelper.getString(Configs.APPNAME));
            httpGet.addHeader("X-TimeStamp", "" + ts);
            httpGet.addHeader("X-UserType", SPHelper.getInt(Configs.USER_TYPE.TYPE) + "");
            Logs.d("X-UserId", userId + "");
            Logs.d("X-AppName", "android.jinqiao.com");
            Logs.d("X-UserId", userId + "");
            Logs.d("X-TimeStamp", ts + "");
            Logs.d("X-UserType", SPHelper.getInt(Configs.USER_TYPE.TYPE) + "");
        }
        httpGet.addHeader("X-Locale", SPHelper.getString(Configs.LANGUAGE));
//        httpGet.addHeader("X-Locale", "CN");
    }

    public static void constructHeader(boolean isLogin, HttpPost httpPost,
                                       int userId, String token) {
        long ts = DateUtils.getTimeStamp();
        StringBuffer buf = new StringBuffer();
        httpPost.addHeader("X-TimeStamp", "" + ts);
        if (!isLogin) {
            buf.append("token=").append("jinqiao@geH123");
            buf.append("&ts=").append(ts);
            String sign = Md5Encrypt.md5(buf.toString());
            // Log.i("mylog", buf.toString());
            httpPost.addHeader("X-AppName",  SPHelper.getString(Configs.APPNAME,"android.jinqiao.com"));
            httpPost.addHeader("X-Sign", sign);
            httpPost.addHeader("X-UserId", String.valueOf(userId));
            httpPost.addHeader("X-UserType", SPHelper.getInt(Configs.USER_TYPE.TYPE) + "");
            Logs.d("X-Sign", sign);
            Logs.d("X-UserId", userId + "");
            Logs.d("X-AppName", "android.jinqiao.com-????");
            Logs.d("X-TimeStamp", ts + "");
            Logs.d("X-UserType", SPHelper.getInt(Configs.USER_TYPE.TYPE) + "");
        } else {
            buf.append("userId=").append(userId);
            buf.append("&appName=").append(SPHelper.getString(Configs.APPNAME));
            buf.append("&token=").append(token);
            buf.append("&ts=").append(ts);
            String sign = Md5Encrypt.md5(buf.toString());
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
