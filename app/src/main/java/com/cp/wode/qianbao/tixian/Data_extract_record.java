package com.cp.wode.qianbao.tixian;

import com.cp.R;

import java.util.List;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.common.UiTool;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 * 登录接口
 */

public class Data_extract_record extends ParentServerData {


    /**
     * code : 16
     * start : 2017-08-31
     * end : 2017-08-31
     * pagingList : {"totalRecode":1,"totalPage":1,"currPage":1,"pageSize":10,"resultList":[{"id":1,"extract_serial":"170831625620","base_uuid":"12691809","extract_apply_time":"2017-08-31 17:20:02","extract_amount":200,"extract_amount_fact":200,"extract_fee":0,"extract_name":"tanghao","extract_bank_name":"中国建设银行","extract_bank_account":"62222222222222222222","extract_state":0,"remark":null}]}
     */

    public String start;
    public String end;
    public PagingListBean pagingList=new PagingListBean();

    public static void load(int page, int pageSize, final HttpUiCallBack<Data_extract_record> httpUiCallBack){
        HttpToolAx.urlBase("extract/record")
                .setPageNum(page)
                .setPageSize(pageSize)
                .send(Data_extract_record.class, httpUiCallBack);
    }


    public static class PagingListBean {
        /**
         * totalRecode : 1
         * totalPage : 1
         * currPage : 1
         * pageSize : 10
         * resultList : [{"id":1,"extract_serial":"170831625620","base_uuid":"12691809","extract_apply_time":"2017-08-31 17:20:02","extract_amount":200,"extract_amount_fact":200,"extract_fee":0,"extract_name":"tanghao","extract_bank_name":"中国建设银行","extract_bank_account":"62222222222222222222","extract_state":0,"remark":null}]
         */

        public int totalRecode;
        public int totalPage;
        public int currPage;
        public int pageSize;
        public List<TiXianListBean> resultList;

        public static class TiXianListBean {
            /**
             * id : 1
             * extract_serial : 170831625620
             * base_uuid : 12691809
             * extract_apply_time : 2017-08-31 17:20:02
             * extract_amount : 200.0
             * extract_amount_fact : 200.0
             * extract_fee : 0.0
             * extract_name : tanghao
             * extract_bank_name : 中国建设银行
             * extract_bank_account : 62222222222222222222
             * extract_state : 0
             * remark : null
             */

            public int id;
            public String extract_serial;
            public String base_uuid;
            public String extract_apply_time;
            public double extract_amount;
            public double extract_amount_fact;
            public double extract_fee;
            public String extract_name;
            public String extract_bank_name;
            public String extract_bank_account;
            public int extract_state;
            public Object remark;

            public String getStateStr() {
                if(extract_state==1){
                    return "提现成功";
                }else if(extract_state==0){
                    return "未处理";
                }else if(extract_state==-1){
                    return "提现失败";
                }
                return "";
            }
        }
    }
}
