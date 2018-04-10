package com.cp.wode.fenxiang;

import java.util.List;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 * 分享帮别人注册接口
 */

public class Data_share_bonus extends ParentServerData {

    /**
     * code : 16
     * pagingList : {"totalRecode":2,"totalPage":1,"currPage":1,"pageSize":10,"resultList":[{"id":760,"base_uuid":38254847,"cwallet_amount":836.22,"cwallet_time":"2018-03-20 15:23:56","remark":"abc123","spare":"2018-03-20"},{"id":759,"base_uuid":38254847,"cwallet_amount":20.4,"cwallet_time":"2018-03-20 15:23:56","remark":"thao333","spare":"2018-03-20"}]}
     */

    public PagingListBean pagingList;

    public static void load(int page, int pageSize , final HttpUiCallBack<Data_share_bonus> httpUiCallBack){
        HttpToolAx.urlBase("share/bonus")
                .setPageNum(page)
                .setPageSize(pageSize)
                .send(Data_share_bonus.class, httpUiCallBack);
    }


    public static class PagingListBean {
        /**
         * totalRecode : 2
         * totalPage : 1
         * currPage : 1
         * pageSize : 10
         * resultList : [{"id":760,"base_uuid":38254847,"cwallet_amount":836.22,"cwallet_time":"2018-03-20 15:23:56","remark":"abc123","spare":"2018-03-20"},{"id":759,"base_uuid":38254847,"cwallet_amount":20.4,"cwallet_time":"2018-03-20 15:23:56","remark":"thao333","spare":"2018-03-20"}]
         */

        public int totalRecode;
        public int totalPage;
        public int currPage;
        public int pageSize;
        public List<ShareBonusListBean> resultList;

        public static class ShareBonusListBean {
            /**
             * id : 760
             * base_uuid : 38254847
             * cwallet_amount : 836.22
             * cwallet_time : 2018-03-20 15:23:56
             * remark : abc123
             * spare : 2018-03-20
             */

            public int id;
            public int base_uuid;
            public double cwallet_amount;
            public String cwallet_time;
            public String remark;
            public String spare;
        }
    }
}
