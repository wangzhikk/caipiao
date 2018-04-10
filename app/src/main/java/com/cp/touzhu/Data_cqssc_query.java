package com.cp.touzhu;

import com.cp.shouye.Data_room_queryGame;

import java.util.List;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 */

public class Data_cqssc_query extends ParentServerData {
    public PagingListBean pagingList;
    public static void load(Data_room_queryGame.YouXiEnum youXiEnum,int page, int pageSize, HttpUiCallBack<Data_cqssc_query> httpUiCallBack){
        HttpToolAx.urlBase(youXiEnum.method+"/query")
                .addQueryParams("page",page)
                .addQueryParams("pageSize",pageSize)
                .send(Data_cqssc_query.class,httpUiCallBack );
    }

    public static class PagingListBean {
        public int totalRecode;
        public int totalPage;
        public int currPage;
        public int pageSize;
        public List<Data_cqssc_top10.CQSSCBean> resultList;
    }
}
