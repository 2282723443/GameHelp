package com.wangwei.gamehelp.util;

import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Response;
import com.wangwei.gamehelp.config.Constant;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tarena on 2017/7/18.
 */

public class HttpUtil {
    //42270 我的
    public static final String APPID = "42666";
    //7592462f4ad942648b8e5f136bab8bdd
    //db77767a23cd43c59da1b803128f25fa 我的
    public static final String SIGN = "7592462f4ad942648b8e5f136bab8bdd";

    public static String getURL(String url, Map<String, String> params) {
        String result = "";
        String query = getQuery(APPID,SIGN, params);
        result = url + "?" + query;
        return result;

    }


    private static String getQuery(String appid, String sign, Map<String, String> params) {
        try {
            //添加签名
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("showapi_appid=").append(appid).append("&showapi_sign=").append(sign);
            for (Map.Entry<String,String> entry:params.entrySet()) {
                stringBuilder.append('&').append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(), "utf8"));
            }
            String queryString = stringBuilder.toString();
            return queryString;

        } catch (Exception e) {
            e.printStackTrace();
            //扔异常
            throw new RuntimeException("使用了不正确的字符集合名称");
        }
    }
    public static void testHttpURLConnection(){
        //获取符合大众点评要求的请求地址
        Map<String,String> params = new HashMap<String,String>();
//        params.put("channelId","5572a108b3cdc86cf39001d6" );
        final String url = getURL(Constant.BASEURL, params);
        Log.v("tedu", "生成的网络请求地址是："+url);
        new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    URL u = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) u.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);//该方法可写可不写，因为默认就是true
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine())!=null){
                        sb.append(line);
                    }
                    reader.close();
                    String response = sb.toString();
                    Log.v("tedu", "HttpURLConnection获得的服务器响应内容："+response);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }
    public static void testVolley( Response.Listener<String> listener){

        VolleyClient.getInstance().test(listener);


    }

    public static void loadImage(String url, ImageView imageView) {
        VolleyClient.getInstance().loadImage(url,imageView);
    }

    public static void testVolleylol( Response.Listener<String> listener){

        VolleyClient.getInstance().testlol(listener);


    }
    public static void testVolleynong( Response.Listener<String> listener){

        VolleyClient.getInstance().testnong(listener);


    }
    public static void testVolleyshou( Response.Listener<String> listener){

        VolleyClient.getInstance().testshou(listener);


    }
   public static void testVolleymo( Response.Listener<String> listener){
        VolleyClient.getInstance().testmo(listener);
    }

    public static void testVolleydnf( Response.Listener<String> listener){
        VolleyClient.getInstance().testdnf(listener);
    }
    public static void testVolleyelse( Response.Listener<String> listener){
        VolleyClient.getInstance().testelse(listener);
    }
 public static void testVolleyseek(String seek, Response.Listener<String> listener){
        VolleyClient.getInstance().testseek(seek,listener);
    }

}
