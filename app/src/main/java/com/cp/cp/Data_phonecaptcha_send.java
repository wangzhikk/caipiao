package com.cp.cp;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.common.StringTool;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 */

public class Data_phonecaptcha_send extends ParentServerData {

    public long time;
    public static void load( String username,String phone,HttpUiCallBack<Data_phonecaptcha_send> httpUiCallBack){
        String method="";
        if(StringTool.notEmpty(username)){//找回登陆密码
            method="phonecaptcha/sendforgotpwd";
        }else if(StringTool.notEmpty(phone)){//更换或者绑定手机号
            method="phonecaptcha/send";
        }else {//啥都没填，
            method="phonecaptcha/sendbytoken";
        }
        HttpToolAx.urlBase(method)
                .addQueryParams("username",username)
                .addQueryParams("country","86")
                .addQueryParams("phone",phone)
                .send(Data_phonecaptcha_send.class,httpUiCallBack );
    }

}
