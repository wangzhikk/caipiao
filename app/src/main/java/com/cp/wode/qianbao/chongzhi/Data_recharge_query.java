package com.cp.wode.qianbao.chongzhi;

import java.io.Serializable;
import java.util.List;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.common.StringTool;
import utils.wzutils.http.HttpUiCallBack;

/**
 * abc on 2018/2/24.
 * 登录接口
 */

public class Data_recharge_query extends ParentServerData {

    public List<OfflineBean> offline;
    public List<OnLineBean> online;
    public String time;
    public static void load( final HttpUiCallBack<Data_recharge_query> httpUiCallBack){
        HttpToolAx.urlBase("recharge/query")
                .send(Data_recharge_query.class, httpUiCallBack);
    }

    public OfflineBean getOfflineBeanAlipay(){
        if(offline!=null){
            for(OfflineBean offlineBean:offline){
                if(offlineBean.isAlipay())return offlineBean;
            }
        }
        return new OfflineBean();
    }

    public OfflineBean getOfflineBeanWeixin(){
        if(offline!=null){
            for(OfflineBean offlineBean:offline){
                if(offlineBean.isWeixin())return offlineBean;
            }
        }
        return new OfflineBean();
    }

    public OfflineBean getOfflineBeanBank(){
        if(offline!=null){
            for(OfflineBean offlineBean:offline){
                if(offlineBean.isBank())return offlineBean;
            }
        }
        return new OfflineBean();
    }

    public static class OnLineBean implements Serializable{
        public String image;
        public double min;
        public double max;
        public String name;
        public int type;
    }
    public static class OfflineBean implements Serializable{
        /**
         * recharge_type : Bank
         * recharge_name : 李三四
         * recharge_bankname : 中国农业银行
         * recharge_account : 6222084666229909137
         * recharge_min : 100.0
         * recharge_max : 50000.0
         */

        public String recharge_type;

        public String getRecharge_name() {
            return StringTool.isEmpty(recharge_name)?"-":recharge_name;
        }

        public String recharge_name;
        public String recharge_bankname;
        public String recharge_account;
        public double recharge_min;
        public double recharge_max;


        public boolean isBank(){
            return "Bank".equals(recharge_type);
        }
        public boolean isAlipay(){
            return "Alipay".equals(recharge_type);
        }
        public boolean isWeixin(){
            return "Weixin".equals(recharge_type);
        }

    }
}
