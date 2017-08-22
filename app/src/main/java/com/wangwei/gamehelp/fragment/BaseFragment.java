package com.wangwei.gamehelp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangwei.gamehelp.BaseActivity;
import com.wangwei.gamehelp.LoginActivity;

import butterknife.ButterKnife;

/**
 * Created by pjy on 2017/6/15.
 */

public abstract class BaseFragment extends Fragment {
    BaseActivity baseActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = createMyView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        baseActivity = (BaseActivity) getActivity();
        //如果需要在fragment页面的ActionBar里面显示按钮的话需要调用下面代码
        setHasOptionsMenu(true);
        return view;
    }
    public void init(){

    }

    public abstract View createMyView(LayoutInflater inflater,
                                      ViewGroup container,
                                      Bundle savedInstanceState);

    public void skip(View view){

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }



}
