package com.cp.wode.fenxiang;

import com.google.gson.annotations.SerializedName;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 * 分享帮别人注册接口
 */

public class Data_share_link extends ParentServerData {

    public int uuid;
    public String url;

    public static void load(final HttpUiCallBack<Data_share_link> httpUiCallBack){
        HttpToolAx.urlBase("share/link")
                .send(Data_share_link.class, httpUiCallBack);
    }


}
