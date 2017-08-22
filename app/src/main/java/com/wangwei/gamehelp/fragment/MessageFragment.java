package com.wangwei.gamehelp.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.wangwei.gamehelp.R;
import com.wangwei.gamehelp.SeekActivity;
import com.wangwei.gamehelp.adapter.MyFragmentPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends BaseFragment {



    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private TabLayout.Tab four;
    private TabLayout.Tab five;
    private TabLayout.Tab six;
    private TabLayout.Tab seven;



    @Override
    public View createMyView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_message, container, false);
        initViews(view);

        return view;
    }

    private void initViews(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager_tab_layout);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager());
     //mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(myFragmentPagerAdapter);

        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
         mTabLayout.setupWithViewPager(mViewPager);


        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
        three = mTabLayout.getTabAt(2);
        four = mTabLayout.getTabAt(3);
        five = mTabLayout.getTabAt(4);
        six = mTabLayout.getTabAt(5);
        seven = mTabLayout.getTabAt(6);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        baseActivity.jumpTo(SeekActivity.class, false, false);

        return super.onOptionsItemSelected(item);
    }


}
