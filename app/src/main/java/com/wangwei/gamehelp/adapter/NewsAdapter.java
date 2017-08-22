package com.wangwei.gamehelp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangwei.gamehelp.R;
import com.wangwei.gamehelp.model.NewsMessage;
import com.wangwei.gamehelp.util.HttpUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tarena on 2017/7/18.
 */

public class NewsAdapter extends MyBaseAdapter<NewsMessage.ShowapiResBodyBean.PagebeanBean.ContentlistBean> {
    public NewsAdapter(Context context, List<NewsMessage.ShowapiResBodyBean.PagebeanBean.ContentlistBean> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder vh;
        if (view == null) {
            view = inflater.inflate(R.layout.content_title_layout, parent, false);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        NewsMessage.ShowapiResBodyBean.PagebeanBean.ContentlistBean
                news = getItem(position);

//
       if (!news.getImageurls().isEmpty()){
           HttpUtil.loadImage(news.getImageurls().get(0).getUrl(),vh.imageUrl);

           vh.tvTitle.setText(news.getTitle());
           vh.tvDesc.setText(news.getDesc());
           vh.tvTime.setText(news.getPubDate());
           return view;
//       }else {
//           HttpUtil.loadImage(news.getImageurls().get(0).getUrl(),vh.imageUrl);
       }
       vh.tvTitle.setText(news.getTitle());
        vh.tvDesc.setText(news.getDesc());
        vh.tvTime.setText(news.getPubDate());
        vh.imageUrl.setImageResource(R.drawable.ic_content_meitu);
        return view;
    }

    public class ViewHolder {
        @BindView(R.id.tv_Text_Title)
        TextView tvTitle;
        @BindView(R.id.tv_Text_desc)
        TextView tvDesc;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.image_url)
        ImageView imageUrl;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
