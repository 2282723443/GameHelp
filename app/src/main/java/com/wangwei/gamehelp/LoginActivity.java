package com.wangwei.gamehelp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wangwei.gamehelp.model.MyUser;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_phone_login)
    TextView tvPhoneLogin;
    @BindView(R.id.et_login_username)
    EditText etLoginUsername;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.tv_retrieve_password)
    TextView tvRetrievePassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tv_regist)
    TextView tvRegist;

    @Override
    public int getLayoutID() {
        return R.layout.activity_login;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTV.setText("登录");
    }

    private void login(String username, String password) {
        MyUser user = new MyUser();
        user.setUsername(username);
        user.setPassword(password);
        user.login(this, new SaveListener() {
            @Override
            public void onSuccess() {

                SharedPreferences sharedPreferences= getSharedPreferences("test",
                        Activity.MODE_PRIVATE);
                //实例化SharedPreferences.Editor对象
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //用putString的方法保存数据
                editor.putString("username",etLoginUsername.getText().toString());
                editor.putString("password", etLoginPassword.getText().toString());

                //提交当前数据
                editor.apply();

                jumpTo(FirstActivity.class,false,true);
                finish();
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                //登录失败


                //根据不同的arg0,尽量给出详细的提示
                switch (arg0) {
                    case 101:
                        toast("用户名或密码错误");
                        break;

                    default:
                        toastAndLog("登录失败", arg0, arg1);
                        break;
                }

            }
        });
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        if (isEmpty(etLoginUsername, etLoginPassword)) {
            return;
        }
        login(etLoginUsername.getText().toString(),etLoginPassword.getText().toString());
    }



    @OnClick(R.id.tv_phone_login)
    public void phoneLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_regist)
    public void regist(View view) {
        Intent intent = new Intent(this, RegistActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_retrieve_password)
    public void retrieve(View view) {
        Intent intent = new Intent(this, RetrieveActivity.class);
        startActivity(intent);
    }
}
