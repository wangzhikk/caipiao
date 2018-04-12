package com.cp.wode.qianbao.tixian;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 * 登录接口
 */

public class Data_extract_query extends ParentServerData {


    /**
     * min : 200
     * max : 10000000
     * freeTimes : 5
     * feeRate : 0.01
     * remain : 0.0
     */

    public String min;
    public String max;
    public String freeTimes;
    public double feeRate;
    public double remain;
    public String timesRemain;
    public  double needBetting;
    public String time;
    public static void load(final HttpUiCallBack<Data_extract_query> httpUiCallBack){
        HttpToolAx.urlBase("extract/query")
                .send(Data_extract_query.class, httpUiCallBack);
    }




}
