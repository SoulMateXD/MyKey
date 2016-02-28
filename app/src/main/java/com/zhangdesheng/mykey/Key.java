package com.zhangdesheng.mykey;

/**
 * Created by Administrator on 2016/2/28.
 */
public class Key {
    String name;
    String account;
    String password;
    String remark;

    Key(String name , String account, String password, String remark){
        this.name = name;
        this.account = account;
        this.password = password;
        this.remark = remark;
    }

    public String getName(){
        return name;
    }

    public String getAccount(){
        return account;
    }

    public String getPassword(){
        return password;
    }

    public String getRemark(){
        return remark;
    }
}
