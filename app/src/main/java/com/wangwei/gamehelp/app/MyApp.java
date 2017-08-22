package com.wangwei.gamehelp.app;

import com.mob.MobApplication;
import com.wangwei.gamehelp.config.Constant;

import cn.bmob.v3.Bmob;

/**
 * Created by tarena on 2017/7/14.
 */

public class MyApp extends MobApplication {

    public static MyApp CONTEXT;


    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this;

        Bmob.initialize(this, Constant.BMOBKEY);
    }
}
