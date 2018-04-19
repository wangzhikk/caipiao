package com.cp.wode.qianbao.tixian;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * abc on 2018/2/24.
 * 登录接口
 */

public class Data_extract_apply extends ParentServerData {


    public static void load(String amount,String password,final HttpUiCallBack<Data_extract_apply> httpUiCallBack){
        HttpToolAx.urlBase("extract/apply")
                .addQueryParams("amount",amount)
                .addQueryParams("password",password)
                .send(Data_extract_apply.class, httpUiCallBack);
    }



}
