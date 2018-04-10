package com.cp.wode.qianbao;

import java.util.List;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 * 登录接口
 */

public class Data_wallet_record extends ParentServerData {


    /**
     * code : 16
     * type : 1
     * start : 2017-08-31
     * end : 2017-08-31
     * pagingList : {"totalPage":4,"currPage":1,"pageSize":2,"resultList":[{"id":10,"base_uuid":12691809,"cwallet_type":2,"cwallet_type_name":"投注","cwallet_amount":-200,"cwallet_time":"2017-08-31 20:33:00","cwallet_remain":98450,"remark":"170831927109","spare":"重庆时时彩-创新版"},{"id":7,"base_uuid":12691809,"cwallet_type":1,"cwallet_type_name":"提现","cwallet_amount":-200,"cwallet_time":"2017-08-31 20:28:23","cwallet_remain":98650,"remark":"170831615000","spare":""}],"totalRecode":7}
     */

    public String type;
    public String start;
    public String end;
    public PagingListBean pagingList=new PagingListBean();

    /***
     *   0-充值，1-提现，2-投注，3-奖金，4-退款，5-分享收益
     * @param page
     * @param pageSize
     * @param type
     * @param start
     * @param end
     * @param httpUiCallBack
     */
    public static void load(int page, int pageSize,String type,String start,String end, final HttpUiCallBack<Data_wallet_record> httpUiCallBack){
        HttpToolAx.urlBase("wallet/record")
                .setPageNum(page)
                .setPageSize(pageSize)
                .addQueryParams("type",type)
                .addQueryParams("start",start)
                .addQueryParams("end",end)
                .send(Data_wallet_record.class, httpUiCallBack);
    }

    public static class PagingListBean {
        /**
         * totalPage : 4
         * currPage : 1
         * pageSize : 2
         * resultList : [{"id":10,"base_uuid":12691809,"cwallet_type":2,"cwallet_type_name":"投注","cwallet_amount":-200,"cwallet_time":"2017-08-31 20:33:00","cwallet_remain":98450,"remark":"170831927109","spare":"重庆时时彩-创新版"},{"id":7,"base_uuid":12691809,"cwallet_type":1,"cwallet_type_name":"提现","cwallet_amount":-200,"cwallet_time":"2017-08-31 20:28:23","cwallet_remain":98650,"remark":"170831615000","spare":""}]
         * totalRecode : 7
         */

        public int totalPage;
        public int currPage;
        public int pageSize;
        public int totalRecode;
        public List<QianBaoListBean> resultList;

        public static class QianBaoListBean {
            /**
             * id : 10
             * base_uuid : 12691809
             * cwallet_type : 2
             * cwallet_type_name : 投注
             * cwallet_amount : -200.0
             * cwallet_time : 2017-08-31 20:33:00
             * cwallet_remain : 98450.0
             * remark : 170831927109
             * spare : 重庆时时彩-创新版
             */

            public int id;
            public int base_uuid;
            public int cwallet_type;
            public String cwallet_type_name;
            public double cwallet_amount;
            public String cwallet_time;
            public double cwallet_remain;
            public String remark;
            public String spare;
        }
    }
}
