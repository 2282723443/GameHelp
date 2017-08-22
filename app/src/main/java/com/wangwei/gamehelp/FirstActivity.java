package com.wangwei.gamehelp;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wangwei.gamehelp.fragment.GameFragment;
import com.wangwei.gamehelp.fragment.MessageFragment;
import com.wangwei.gamehelp.fragment.MyFragment;
import com.wangwei.gamehelp.fragment.PlayFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FirstActivity extends BaseActivity {

    @BindView(R.id.rg_main_footer)
    RadioGroup mainRG;
    //声明fragment集合  注意导V4包
    List<Fragment> fragments;
    //声明viewpager
    @BindView(R.id.fragment_container)
    ViewPager vp;
    //声明pagerAdapter
    PagerAdapter adapter;
    private FragmentManager mFragmentManager;;


    @Override
    public int getLayoutID() {
        return R.layout.activity_first;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTV.setText("看资讯");
        setAdapter();
        setListenera();
    }


    private void setAdapter() {
        //初始化fragment集合
        fragments = new ArrayList<Fragment>();
        //给fragment集合当中添加数据
        fragments.add(new MessageFragment());
        fragments.add(new PlayFragment());
        fragments.add(new GameFragment());
        fragments.add(new MyFragment());
        //初始化适配器
        adapter = new MyAdapter(getSupportFragmentManager());
        vp.setOffscreenPageLimit(4);
        vp.setAdapter(adapter);

    }
    //创建自定义myAdapter
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        //拿到position位置的fragment对象 作为方法返回值
        @Override
        public Fragment getItem(int position) {
            // TODO Auto-generated method stub
            return fragments.get(position);
        }
        //fragment的数量
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return fragments.size();
        }
    }
    private void setListenera() {
        mainRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_main_footer_home:
                        vp.setCurrentItem(0);
                        break;
                    case R.id.rb_main_footer_tuan:
                        vp.setCurrentItem(1);
                        break;
                    case R.id.rb_main_footer_find:
                        vp.setCurrentItem(2);
                        break;
                    case R.id.rb_main_footer_my:
                        vp.setCurrentItem(3);
                        break;

                }
                RadioButton rb = (RadioButton) mainRG.getChildAt(vp.getCurrentItem());

                titleTV.setText(rb.getText());
            }
        });

    }




}
