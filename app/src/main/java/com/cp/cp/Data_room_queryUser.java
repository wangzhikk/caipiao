package com.cp.cp;

import com.cp.shouye.Data_room_queryGame;

import java.util.List;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * abc on 2018/2/24.
 */

public class Data_room_queryUser extends ParentServerData {

    /**
     * code : 16
     * level : 1
     * roomList : [{"no":"CQSSC_CX-1-001","total":602},{"no":"CQSSC_CX-1-002","total":618},{"no":"CQSSC_CX-1-003","total":829},{"no":"CQSSC_CX-1-004","total":814}]
     */

    public String level;
    public List<Data_room_queryGame.InfoBean.RoomLevelsBean.RoomListBean> roomList;

    /***
     * issue	是	String	投注期号
     roomNo	是	String	房间号
     type	是	String	投注类型，例：大，小
     amount	是	String	投注金额，根据房间级别进行限制
     */
    public static void load(String game,String level, HttpUiCallBack<Data_room_queryUser> httpUiCallBack){
        HttpToolAx.urlBase("room/queryUser")
                .addQueryParams("game",game)
                .addQueryParams("level",level)
                .send(Data_room_queryUser.class,httpUiCallBack );
    }



}
