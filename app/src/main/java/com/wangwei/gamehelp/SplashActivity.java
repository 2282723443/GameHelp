package com.wangwei.gamehelp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.wangwei.gamehelp.model.MyUser;
import com.wangwei.gamehelp.util.SPUtil;

import cn.bmob.v3.listener.SaveListener;

public class SplashActivity extends Activity {

    SPUtil spUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        spUtil = new SPUtil(this);
        //界面停留几秒钟
        autoLogin();
    }
    private void autoLogin() {
        SharedPreferences sharedPreferences= getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        String username=sharedPreferences.getString("username","");
        String password=sharedPreferences.getString("password","");

        if (username!=null&&password!=null){


            MyUser user= new MyUser();
            user.setUsername(username);

            user.setPassword(password);

            user.login(this, new SaveListener() {

                @Override
                public void onSuccess() {

                    //跳转至 MainActivity
                    Intent intent = new Intent(SplashActivity.this, FirstActivity.class);
                    startActivity(intent);
                    //结束当前的 Activity
                    SplashActivity.this.finish();


                }

                @Override
                public void onFailure(int arg0, String arg1) {
                    //登录失败
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //读取偏好设置文件中的值
                            //根据是否是第一次使用进行相应的界面跳转
                            Intent intent;

                            if(spUtil.isFirst()){
                                //向新手指导页跳转
                                intent = new Intent(SplashActivity.this,GuideActivity.class);
                                spUtil.setFirst(false);
                            }else{
                                //向主页面跳转
                                intent = new Intent(SplashActivity.this,LoginActivity.class);
                            }

                            startActivity(intent);
                            finish();
                        }
                    },1500);




                }
            });
        }else{//从来没有登录过
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //读取偏好设置文件中的值
                    //根据是否是第一次使用进行相应的界面跳转
                    Intent intent;

                    if(spUtil.isFirst()){
                        //向新手指导页跳转
                        intent = new Intent(SplashActivity.this,GuideActivity.class);
                        spUtil.setFirst(false);
                    }else{
                        //向主页面跳转
                        intent = new Intent(SplashActivity.this,LoginActivity.class);
                    }

                    startActivity(intent);
                    finish();
                }
            },1500);
        }

    }
}
