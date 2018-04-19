package com.cp.wode.shezhi.mima;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * abc on 2018/2/24.
 * 修改登录密码接口
 */

public class Data_firstpassword_modify extends ParentServerData {
    public static void load( String oldFirstPassword,String newFirstPassword,HttpUiCallBack<Data_firstpassword_modify> httpUiCallBack){
        HttpToolAx.urlBase("firstpassword/modify")
                .addQueryParams("oldFirstPassword",oldFirstPassword)
                .addQueryParams("newFirstPassword",newFirstPassword)
                .send(Data_firstpassword_modify.class,httpUiCallBack );
    }

}
