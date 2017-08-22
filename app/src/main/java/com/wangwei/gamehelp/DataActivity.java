package com.wangwei.gamehelp;

import android.os.Bundle;
import android.util.Log;

public class DataActivity extends BaseActivity {
    String username;
    @Override
    public int getLayoutID() {
        return R.layout.activity_data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getIntent().getStringExtra("username");
        Log.v("tedu","username===" + username);
        titleTV.setText("个人资料");
        showBackBtn();
    }
}
