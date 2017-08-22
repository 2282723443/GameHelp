package com.wangwei.gamehelp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wangwei.gamehelp.fragment.DnfFragment;
import com.wangwei.gamehelp.fragment.ElseFragment;
import com.wangwei.gamehelp.fragment.LolFragment;
import com.wangwei.gamehelp.fragment.MoFragment;
import com.wangwei.gamehelp.fragment.NewFragment;
import com.wangwei.gamehelp.fragment.NongFragment;
import com.wangwei.gamehelp.fragment.ShouFragment;

/**
 * Created by tarena on 2017/7/17.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"最新", "英雄联盟", "王者荣耀","守望先锋","魔兽","DNF","其他"};

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new LolFragment();
        } else if (position == 2) {
            return new NongFragment();
        }else if (position==3){
            return new ShouFragment();
        } else if (position == 4) {
            return new MoFragment();
        } else if (position == 5) {
            return new DnfFragment();
        } else if (position == 6) {
            return new ElseFragment();
        }
        return new NewFragment();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
