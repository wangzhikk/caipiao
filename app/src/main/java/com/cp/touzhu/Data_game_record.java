package com.cp.touzhu;

import java.util.List;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * abc on 2018/2/24.
 */

public class Data_game_record extends ParentServerData {

    /**
     * code : 16
     * lottery : CQSSC
     * start : 2071-09-01
     * end : 2017-09-17
     * pagingList : {"totalPage":1,"currPage":1,"pageSize":10,"resultList":[{"order_serial":"K8NG60","base_uuid":12691809,"order_lottery":"CQSSC","order_issue":170910015,"order_betting_type":"大","order_time":"2017-09-10 01:11:11","order_amount":10,"order_lottery_num":null,"order_lottery_type":null,"order_state":0,"order_bonus":0}],"totalRecode":1}
     */

    public String lottery;
    public String start;
    public String end;
    public GameRecordListBean pagingList;

    public static void load(int page,int pageSize,String lottery,String roomNo, String start,String end,HttpUiCallBack<Data_game_record> httpUiCallBack){
        HttpToolAx.urlBase("game/record")
                .setPageNum(page)
                .setPageSize(pageSize)
                .addQueryParams("lottery",lottery)
                .addQueryParams("roomNo",roomNo)
                .addQueryParams("start",start)
                .addQueryParams("end",end)
                .send(Data_game_record.class,httpUiCallBack );
    }


    public static class GameRecordListBean {
        /**
         * totalPage : 1
         * currPage : 1
         * pageSize : 10
         * resultList : [{"order_serial":"K8NG60","base_uuid":12691809,"order_lottery":"CQSSC","order_issue":170910015,"order_betting_type":"大","order_time":"2017-09-10 01:11:11","order_amount":10,"order_lottery_num":null,"order_lottery_type":null,"order_state":0,"order_bonus":0}]
         * totalRecode : 1
         */

        public int totalPage;
        public int currPage;
        public int pageSize;
        public int totalRecode;
        public List<GameRecordBean> resultList;

        public static class GameRecordBean {
            /**
             * order_serial : K8NG60
             * base_uuid : 12691809
             * order_lottery : CQSSC
             * order_issue : 170910015
             * order_betting_type : 大
             * order_time : 2017-09-10 01:11:11
             * order_amount : 10.0
             * order_lottery_num : null
             * order_lottery_type : null
             * order_state : 0
             * order_bonus : 0.0
             */

            public String order_serial;
            public int base_uuid;
            public String order_lottery;
            public String lottery_name;
            public int order_issue;
            public String order_betting_type;
            public String order_time;
            public double order_amount;
            public String order_lottery_num;
            public Object order_lottery_type;
            public int order_state;
            public double order_bonus;
        }
    }
}
