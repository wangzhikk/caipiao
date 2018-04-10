package com.cp.wode.fenxiang;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 * 分享帮别人注册接口
 */

public class Data_share_info extends ParentServerData {



    public double shareBonus;
    public int recommendTotal;
    public static void load( final HttpUiCallBack<Data_share_info> httpUiCallBack){
        HttpToolAx.urlBase("share/info")
                .send(Data_share_info.class, httpUiCallBack);
    }



}
