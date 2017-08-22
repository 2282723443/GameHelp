package com.wangwei.gamehelp.adapter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wangwei.gamehelp.NewsWebViewActivity;
import com.wangwei.gamehelp.model.NewsMessage;

/**
 * Created by tarena on 2017/7/22.
 */

public class ItemClickLister extends Fragment {
    public void  Click(ListView listView , final NewsAdapter adapter , final String webhttp) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NewsMessage.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean;
                contentlistBean = adapter.getItem(position-1);
                Intent intent = new Intent(getActivity(), NewsWebViewActivity.class);
                String webhttp = contentlistBean.getLink();
                intent.putExtra("webhttp",webhttp);
                Log.v("tedu", "contentlistBean传出的是" + webhttp);
                startActivity(intent);

            }
        });
    }
}
