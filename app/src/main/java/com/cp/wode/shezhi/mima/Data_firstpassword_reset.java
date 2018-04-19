package com.cp.wode.shezhi.mima;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * abc on 2018/2/24.
 * 通过手机号找回登录密码接口
 */

public class Data_firstpassword_reset extends ParentServerData {
    public static void load( String username,String phone,String captcha,String password,HttpUiCallBack<Data_firstpassword_reset> httpUiCallBack){
        HttpToolAx.urlBase("firstpassword/reset")
                .addQueryParams("username",username)
                .addQueryParams("country",86)
                .addQueryParams("phone",phone)
                .addQueryParams("captcha",captcha)
                .addQueryParams("password",password)
                .send(Data_firstpassword_reset.class,httpUiCallBack );
    }

}
