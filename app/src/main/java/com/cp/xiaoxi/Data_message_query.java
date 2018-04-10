package com.cp.xiaoxi;

import java.util.List;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;



public class Data_message_query extends ParentServerData {

    /**
     * code : 16
     * type : 0
     * pagingList : {"totalRecode":1,"totalPage":1,"currPage":1,"pageSize":10,"resultList":[{"id":6,"message_title":"Test","message_time":"2017-09-17 20:00:00","read_state":0}]}
     */

    public int type;
    public PagingListBean pagingList;

    public static void load(int page, int pageSize, Object type, final HttpUiCallBack<Data_message_query> httpUiCallBack){
        HttpToolAx.urlBase("message/query")
                .addQueryParams("type",type)
                .setPageNum(page)
                .setPageSize(pageSize)
                .send(Data_message_query.class, httpUiCallBack);
    }


    public static class PagingListBean {
        /**
         * totalRecode : 1
         * totalPage : 1
         * currPage : 1
         * pageSize : 10
         * resultList : [{"id":6,"message_title":"Test","message_time":"2017-09-17 20:00:00","read_state":0}]
         */

        public int totalRecode;
        public int totalPage;
        public int currPage;
        public int pageSize;
        public List<Data_message_detail.MessageDetailBean> resultList;
    }


}
