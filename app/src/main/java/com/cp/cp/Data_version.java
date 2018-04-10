package com.cp.cp;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.update.Version;

/**
 * Created by wz on 2018/2/24.
 */

public class Data_version extends ParentServerData {
    public Version appVersion;
    public static void load( HttpUiCallBack<Data_version> httpUiCallBack){
        HttpToolAx.urlBase("version")
                .send(Data_version.class,httpUiCallBack );
    }

}
