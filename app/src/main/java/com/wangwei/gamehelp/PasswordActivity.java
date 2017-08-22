package com.wangwei.gamehelp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wangwei.gamehelp.model.MyUser;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.listener.UpdateListener;

public class PasswordActivity extends BaseActivity {
    String password;
    String username;
    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.btnSubmitCodeabc)
    Button btnSubmitCode;

    @Override
    public int getLayoutID() {
        return R.layout.activity_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTV.setText("找回密码");
        showBackBtn();
        password = (String) getIntent().getSerializableExtra("password");
        username = (String) getIntent().getSerializableExtra("username");
        Log.v("tedu", "传过来的变量ID" + password);
        Log.v("tedu", "传过来的用户名" + username);

    }

    @OnClick(R.id.btnSubmitCodeabc)
    public void setPassword(View view) {
        Log.v("tedu", "传过来的变量ID" + password);
        Log.v("tedu", "传过来的用户名" + username);
        MyUser user = new MyUser();
        user.setValue("password",etPhoneNumber.getText().toString());
        user.update(PasswordActivity.this,password, new UpdateListener() {
           @Override
           public void onSuccess() {
               Log.v("tedu", "密码更新成功" );
               Toast.makeText(PasswordActivity.this, "密码更改成功!!!",
                       Toast.LENGTH_SHORT).show();
               SharedPreferences sharedPreferences= getSharedPreferences("test",
                       Activity.MODE_PRIVATE);

               //实例化SharedPreferences.Editor对象
               SharedPreferences.Editor editor = sharedPreferences.edit();
               //用putString的方法保存数据
               editor.putString("username",username);

               editor.putString("password", password);
               editor.apply();

               Intent intent = new Intent(PasswordActivity.this, FirstActivity.class);
               startActivity(intent);
//               Log.v("tedu", "密码是" + password);
//               Log.v("tedu", "账号是" + username);
           }

           @Override
           public void onFailure(int i, String s) {

           }
       });
    }

}
