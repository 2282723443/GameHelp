package com.wangwei.gamehelp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.wangwei.gamehelp.adapter.NewsAdapter;
import com.wangwei.gamehelp.model.NewsMessage;
import com.wangwei.gamehelp.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SeekActivity extends Activity {

    @BindView(R.id.image_Back)
    ImageView imageBack;
    @BindView(R.id.et_seek)
    EditText etSeek;
    @BindView(R.id.btn_seek)
    TextView btnSeek;
    String seekContent;
    @BindView(R.id.ptrlv_main1)
    ListView listViewseek;
    @BindView(R.id.iv_business_loading)
    ImageView ivLoading;
    String webhttp;
    List<NewsMessage.ShowapiResBodyBean.PagebeanBean.ContentlistBean> datas;

    NewsAdapter adapter;

    int seeknull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek);
        ButterKnife.bind(this);

        datas = new ArrayList<NewsMessage.ShowapiResBodyBean.PagebeanBean.ContentlistBean>();
        adapter = new NewsAdapter(this, datas);
        listViewseek.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        listViewseek.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsMessage.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean;
                contentlistBean = adapter.getItem(position);
                Intent intent = new Intent(SeekActivity.this, NewsWebViewActivity.class);
                webhttp = contentlistBean.getLink();
                intent.putExtra("webhttp",webhttp);
                Log.v("tedu", "contentlistBean传出的是" + webhttp);
                startActivity(intent);
            }
        });
    }



    @OnClick(R.id.btn_seek)
    public void setBtnSeek(View view) {
        Log.v("tedu", "我是点击搜索刚开始时候的："+etSeek.getText().toString());
        initseek();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        AnimationDrawable d = (AnimationDrawable) ivLoading.getDrawable();
        d.start();
        listViewseek.setEmptyView(ivLoading);

    }

    private void initseek() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HttpUtil.testVolleyseek(etSeek.getText().toString(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.v("tedu", "我是搜索线程运行的："+s);

                            Gson gson = new Gson();
                            NewsMessage newsMessage = gson.fromJson(s, NewsMessage.class);
                           seeknull = newsMessage.getShowapi_res_body().getPagebean().getAllPages();
                        Log.v("tedu", "allpages我的值是多少："+seeknull);
                            if (seeknull == 0) {
                                Toast.makeText(SeekActivity.this, "没有搜索到“"+etSeek.getText().toString()+"”内容", Toast.LENGTH_SHORT).show();
                                AnimationDrawable d = (AnimationDrawable) ivLoading.getDrawable();
                                d.stop();
                                ivLoading.setVisibility(View.INVISIBLE);
                            }else {

                                List<NewsMessage.ShowapiResBodyBean.PagebeanBean.ContentlistBean> message =
                                        newsMessage.getShowapi_res_body().getPagebean().getContentlist();
                                adapter.addAll(message, true);
                            }
//                        message.size() ;
//                        Log.v("tedu", "去得的长度是"+message.size() );
//                        Log.v("tedu", "z这个属性是"+message);

                        adapter.notifyDataSetChanged();
                    }

                    private void toastseek() {
                        Toast.makeText(SeekActivity.this, "今日无新增团购内容", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }, 3000);

    }


    @OnClick(R.id.image_Back)
    public void setImageBack(View view) {
        finish();
    }


}
