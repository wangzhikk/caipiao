package com.cp.wode.fenxiang;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 * 分享帮别人注册接口
 */

public class Data_share_record extends ParentServerData {

    public PagingListBean pagingList;

    public static void load(int page, int pageSize , final HttpUiCallBack<Data_share_record> httpUiCallBack){
        HttpToolAx.urlBase("share/record")
                .setPageNum(page)
                .setPageSize(pageSize)
                .send(Data_share_record.class, httpUiCallBack);
    }


    public static class PagingListBean {
        /**
         * totalRecode : 4
         * pageSize : 10
         * totalPage : 1
         * currPage : 1
         * resultList : [{"base_username":"thao333","base_grade":0,"base_register_time":"2017-08-27 14:33:42"},{"base_username":"thao222","base_grade":0,"base_register_time":"2017-08-24 15:35:07"},{"base_username":"thao999","base_grade":0,"base_register_time":"2017-08-24 10:57:23"},{"base_username":"thao888","base_grade":0,"base_register_time":"2017-08-22 07:04:52"}]
         */

        public int totalRecode;
        public int pageSize;
        public int totalPage;
        public int currPage;
        public List<ShareListBean> resultList;

        public static class ShareListBean {
            /**
             * base_username : thao333
             * base_grade : 0
             * base_register_time : 2017-08-27 14:33:42
             */

            public String base_username;
            public int base_grade;
            public String base_register_time;
        }
    }
}
