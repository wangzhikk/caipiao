package com.cp.shouye;

import java.util.List;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * abc on 2018/2/24.
 * 登录接口
 */

public class Data_index_dynamic extends ParentServerData {



    public List<DynamicBean> dynamic;

    public static void load(final HttpUiCallBack<Data_index_dynamic> httpUiCallBack){
        HttpToolAx.urlBase("index/dynamic")
                .send(Data_index_dynamic.class, httpUiCallBack);
    }

    public static class DynamicBean {
        /**
         * amount : 200.0
         * nickname : Give我
         * lottery : 北京28
         * bettingType : 双
         * timestamp : 1521713351
         */

        public double amount;
        public String nickname;
        public String lottery;
        public String bettingType;
        public long timestamp;
    }
}
