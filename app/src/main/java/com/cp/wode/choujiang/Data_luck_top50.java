package com.cp.wode.choujiang;

import java.util.List;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 * 分享帮别人注册接口
 */

public class Data_luck_top50 extends ParentServerData {


    public static void load(final HttpUiCallBack<Data_luck_top50> httpUiCallBack){
        HttpToolAx.urlBase("luck/top50")

                .send(Data_luck_top50.class, httpUiCallBack);
    }

    public List<Data_luck_record.PagingListBean.ChouJiangJiLuListBean> top50;

}
