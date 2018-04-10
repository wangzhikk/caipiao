package com.cp.cp;

import com.cp.shouye.Data_room_queryGame;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 */

public class Data_cqssc_betting extends ParentServerData {

    /***
     * issue	是	String	投注期号
     roomNo	是	String	房间号
     type	是	String	投注类型，例：大，小
     amount	是	String	投注金额，根据房间级别进行限制
     */
    public static void load(Data_room_queryGame.YouXiEnum youXiEnum, String issue, String roomNo, String type, String amount, HttpUiCallBack<Data_cqssc_betting> httpUiCallBack){
        HttpToolAx.urlBase(youXiEnum.method+"/betting")
                .addQueryParams("issue",issue)
                .addQueryParams("roomNo",roomNo)
                .addQueryParams("type",type)
                .addQueryParams("amount",amount)
                .send(Data_cqssc_betting.class,httpUiCallBack );
    }

}
