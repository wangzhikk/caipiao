package com.cp.dengluzhuce;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * abc on 2018/2/24.
 */

public class Data_register_appreg extends ParentServerData {

    public static void load( String username,String password,String ruuid, HttpUiCallBack<Data_register_appreg> httpUiCallBack){
        HttpToolAx.urlBase("register/appreg")
                .addQueryParams("username",username)
                .addQueryParams("password",password)
                .addQueryParams("ruuid",ruuid)
                .send(Data_register_appreg.class,httpUiCallBack );
    }



}
