package com.wangwei.gamehelp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wangwei.gamehelp.model.MyUser;

import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RetrieveActivity extends BaseActivity implements View.OnClickListener {
    private Button btnSendMsg, btnSubmitCode;
    private EditText etPhoneNumber, etCode;
    private int i = 60;//倒计时
    private String password;
    private String username;


    @Override
    public int getLayoutID() {
        return R.layout.activity_retrieve;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTV.setText("找回密码");
        showBackBtn();
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etCode = (EditText) findViewById(R.id.etCode);
        btnSendMsg = (Button) findViewById(R.id.btnSendMsg);
        btnSubmitCode = (Button) findViewById(R.id.btnSubmitCode);

        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);

            }
        };
        //注册短信回调
        SMSSDK.registerEventHandler(eventHandler);
        btnSendMsg.setOnClickListener(this);
        btnSubmitCode.setOnClickListener(this);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -1) {
                btnSendMsg.setText(i + " s");
            } else if (msg.what == -2) {
                btnSendMsg.setText("重新发送");
                btnSendMsg.setClickable(true);
                i = 60;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("asd", "event=" + event + "  result=" + result + "  ---> result=-1 success , result=0 error");
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        // 提交验证码成功,调用注册接口，之后直接登录
                        //当号码来自短信注册页面时调用登录注册接口
                        //当号码来自绑定页面时调用绑定手机号码接口

                        Toast.makeText(getApplicationContext(), "短信验证成功",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RetrieveActivity.this, PasswordActivity.class);
                        intent.putExtra("password",password);
                        intent.putExtra("username",username);
                        Log.v("tedu", "全局变量" + password);
                        startActivity(intent);

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "验证码已经发送",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                } else if (result == SMSSDK.RESULT_ERROR) {
                    try {
                        Throwable throwable = (Throwable) data;
                        throwable.printStackTrace();
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");//错误描述
                        int status = object.optInt("status");//错误代码
                        if (status > 0 && !TextUtils.isEmpty(des)) {
                            Log.e("asd", "des: " + des);
                            Toast.makeText(RetrieveActivity.this, des, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (Exception e) {
                        //do something
                    }
                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        final String phoneNum = etPhoneNumber.getText().toString();
            switch (v.getId()) {
                case R.id.btnSendMsg:
                    if (TextUtils.isEmpty(phoneNum)) {
                        Toast.makeText(getApplicationContext(), "手机号码不能为空",
                                Toast.LENGTH_SHORT).show();
                    }
                    BmobQuery<MyUser> query = new BmobQuery<MyUser>();
                    query.addWhereEqualTo("phone", phoneNum);
                    query.findObjects(this, new FindListener<MyUser>() {
                        @Override
                        public void onSuccess(List<MyUser> list) {
                            for (MyUser myUser : list) {

                                if (myUser.getPhone().equals(phoneNum)) {
                                    Log.v("tedu", "手机验证成功");
                                    SMSSDK.getVerificationCode("86", phoneNum);
                                    btnSendMsg.setClickable(false);
                                    //开始倒计
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            for (; i > 0; i--) {
                                                handler.sendEmptyMessage(-1);
                                                if (i <= 0) {
                                                    break;
                                                }
                                                try {
                                                    Thread.sleep(1000);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            handler.sendEmptyMessage(-2);
                                        }
                                    }).start();
                                }
                                myUser.getUsername();
                                Log.v("tedu", "ObjectId"+myUser.getUsername());
                                username = myUser.getUsername();
                                myUser.getObjectId();
                                password = myUser.getObjectId();
                                Log.v("tedu", "ObjectId"+myUser.getObjectId());


                            }
                            Log.v("tedu", "手机验证失败");
                            Toast.makeText(RetrieveActivity.this, "手机号码不存在",
                                    Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onError(int i, String s) {
                            Log.v("tedu", "c" + s);
                            Toast.makeText(RetrieveActivity.this, "手机号码不存在",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    break;
                case R.id.btnSubmitCode:
                    String code = etCode.getText().toString().trim();
                    if (TextUtils.isEmpty(phoneNum)) {
                        Toast.makeText(getApplicationContext(), "手机号码不能为空",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(code)) {
                        Toast.makeText(getApplicationContext(), "验证码不能为空",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    SMSSDK.submitVerificationCode("86", phoneNum, code);
                    break;
                default:
                    break;
            }

        }



    @Override
        protected void onDestroy () {
            super.onDestroy();
            // 销毁回调监听接口
            SMSSDK.unregisterAllEventHandler();
        }
    }
