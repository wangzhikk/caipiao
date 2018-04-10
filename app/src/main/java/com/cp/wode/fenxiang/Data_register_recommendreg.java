package com.cp.wode.fenxiang;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 * 分享帮别人注册接口
 */

public class Data_register_recommendreg extends ParentServerData {

    public static void load(String username, String password, final HttpUiCallBack<Data_register_recommendreg> httpUiCallBack){
        HttpToolAx.urlBase("register/recommendreg")
                .addQueryParams("username",username)
                .addQueryParams("password",password)
                .send(Data_register_recommendreg.class, httpUiCallBack);
    }


}
