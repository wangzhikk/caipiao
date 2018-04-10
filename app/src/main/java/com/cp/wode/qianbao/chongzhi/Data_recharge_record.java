package com.cp.wode.qianbao.chongzhi;

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

public class Data_recharge_record extends ParentServerData {

    public String start;
    public String end;
    public PagingListBean pagingList=new PagingListBean();

    public static void load(int page, int pageSize, final HttpUiCallBack<Data_recharge_record> httpUiCallBack){
        HttpToolAx.urlBase("recharge/record")
                .setPageNum(page)
                .setPageSize(pageSize)
                .send(Data_recharge_record.class, httpUiCallBack);
    }


    public static class PagingListBean {

        public int totalRecode;
        public int totalPage;
        public int currPage;
        public int pageSize;
        public List<ChongZhiListBean> resultList;

        public static class ChongZhiListBean {
            /**
             * id : 1
             * recharge_serial : 20170831234561278
             * recharge_time : 2017-08-31 15:02:00
             * recharge_amount : 100.0
             * recharge_state : 1
             * recharge_channel : WX
             */

            public int id;
            public String recharge_serial;
            public String recharge_time;
            public double recharge_amount;
            public int recharge_state;
            public String recharge_channel;

            public String getStateStr() {
                if(recharge_state==1){
                    return "充值成功";
                }else if(recharge_state==0){
                    return "未处理";
                }else if(recharge_state==-1){
                    return "充值失败";
                }
                return "未处理";
            }
        }
    }
}
