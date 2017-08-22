package com.wangwei.gamehelp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wangwei.gamehelp.R;
import com.wangwei.gamehelp.app.MyApp;
import com.wangwei.gamehelp.config.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tarena on 2017/7/18.
 */

public class VolleyClient {
    private static  VolleyClient INSTANCE ;
    private static int XIANSHICHANGDU = 99;
    //声明一个私有的静态的属性
    public static VolleyClient getInstance(){
        if (INSTANCE == null) {
            synchronized (VolleyClient.class){
                if (INSTANCE == null){
                    INSTANCE = new VolleyClient();
                }
            }
        }
        return INSTANCE;
    }
    RequestQueue queue;
    ImageLoader imageLoader;
    //3)构造器私有化
    private VolleyClient() {
        queue = Volley.newRequestQueue(MyApp.CONTEXT);
        imageLoader=new ImageLoader(queue, new ImageLoader.ImageCache() {
            LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory()/8)){
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getHeight() * value.getRowBytes();
                }
            };

            @Override
            public Bitmap getBitmap(String s) {
                return cache.get(s);
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                cache.put(s, bitmap);

            }
        });
    }


    private VolleyClient(Context context) {
        queue = Volley.newRequestQueue(context);
    }
    public void test(Response.Listener<String> listener) {
        Map<String, String> params = new HashMap<String, String>();
        //5572a10ab3cdc86cf39001ee 焦点
        //5572a108b3cdc86cf39001d6 最新
        params.put("channelId", "5572a108b3cdc86cf39001d6");
        params.put("maxResult", "50");
//        params.put("title", "游戏");
        String url = HttpUtil.getURL(Constant.BASEURL, params);
        Log.v("tedu", "生成的网络请求地址是："+url);
        StringRequest request = new StringRequest(url, listener, null);

        //3)请求对象放到请求队列中
        queue.add(request);
    }
    public void testlol(Response.Listener<String> listener) {
        Map<String, String> params = new HashMap<String, String>();
//        params.put("channelId", "5572a10ab3cdc86cf39001ee");
        params.put("title", "英雄联盟");
        params.put("maxResult", "59");
        String url = HttpUtil.getURL(Constant.BASEURL, params);
        Log.v("tedu", "生成的网络请求地址是："+url);
        StringRequest request = new StringRequest(url, listener, null);

        //3)请求对象放到请求队列中
        queue.add(request);
    }
    public void testnong(Response.Listener<String> listener) {
        Map<String, String> params = new HashMap<String, String>();
//        params.put("channelId", "5572a10ab3cdc86cf39001ee");
        params.put("title", "王者");
        params.put("maxResult", "59");
        String url = HttpUtil.getURL(Constant.BASEURL, params);
        Log.v("tedu", "生成的网络请求地址是："+url);
        StringRequest request = new StringRequest(url, listener, null);

        //3)请求对象放到请求队列中
        queue.add(request);
    }
   public void testshou(Response.Listener<String> listener) {
        Map<String, String> params = new HashMap<String, String>();
//        params.put("channelId", "5572a10ab3cdc86cf39001ee");
        params.put("title", "守望");
       params.put("maxResult", "59");
        String url = HttpUtil.getURL(Constant.BASEURL, params);
        Log.v("tedu", "生成的网络请求地址是："+url);
        StringRequest request = new StringRequest(url, listener, null);

        //3)请求对象放到请求队列中
        queue.add(request);
    }

    public void testmo(Response.Listener<String> listener) {

        Map<String, String> params = new HashMap<String, String>();
//        params.put("channelId", "5572a10ab3cdc86cf39001ee");
        params.put("title", "魔兽");
        params.put("maxResult", "59");
        String url = HttpUtil.getURL(Constant.BASEURL, params);
        Log.v("tedu", "生成的网络请求地址是：" + url);
        StringRequest request = new StringRequest(url, listener, null);

        //3)请求对象放到请求队列中
        queue.add(request);
    }
  public void testdnf(Response.Listener<String> listener) {

        Map<String, String> params = new HashMap<String, String>();
//        params.put("channelId", "5572a10ab3cdc86cf39001ee");
        params.put("title", "dnf");
        params.put("maxResult", "59");
        String url = HttpUtil.getURL(Constant.BASEURL, params);
        Log.v("tedu", "生成的网络请求地址是：" + url);
        StringRequest request = new StringRequest(url, listener, null);

        //3)请求对象放到请求队列中
        queue.add(request);
    }

    public void testelse(Response.Listener<String> listener) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("channelId", "5572a10ab3cdc86cf39001ee");

        params.put("maxResult", "59");
        String url = HttpUtil.getURL(Constant.BASEURL, params);
        Log.v("tedu", "生成的网络请求地址是：" + url);
        StringRequest request = new StringRequest(url, listener, null);

        //3)请求对象放到请求队列中
        queue.add(request);
    }
 public void testseek(String seek,Response.Listener<String> listener) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("title", seek);
        params.put("maxResult", "59");
        String url = HttpUtil.getURL(Constant.BASEURL, params);
        Log.v("tedu", "生成的网络请求地址是：" + url);
        StringRequest request = new StringRequest(url, listener, null);

        //3)请求对象放到请求队列中
        queue.add(request);
    }




    public void loadImage(String url, ImageView iv) {
        ImageLoader.ImageListener listener = ImageLoader.
                getImageListener(iv, R.drawable.ic_content_meitu,R.drawable.
                        ic_content_meitu);

        imageLoader.get(url,listener);
    }
}
