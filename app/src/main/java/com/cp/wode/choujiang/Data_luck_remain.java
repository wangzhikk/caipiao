package com.cp.wode.choujiang;

import com.cp.cp.Data_login_validate;
import com.cp.im.IMTool;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.common.LogTool;
import utils.wzutils.http.HttpUiCallBack;

/**
 * abc on 2018/2/24.
 * 分享帮别人注册接口
 */

public class Data_luck_remain extends ParentServerData {


    public int remain;

    public static void load(final HttpUiCallBack<Data_luck_remain> httpUiCallBack){
        HttpToolAx.urlBase("luck/remain")
                .send(Data_luck_remain.class, httpUiCallBack);
    }

}
