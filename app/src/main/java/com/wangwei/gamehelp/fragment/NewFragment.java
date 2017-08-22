package com.wangwei.gamehelp.fragment;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.wangwei.gamehelp.NewsWebViewActivity;
import com.wangwei.gamehelp.R;
import com.wangwei.gamehelp.adapter.NewsAdapter;
import com.wangwei.gamehelp.model.NewsMessage;
import com.wangwei.gamehelp.util.HttpUtil;
import com.wangwei.gamehelp.util.SPUtil;
import com.wangwei.gamehelp.view.MyBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewFragment extends Fragment {


    @BindView(R.id.ptrlv_main)
    PullToRefreshListView ptrListView;
    @BindView(R.id.iv_business_loading)
    ImageView ivLoading;
    Unbinder unbinder;
    String webhttp;
    ListView listView;
    List<NewsMessage.ShowapiResBodyBean.PagebeanBean.ContentlistBean> datas;
    NewsAdapter adapter;
    SPUtil sputil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        unbinder = ButterKnife.bind(this, view);

        initDatas();

        return view;
    }

    private void initDatas() {
        listView = ptrListView.getRefreshableView();
        datas = new ArrayList<NewsMessage.ShowapiResBodyBean.PagebeanBean.ContentlistBean>();
        adapter = new NewsAdapter(getActivity(), datas);

        MyBanner myBanner = new MyBanner(getActivity(),null);
        listView.addHeaderView(myBanner);


        AnimationDrawable d = (AnimationDrawable) ivLoading.getDrawable();
        d.start();
        listView.setEmptyView(ivLoading);

        listView.setAdapter(adapter);
          ptrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
              @Override
              public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                  refresh();
              }
          });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NewsMessage.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean;
                contentlistBean = adapter.getItem(position-2);
                Intent intent = new Intent(getActivity(), NewsWebViewActivity.class);
                webhttp = contentlistBean.getLink();
                intent.putExtra("webhttp",webhttp);
                Log.v("tedu", "contentlistBean传出的是" + webhttp);
                startActivity(intent);

            }
        });
    }




    @Override
    public void onResume() {
        super.onResume();
        Log.v("tedu", "我是第一次运行的：");

        refresh();
    }
    private void refresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.v("tedu", "我是线程运行的：");
                HttpUtil.testVolley(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.v("tedu", "11111主界面生成的网络请求地址是："+s);
                        Gson gson = new Gson();
                        NewsMessage newsMessage = gson.fromJson(s, NewsMessage.class);
                        List<NewsMessage.ShowapiResBodyBean.PagebeanBean.ContentlistBean> message =
                                newsMessage.getShowapi_res_body().getPagebean().getContentlist();
                        Log.v("tedu", "最新是不是空的" + message);
                         adapter.addAll(message,true);

//                        message.size() ;
//                        Log.v("tedu", "去得的长度是"+message.size() );
//                        Log.v("tedu", "z这个属性是"+message);
                        if (ptrListView!=null) {
                            ptrListView.onRefreshComplete();
                        }
                    }

                });

            }
        }, 3000);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
