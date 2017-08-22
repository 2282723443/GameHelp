package com.wangwei.gamehelp.fragment;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.wangwei.gamehelp.R;
import com.wangwei.gamehelp.adapter.ItemClickLister;
import com.wangwei.gamehelp.adapter.NewsAdapter;
import com.wangwei.gamehelp.model.NewsMessage;
import com.wangwei.gamehelp.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class LolFragment extends ItemClickLister {

    private Fragment mContent;
    @BindView(R.id.ptrlv_main)
    PullToRefreshListView ptrListView;
    Unbinder unbinder;
    String webhttp;
    String titlebiao;
    ListView listView;
    List<NewsMessage.ShowapiResBodyBean.PagebeanBean.ContentlistBean> datas;
    NewsAdapter adapter;
    @BindView(R.id.iv_business_loading)
    ImageView ivLoading;

    //存放轮播图的线性布局
    private LinearLayout linearLayout;
    //存放指示点的线性布局
    private LinearLayout pointLinearLayout;
    //存放指示点的集合
    private List<ImageView> pointList=new ArrayList<>();
    //上一个指示点
    private int lastPosition=0;
    private int[] urls={R.drawable.ic_lunbotupian1,R.drawable.ic_lunbotupian2,R.drawable.ic_lunbotupian3,R.drawable.ic_lunbotupian4};
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lol, container, false);
        unbinder = ButterKnife.bind(this, view);

        initDatas();

        return view;
    }




    private void initDatas() {
        listView = ptrListView.getRefreshableView();
        datas = new ArrayList<NewsMessage.ShowapiResBodyBean.PagebeanBean.ContentlistBean>();
        adapter = new NewsAdapter(getActivity(), datas);
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

       Click(listView,adapter,webhttp);

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
                Log.v("tedu", "我是LOL线程运行的：");
                HttpUtil.testVolleylol(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.v("tedu", "LOL界面生成的网络请求地址是："+s);
                        Gson gson = new Gson();
                        NewsMessage newsMessage = gson.fromJson(s, NewsMessage.class);
                        List<NewsMessage.ShowapiResBodyBean.PagebeanBean.ContentlistBean> message =
                                newsMessage.getShowapi_res_body().getPagebean().getContentlist();


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
