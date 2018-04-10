package com.cp.wode.shezhi.phone;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 * 修改登录密码接口
 */

public class Data_phone_replace extends ParentServerData {
    public static void load(String phone, String captcha,String newCaptcha,HttpUiCallBack<Data_phone_replace> httpUiCallBack){
        HttpToolAx.urlBase("phone/replace")
                .addQueryParams("country",86)
                .addQueryParams("phone",phone)
                .addQueryParams("captcha",captcha)
                .addQueryParams("newCaptcha",newCaptcha)
                .send(Data_phone_replace.class,httpUiCallBack );
    }

}
