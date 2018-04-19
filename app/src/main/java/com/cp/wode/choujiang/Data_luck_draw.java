package com.cp.wode.choujiang;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * abc on 2018/2/24.
 * 分享帮别人注册接口
 */

public class Data_luck_draw extends ParentServerData {
    public String amount;
    public int grade;
    public static void load(final HttpUiCallBack<Data_luck_draw> httpUiCallBack){
        HttpToolAx.urlBase("luck/draw")
                .send(Data_luck_draw.class, httpUiCallBack);
    }

}
