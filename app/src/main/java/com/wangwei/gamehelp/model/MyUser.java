package com.wangwei.gamehelp.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by tarena on 2017/7/14.
 */

public class MyUser extends BmobUser {
    Boolean gender;
    String phone;

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
