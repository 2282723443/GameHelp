package com.wangwei.gamehelp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wangwei.gamehelp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pjy on 2017/6/15.
 */

public class FragmentD extends BaseFragment{

    @BindView(R.id.btn_fragment_skip)
    Button btnSkip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d,container,false);
        ButterKnife.bind(this,view);
        skip(btnSkip);
        return view;
    }

    @Override
    public View createMyView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }


}
