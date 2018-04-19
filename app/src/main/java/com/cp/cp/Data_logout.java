package com.cp.cp;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * abc on 2018/2/24.
 */

public class Data_logout extends ParentServerData {
    public static void load( HttpUiCallBack<Data_logout> httpUiCallBack){
        HttpToolAx.urlBase("logout")
                .send(Data_logout.class,httpUiCallBack );
    }

}
