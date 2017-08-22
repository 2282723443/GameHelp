package com.wangwei.gamehelp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wangwei.gamehelp.model.MyUser;

import org.json.JSONObject;

import butterknife.BindView;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegistActivity extends BaseActivity implements View.OnClickListener {
    private Button btnSendMsg, btnSubmitCode;
    private EditText etPhoneNumber, etCode;
    private int i = 60;//倒计时

    @BindView(R.id.et_regist_username)
    EditText etUsername;
    @BindView(R.id.et_regist_password)
    EditText etPassword;
    @BindView(R.id.et_regist_repassword)
    EditText etRepassword;
    @BindView(R.id.rg_regist_gender)
    RadioGroup rgGender;

    @Override
    public int getLayoutID() {
        return R.layout.activity_regist;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTV.setText("注册");
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
                Log.e("asd", "event=" + event + "  result=" + result + " " +
                        " ---> result=-1 success , result=0 error");
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        // 提交验证码成功,调用注册接口，之后直接登录
                        //当号码来自短信注册页面时调用登录注册接口
                        //当号码来自绑定页面时调用绑定手机号码接口

//                        Toast.makeText(getApplicationContext(), "短信验证成功",
//                                Toast.LENGTH_SHORT).show();


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
                            Toast.makeText(RegistActivity.this, des, Toast.LENGTH_SHORT).show();
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
        String phoneNum = etPhoneNumber.getText().toString().trim();
        switch (v.getId()) {
            case R.id.btnSendMsg:
                if (TextUtils.isEmpty(phoneNum)) {
                    Toast.makeText(getApplicationContext(), "手机号码不能为空",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
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
                break;
            case R.id.btnSubmitCode:
                String code = etCode.getText().toString().trim();
                if (TextUtils.isEmpty(etUsername.getText().toString())||
                        TextUtils.isEmpty(etPassword.getText().toString())||
                        TextUtils.isEmpty(etRepassword.getText().toString())){

                    Toast.makeText(this, "请输入完整", Toast.LENGTH_SHORT).show();

                }else if (!(etPassword.getText().toString()).equals(etRepassword.getText().toString())){
                    Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                }
                //3)构建实体类(MyUser)对象
                final MyUser user = new MyUser();
                user.setUsername(etUsername.getText().toString());
                user.setPassword(etPassword.getText().toString());

                boolean gender = true;
                if (rgGender.getCheckedRadioButtonId() == R.id.rb_gender_girl) {
                    gender = false;
                }
                user.setGender(gender);
                user.setPhone(phoneNum);
                //进行注册
                user.signUp(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        //进行登录
                        user.login(RegistActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                //登录成功

                                SharedPreferences sharedPreferences = getSharedPreferences(
                                        "test", Activity.MODE_PRIVATE);
                                //实例化SharedPreferences.Editor对象
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                //用putString的方法保存数据
                                editor.putString("username", etUsername.getText().toString());
                                editor.putString("password", etPassword.getText().toString());
                                //提交当前数据
                                editor.apply();

                                //跳转界面，跳转到MainActivity
                                jumpTo(FirstActivity.class, true,true);
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                toastAndLog("登录失败", i, s);
                            }
                        });
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        switch (i) {
                            case 202:
                                toast("用户名重复");
                                break;
                            case 401:
                                Toast.makeText(RegistActivity.this, "您的手机已经注册过，请找回密码", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                toastAndLog("注册用户失败稍后重试", i, s);
                                break;
                        }

                    }
                });
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
    protected void onDestroy() {
        super.onDestroy();
        // 销毁回调监听接口
        SMSSDK.unregisterAllEventHandler();
    }
}
