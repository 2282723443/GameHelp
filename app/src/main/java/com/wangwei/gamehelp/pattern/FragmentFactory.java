package com.wangwei.gamehelp.pattern;

import android.support.v4.app.Fragment;

import com.wangwei.gamehelp.R;
import com.wangwei.gamehelp.fragment.GameFragment;
import com.wangwei.gamehelp.fragment.MessageFragment;
import com.wangwei.gamehelp.fragment.MyFragment;
import com.wangwei.gamehelp.fragment.PlayFragment;

/**
 * Created by tarena on 2017/7/17.
 */

public class FragmentFactory {
    public static Fragment getInstanceByIndex(int index) {
        Fragment fragment = null;
        switch (index) {
            case R.id.rb_main_footer_home:
                fragment = new MessageFragment();
                break;
            case R.id.rb_main_footer_tuan:
                fragment = new PlayFragment();
                break;
            case R.id.rb_main_footer_find:
                fragment = new GameFragment();
                break;
            case R.id.rb_main_footer_my:
                fragment = new MyFragment();
                break;
        }
        return fragment;
    }
}
