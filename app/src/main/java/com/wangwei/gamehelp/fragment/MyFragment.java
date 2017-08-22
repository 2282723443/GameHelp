package com.wangwei.gamehelp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wangwei.gamehelp.DataActivity;
import com.wangwei.gamehelp.R;
import com.wangwei.gamehelp.app.MyApp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {


    @BindView(R.id.gerenziliao_layout)
    LinearLayout gerenziliaoLayout;
    @BindView(R.id.zhanghuyue_layout)
    LinearLayout zhanghuyueLayout;
    @BindView(R.id.wodeshouru_layout)
    LinearLayout wodeshouruLayout;
    @BindView(R.id.shengqing_layout)
    LinearLayout shengqingLayout;
    @BindView(R.id.shezhi_layout)
    LinearLayout shezhilayout;
    @BindView(R.id.hezuojiaru_layout)
    LinearLayout hezuojiaruLayout;

    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.gerenziliao_layout)
    public void geren(View view) {

        Intent intent = new Intent(getActivity(), DataActivity.class);

        intent.putExtra("username", BmobUser.getCurrentUser(MyApp.CONTEXT).getUsername());

        startActivity(intent);

    }








    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
