package com.cp.wode.choujiang;

import java.util.List;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 * 分享帮别人注册接口
 */

public class Data_luck_record extends ParentServerData {


    public String start;
    public String end;
    public PagingListBean pagingList;

    public static void load(int page,int pageSize,final HttpUiCallBack<Data_luck_record> httpUiCallBack){
        HttpToolAx.urlBase("luck/record")
                .setPageNum(page)
                .setPageSize(pageSize)
                .send(Data_luck_record.class, httpUiCallBack);
    }


    public static class PagingListBean {
        public int totalRecode;
        public int totalPage;
        public int currPage;
        public int pageSize;
        public List<ChouJiangJiLuListBean> resultList;
        public static class ChouJiangJiLuListBean {
            public String base_uuid;
            public  String base_nickname;
            public String luck_time;
            public String luck_amount;
        }
    }
}
